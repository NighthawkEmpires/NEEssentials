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

public class GodCommand implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            User user = NECore.getUserManager().getUser(player.getUniqueId());

            if (!player.hasPermission("ne.god")) {
                player.sendMessage(Lang.NO_PERM.getServerMessage());
                return true;
            }

            if (args.length == 0) {
                if (NEEssentials.getData().godMode.contains(player.getUniqueId())) {
                    player.sendMessage(Lang.CHAT_TAG.getServerChatTag() + ChatColor.GRAY + "You " + ChatColor.RED + "disabled " + ChatColor.GRAY + " god mode.");
                    NEEssentials.getData().godMode.remove(player.getUniqueId());
                } else {
                    player.sendMessage(Lang.CHAT_TAG.getServerChatTag() + ChatColor.GRAY + "You " + ChatColor.GREEN + "enabled " + ChatColor.GRAY + " god mode.");
                    NEEssentials.getData().godMode.add(player.getUniqueId());
                }
            } else if (args.length == 1) {
                String name = args[0];
                if (!Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(name))) {
                    player.sendMessage(Lang.PLAYER_NULL.getServerMessage());
                    return true;
                }

                Player target = Bukkit.getPlayer(name);
                if (NEEssentials.getData().godMode.contains(target.getUniqueId())) {
                    player.sendMessage(Lang.CHAT_TAG.getServerChatTag() + ChatColor.GRAY + "You " + ChatColor.RED + "disabled " + ChatColor.GRAY + " god mode for "
                            + ChatColor.BLUE + target.getName() + ChatColor.GRAY + ".");
                    target.sendMessage(Lang.CHAT_TAG.getServerChatTag() + ChatColor.GRAY + "You're no longer in god mode.");
                    NEEssentials.getData().godMode.remove(target.getUniqueId());
                    return true;
                } else {
                    player.sendMessage(Lang.CHAT_TAG.getServerChatTag() + ChatColor.GRAY + "You " + ChatColor.GREEN + "enabled " + ChatColor.GRAY + " god mode for "
                            + ChatColor.BLUE + target.getName() + ChatColor.GRAY + ".");
                    target.sendMessage(Lang.CHAT_TAG.getServerChatTag() + ChatColor.GRAY + "You're now in god mode.");
                    NEEssentials.getData().godMode.add(target.getUniqueId());
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
