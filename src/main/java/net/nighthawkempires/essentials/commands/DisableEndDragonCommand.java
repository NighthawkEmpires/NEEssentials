package net.nighthawkempires.essentials.commands;

import net.nighthawkempires.core.language.Lang;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class DisableEndDragonCommand implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (!player.hasPermission("ne.admin")) {
                player.sendMessage(Lang.NO_PERM.getServerMessage());
                return true;
            }

            if (args.length == 0) {
                player.sendMessage(Lang.CHAT_TAG.getServerMessage(ChatColor.GRAY + "use /ded on or off."));
                return true;
            } else if (args.length == 1) {
                if (args[0].toLowerCase().equals("on")) {
                    for (Entity entity : player.getNearbyEntities(50, 50, 50)) {
                        if (entity instanceof EnderDragon) {
                            ((EnderDragon) entity).setAI(true);
                        }
                    }
                } else if (args[0].toLowerCase().equals("off")) {
                    for (Entity entity : player.getNearbyEntities(50, 50, 50)) {
                        if (entity instanceof EnderDragon) {
                            ((EnderDragon) entity).setAI(false);
                        }
                    }
                }
            }
        }
        return true;
    }
}
