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

public class TPACommand implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            User user = NECore.getUserManager().getUser(player.getUniqueId());

            if (!player.hasPermission("ne.tpa")) {
                player.sendMessage(Lang.NO_PERM.getServerMessage());
                return true;
            }

            if (args.length == 0) {
                player.sendMessage(Lang.CHAT_TAG.getServerMessage(ChatColor.AQUA + "/tpa" + ChatColor.DARK_AQUA + " [player]"));
                return true;
            } else if (args.length == 1) {
                String name = args[0];
                if (!Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(name))) {
                    player.sendMessage(Lang.PLAYER_NULL.getServerMessage());
                    return true;
                }


                Player target = Bukkit.getPlayer(name);

                if (NEEssentials.getData().tpRequests.containsKey(target.getUniqueId()) && NEEssentials.getData().tpRequests.get(target.getUniqueId()).equals(player.getUniqueId())) {
                    player.sendMessage(Lang.CHAT_TAG.getServerMessage(ChatColor.RED + "You have already sent that player a teleportaion request."));
                    return true;
                }

                if (!player.hasPermission("ne.no.cmdwarmup")) {
                    NEEssentials.getData().dontMove.add(player.getUniqueId());
                    player.sendMessage(Lang.CHAT_TAG.getServerChatTag() + ChatColor.GRAY + "Executing " + ChatColor.AQUA + "/tpa " + ChatColor.DARK_AQUA + target.getName() + ChatColor.GRAY
                            + " in " + ChatColor.GOLD + "4 seconds.");
                    Bukkit.getScheduler().scheduleSyncDelayedTask(NEEssentials.getPlugin(), () -> {
                        if (NEEssentials.getData().dontMove.contains(player.getUniqueId())) {
                            NEEssentials.getData().dontMove.remove(player.getUniqueId());
                            target.sendMessage(Lang.CHAT_TAG.getServerMessage(ChatColor.BLUE + player.getName() + ChatColor.GRAY + " has sent you a teleportation request type "
                                    + ChatColor.AQUA + "/tpaccept " + ChatColor.GRAY + "to accept. This request will time out in " + ChatColor.GOLD + "60 seconds" + ChatColor.GRAY + "."));
                            player.sendMessage(Lang.CHAT_TAG.getServerMessage(ChatColor.GRAY + "You have sent " + ChatColor.BLUE + target.getName() + ChatColor.GRAY + " a teleportation request."));
                            NEEssentials.getData().tpRequests.put(target.getUniqueId(), player.getUniqueId());

                            Bukkit.getScheduler().scheduleSyncDelayedTask(NEEssentials.getPlugin(), () -> {
                                if (NEEssentials.getData().tpRequests.containsKey(target.getUniqueId()) && NEEssentials.getData().tpRequests.get(target.getUniqueId()).equals(player.getUniqueId())) {
                                    NEEssentials.getData().tpRequests.remove(target.getUniqueId(), player.getUniqueId());
                                }
                            }, 1200L);
                        }
                    }, 80L);
                    return true;
                }

                target.sendMessage(Lang.CHAT_TAG.getServerMessage(ChatColor.BLUE + player.getName() + ChatColor.GRAY + " has sent you a teleportation request type "
                        + ChatColor.AQUA + "/tpaccept " + ChatColor.GRAY + "to accept. This request will time out in " + ChatColor.GOLD + "60 seconds" + ChatColor.GRAY + "."));
                player.sendMessage(Lang.CHAT_TAG.getServerMessage(ChatColor.GRAY + "You have sent " + ChatColor.BLUE + target.getName() + ChatColor.GRAY + " a teleportation request."));
                NEEssentials.getData().tpRequests.put(target.getUniqueId(), player.getUniqueId());

                Bukkit.getScheduler().scheduleSyncDelayedTask(NEEssentials.getPlugin(), () -> {
                    if (NEEssentials.getData().tpRequests.containsKey(target.getUniqueId()) && NEEssentials.getData().tpRequests.get(target.getUniqueId()).equals(player.getUniqueId())) {
                        NEEssentials.getData().tpRequests.remove(target.getUniqueId(), player.getUniqueId());
                    }
                }, 1200L);
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
