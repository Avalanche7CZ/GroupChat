package eu.avalanche7.Utils;

import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class MessageText {

    public static void sendInteractiveMessage(Player player, String label, String command, String description, ChatColor commandColor) {
        TextComponent arrow = new TextComponent(ChatColor.BLUE + " > ");
        TextComponent message = new TextComponent(commandColor + "/" + label + " " + command);
        message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(description).create()));
        message.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/" + label + " " + command));

        arrow.addExtra(message);
        player.spigot().sendMessage(arrow);
    }
}
