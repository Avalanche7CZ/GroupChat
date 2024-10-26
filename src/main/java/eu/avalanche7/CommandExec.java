package eu.avalanche7;

import eu.avalanche7.Utils.GroupChatManager;
import eu.avalanche7.commands.GroupChatCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public class CommandExec implements CommandExecutor {

    private final Map<String, CommandExecutor> commands = new HashMap<>();

    public CommandExec(JavaPlugin plugin, GroupChatManager groupChatManager) {

        commands.put("groupchat", new GroupChatCommand(groupChatManager));

        for (String command : commands.keySet()) {
            plugin.getCommand(command).setExecutor(this);
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        CommandExecutor executor = commands.get(command.getName().toLowerCase());
        if (executor != null) {
            return executor.onCommand(sender, command, label, args);
        }
        return false;
    }
}