package net.nighthawkempires.essentials.commands;

import net.nighthawkempires.core.language.Lang;
import net.nighthawkempires.core.utils.LocationUtil;
import net.nighthawkempires.essentials.NEEssentials;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class WarpCommand implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (!player.hasPermission("ne.warp")) {
                player.sendMessage(Lang.NO_PERM.getServerMessage());
                return true;
            }

            if (args.length == 0) {
                String[] help = new String[] {
                        Lang.HEADER.getServerHeader(),
                        Lang.CMD_NAME.getCommandName("warp"),
                        Lang.FOOTER.getMessage(),
                        Lang.CMD_HELP.getCommand("warp", "list", "List all warps"),
                        Lang.CMD_HELP.getCommand("warp", "[name]", "Warp to a warp"),
                        Lang.CMD_HELP.getCommand("warp", "set [name]", "Set a warp"),
                        Lang.CMD_HELP.getCommand("warp", "remove [name]", "Remove a warp"),
                        Lang.FOOTER.getMessage(),
                };
                player.sendMessage(help);
                return true;
            } else if (args.length == 1) {
                if (args[0].toLowerCase().equals("list")) {
                    String[] info = new String[] {
                            Lang.HEADER.getServerHeader(),
                            Lang.LIST.getListName("warps"),
                            Lang.FOOTER.getMessage(),
                    };
                    StringBuilder warps = new StringBuilder();
                    if (!LocationUtil.getWarps().isEmpty()) {
                        for (String string : LocationUtil.getWarps()) {
                            warps.append(ChatColor.GREEN).append(string).append(ChatColor.DARK_GRAY).append(", ");
                        }
                    } else {
                        warps.append(ChatColor.RED).append("NaNaN");
                    }
                    player.sendMessage(info);
                    player.sendMessage(ChatColor.DARK_GRAY + " -" + warps.substring(0, warps.length() - 2));
                    player.sendMessage(Lang.FOOTER.getMessage());
                    return true;
                } else {
                    String name = args[0];

                    if (!LocationUtil.warpExists(name)) {
                        player.sendMessage(Lang.CHAT_TAG.getServerMessage(ChatColor.RED + "That warp does not exist!"));
                        return true;
                    }

                    if (!player.hasPermission("ne.no.cmdwarmup")) {
                        NEEssentials.getData().dontMove.add(player.getUniqueId());
                        player.sendMessage(Lang.CHAT_TAG.getServerMessage(ChatColor.GRAY + "Executing " + ChatColor.AQUA + "/warp " + ChatColor.DARK_AQUA + name + ChatColor.GRAY + " in "
                                + ChatColor.GOLD + "4 seconds" + ChatColor.GRAY + "."));
                        Bukkit.getScheduler().scheduleSyncDelayedTask(NEEssentials.getPlugin(), () -> {
                            if (NEEssentials.getData().dontMove.contains(player.getUniqueId())) {
                                NEEssentials.getData().dontMove.remove(player.getUniqueId());
                                player.sendMessage(Lang.CHAT_TAG.getServerMessage(ChatColor.GRAY + "You have warped to " + ChatColor.BLUE + name + ChatColor.GRAY + "."));
                                player.teleport(LocationUtil.getWarp(name));
                            }
                        }, 80L);
                        return true;
                    }

                    player.sendMessage(Lang.CHAT_TAG.getServerMessage(ChatColor.GRAY + "You have warped to " + ChatColor.BLUE + name + ChatColor.GRAY + "."));
                    player.teleport(LocationUtil.getWarp(name));
                    return true;
                }
            } else if (args.length == 2) {
                if (!player.hasPermission("ne.admin")) {
                    player.sendMessage(Lang.NO_PERM.getServerMessage());
                    return true;
                }

                if (args[0].toLowerCase().equals("set")) {
                    String name = args[1];

                    if (LocationUtil.warpExists(name)) {
                        player.sendMessage(Lang.CHAT_TAG.getServerMessage(ChatColor.RED + "That warp already exists!"));
                        return true;
                    }

                    LocationUtil.setWarp(name, player.getLocation());
                    player.sendMessage(Lang.CHAT_TAG.getServerMessage(ChatColor.GRAY + "You have set warp " + ChatColor.BLUE + name + ChatColor.GRAY + "."));
                } else if (args[0].toLowerCase().equals("remove")) {
                    String name = args[1];

                    if (!LocationUtil.warpExists(name)) {
                        player.sendMessage(Lang.CHAT_TAG.getServerMessage(ChatColor.RED + "That warp does not exist!"));
                        return true;
                    }

                    LocationUtil.deleteWarp(name);
                    player.sendMessage(Lang.CHAT_TAG.getServerMessage(ChatColor.GRAY + "You have removed warp " + ChatColor.BLUE + name + ChatColor.GRAY + "."));
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
