package net.cherryflavor.api.spigot.voting;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitTask;

import net.cherryflavor.api.exceptions.InvalidConfigValueException;
import net.cherryflavor.api.spigot.ServerAPI;
import net.cherryflavor.api.spigot.interfaces.Lobby;
import net.cherryflavor.api.spigot.items.CherryItem;
import net.cherryflavor.api.spigot.player.OnlinePlayer;
import net.cherryflavor.api.tools.Grab;
import net.cherryflavor.api.tools.TextFormat;

public class VoteSession {

    private String sessionName;
    private int voteDuration;
    private List<Candidate> candidates;
    private Map<String, Integer> voteMap;
    private Lobby lobby;
    private boolean inSession;
    private List<Player> playersInvolved;
    private BukkitTask session;
    private BallotType ballotType;

    public VoteSession(String sessionName, int voteDuration, Lobby lobby, BallotType ballotType, Candidate... candidates) {
        if (voteDuration > 100 && voteDuration < 10) {
            throw new IndexOutOfBoundsException("Vote Duration cannot exceed 100 seconds / be below 10 seconds!");
        }
        this.sessionName = sessionName;
        this.voteDuration = voteDuration;
        if (candidates.length > 54) {
            throw new IndexOutOfBoundsException("Cannot have more than 64 candidates");
        }
        this.candidates = Candidate.convertToArrayList(candidates);
        this.lobby = lobby;
        this.voteMap = new HashMap<>();
        this.inSession = false;
        this.ballotType = ballotType;
        this.playersInvolved = lobby.getPlayers();
        for (Candidate candidate : candidates) {
            voteMap.put(candidate.getCandidate(), 0);
        }
    }

    List<Integer> possibleIntervalNotifications = (List<Integer>) ServerAPI.getAPI().getConfig().getList("vote-notifications.reminder-intervals");


    /**
     * Begins session
     */
    public void begin() {
        this.inSession = true;
        System.out.println("Session status updated to: " + inSession);
        this.session = Bukkit.getScheduler().runTaskTimer(ServerAPI.getPlugin(), new Runnable() {

            int d = voteDuration;

            @Override
            public void run() {

                String seconds = d + " second" + TextFormat.pluralization(d);

                if (d == voteDuration) {
                    for (Player pl : lobby.getPlayers()) {
                        OnlinePlayer p = new OnlinePlayer(pl);
                        p.sendColorfulMessage(String.format(ServerAPI.getAPI().getBasicMessages().getString("vote-notifications.begun"), seconds));
                    }
                }

                for (int interval : possibleIntervalNotifications) {
                    if (d == interval) {
                        for (Player pl : lobby.getPlayers()) {
                            updateInventoryBallot();
                            OnlinePlayer p = new OnlinePlayer(pl);
                            p.sendColorfulMessage(String.format(ServerAPI.getAPI().getBasicMessages().getString("vote-notifications.reminder"), seconds));
                        }
                    }
                }

                if (d == 0) {
                    String candidate = getResults().getWinner().getKey();
                    String candidateVotes = getResults().getWinner().getValue() + " vote" + TextFormat.pluralization(getResults().getWinner().getValue());

                    for (Player pl : lobby.getPlayers()) {
                        OnlinePlayer p = new OnlinePlayer(pl);
                        if (pl.getOpenInventory().getTitle().equalsIgnoreCase(TextFormat.colorize(ServerAPI.getAPI().getBasicMessages().getString("vote-ballots.inventory.name")))) {
                            pl.closeInventory();
                        }
                        p.sendColorfulMessage(ServerAPI.getAPI().getBasicMessages().getString("vote-notifications.ended").replace("%candidate%", candidate).replace("%candidateVotes%", candidateVotes));
                    }

                    
                    session.cancel();
                    inSession = false;
                    System.out.println("Session status updated to: " + inSession);
                }

                d--;
            }

        }, 0L, 20L);
    }

    /**
     * Cancels BukkitTask
     */
    public void cancel() {
        if (this.session != null) {
            this.inSession = false;
            this.session.cancel();
            for (Player pl : this.lobby.getPlayers()) {
                OnlinePlayer p = new OnlinePlayer(pl);
                p.sendColorfulMessage("&cVote session was suddenly cancelled.");
            }
        }
    }

    public String getSessionName() { return this.sessionName; }

