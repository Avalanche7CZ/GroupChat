package eu.avalanche7.Utils;

import eu.avalanche7.GroupChat;
import eu.avalanche7.data.PlayerGroupData;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.*;

public class GroupChatManager {
    private Map<String, Group> groups = new HashMap<>();
    private Map<UUID, PlayerGroupData> playerData = new HashMap<>();
    private Set<UUID> groupChatToggled = new HashSet<>();

    public boolean createGroup(Player player, String groupName) {
        if (groups.containsKey(groupName)) {
            player.sendMessage(GroupChat.PREFIX + " §4Group already exists.");
            return false;
        }
        Group group = new Group(groupName, player.getUniqueId());
        groups.put(groupName, group);
        getPlayerData(player).setCurrentGroup(groupName);
        player.sendMessage(GroupChat.PREFIX + " §aGroup created successfully.");
        return true;
    }

    public boolean deleteGroup(Player player) {
        PlayerGroupData data = getPlayerData(player);
        String groupName = data.getCurrentGroup();
        if (groupName == null || !groups.containsKey(groupName)) {
            player.sendMessage(GroupChat.PREFIX + " §4No group to delete.");
            return false;
        }
        Group group = groups.get(groupName);
        if (!group.getOwner().equals(player.getUniqueId())) {
            player.sendMessage(GroupChat.PREFIX + " §4You are not the owner of this group.");
            return false;
        }
        groups.remove(groupName);
        player.sendMessage(GroupChat.PREFIX + " §aGroup deleted successfully.");
        return true;
    }

    public boolean invitePlayer(Player player, String targetName) {
        Player target = Bukkit.getPlayer(targetName);
        if (target == null) {
            player.sendMessage(GroupChat.PREFIX + " §4Player not found.");
            return false;
        }
        PlayerGroupData data = getPlayerData(player);
        String groupName = data.getCurrentGroup();
        if (groupName == null || !groups.containsKey(groupName)) {
            player.sendMessage(GroupChat.PREFIX + " §4No group to invite to.");
            return false;
        }
        Group group = groups.get(groupName);
        if (!group.getOwner().equals(player.getUniqueId())) {
            player.sendMessage(GroupChat.PREFIX + " §4You are not the owner of this group.");
            return false;
        }
        getPlayerData(target).addInvitation(groupName);
        target.sendMessage(GroupChat.PREFIX + " §aYou have been invited to join the group: " + groupName);
        return true;
    }

    public boolean joinGroup(Player player, String groupName) {
        PlayerGroupData data = getPlayerData(player);
        if (!data.getInvitations().contains(groupName)) {
            player.sendMessage(GroupChat.PREFIX + " §4You are not invited to this group.");
            return false;
        }
        Group group = groups.get(groupName);
        group.addMember(player.getUniqueId());
        data.setCurrentGroup(groupName);
        player.sendMessage(GroupChat.PREFIX + " §aJoined the group: " + groupName);
        return true;
    }

    public boolean leaveGroup(Player player) {
        PlayerGroupData data = getPlayerData(player);
        String groupName = data.getCurrentGroup();
        if (groupName == null || !groups.containsKey(groupName)) {
            player.sendMessage(GroupChat.PREFIX + " §4No group to leave.");
            return false;
        }
        Group group = groups.get(groupName);
        group.removeMember(player.getUniqueId());
        data.setCurrentGroup(null);
        player.sendMessage(GroupChat.PREFIX + " §4Left the group: " + groupName);
        return true;
    }

    public List<String> listGroups() {
        return new ArrayList<>(groups.keySet());
    }

    public List<String> listPlayerGroups(Player player) {
        List<String> playerGroups = new ArrayList<>();
        for (Group group : groups.values()) {
            if (group.getMembers().contains(player.getUniqueId())) {
                playerGroups.add(group.getName());
            }
        }
        return playerGroups;
    }

    public Group getGroupInfo(String groupName) {
        return groups.get(groupName);
    }

    public boolean sendMessage(Player player, String message) {
        PlayerGroupData data = getPlayerData(player);
        String groupName = data.getCurrentGroup();
        if (groupName == null || !groups.containsKey(groupName)) {
            player.sendMessage(GroupChat.PREFIX + " §4No group to send message to.");
            return false;
        }
        Group group = groups.get(groupName);
        for (UUID memberId : group.getMembers()) {
            Player member = Bukkit.getPlayer(memberId);
            if (member != null) {
                member.sendMessage("[§9" + groupName + "§f] " + player.getName() + " §7>§f " + message);
            }
        }
        return true;
    }

    public void toggleGroupChat(Player player) {
        UUID playerId = player.getUniqueId();
        if (groupChatToggled.contains(playerId)) {
            groupChatToggled.remove(playerId);
            player.sendMessage(GroupChat.PREFIX + " §4Group chat disabled.");
        } else {
            groupChatToggled.add(playerId);
            player.sendMessage(GroupChat.PREFIX + " §aGroup chat enabled.");
        }
    }

    public boolean isGroupChatToggled(Player player) {
        return groupChatToggled.contains(player.getUniqueId());
    }


    private PlayerGroupData getPlayerData(Player player) {
        return playerData.computeIfAbsent(player.getUniqueId(), k -> new PlayerGroupData());
    }
}
