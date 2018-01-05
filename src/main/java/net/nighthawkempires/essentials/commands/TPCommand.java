package net.nighthawkempires.essentials.commands;

import net.nighthawkempires.core.NECore;
import net.nighthawkempires.core.language.Lang;
import net.nighthawkempires.core.users.User;
import net.nighthawkempires.essentials.NEEssentials;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class TPCommand implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            User user = NECore.getUserManager().getUser(player.getUniqueId());

            if (!player.hasPermission("ne.tp")) {
                player.sendMessage(Lang.NO_PERM.getServerMessage());
                return true;
            }

            if (args.length == 0) {
                player.sendMessage(Lang.CHAT_TAG.getServerChatTag() + ChatColor.AQUA + "/tp " + ChatColor.DARK_AQUA + "[player]");
                return true;
            } else if (args.length == 1) {
                String name = args[0];
                if (!Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(name))) {
                    player.sendMessage(Lang.PLAYER_NULL.getServerMessage());
                    return true;
                }

                Player tp = Bukkit.getPlayer(name);
                NEEssentials.getData().lastLocation.put(player.getUniqueId(), player.getLocation());
                player.teleport(tp.getLocation());
                player.sendMessage(Lang.CHAT_TAG.getServerChatTag() + ChatColor.GRAY + "You have teleported to " + ChatColor.BLUE + tp.getName() + ChatColor.GRAY + ".");
                return true;
            } else if (args.length == 2) {
                String name1 = args[0];
                String name2 = args[1];
                if (!Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(name1))) {
                    player.sendMessage(Lang.CHAT_TAG.getServerChatTag() + ChatColor.RED + "Player " + name1 + " is not online!");
                    return true;
                }

                if (!Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(name2))) {
                    player.sendMessage(Lang.CHAT_TAG.getServerChatTag() + ChatColor.RED + "Player " + name2 + " is not online!");
                    return true;
                }

                Player tp = Bukkit.getPlayer(name1);
                Player to = Bukkit.getPlayer(name2);
                NEEssentials.getData().lastLocation.put(tp.getUniqueId(), tp.getLocation());
                tp.teleport(to.getLocation());
                player.sendMessage(Lang.CHAT_TAG.getServerChatTag() + ChatColor.GRAY + "You have teleported " + ChatColor.BLUE + tp.getName() + ChatColor.GRAY + " to " + ChatColor.BLUE
                        + to.getName() + ChatColor.GRAY + ".");
                tp.sendMessage(Lang.CHAT_TAG.getServerChatTag() + ChatColor.GRAY + "You have been teleported to " + ChatColor.BLUE + to.getName() + ChatColor.GRAY + ".");
                to.sendMessage(Lang.CHAT_TAG.getServerChatTag() + ChatColor.BLUE + tp.getName() + ChatColor.GRAY + " has been teleported to you.");
                return true;
            } else {
                player.sendMessage(Lang.SYNTAX_ERROR.getServerMessage());
                return true;
            }
        } else if (sender instanceof ConsoleCommandSender) {
            ConsoleCommandSender console = (ConsoleCommandSender) sender;
            console.sendMessage(Lang.CHAT_TAG.getServerMessage(ChatColor.RED + "This command is not available from the console!"));
            return true;
        }
        return true;
    }
}
