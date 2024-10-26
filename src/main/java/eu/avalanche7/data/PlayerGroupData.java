package eu.avalanche7.data;

import java.util.HashSet;
import java.util.Set;

public class PlayerGroupData {
    private String currentGroup;
    private Set<String> invitations = new HashSet<>();
    private Set<String> visibleGroups = new HashSet<>();

    public String getCurrentGroup() {
        return currentGroup;
    }

    public void setCurrentGroup(String currentGroup) {
        this.currentGroup = currentGroup;
    }

    public Set<String> getInvitations() {
        return invitations;
    }

    public void addInvitation(String groupName) {
        invitations.add(groupName);
    }

    public Set<String> getVisibleGroups() {
        return visibleGroups;
    }

    public void addVisibleGroup(String groupName) {
        visibleGroups.add(groupName);
    }

    public void removeVisibleGroup(String groupName) {
        visibleGroups.remove(groupName);
    }
}