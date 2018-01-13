package net.nighthawkempires.essentials.commands;

import net.nighthawkempires.core.NECore;
import net.nighthawkempires.core.language.Lang;
import net.nighthawkempires.core.users.UserModel;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class InventoryCommand implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (!player.hasPermission("ne.invsee")) {
                player.sendMessage(Lang.NO_PERM.getServerMessage());
                return true;
            }

            if (args.length == 0) {
                Inventory inventory = Bukkit.createInventory(player, 5*9, ChatColor.BLUE + player.getName() + "'s " + ChatColor.BLACK + "Inventory");
                inventory.setContents(player.getInventory().getContents());
                inventory.setItem(36, player.getInventory().getBoots());
                inventory.setItem(37, player.getInventory().getLeggings());
                inventory.setItem(38, player.getInventory().getChestplate());
                inventory.setItem(39, player.getInventory().getHelmet());
                inventory.setItem(40, player.getInventory().getItemInOffHand());
                player.openInventory(inventory);
                player.sendMessage(Lang.CHAT_TAG.getServerChatTag() + ChatColor.GRAY + "You have opened your inventory.");
                return true;
            } else if (args.length == 1) {
                String name = args[0];
                if (!Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(name))) {
                    player.sendMessage(Lang.PLAYER_NULL.getServerMessage());
                    return true;
                }

                Player target = Bukkit.getPlayer(args[0]);
                Inventory inventory = Bukkit.createInventory(player, 5*9, ChatColor.BLUE + target.getName() + "'s " + ChatColor.BLACK + "Inventory");
                inventory.setContents(target.getInventory().getContents());
                inventory.setItem(36, target.getInventory().getBoots());
                inventory.setItem(37, target.getInventory().getLeggings());
                inventory.setItem(38, target.getInventory().getChestplate());
                inventory.setItem(39, target.getInventory().getHelmet());
                inventory.setItem(40, target.getInventory().getItemInOffHand());
                player.openInventory(inventory);
                player.sendMessage(Lang.CHAT_TAG.getServerChatTag() + ChatColor.GRAY + "You have opened " + ChatColor.BLUE + target.getName() + "'s " + ChatColor.GRAY + "inventory.");
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
