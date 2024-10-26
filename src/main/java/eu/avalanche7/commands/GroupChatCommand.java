package eu.avalanche7.commands;

import eu.avalanche7.Utils.Group;
import eu.avalanche7.Utils.GroupChatManager;
import eu.avalanche7.Utils.MessageText;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GroupChatCommand implements CommandExecutor {
    private final GroupChatManager groupChatManager;

    public GroupChatCommand(GroupChatManager groupChatManager) {
        this.groupChatManager = groupChatManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use this command.");
            return false;
        }
        Player player = (Player) sender;

        if (args.length == 0) {
            sendCommandList(player, label);
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "create":
                return handleCreateGroup(player, args);
            case "delete":
                return groupChatManager.deleteGroup(player);
            case "invite":
                return handleInvitePlayer(player, args);
            case "join":
                return handleJoinGroup(player, args);
            case "leave":
                return groupChatManager.leaveGroup(player);
            case "list":
                return handleListGroups(player);
            case "mygroups":
                return handleListPlayerGroups(player);
            case "info":
                return handleGroupInfo(player, args);
            case "say":
                return handleSendMessage(player, args);
            case "toggle":
                groupChatManager.toggleGroupChat(player);
                return true;
            default:
                player.sendMessage(ChatColor.RED + "Unknown command. Use /groupchat for a list of commands.");
                return false;
        }
    }

    private void sendCommandList(Player player, String label) {
        player.sendMessage(ChatColor.GOLD + "=== Group Chat Commands ===");
        MessageText.sendInteractiveMessage(player, label, "create [name]", "Create a new group.", ChatColor.YELLOW);
        MessageText.sendInteractiveMessage(player, label, "delete", "Delete your current group.", ChatColor.YELLOW);
        MessageText.sendInteractiveMessage(player, label, "invite [player]", "Invite a player to your group.", ChatColor.YELLOW);
        MessageText.sendInteractiveMessage(player, label, "join [group name]", "Join an existing group.", ChatColor.YELLOW);
        MessageText.sendInteractiveMessage(player, label, "leave", "Leave your current group.", ChatColor.YELLOW);
        MessageText.sendInteractiveMessage(player, label, "list", "List all groups.", ChatColor.YELLOW);
        MessageText.sendInteractiveMessage(player, label, "mygroups", "List your groups.", ChatColor.YELLOW);
        MessageText.sendInteractiveMessage(player, label, "info [group name]", "Get information about a group.", ChatColor.YELLOW);
        MessageText.sendInteractiveMessage(player, label, "say [message]", "Send a message to the group.", ChatColor.YELLOW);
        MessageText.sendInteractiveMessage(player, label, "toggle", "Toggle group chat on or off.", ChatColor.YELLOW);
    }

    private boolean handleCreateGroup(Player player, String[] args) {
        if (args.length < 2) {
            player.sendMessage(ChatColor.RED + "Usage: /groupchat create [name]");
            return false;
        }
        return groupChatManager.createGroup(player, args[1]);
    }

    private boolean handleInvitePlayer(Player player, String[] args) {
        if (args.length < 2) {
            player.sendMessage(ChatColor.RED + "Usage: /groupchat invite [player]");
            return false;
        }
        return groupChatManager.invitePlayer(player, args[1]);
    }

    private boolean handleJoinGroup(Player player, String[] args) {
        if (args.length < 2) {
            player.sendMessage(ChatColor.RED + "Usage: /groupchat join [group name]");
            return false;
        }
        return groupChatManager.joinGroup(player, args[1]);
    }

    private boolean handleListGroups(Player player) {
        String groups = String.join(", ", groupChatManager.listGroups());
        player.sendMessage("Groups: " + groups);
        return true;
    }

    private boolean handleListPlayerGroups(Player player) {
        String playerGroups = String.join(", ", groupChatManager.listPlayerGroups(player));
        player.sendMessage("Your Groups: " + playerGroups);
        return true;
    }

    private boolean handleGroupInfo(Player player, String[] args) {
        if (args.length < 2) {
            player.sendMessage(ChatColor.RED + "Usage: /groupchat info [group name]");
            return false;
        }
        Group group = groupChatManager.getGroupInfo(args[1]);
        if (group == null) {
            player.sendMessage(ChatColor.RED + "Group not found.");
            return false;
        }
        player.sendMessage("Group: " + group.getName() + ", Owner: " + group.getOwner() + ", Members: " + group.getMembers().size());
        return true;
    }

    private boolean handleSendMessage(Player player, String[] args) {
        if (args.length < 2) {
            player.sendMessage(ChatColor.RED + "Usage: /groupchat say [message]");
            return false;
        }
        String message = String.join(" ", (CharSequence[]) java.util.Arrays.copyOfRange(args, 1, args.length));
        return groupChatManager.sendMessage(player, message);
    }
}

