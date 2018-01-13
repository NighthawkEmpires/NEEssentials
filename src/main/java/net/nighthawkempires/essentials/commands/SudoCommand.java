package net.nighthawkempires.essentials.commands;

import net.nighthawkempires.core.language.Lang;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class SudoCommand implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (!player.hasPermission("ne.sudo")) {
                player.sendMessage(Lang.NO_PERM.getServerMessage());
                return true;
            }

            if (args.length == 0) {
                player.sendMessage(Lang.CHAT_TAG.getServerMessage(ChatColor.AQUA + "/sudo " + ChatColor.DARK_AQUA + "[player] [command] [args]"));
                return true;
            } else if (args.length == 1) {
                player.sendMessage(Lang.CHAT_TAG.getServerMessage(ChatColor.RED + "You must include a command you want the player to execute."));
                return true;
            } else {
                String name = args[0];

                if (!Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(name))) {
                    player.sendMessage(Lang.PLAYER_NULL.getServerMessage());
                    return true;
                }

                Player target = Bukkit.getPlayer(name);

                StringBuilder builder = new StringBuilder();
                for (String string : args) {
                    builder.append(string).append(" ");
                }
                String message = builder.substring(args[0].length() + 1, builder.length() - 1);
                player.sendMessage(Lang.CHAT_TAG.getServerMessage(ChatColor.GRAY + "You forced " + ChatColor.BLUE + target.getName() + ChatColor.GRAY + " to run: " + ChatColor.YELLOW + "/" + message + ChatColor.GRAY + "."));
                target.chat("/" + message);
            }
        } else if (sender instanceof ConsoleCommandSender) {
            ConsoleCommandSender console = (ConsoleCommandSender) sender;

            if (args.length == 0) {
                console.sendMessage(Lang.CHAT_TAG.getServerMessage(ChatColor.AQUA + "/sudo " + ChatColor.DARK_AQUA + "[player] [command] [args]"));
                return true;
            } else if (args.length == 1) {
                console.sendMessage(Lang.CHAT_TAG.getServerMessage(ChatColor.RED + "You must include a command you want the player to execute."));
                return true;
            } else {
                String name = args[0];

                if (!Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(name))) {
                    console.sendMessage(Lang.PLAYER_NULL.getServerMessage());
                    return true;
                }

                Player target = Bukkit.getPlayer(name);

                StringBuilder builder = new StringBuilder();
                for (String string : args) {
                    builder.append(string).append(" ");
                }
                String message = builder.substring(args[0].length() + 1, builder.length() - 1);
                console.sendMessage(Lang.CHAT_TAG.getServerMessage(ChatColor.GRAY + "You forced " + ChatColor.BLUE + target.getName() + ChatColor.GRAY + " to run: " + ChatColor.YELLOW + "/" + message + ChatColor.GRAY + "."));
                target.chat("/" + message);
            }
        }
        return true;
    }
}
