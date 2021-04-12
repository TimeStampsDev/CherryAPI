package net.cherryflavor.api.spigot.voting;

import java.util.Map;

import net.cherryflavor.api.tools.Grab;

public class VoteResults {

    private Map<String, Integer> votes;

    public VoteResults(Map<String, Integer> votes) {
        this.votes = votes;
    }

    public Map.Entry<String, Integer> getWinner() {
        Map.Entry<String, Integer> entryWithMaxVote = null;

        String entryName = Grab.firstEntryWithHighestValue(votes);

        for (Map.Entry<String, Integer> currentEntry : votes.entrySet()) {
            if (currentEntry.getKey().equalsIgnoreCase(entryName)) {
                return currentEntry;
            }
        }

        return entryWithMaxVote;
    }
    
}
