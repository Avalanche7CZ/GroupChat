package eu.avalanche7;

import eu.avalanche7.Utils.GroupChatManager;
import eu.avalanche7.listeners.GroupChatListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
public final class GroupChat extends JavaPlugin {

    public static final String PREFIX = "§9§l[§b§lGroupChat§9§l] §f§";
    private GroupChatManager groupChatManager;

    @Override
    public void onEnable() {
        getLogger().info("GroupChat plugin has been enabled.");
        Bukkit.getConsoleSender().sendMessage("=========================");
        Bukkit.getConsoleSender().sendMessage("GroupChat");
        Bukkit.getConsoleSender().sendMessage("Version " + getDescription().getVersion());
        Bukkit.getConsoleSender().sendMessage("Author: Avalanche7CZ");
        Bukkit.getConsoleSender().sendMessage("=========================");

        groupChatManager = new GroupChatManager();

        new CommandExec(this, groupChatManager);

        getServer().getPluginManager().registerEvents(new GroupChatListener(groupChatManager), this);


    }
    @Override
    public void onDisable() {
        getLogger().info("GroupChat plugin has been disabled.");
    }

}