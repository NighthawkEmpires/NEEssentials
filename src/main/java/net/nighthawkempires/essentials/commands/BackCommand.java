package net.nighthawkempires.essentials.commands;

import net.nighthawkempires.core.NECore;
import net.nighthawkempires.core.language.Lang;
import net.nighthawkempires.core.users.UserModel;
import net.nighthawkempires.essentials.NEEssentials;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class BackCommand implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (!player.hasPermission("ne.back")) {
                player.sendMessage(Lang.NO_PERM.getServerMessage());
                return true;
            }

            if (args.length != 0) {
                player.sendMessage(Lang.SYNTAX_ERROR.getServerMessage());
                return true;
            }

            if (!NEEssentials.getData().lastLocation.containsKey(player.getUniqueId())) {
                player.sendMessage(Lang.CHAT_TAG.getServerChatTag() + ChatColor.RED + "No last location found!");
                return true;
            }

            Location tp = NEEssentials.getData().lastLocation.get(player.getUniqueId());
            NEEssentials.getData().lastLocation.put(player.getUniqueId(), player.getLocation());
            player.teleport(tp);
            player.sendMessage(Lang.CHAT_TAG.getServerChatTag() + ChatColor.GRAY + "You teleported to your last known location.");
            return true;
        } else if (sender instanceof ConsoleCommandSender) {
            ConsoleCommandSender console = (ConsoleCommandSender) sender;
            console.sendMessage(Lang.CHAT_TAG.getServerMessage(ChatColor.RED + "This command is not available from the console!"));
            return true;
        }
        return true;
    }
}
