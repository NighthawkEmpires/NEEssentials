package net.nighthawkempires.essentials.commands;

import com.google.common.collect.Sets;
import net.nighthawkempires.core.NECore;
import net.nighthawkempires.core.language.Lang;
import net.nighthawkempires.core.users.User;
import net.nighthawkempires.core.utils.LocationUtil;
import org.apache.commons.lang.math.NumberUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.Set;

public class SpawnMobCommand implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            User user = NECore.getUserManager().getUser(player.getUniqueId());

            if (!player.hasPermission("ne.spawnmob")) {
                player.sendMessage(Lang.NO_PERM.getServerMessage());
                return true;
            }

            if (args.length == 0) {
                String[] help = new String[] {
                        Lang.HEADER.getServerHeader(),
                        Lang.CMD_NAME.getCommandName("spawnmob"),
                        Lang.FOOTER.getMessage(),
                        Lang.CMD_HELP.getCommand("spawnmob", "list", "Show a list of mobs"),
                        Lang.CMD_HELP.getCommand("spawnmob", "[mob]", "Spawn a mob"),
                        Lang.CMD_HELP.getCommand("spawnmob", "[mob] [amount]", "Spawn a number of mobs"),
                        Lang.FOOTER.getMessage()
                };
                player.sendMessage(help);
                return true;
            } else if (args.length == 1) {
                if (args[0].toLowerCase().equals("list")) {
                    StringBuilder stringBuilder = new StringBuilder();
                    for (EntityType type : EntityType.values()) {
                        stringBuilder.append(ChatColor.GREEN).append(type.name()).append(ChatColor.DARK_GRAY).append(", ");
                    }
                    String mobs = stringBuilder.substring(0, stringBuilder.length() -2);
                    String[] list = new String[] {
                            Lang.HEADER.getServerHeader(),
                            Lang.LIST.getListName("Mob Types"),
                            Lang.FOOTER.getMessage(),
                            ChatColor.DARK_GRAY + " - " + mobs,
                            Lang.FOOTER.getMessage(),
                    };
                    player.sendMessage(list);
                    return true;
                } else {
                    String type = args[0].toUpperCase();
                    EntityType entityType = null;
                    for (EntityType types : EntityType.values()) {
                        if (types.name().equals(type)) {
                            entityType = types;
                        }
                    }

                    if (entityType == null) {
                        player.sendMessage(Lang.CHAT_TAG.getServerMessage(ChatColor.RED + "That is an invalid mob type!"));
                        return true;
                    }

                    try {
                        player.getWorld().spawnEntity(LocationUtil.getTargetBlock(player, 10), entityType);
                    } catch (Exception e) {
                        player.sendMessage(Lang.CHAT_TAG.getServerMessage(ChatColor.RED + "That mob is not spawnable."));
                        return true;
                    }
                    player.sendMessage(Lang.CHAT_TAG.getServerMessage(ChatColor.GRAY + "You have spawned " + ChatColor.GOLD + "1 " + ChatColor.BLUE + entityType.name() + ChatColor.DARK_GRAY + "(s)."));
                    return true;
                }
            } else if (args.length == 2) {
                String type = args[0].toUpperCase();
                EntityType entityType = null;
                for (EntityType types : EntityType.values()) {
                    if (types.name().equals(type)) {
                        entityType = types;
                    }
                }

                if (entityType == null) {
                    player.sendMessage(Lang.CHAT_TAG.getServerMessage(ChatColor.RED + "That is an invalid mob type!"));
                    return true;
                }

                if (!NumberUtils.isDigits(args[1])) {
                    player.sendMessage(Lang.CHAT_TAG.getServerMessage(ChatColor.RED + "That is an invalid amount!"));
                    return true;
                }

                int amount = Integer.parseInt(args[1]);

                try {
                    for (int i = 0; i < amount; i++) {
                        player.getWorld().spawnEntity(LocationUtil.getTargetBlock(player, 10), entityType);
                    }
                } catch (Exception e) {
                    player.sendMessage(Lang.CHAT_TAG.getServerMessage(ChatColor.RED + "That mob is not spawnable."));
                    return true;
                }
                player.sendMessage(Lang.CHAT_TAG.getServerMessage(ChatColor.GRAY + "You have spawned " + ChatColor.GOLD + amount + " " + ChatColor.BLUE + entityType.name() + ChatColor.DARK_GRAY + "(s)."));
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
