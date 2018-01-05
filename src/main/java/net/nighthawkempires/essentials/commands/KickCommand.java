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

public class KickCommand implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            User user = NECore.getUserManager().getUser(player.getUniqueId());

            if (!player.hasPermission("ne.kick")) {
                player.sendMessage(Lang.NO_PERM.getServerMessage());
                return true;
            }

            if (args.length == 0) {
                String[] help = new String[] {
                        Lang.HEADER.getServerHeader(),
                        Lang.CMD_NAME.getCommandName("kick"),
                        Lang.FOOTER.getMessage(),
                        Lang.CMD_HELP.getCommand("kick", "[player]", "Kick a player"),
                        Lang.CMD_HELP.getCommand("kick", "[player] [reason]", "Kick a player with reason"),
                        Lang.FOOTER.getMessage(),
                };
                player.sendMessage(help);
            } else if (args.length == 1) {
                String name = args[0];
                if (!Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(name))) {
                    player.sendMessage(Lang.PLAYER_NULL.getServerMessage());
                    return true;
                }

                String kicked = ChatColor.translateAlternateColorCodes('&', "\n&c&l&oKICKED&7&l&o!&r\n   \n&8&l&oBy&7&l&o: &9&l&o" + player.getName() + "&r");

                Player target = Bukkit.getPlayer(name);
                player.sendMessage(Lang.CHAT_TAG.getServerChatTag() + ChatColor.GRAY + "You kicked " + ChatColor.BLUE + target.getName() + ChatColor.GRAY + ".");
                target.kickPlayer(kicked);
                for (Player players : Bukkit.getOnlinePlayers()) {
                    players.sendMessage(Lang.CHAT_TAG.getServerChatTag() + ChatColor.BLUE + player.getName() + ChatColor.GRAY + " kicked " + ChatColor.BLUE + target.getName() + ChatColor.GRAY + ".");
                }
                return true;
            } else {
                String name = args[0];
                if (!Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(name))) {
                    player.sendMessage(Lang.PLAYER_NULL.getServerMessage());
                    return true;
                }

                StringBuilder builder = new StringBuilder();
                for (String string : args) {
                    builder.append(string).append(" ");
                }
                String reason = builder.substring(args[0].length() + 1, builder.length() - 1);
                String kicked = ChatColor.translateAlternateColorCodes('&', "\n&c&l&oKICKED&7&l&o!&r\n   \n&8&l&oBy&7&l&o: &9&l&o" + player.getName()
                        + "&r\n&8&l&oReason&7&l&o: &7&l&o" + reason + ChatColor.GRAY + ".");

                Player target = Bukkit.getPlayer(name);
                target.kickPlayer(kicked);
                for (Player players : Bukkit.getOnlinePlayers()) {
                    players.sendMessage(Lang.CHAT_TAG.getServerChatTag() + ChatColor.BLUE + player.getName() + ChatColor.GRAY + " kicked " + ChatColor.BLUE + target.getName()
                            + ChatColor.GRAY + " for: " + reason + ChatColor.GRAY + ".");
                }
                return true;
            }
        } else if (sender instanceof ConsoleCommandSender) {
            ConsoleCommandSender console = (ConsoleCommandSender) sender;

            if (args.length == 0) {
                String[] help = new String[] {
                        Lang.HEADER.getServerHeader(),
                        Lang.CMD_NAME.getCommandName("kick"),
                        Lang.FOOTER.getMessage(),
                        Lang.CMD_HELP.getCommand("kick", "[player]", "Kick a player"),
                        Lang.CMD_HELP.getCommand("kick", "[player] [reason]", "Kick a player with reason"),
                        Lang.FOOTER.getMessage(),
                };
                console.sendMessage(help);
            } else if (args.length == 1) {
                String name = args[0];
                if (!Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(name))) {
                    console.sendMessage(Lang.PLAYER_NULL.getServerMessage());
                    return true;
                }

                String kicked = ChatColor.translateAlternateColorCodes('&', "\n&c&l&oKICKED&7&l&o!&r\n   \n&8&l&oBy&7&l&o: &9&l&o" + NECore.getSettings().consoleDisplay + "&r");

                Player target = Bukkit.getPlayer(name);
                console.sendMessage(Lang.CHAT_TAG.getServerChatTag() + ChatColor.GRAY + "You kicked " + ChatColor.BLUE + target.getName() + ChatColor.GRAY + ".");
                target.kickPlayer(kicked);
                for (Player players : Bukkit.getOnlinePlayers()) {
                    players.sendMessage(Lang.CHAT_TAG.getServerChatTag() + ChatColor.BLUE + NECore.getSettings().consoleDisplay + ChatColor.GRAY + " kicked " + ChatColor.BLUE + target.getName() + ChatColor.GRAY + ".");
                }
                return true;
            } else {
                String name = args[0];
                if (!Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(name))) {
                    console.sendMessage(Lang.PLAYER_NULL.getServerMessage());
                    return true;
                }

                StringBuilder builder = new StringBuilder();
                for (String string : args) {
                    builder.append(string).append(" ");
                }
                String reason = builder.substring(args[0].length() + 1, builder.length() - 1);
                String kicked = ChatColor.translateAlternateColorCodes('&', "\n&c&l&oKICKED&7&l&o!&r\n   \n&8&l&oBy&7&l&o: &9&l&o" + NECore.getSettings().consoleDisplay
                        + "&r\n&8&l&oReason&7&l&o: &7&l&o" + reason + ChatColor.GRAY + ".");

                Player target = Bukkit.getPlayer(name);
                target.kickPlayer(kicked);
                for (Player players : Bukkit.getOnlinePlayers()) {
                    players.sendMessage(Lang.CHAT_TAG.getServerChatTag() + ChatColor.BLUE + NECore.getSettings().consoleDisplay + ChatColor.GRAY + " kicked " + ChatColor.BLUE + target.getName()
                            + ChatColor.GRAY + " for: " + reason + ChatColor.GRAY + ".");
                }
                return true;
            }
        }
        return true;
    }
}
