package eu.avalanche7.Utils;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class Group {
    private String name;
    private UUID owner;
    private Set<UUID> members = new HashSet<>();
    private boolean isPrivate;

    public Group(String name, UUID owner) {
        this.name = name;
        this.owner = owner;
        this.members.add(owner);
        this.isPrivate = false;
    }

    public String getName() {
        return name;
    }

    public UUID getOwner() {
        return owner;
    }

    public Set<UUID> getMembers() {
        return members;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public void setPrivate(boolean isPrivate) {
        this.isPrivate = isPrivate;
    }

    public void addMember(UUID member) {
        members.add(member);
    }

    public void removeMember(UUID member) {
        members.remove(member);
    }
}
