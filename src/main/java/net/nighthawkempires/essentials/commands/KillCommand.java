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

public class KillCommand implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            User user = NECore.getUserManager().getUser(player.getUniqueId());

            if (!player.hasPermission("ne.kill")) {
                player.sendMessage(Lang.NO_PERM.getServerMessage());
                return true;
            }

            if (args.length == 0) {
                player.setHealth(0.0);
                player.setFoodLevel(0);
                player.sendMessage(Lang.CHAT_TAG.getServerChatTag() + ChatColor.GRAY + "You killed yourself!");
                return true;
            } else if (args.length == 1) {
                String name = args[0];
                if (!Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(name))) {
                    player.sendMessage(Lang.PLAYER_NULL.getServerMessage());
                    return true;
                }

                Player target = Bukkit.getPlayer(name);
                target.setHealth(0.0);
                target.setFoodLevel(0);
                target.sendMessage(Lang.CHAT_TAG.getServerChatTag() + ChatColor.GRAY + "You were killed!");
                player.sendMessage(Lang.CHAT_TAG.getServerChatTag() + ChatColor.GRAY + "You killed " + ChatColor.BLUE + target.getName() + ChatColor.GRAY + ".");
                return true;
            }
        } else if (sender instanceof ConsoleCommandSender) {
            ConsoleCommandSender console = (ConsoleCommandSender) sender;

            if (args.length == 0) {
                console.sendMessage(Lang.CHAT_TAG.getServerMessage(ChatColor.RED + "The console can not be killed!"));
                return true;
            } else if (args.length == 1) {
                String name = args[0];
                if (!Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(name))) {
                    console.sendMessage(Lang.PLAYER_NULL.getServerMessage());
                    return true;
                }

                Player target = Bukkit.getPlayer(name);
                target.setHealth(0.0);
                target.setFoodLevel(0);
                target.sendMessage(Lang.CHAT_TAG.getServerChatTag() + ChatColor.GRAY + "You were killed!");
                console.sendMessage(Lang.CHAT_TAG.getServerChatTag() + ChatColor.GRAY + "You killed " + ChatColor.BLUE + target.getName() + ChatColor.GRAY + ".");
                return true;
            }
        }
        return true;
    }
}
