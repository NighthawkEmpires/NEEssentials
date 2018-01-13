package net.nighthawkempires.essentials.commands;

import net.nighthawkempires.core.NECore;
import net.nighthawkempires.core.language.Lang;
import net.nighthawkempires.essentials.NEEssentials;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class ReplyCommand implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (!sender.hasPermission("ne.message")) {
                player.sendMessage(Lang.NO_PERM.getServerMessage());
                return true;
            }

            if (NECore.getMuteManager().isMuted(player.getUniqueId())) {
                player.sendMessage(Lang.CHAT_TAG.getServerMessage(ChatColor.RED + "You can not use this command while muted!"));
                return true;
            }

            if (args.length == 0) {
                player.sendMessage(Lang.CHAT_TAG.getServerMessage(ChatColor.AQUA + "/reply " + ChatColor.DARK_GRAY + "[message]"));
                return true;
            } else {
                if (!NEEssentials.getData().reply.containsKey(player.getUniqueId())) {
                    player.sendMessage(Lang.CHAT_TAG.getServerMessage(ChatColor.RED + "You have no one to reply to!"));
                    return true;
                }

                if (!Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(NEEssentials.getData().reply.get(player.getUniqueId())))) {
                    player.sendMessage(Lang.CHAT_TAG.getServerMessage(ChatColor.RED + "That player is no longer online!"));
                    return true;
                }

                Player target = Bukkit.getPlayer(NEEssentials.getData().reply.get(player.getUniqueId()));
                if (NEEssentials.getData().reply.containsKey(player.getUniqueId())) {
                    NEEssentials.getData().reply.remove(player.getUniqueId());
                }
                if (NEEssentials.getData().reply.containsKey(target.getUniqueId())) {
                    NEEssentials.getData().reply.remove(target.getUniqueId());
                }

                StringBuilder builder = new StringBuilder();
                for (String string : args) {
                    builder.append(string).append(" ");
                }
                String message = builder.substring(0, builder.length() - 1);

                player.sendMessage(Lang.MESSAGE.formatMSGOut(target, message));
                target.sendMessage(Lang.MESSAGE.formatMSGIn(player, message));
                NEEssentials.getData().reply.put(player.getUniqueId(), target.getUniqueId());
                NEEssentials.getData().reply.put(target.getUniqueId(), player.getUniqueId());
            }
        } else if (sender instanceof ConsoleCommandSender) {
            ConsoleCommandSender console = (ConsoleCommandSender) sender;
            console.sendMessage(Lang.CHAT_TAG.getServerMessage(ChatColor.RED + "This command is not available from the console!"));
            return true;
        }
        return true;
    }
}
