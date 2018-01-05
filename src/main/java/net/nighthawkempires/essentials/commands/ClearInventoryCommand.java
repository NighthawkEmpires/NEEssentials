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
import org.bukkit.inventory.ItemStack;

public class ClearInventoryCommand implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            User user = NECore.getUserManager().getUser(player.getUniqueId());

            if (!player.hasPermission("ne.clear")) {
                player.sendMessage(Lang.NO_PERM.getServerMessage());
                return true;
            }

            if (args.length == 0) {
                player.getInventory().clear();
                player.getInventory().setArmorContents(new ItemStack[]{null, null, null, null});
                player.getInventory().setItemInOffHand(null);
                player.getInventory().setItemInMainHand(null);
                player.getInventory().setExtraContents(null);
                player.sendMessage(Lang.CHAT_TAG.getServerMessage(ChatColor.GRAY + "You have cleared your inventory."));
                return true;
            } else if (args.length == 1) {
                String name = args[0];
                if (!Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(name))) {
                    player.sendMessage(Lang.PLAYER_NULL.getServerMessage());
                    return true;
                }

                Player target = Bukkit.getPlayer(name);
                target.getInventory().clear();
                target.getInventory().setArmorContents(new ItemStack[]{null, null, null, null});
                target.getInventory().setItemInOffHand(null);
                target.getInventory().setItemInMainHand(null);
                target.getInventory().setExtraContents(null);
                target.sendMessage(Lang.CHAT_TAG.getServerMessage(ChatColor.GRAY + "Your inventory has been cleared."));
                player.sendMessage(Lang.CHAT_TAG.getServerMessage(ChatColor.GRAY + "You have cleared " + ChatColor.BLUE + target.getName() + "'s " + ChatColor.GRAY + "inventory."));
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
