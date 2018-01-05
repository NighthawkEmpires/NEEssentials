package net.nighthawkempires.essentials.commands;

import net.nighthawkempires.core.NECore;
import net.nighthawkempires.core.language.Lang;
import net.nighthawkempires.core.users.User;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class UnmuteCommand implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            User user = NECore.getUserManager().getUser(player.getUniqueId());

            if (!player.hasPermission("ne.mute")) {
                player.sendMessage(Lang.NO_PERM.getServerMessage());
                return true;
            }

            if (args.length == 0) {
                player.sendMessage(Lang.CHAT_TAG.getServerChatTag() + ChatColor.AQUA + "/unmute" + ChatColor.DARK_AQUA + " [player]");
                return true;
            } else if (args.length == 1) {
                String name = args[0];

                OfflinePlayer target = Bukkit.getOfflinePlayer(name);
                if (!NECore.getMuteManager().isMuted(target.getUniqueId())) {
                    player.sendMessage(Lang.CHAT_TAG.getServerChatTag() + ChatColor.RED + "That player is not muted!");
                    return true;
                }

                NECore.getMuteManager().unmute(target.getUniqueId());
                player.sendMessage(Lang.CHAT_TAG.getServerChatTag() + ChatColor.GRAY + "You have unmuted " + ChatColor.BLUE + target.getName() + ChatColor.GRAY + ".");
                if (Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(target.getUniqueId()))) {
                    Bukkit.getPlayer(target.getUniqueId()).sendMessage(Lang.CHAT_TAG.getServerChatTag() + ChatColor.GRAY + "You have been unmuted.");
                }
                return true;
            } else {
                player.sendMessage(Lang.SYNTAX_ERROR.getServerMessage());
                return true;
            }
        } else if (sender instanceof ConsoleCommandSender) {
            ConsoleCommandSender console = (ConsoleCommandSender) sender;

            if (args.length == 0) {
                console.sendMessage(Lang.CHAT_TAG.getServerChatTag() + ChatColor.AQUA + "/unmute" + ChatColor.DARK_AQUA + " [player]");
                return true;
            } else if (args.length == 1) {
                String name = args[0];

                OfflinePlayer target = Bukkit.getOfflinePlayer(name);
                if (!NECore.getMuteManager().isMuted(target.getUniqueId())) {
                    console.sendMessage(Lang.CHAT_TAG.getServerChatTag() + ChatColor.RED + "That player is not muted!");
                    return true;
                }

                NECore.getMuteManager().unmute(target.getUniqueId());
                console.sendMessage(Lang.CHAT_TAG.getServerChatTag() + ChatColor.GRAY + "You have unmuted " + ChatColor.BLUE + target.getName() + ChatColor.GRAY + ".");
                if (Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(target.getUniqueId()))) {
                    Bukkit.getPlayer(target.getUniqueId()).sendMessage(Lang.CHAT_TAG.getServerChatTag() + ChatColor.GRAY + "You have been unmuted.");
                }
                return true;
            } else {
                console.sendMessage(Lang.SYNTAX_ERROR.getServerMessage());
                return true;
            }
        }
        return true;
    }
}
