package net.nighthawkempires.essentials.commands;

import net.nighthawkempires.core.NECore;
import net.nighthawkempires.core.chat.ChatType;
import net.nighthawkempires.core.language.Lang;
import net.nighthawkempires.core.users.User;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class HealCommand implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            User user = NECore.getUserManager().getUser(player.getUniqueId());

            if (!player.hasPermission("ne.heal")) {
                player.sendMessage(Lang.NO_PERM.getServerMessage());
                return true;
            }

            if (args.length == 0) {
                player.setHealth(player.getMaxHealth());
                player.setFoodLevel(20);
                player.sendMessage(Lang.CHAT_TAG.getServerChatTag() + ChatColor.GRAY + "You have healed yourself!");
                return true;
            } else if (args.length == 1) {
                if (args[0].toLowerCase().equalsIgnoreCase("*")) {
                    for (Player players : Bukkit.getOnlinePlayers()) {
                        players.setHealth(players.getMaxHealth());
                        players.setFoodLevel(20);
                        players.sendMessage(Lang.CHAT_TAG.getServerChatTag() + ChatColor.GRAY + "You have been healed!");
                    }
                    player.sendMessage(Lang.CHAT_TAG.getServerChatTag() + ChatColor.GRAY + "You have healed everyone.");
                    return true;
                }
                String name = args[0];
                if (!Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(name))) {
                    player.sendMessage(Lang.PLAYER_NULL.getServerMessage());
                    return true;
                }
                Player healed = Bukkit.getPlayer(name);
                healed.setHealth(healed.getMaxHealth());
                healed.setFoodLevel(20);
                healed.sendMessage(Lang.CHAT_TAG.getServerChatTag() + ChatColor.GRAY + "You have been healed!");
                player.sendMessage(Lang.CHAT_TAG.getServerChatTag() + ChatColor.GRAY + "You have healed " + ChatColor.BLUE + healed.getName() + ChatColor.GRAY + ".");
                return true;
            } else {
                player.sendMessage(Lang.SYNTAX_ERROR.getServerMessage());
                return true;
            }
        } else if (sender instanceof ConsoleCommandSender) {
            ConsoleCommandSender console = (ConsoleCommandSender) sender;

            if (args.length == 0) {
                console.sendMessage(Lang.CHAT_TAG.getServerMessage(ChatColor.RED + "The console can not be healed."));
                return true;
            } else if (args.length == 1) {
                if (args[0].toLowerCase().equalsIgnoreCase("*")) {
                    for (Player players : Bukkit.getOnlinePlayers()) {
                        players.setHealth(players.getMaxHealth());
                        players.setFoodLevel(20);
                        players.sendMessage(Lang.CHAT_TAG.getServerChatTag() + ChatColor.GRAY + "You have been healed!");
                    }
                    console.sendMessage(Lang.CHAT_TAG.getServerChatTag() + ChatColor.GRAY + "You have healed everyone.");
                    return true;
                }
                String name = args[0];
                if (!Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(name))) {
                    console.sendMessage(Lang.PLAYER_NULL.getServerMessage());
                    return true;
                }
                Player healed = Bukkit.getPlayer(name);
                healed.setHealth(healed.getMaxHealth());
                healed.setFoodLevel(20);
                healed.sendMessage(Lang.CHAT_TAG.getServerChatTag() + ChatColor.GRAY + "You have been healed!");
                console.sendMessage(Lang.CHAT_TAG.getServerChatTag() + ChatColor.GRAY + "You have healed " + ChatColor.BLUE + healed.getName() + ChatColor.GRAY + ".");
                return true;
            } else {
                console.sendMessage(Lang.SYNTAX_ERROR.getServerMessage());
                return true;
            }
        }
        return true;
    }
}
