package eu.avalanche7.listeners;

import eu.avalanche7.Utils.GroupChatManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.entity.Player;

public class GroupChatListener implements Listener {
    private GroupChatManager groupChatManager;

    public GroupChatListener(GroupChatManager groupChatManager) {
        this.groupChatManager = groupChatManager;
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        if (groupChatManager.isGroupChatToggled(player)) {
            event.setCancelled(true);
            String message = event.getMessage();
            groupChatManager.sendMessage(player, message);
        }
    }
}
