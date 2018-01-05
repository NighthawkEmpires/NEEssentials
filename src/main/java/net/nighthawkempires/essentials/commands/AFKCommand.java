package net.nighthawkempires.essentials.commands;

import net.nighthawkempires.core.language.Lang;
import net.nighthawkempires.essentials.NEEssentials;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class AFKCommand implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (!player.hasPermission("ne.afk")) {
                player.sendMessage(Lang.NO_PERM.getServerMessage());
                return true;
            }

            if (args.length == 0) {
                if (NEEssentials.getData().afk.contains(player.getUniqueId())) {
                    NEEssentials.getData().afk.remove(player.getUniqueId());
                    for (Player players : Bukkit.getOnlinePlayers()) {
                        players.sendMessage(Lang.CHAT_TAG.getServerMessage(ChatColor.BLUE + "" + ChatColor.BOLD + player.getName() + ChatColor.GRAY + "" + ChatColor.BOLD + " is no longer afk!"));
                    }
                } else {
                    NEEssentials.getData().afk.add(player.getUniqueId());
                    for (Player players : Bukkit.getOnlinePlayers()) {
                        players.sendMessage(Lang.CHAT_TAG.getServerMessage(ChatColor.BLUE + "" + ChatColor.BOLD + player.getName() + ChatColor.GRAY + "" + ChatColor.BOLD + " is afk!"));
                    }
                }
            } else {
                player.sendMessage(Lang.SYNTAX_ERROR.getServerMessage());
            }
        } else if (sender instanceof ConsoleCommandSender) {
            ConsoleCommandSender console = (ConsoleCommandSender) sender;
            console.sendMessage(Lang.CHAT_TAG.getServerMessage(ChatColor.RED + "This command is not available from the console!"));
            return true;
        }
        return true;
    }
}
