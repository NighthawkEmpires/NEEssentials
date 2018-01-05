package net.nighthawkempires.essentials.commands;

import net.nighthawkempires.core.NECore;
import net.nighthawkempires.core.language.Lang;
import net.nighthawkempires.core.users.User;
import net.nighthawkempires.core.utils.LocationUtil;
import net.nighthawkempires.essentials.NEEssentials;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class SpawnCommand implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            User user = NECore.getUserManager().getUser(player.getUniqueId());

            if (!player.hasPermission("ne.spawn")) {
                player.sendMessage(Lang.NO_PERM.getServerMessage());
                return true;
            }

            if (args.length == 0) {
                if (!LocationUtil.hasSpawn(player.getWorld())) {
                    player.sendMessage(Lang.CHAT_TAG.getServerChatTag() + ChatColor.RED + "A spawn for this world is not set!");
                    return true;
                }
                if (!player.hasPermission("ne.no.cmdwarmup")) {
                    NEEssentials.getData().dontMove.add(player.getUniqueId());
                    player.sendMessage(Lang.CHAT_TAG.getServerChatTag() + ChatColor.GRAY + "Stay still for " + ChatColor.GOLD + "6 seconds " + ChatColor.GRAY + "for this command to warm up.");
                    Bukkit.getScheduler().scheduleSyncDelayedTask(NEEssentials.getPlugin(), () ->{
                        if (NEEssentials.getData().dontMove.contains(player.getUniqueId())) {
                            NEEssentials.getData().lastLocation.put(player.getUniqueId(), player.getLocation());
                            player.teleport(LocationUtil.getSpawn(player.getWorld()));
                            player.sendMessage(Lang.CHAT_TAG.getServerChatTag() + ChatColor.GRAY + "You have teleported to spawn!");
                            NEEssentials.getData().dontMove.remove(player.getUniqueId());
                        }
                    }, 120L);
                    return true;
                }
                NEEssentials.getData().lastLocation.put(player.getUniqueId(), player.getLocation());
                player.teleport(LocationUtil.getSpawn(player.getWorld()));
                player.sendMessage(Lang.CHAT_TAG.getServerChatTag() + ChatColor.GRAY + "You have teleported to spawn!");
            } else if (args.length == 1) {
                if (!player.hasPermission("ne.admin")) {
                    player.sendMessage(Lang.NO_PERM.getServerMessage());
                    return true;
                }
                if (args[0].toLowerCase().equalsIgnoreCase("set")) {
                    LocationUtil.setSpawn(player.getWorld(), player.getLocation());
                    player.sendMessage(Lang.CHAT_TAG.getServerChatTag() + ChatColor.GRAY + "You have set the spawn for " + ChatColor.YELLOW + player.getWorld().getName() + ChatColor.GRAY + ".");
                    return true;
                } else if (args[0].toLowerCase().equalsIgnoreCase("remove")) {
                    LocationUtil.deleteSpawn(player.getWorld());
                    player.sendMessage(Lang.CHAT_TAG.getServerChatTag() + ChatColor.GRAY + "You have removed the spawn for " + ChatColor.YELLOW + player.getWorld().getName() + ChatColor.GRAY + ".");
                    return true;
                } else {
                    player.sendMessage(Lang.SYNTAX_ERROR.getServerMessage());
                    return true;
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
