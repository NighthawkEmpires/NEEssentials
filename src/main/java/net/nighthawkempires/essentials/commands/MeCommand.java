package net.nighthawkempires.essentials.commands;

import net.nighthawkempires.core.NECore;
import net.nighthawkempires.core.language.Lang;
import net.nighthawkempires.core.users.User;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class MeCommand implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            User user = NECore.getUserManager().getUser(player.getUniqueId());

            if (!player.hasPermission("ne.me")) {
                player.sendMessage(Lang.NO_PERM.getServerMessage());
                return true;
            }

            if (NECore.getMuteManager().isMuted(player.getUniqueId())) {
                player.sendMessage(Lang.CHAT_TAG.getServerMessage(ChatColor.RED + "You can not run this command while muted!"));
                return true;
            }

            StringBuilder builder = new StringBuilder();
            for (String string : args) {
                builder.append(string).append(" ");
            }
            String msg = ChatColor.translateAlternateColorCodes('&', builder.toString().substring(0, builder.length()-1));
            for (Player players : Bukkit.getOnlinePlayers()) {
                players.sendMessage(ChatColor.DARK_GRAY + "" + ChatColor.ITALIC + "** " + ChatColor.BLUE + "" + ChatColor.ITALIC + player.getName() + ChatColor.GRAY + " " + ChatColor.ITALIC + msg
                        + ChatColor.DARK_GRAY  + "" + ChatColor.ITALIC + " **");
            }
        } else if (sender instanceof ConsoleCommandSender) {
            ConsoleCommandSender console = (ConsoleCommandSender) sender;

            StringBuilder builder = new StringBuilder();
            for (String string : args) {
                builder.append(string).append(" ");
            }
            String msg = ChatColor.translateAlternateColorCodes('&', builder.toString().substring(0, builder.length()-1));
            for (Player players : Bukkit.getOnlinePlayers()) {
                players.sendMessage(ChatColor.DARK_GRAY + "" + ChatColor.ITALIC + "** " + ChatColor.BLUE + "" + ChatColor.ITALIC + NECore.getSettings().consoleDisplay + ChatColor.GRAY + " " + ChatColor.ITALIC + msg
                        + ChatColor.DARK_GRAY  + "" + ChatColor.ITALIC + " **");
            }
        }
        return true;
    }
}
