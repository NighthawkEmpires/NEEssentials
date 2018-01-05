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

public class DeOPCommand implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            User user = NECore.getUserManager().getUser(player.getUniqueId());

            if (!player.hasPermission("ne.op")) {
                player.sendMessage(Lang.NO_PERM.getServerMessage());
                return true;
            }

            if (args.length == 0) {
                player.sendMessage(Lang.CHAT_TAG.getServerMessage(ChatColor.AQUA + "/deop" + ChatColor.DARK_AQUA + " [player]" + ChatColor.GRAY + "."));
                return true;
            } else if (args.length == 1) {
                String name = args[0];
                if (!NECore.getUserManager().userExists(Bukkit.getOfflinePlayer(name).getUniqueId())) {
                    player.sendMessage(Lang.PLAYER_NULL.getServerMessage());
                    return true;
                }

                if (!Bukkit.getOfflinePlayer(name).isOp()) {
                    player.sendMessage(Lang.CHAT_TAG.getServerMessage(ChatColor.RED + "That player is not OP!"));
                    return true;
                }

                Bukkit.getOfflinePlayer(name).setOp(false);
                player.sendMessage(Lang.CHAT_TAG.getServerMessage(ChatColor.GRAY + "You have deopped " + ChatColor.BLUE + Bukkit.getOfflinePlayer(name).getName() + ChatColor.GRAY + "."));
                return true;
            } else {
                player.sendMessage(Lang.SYNTAX_ERROR.getServerMessage());
                return true;
            }
        } else if (sender instanceof ConsoleCommandSender) {
            ConsoleCommandSender console = (ConsoleCommandSender) sender;

            if (args.length == 0) {
                console.sendMessage(Lang.CHAT_TAG.getServerMessage(ChatColor.AQUA + "/deop" + ChatColor.DARK_AQUA + " [player]" + ChatColor.GRAY + "."));
                return true;
            } else if (args.length == 1) {
                String name = args[0];
                if (!NECore.getUserManager().userExists(Bukkit.getOfflinePlayer(name).getUniqueId())) {
                    console.sendMessage(Lang.PLAYER_NULL.getServerMessage());
                    return true;
                }

                if (!Bukkit.getOfflinePlayer(name).isOp()) {
                    console.sendMessage(Lang.CHAT_TAG.getServerMessage(ChatColor.RED + "That player is not OP!"));
                    return true;
                }

                Bukkit.getOfflinePlayer(name).setOp(false);
                console.sendMessage(Lang.CHAT_TAG.getServerMessage(ChatColor.GRAY + "You have deopped " + ChatColor.BLUE + Bukkit.getOfflinePlayer(name).getName() + ChatColor.GRAY + "."));
                return true;
            } else {
                console.sendMessage(Lang.SYNTAX_ERROR.getServerMessage());
                return true;
            }
        }
        return true;
    }
}