    public BallotType getBallotType() { return ballotType; }

    public void openBallot(Player player) {
        if (ballotType == BallotType.INVENTORY) {
            Material optionMaterial = null;
            if (Material.getMaterial((ServerAPI.getAPI().getConfig().getString("vote-ballots.inventory.candidate-material-id"))) == null) {
                throw new InvalidConfigValueException("Item ID for vote-ballots.inventory.candidate-material-id is invalid, use appropiate id's");
            } else {
                optionMaterial = Material.getMaterial((ServerAPI.getAPI().getConfig().getString("vote-ballots.inventory.candidate-material-id")));
            }

            

            Inventory inv = Bukkit.createInventory(player, Grab.accomodatingInventorySize2(this.candidates.size()), TextFormat.colorize(ServerAPI.getAPI().getBasicMessages().getString("vote-ballots.inventory.name")));

            for (Candidate candidate : this.candidates) {
                String votes = String.valueOf(getVotes(candidate.getCandidate()));
                
                CherryItem item = new CherryItem(optionMaterial, 1);

                item.setDisplayName(ServerAPI.getAPI().getBasicMessages().getString("vote-ballots.inventory.candidate-color-name") + candidate.getCandidate(), true);
                if (candidate.getDescription().isEmpty()) {
                    item.setLore(true, 
                        " ", 
                        ServerAPI.getAPI().getBasicMessages().getString("vote-ballots.inventory.candidate-votes").replace("%votes%", votes)
                    );
                } else {
                    item.setLore(true, 
                    ServerAPI.getAPI().getBasicMessages().getString("vote-ballots.inventory.candidate-description-color") + candidate.getDescription(),
                        " ", 
                        ServerAPI.getAPI().getBasicMessages().getString("vote-ballots.inventory.candidate-votes").replace("%votes%", votes)
                    );
                }
              

                inv.addItem(item.build());
            }   

            player.openInventory(inv);

        } else {
            


        }
    }

    public void updateInventoryBallot() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (p.getOpenInventory().getTitle().equalsIgnoreCase(TextFormat.colorize(ServerAPI.getAPI().getBasicMessages().getString("vote-ballots.inventory.name")))) {
                p.getOpenInventory().getTopInventory().clear();
                Inventory inv = p.getOpenInventory().getTopInventory();

                Material optionMaterial = null;
                if (Material.getMaterial((ServerAPI.getAPI().getConfig().getString("vote-ballots.inventory.candidate-material-id"))) == null) {
                    throw new InvalidConfigValueException("Item ID for vote-ballots.inventory.candidate-material-id is invalid, use appropiate id's");
                } else {
                    optionMaterial = Material.getMaterial((ServerAPI.getAPI().getConfig().getString("vote-ballots.inventory.candidate-material-id")));
                }

                for (Candidate candidate : this.candidates) {
                    String votes = String.valueOf(getVotes(candidate.getCandidate()));
                    
                    CherryItem item = new CherryItem(optionMaterial, 1);
                    
                    item.setDisplayName(ServerAPI.getAPI().getBasicMessages().getString("vote-ballots.inventory.candidate-color-name") + candidate.getCandidate(), true);
                    if (candidate.getDescription().isEmpty()) {
                        item.setLore(true, 
                            " ", 
                            ServerAPI.getAPI().getBasicMessages().getString("vote-ballots.inventory.candidate-votes").replace("%votes%", votes)
                        );
                    } else {
                        item.setLore(true, 
                        ServerAPI.getAPI().getBasicMessages().getString("vote-ballots.inventory.candidate-description-color") + candidate.getDescription(),
                            " ", 
                            ServerAPI.getAPI().getBasicMessages().getString("vote-ballots.inventory.candidate-votes").replace("%votes%", votes)
                        );
                    }

                    inv.addItem(item.build());
                }   

                p.updateInventory();
            }
        }

    }

    public List<Candidate> getCandidates() { return this.candidates; }

    public Integer getVotes(String candidate) { return voteMap.get(candidate); }

    public Integer getVoteDuration() { return voteDuration; }

    public boolean inSession() { return inSession; }

    public Lobby getLobby() { return lobby; }

    public boolean isInvolved(OnlinePlayer player) {
        return playersInvolved.contains(player.getStringId());
    }

    public VoteResults getResults() { return new VoteResults(voteMap); }
    
}
