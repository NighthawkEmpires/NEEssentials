package net.nighthawkempires.essentials.commands;

import net.nighthawkempires.core.language.Lang;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SlapCommand implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (!player.hasPermission("ne.slap")) {
                player.sendMessage(Lang.NO_PERM.getServerMessage());
                return true;
            }

            if (args.length == 0) {
                player.sendMessage(Lang.CHAT_TAG.getServerMessage(ChatColor.AQUA + "/slap " + ChatColor.DARK_AQUA + "[player]" + ChatColor.GRAY + "."));
            } else if (args.length == 1) {
                String name = args[0];
                if (!Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(name))) {
                    player.sendMessage(Lang.PLAYER_NULL.getServerMessage());
                    return true;
                }

                Player target = Bukkit.getPlayer(name);
                target.setVelocity(target.getLocation().getDirection().multiply(-1.25));
                for (Player players : Bukkit.getOnlinePlayers()) {
                    players.sendMessage(Lang.CHAT_TAG.getServerMessage(ChatColor.BLUE + player.getName() + ChatColor.GRAY + " slapped " + ChatColor.BLUE + target.getName() + ChatColor.GRAY + "."));
                }
            }
        } else {

        }
        return true;
    }
}
