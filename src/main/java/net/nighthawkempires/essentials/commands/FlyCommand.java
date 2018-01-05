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

public class FlyCommand implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            User user = NECore.getUserManager().getUser(player.getUniqueId());

            if (!player.hasPermission("ne.fly")) {
                player.sendMessage(Lang.NO_PERM.getServerMessage());
                return true;
            }

            if (args.length == 0) {
                if (NEEssentials.getData().flyMode.contains(player.getUniqueId())) {
                    player.sendMessage(Lang.CHAT_TAG.getServerChatTag() + ChatColor.GRAY + "You " + ChatColor.RED + "disabled " + ChatColor.GRAY + "flight.");
                    NEEssentials.getData().flyMode.remove(player.getUniqueId());
                    player.setAllowFlight(false);
                    player.setFlying(false);
                } else {
                    player.sendMessage(Lang.CHAT_TAG.getServerChatTag() + ChatColor.GRAY + "You " + ChatColor.GREEN + "enabled " + ChatColor.GRAY + "flight.");
                    NEEssentials.getData().flyMode.add(player.getUniqueId());
                    player.setAllowFlight(true);
                    player.setFlying(true);
                }
            } else {
                String name = args[0];
                if (!Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(name))) {
                    player.sendMessage(Lang.PLAYER_NULL.getServerMessage());
                    return true;
                }

                Player target = Bukkit.getPlayer(name);
                if (NEEssentials.getData().flyMode.contains(target.getUniqueId())) {
                    player.sendMessage(Lang.CHAT_TAG.getServerChatTag() + ChatColor.GRAY + "You " + ChatColor.RED + "disabled " + ChatColor.GRAY + "flight for "
                            + ChatColor.BLUE + target.getName() + ChatColor.GRAY + ".");
                    target.sendMessage(Lang.CHAT_TAG.getServerChatTag() + ChatColor.GRAY + "You can no longer fly.");
                    target.setAllowFlight(false);
                    target.setFlying(false);
                    NEEssentials.getData().flyMode.remove(target.getUniqueId());
                    return true;
                } else {
                    player.sendMessage(Lang.CHAT_TAG.getServerChatTag() + ChatColor.GRAY + "You " + ChatColor.GREEN + "enabled " + ChatColor.GRAY + "flight for "
                            + ChatColor.BLUE + target.getName() + ChatColor.GRAY + ".");
                    target.sendMessage(Lang.CHAT_TAG.getServerChatTag() + ChatColor.GRAY + "You can fly now.");
                    target.setAllowFlight(true);
                    target.setFlying(true);
                    NEEssentials.getData().flyMode.add(target.getUniqueId());
                    return true;
                }
            }
        } else if (sender instanceof ConsoleCommandSender) {
            ConsoleCommandSender console = (ConsoleCommandSender) sender;
            console.sendMessage(Lang.CHAT_TAG.getServerMessage(ChatColor.RED + "This command is not available from the console!"));
            return true;
        }
        return true;
    }
}
