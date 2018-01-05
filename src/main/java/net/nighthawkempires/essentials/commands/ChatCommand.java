package net.nighthawkempires.essentials.commands;

import net.nighthawkempires.core.NECore;
import net.nighthawkempires.core.language.Lang;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class ChatCommand implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (args.length == 0) {
                player.sendMessage(Lang.CHAT_TAG.getServerMessage(ChatColor.RED + "You must include the message you want to send!"));
                return true;
            }

            StringBuilder builder = new StringBuilder();
            for (String string : args) {
                builder.append(string).append(" ");
            }

            Bukkit.getConsoleSender().spigot().sendMessage(NECore.getChatFormat().getFormattedMessage(player, builder.substring(0, builder.length() -1)));
            NECore.getChatFormat().sendMessage(NECore.getChatFormat().getFormattedMessage(player, builder.substring(0, builder.length() -1)));
        } else if (sender instanceof ConsoleCommandSender) {
            ConsoleCommandSender console = (ConsoleCommandSender) sender;

            if (args.length == 0) {
                console.sendMessage(Lang.CHAT_TAG.getServerMessage(ChatColor.RED + "You must include the message you want to send!"));
                return true;
            }

            StringBuilder builder = new StringBuilder();
            for (String string : args) {
                builder.append(string).append(" ");
            }

            console.spigot().sendMessage(NECore.getChatFormat().getFormattedMessage(console, builder.substring(0, builder.length() -1)));
            NECore.getChatFormat().sendMessage(NECore.getChatFormat().getFormattedMessage(console, builder.substring(0, builder.length() -1)));
        }
        return true;
    }
}
