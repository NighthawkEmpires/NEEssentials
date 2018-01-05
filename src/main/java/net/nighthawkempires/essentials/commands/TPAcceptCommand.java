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

public class TPAcceptCommand implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            User user = NECore.getUserManager().getUser(player.getUniqueId());

            if (!player.hasPermission("ne.tpa")) {
                player.sendMessage(Lang.NO_PERM.getServerMessage());
                return true;
            }

            if (args.length == 0) {
                if (!NEEssentials.getData().tpRequests.containsKey(player.getUniqueId())) {
                    player.sendMessage(Lang.CHAT_TAG.getServerMessage(ChatColor.RED + "You do not have any teleportaion requests!"));
                    return true;
                }

                if (!Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(NEEssentials.getData().tpRequests.get(player.getUniqueId())))) {
                    player.sendMessage(Lang.CHAT_TAG.getServerMessage(ChatColor.RED + "That player is no longer online!"));
                    return true;
                }

                Player target = Bukkit.getPlayer(NEEssentials.getData().tpRequests.get(player.getUniqueId()));
                if (!player.hasPermission("ne.no.cmdwarmup")) {
                    NEEssentials.getData().dontMove.add(player.getUniqueId());
                    player.sendMessage(Lang.CHAT_TAG.getServerMessage(ChatColor.GRAY + "Executing" + ChatColor.AQUA + " /tpaccept " + ChatColor.GRAY + "in "
                            + ChatColor.GOLD + "4 seconds" + ChatColor.GRAY + "."));
                    Bukkit.getScheduler().scheduleSyncDelayedTask(NEEssentials.getPlugin(), () -> {
                        if (NEEssentials.getData().dontMove.contains(player.getUniqueId())) {
                            NEEssentials.getData().dontMove.remove(player.getUniqueId());
                            NEEssentials.getData().tpRequests.remove(player.getUniqueId(), target.getUniqueId());
                            target.teleport(player.getLocation());
                            target.sendMessage(Lang.CHAT_TAG.getServerMessage(ChatColor.BLUE + player.getName() + ChatColor.GRAY + " accepted your teleportation request."));
                            player.sendMessage(Lang.CHAT_TAG.getServerMessage(ChatColor.GRAY + "You accepted " + ChatColor.BLUE + target.getName() + "'s " + ChatColor.GRAY + "teleportation request"));
                        }
                    }, 80L);
                    return true;
                }

                target.teleport(player.getLocation());
                NEEssentials.getData().tpRequests.remove(player.getUniqueId(), target.getUniqueId());
                target.sendMessage(Lang.CHAT_TAG.getServerMessage(ChatColor.BLUE + player.getName() + ChatColor.GRAY + " accepted your teleportation request."));
                player.sendMessage(Lang.CHAT_TAG.getServerMessage(ChatColor.GRAY + "You accepted " + ChatColor.BLUE + target.getName() + "'s " + ChatColor.GRAY + "teleportation request"));
                return true;
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
