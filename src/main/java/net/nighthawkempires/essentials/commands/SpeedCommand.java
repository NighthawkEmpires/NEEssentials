package net.nighthawkempires.essentials.commands;

import net.nighthawkempires.core.NECore;
import net.nighthawkempires.core.language.Lang;
import net.nighthawkempires.core.users.User;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class SpeedCommand implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            User user = NECore.getUserManager().getUser(player.getUniqueId());

            if (!player.hasPermission("ne.speed")) {
                player.sendMessage(Lang.NO_PERM.getServerMessage());
                return true;
            }

            if (args.length == 0) {
                String[] help = new String[] {
                        Lang.HEADER.getServerHeader(),
                        Lang.CMD_NAME.getCommandName("speed"),
                        Lang.FOOTER.getMessage(),
                        Lang.CMD_HELP.getCommand("speed", "walk [speed]", "Set your walk speed"),
                        Lang.CMD_HELP.getCommand("speed", "fly [speed]", "Set your fly speed"),
                        Lang.FOOTER.getMessage()
                };
                player.sendMessage(help);
                return true;
            } else if (args.length == 1) {
                if (args[0].toLowerCase().equals("walk") || args[0].toLowerCase().equals("w")) {
                    player.sendMessage(Lang.CHAT_TAG.getServerMessage(ChatColor.GRAY + "Your walk speed is set to " + ChatColor.GOLD + player.getWalkSpeed() * 10 + ChatColor.GRAY + "."));
                } else if (args[0].toLowerCase().equals("fly") || args[0].toLowerCase().equals("f")) {
                    player.sendMessage(Lang.CHAT_TAG.getServerMessage(ChatColor.GRAY + "Your fly speed is set to " + ChatColor.GOLD + player.getFlySpeed() * 10 + ChatColor.GRAY + "."));
                } else {
                    player.sendMessage(Lang.SYNTAX_ERROR.getServerMessage());
                    return true;
                }
            } else if (args.length == 2) {
                if (args[0].toLowerCase().equals("walk") || args[0].toLowerCase().equals("w")) {
                    float speed = Float.parseFloat(args[1]);
                    float decimalspeed = speed / 10;

                    if (decimalspeed > 1) {
                        player.sendMessage(Lang.HEADER.getServerMessage(ChatColor.RED + "That value is too high! The max is 10"));
                        return true;
                    }

                    player.setWalkSpeed(decimalspeed);
                    player.sendMessage(Lang.CHAT_TAG.getServerMessage(ChatColor.GRAY + "You have set your walk speed to " + ChatColor.GOLD + speed + ChatColor.GRAY + "."));
                } else if (args[0].toLowerCase().equals("fly") || args[0].toLowerCase().equals("f")) {
                    float speed = Float.parseFloat(args[1]);
                    float decimalspeed = speed / 10;

                    if (decimalspeed > 1) {
                        player.sendMessage(Lang.HEADER.getServerMessage(ChatColor.RED + "That value is too high! The max is 10"));
                        return true;
                    }

                    player.setFlySpeed(decimalspeed);
                    player.sendMessage(Lang.CHAT_TAG.getServerMessage(ChatColor.GRAY + "You have set your fly speed to " + ChatColor.GOLD + speed + ChatColor.GRAY + "."));
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
