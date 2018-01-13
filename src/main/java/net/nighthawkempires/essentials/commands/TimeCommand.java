package net.nighthawkempires.essentials.commands;

import net.nighthawkempires.core.language.Lang;
import org.apache.commons.lang.math.NumberUtils;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class TimeCommand implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (!player.hasPermission("ne.time")) {
                player.sendMessage(Lang.NO_PERM.getServerMessage());
                return true;
            }

            if (args.length == 0 || args.length == 1) {
                player.sendMessage(Lang.CHAT_TAG.getServerMessage(ChatColor.AQUA + "/time" + ChatColor.DARK_AQUA + " set [value]"));
                return true;
            } else if (args.length == 2) {
                if (args[0].toLowerCase().equals("set")) {
                    World world = player.getWorld();
                    World.Environment environment = world.getEnvironment();

                    if (environment.equals(World.Environment.THE_END) || environment.equals(World.Environment.NETHER)) {
                        player.sendMessage(Lang.CHAT_TAG.getServerMessage(ChatColor.RED + "You cannot set the time in this world."));
                        return true;
                    }
                    if (args[1].toLowerCase().equals("day")) {
                        world.setTime(1000L);
                        player.sendMessage(Lang.CHAT_TAG.getServerMessage(ChatColor.GRAY + "You have set the time in " + ChatColor.YELLOW + world.getName() + ChatColor.GRAY + " to "
                                + ChatColor.BLUE + "DAY" + ChatColor.GRAY + "."));
                    } else if (args[1].toLowerCase().equals("night")) {
                        world.setTime(13000L);
                        player.sendMessage(Lang.CHAT_TAG.getServerMessage(ChatColor.GRAY + "You have set the time in " + ChatColor.YELLOW + world.getName() + ChatColor.GRAY + " to "
                                + ChatColor.BLUE + "NIGHT" + ChatColor.GRAY + "."));
                    } else {
                        if (!NumberUtils.isDigits(args[1])) {
                            player.sendMessage(Lang.CHAT_TAG.getServerMessage(ChatColor.RED + "That is not a valid number!"));
                            return true;
                        }
                        Long time = Long.parseLong(args[1]);

                        world.setTime(time);
                        player.sendMessage(Lang.CHAT_TAG.getServerMessage(ChatColor.GRAY + "You have set the time in " + ChatColor.YELLOW + world.getName() + ChatColor.GRAY + " to "
                                + time + ChatColor.GRAY + "."));
                    }
                } else {
                    player.sendMessage(Lang.SYNTAX_ERROR.getServerMessage());
                    return true;
                }
            } else {
                player.sendMessage(Lang.SYNTAX_ERROR.getServerMessage());
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
