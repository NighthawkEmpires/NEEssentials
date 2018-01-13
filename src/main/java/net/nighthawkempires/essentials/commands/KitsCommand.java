package net.nighthawkempires.essentials.commands;

import com.google.common.collect.Lists;
import net.nighthawkempires.core.NECore;
import net.nighthawkempires.core.kit.Kit;
import net.nighthawkempires.core.language.Lang;
import net.nighthawkempires.core.users.UserModel;
import net.nighthawkempires.core.utils.CooldownUtil;
import net.nighthawkempires.core.utils.ItemUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class KitsCommand implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (!player.hasPermission("ne.kits")) {
                player.sendMessage(Lang.NO_PERM.getServerMessage());
                return true;
            }

            if (args.length == 0) {
                if (NECore.getKitManager().getKits().isEmpty()) {
                    player.sendMessage(Lang.CHAT_TAG.getServerMessage(ChatColor.RED + "There are no available kits!"));
                    return true;
                }

                Inventory inventory = Bukkit.createInventory(player, 18, ChatColor.RED + "" + ChatColor.BOLD + "" + ChatColor.ITALIC + "Kits");
                for (Kit kit : NECore.getKitManager().getKits()) {
                    inventory.addItem(ItemUtil.customItem(Material.CHEST, 1, ChatColor.YELLOW + kit.getName().substring(0,1).toLowerCase() + kit.getName().substring(1),
                            Lists.newArrayList(ChatColor.DARK_GRAY + "Cooldown" + ChatColor.GRAY + ": " + ChatColor.GOLD + TimeUnit.SECONDS.toHours(kit.getCooldown()) + " hours")));
                }
                player.openInventory(inventory);
                return true;
            } else if (args.length == 1) {
                String name = args[0];

                boolean exists = false;
                Kit kit = null;
                for (Kit kit1 : NECore.getKitManager().getKits()) {
                    if (kit1.getName().toLowerCase().equals(name.toLowerCase())) {
                        exists = true;
                        kit = kit1;
                    }
                }

                if (!exists) {
                    player.sendMessage(Lang.CHAT_TAG.getServerMessage(ChatColor.RED + "That kit does not exist!"));
                    return true;
                }

                if (!CooldownUtil.cooledDown(player.getUniqueId(), kit.getName())) {
                    player.sendMessage(Lang.CHAT_TAG.getServerMessage(ChatColor.RED + "You have to wait another " + ChatColor.GOLD
                            + TimeUnit.SECONDS.toHours(CooldownUtil.timeLeft(player.getUniqueId(), kit.getName())) + " hours" + ChatColor.GRAY + "."));
                    return true;
                }

                player.getInventory().addItem(kit.getItems());
                player.sendMessage(Lang.CHAT_TAG.getServerMessage(ChatColor.GRAY + "You have redeemed kit " + ChatColor.YELLOW + kit.getName() + ChatColor.GRAY + "."));
                CooldownUtil.setCooldown(player.getUniqueId(), kit.getName(), (int) kit.getCooldown());
                return true;
            } else if (args.length == 2) {
                if (args[0].toLowerCase().equals("create")) {
                    if (!sender.hasPermission("ne.kits.admin")) {
                        player.sendMessage(Lang.NO_PERM.getServerMessage());
                        return true;
                    }
                    String name = args[1];

                    for (Kit kit : NECore.getKitManager().getKits()) {
                        if (kit.getName().toLowerCase().equals(name.toLowerCase())) {
                            player.sendMessage(Lang.CHAT_TAG.getServerMessage(ChatColor.RED + "That kit already exists!"));
                            return true;
                        }
                    }


                    ItemStack display = player.getInventory().getItem(0);
                    long cooldown = 60L*60L*24L;

                    List<ItemStack> stacks = Lists.newArrayList();
                    for (ItemStack itemStack : player.getInventory().getContents()) {
                        if (itemStack != null) {
                            stacks.add(itemStack);
                        }
                    }
                    ItemStack[] items;
                    if (stacks == null) {
                        items = null;
                    } else {
                        ItemStack[] l = new ItemStack[stacks.size()];
                        l = stacks.toArray(l);
                        items = l;
                    }
                    NECore.getKitManager().addKit(name, display, cooldown, items);
                    player.sendMessage(Lang.CHAT_TAG.getServerMessage(ChatColor.GRAY + "You created kit " + ChatColor.YELLOW + name + ChatColor.YELLOW + "."));
                } else if (args[0].toLowerCase().equals("delete")) {
                    if (!sender.hasPermission("ne.kits.admin")) {
                        player.sendMessage(Lang.NO_PERM.getServerMessage());
                        return true;
                    }

                    String name = args[1];

                    boolean exists = false;
                    Kit kit = null;
                    for (Kit kit1 : NECore.getKitManager().getKits()) {
                        if (kit1.getName().toLowerCase().equals(args[1].toLowerCase())) {
                            exists = true;
                            kit = kit1;
                        }
                    }

                    if (!exists || kit == null) {
                        player.sendMessage(Lang.CHAT_TAG.getServerMessage(ChatColor.RED + "That kit does not exist!"));
                        return true;
                    }

                    NECore.getKitManager().getKits().remove(kit);
                    player.sendMessage(Lang.CHAT_TAG.getServerMessage(ChatColor.GRAY + "You have deleted kit " + ChatColor.YELLOW + kit.getName() + ChatColor.GRAY + "."));
                    return true;
                } else {
                    player.sendMessage(Lang.SYNTAX_ERROR.getServerMessage());
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
