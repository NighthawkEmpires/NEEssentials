package net.nighthawkempires.essentials.commands;

import com.google.common.collect.Lists;
import net.nighthawkempires.core.language.Lang;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class WeatherCommand implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (!player.hasPermission("ne.weather")) {
                player.sendMessage(Lang.NO_PERM.getServerMessage());
                return true;
            }

            if (args.length == 0) {
                String[] help = new String[] {
                        Lang.HEADER.getServerHeader(),
                        Lang.CMD_NAME.getCommandName("weather"),
                        Lang.FOOTER.getMessage(),
                        Lang.CMD_HELP.getCommand("weather", "list", "List all weather conditions"),
                        Lang.CMD_HELP.getCommand("weather", "set [value]", "Set the weather conditions"),
                        Lang.FOOTER.getMessage()
                };
                player.sendMessage(help);
            } else if (args.length == 1) {
                if (args[0].toLowerCase().equals("list")) {
                    ArrayList<String> types = Lists.newArrayList("Clear", "Rain", "Thunder");
                    StringBuilder builder = new StringBuilder();

                    for (String s : types) {
                        builder.append(ChatColor.BLUE).append(s).append(ChatColor.DARK_GRAY).append(", ");
                    }

                    String type = builder.toString().substring(0, builder.toString().length() - 2);
                    String[] list = new String[]{
                            Lang.HEADER.getServerHeader(),
                            Lang.LIST.getListName("Weather Conditions"),
                            Lang.FOOTER.getMessage(),
                            type,
                            Lang.FOOTER.getMessage(),
                    };
                    player.sendMessage(list);
                } else {
                    player.sendMessage(Lang.SYNTAX_ERROR.getServerMessage());
                    return true;
                }
            } else if (args.length == 2) {
                if (args[0].toLowerCase().equals("set")) {
                    World world = player.getWorld();
                    World.Environment environment = world.getEnvironment();

                    if (environment.equals(World.Environment.NETHER) || environment.equals(World.Environment.THE_END)) {
                        player.sendMessage(Lang.CHAT_TAG.getServerMessage(ChatColor.RED + "You can not change the weather conditions in this world!"));
                        return true;
                    }

                    if (args[1].toLowerCase().equals("clear")) {
                        world.setStorm(false);
                        world.setThundering(false);
                        player.sendMessage(Lang.CHAT_TAG.getServerMessage(ChatColor.GRAY + "You have set the weather conditions in " + ChatColor.YELLOW + world.getName() + ChatColor.GRAY
                                + " to " + ChatColor.BLUE + "CLEAR" + ChatColor.GRAY + "."));
                    } else if (args[1].toLowerCase().equals("rain")) {
                        world.setStorm(true);
                        world.setThundering(false);
                        player.sendMessage(Lang.CHAT_TAG.getServerMessage(ChatColor.GRAY + "You have set the weather conditions in " + ChatColor.YELLOW + world.getName() + ChatColor.GRAY
                                + " to " + ChatColor.BLUE + "RAINING" + ChatColor.GRAY + "."));
                    } else if (args[1].toLowerCase().equals("thunder")) {
                        world.setStorm(true);
                        world.setThundering(true);
                        player.sendMessage(Lang.CHAT_TAG.getServerMessage(ChatColor.GRAY + "You have set the weather conditions in " + ChatColor.YELLOW + world.getName() + ChatColor.GRAY
                                + " to " + ChatColor.BLUE + "THUNDERING" + ChatColor.GRAY + "."));
                    } else {
                        player.sendMessage(Lang.CHAT_TAG.getServerMessage(ChatColor.RED + "That is not a valid weather condition."));
                        return true;
                    }
                } else {
                    player.sendMessage(Lang.SYNTAX_ERROR.getServerMessage());
                    return true;
                }
            } else {
                player.sendMessage(Lang.SYNTAX_ERROR.getServerMessage());
                return true;
            }
        }  else if (sender instanceof ConsoleCommandSender) {
            ConsoleCommandSender console = (ConsoleCommandSender) sender;
            console.sendMessage(Lang.CHAT_TAG.getServerMessage(ChatColor.RED + "This command is not available from the console!"));
            return true;
        }
        return true;
    }
}
