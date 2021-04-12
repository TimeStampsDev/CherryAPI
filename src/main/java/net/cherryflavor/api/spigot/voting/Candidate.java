package net.cherryflavor.api.spigot.voting;

import java.util.ArrayList;
import java.util.List;

public class Candidate {

    String candidate;
    String description;

    public Candidate(String candidate, String description) {
        this.candidate = candidate;
        this.description = description;
    }
    
    public String getCandidate() { return this.candidate; }
    public String getDescription() { return this.description; }

    public static List<Candidate> convertToArrayList(Candidate... candidates) {
        List<Candidate> list = new ArrayList<>();
        for (Candidate candidate : candidates) {
            list.add(candidate);
        }
        return list;
    }

}
