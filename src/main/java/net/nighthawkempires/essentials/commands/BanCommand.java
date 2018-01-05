package net.nighthawkempires.essentials.commands;

import net.nighthawkempires.core.NECore;
import net.nighthawkempires.core.language.Lang;
import net.nighthawkempires.core.users.User;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class BanCommand implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            User user = NECore.getUserManager().getUser(player.getUniqueId());

            if (!player.hasPermission("ne.ban")) {
                player.sendMessage(Lang.NO_PERM.getServerMessage());
                return true;
            }

            if (args.length == 0) {
                String[] help = new String[] {
                        Lang.HEADER.getServerHeader(),
                        Lang.CMD_NAME.getCommandName("ban"),
                        Lang.FOOTER.getMessage(),
                        Lang.CMD_HELP.getCommand("ban", "[player]", "Ban a player"),
                        Lang.CMD_HELP.getCommand("ban", "[player] [reason]", "Ban a player with reason"),
                        Lang.FOOTER.getMessage(),
                };
                player.sendMessage(help);
                return true;
            } else if (args.length == 1) {
                String name = args[0];

                OfflinePlayer target = Bukkit.getOfflinePlayer(name);
                if (NECore.getBanManager().isBanned(target.getUniqueId())) {
                    player.sendMessage(Lang.CHAT_TAG.getServerChatTag() + ChatColor.GRAY + "That player is already banned!");
                    return true;
                }

                NECore.getBanManager().ban(target.getUniqueId(), "Unspecified", player.getUniqueId());
                player.sendMessage(Lang.CHAT_TAG.getServerChatTag() + ChatColor.GRAY + "You have banned " + ChatColor.BLUE + target.getName() + ChatColor.GRAY + ".");
                if (Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(target.getUniqueId()))) {
                    Player onlineTarget = Bukkit.getPlayer(target.getUniqueId());
                    onlineTarget.kickPlayer(NECore.getBanManager().getBanInfo(onlineTarget.getUniqueId()));
                }
                for (Player players : Bukkit.getOnlinePlayers()) {
                    players.sendMessage(Lang.CHAT_TAG.getServerChatTag() + ChatColor.BLUE + target.getName() + ChatColor.GRAY + " has been banned by " + ChatColor.BLUE
                            + player.getName() + ChatColor.GRAY + ".");
                }
                return true;
            } else {
                String name = args[0];

                StringBuilder builder = new StringBuilder();
                for (String string : args) {
                    builder.append(string).append(" ");
                }
                String reason = builder.substring(args[0].length() + 1);

                OfflinePlayer target = Bukkit.getOfflinePlayer(name);
                if (NECore.getBanManager().isBanned(target.getUniqueId())) {
                    player.sendMessage(Lang.CHAT_TAG.getServerChatTag() + ChatColor.GRAY + "That player is already banned!");
                    return true;
                }

                NECore.getBanManager().ban(target.getUniqueId(), reason, player.getUniqueId());
                player.sendMessage(Lang.CHAT_TAG.getServerChatTag() + ChatColor.GRAY + "You have banned " + ChatColor.BLUE + target.getName() + ChatColor.GRAY + " for: "
                        + ChatColor.RED + reason + ".");
                if (Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(target.getUniqueId()))) {
                    Player onlineTarget = Bukkit.getPlayer(target.getUniqueId());
                    onlineTarget.kickPlayer(NECore.getBanManager().getBanInfo(onlineTarget.getUniqueId()));
                }
                for (Player players : Bukkit.getOnlinePlayers()) {
                    players.sendMessage(Lang.CHAT_TAG.getServerChatTag() + ChatColor.BLUE + target.getName() + ChatColor.GRAY + " has been banned by "
                            + ChatColor.BLUE + player.getName() + ChatColor.GRAY + " for: " + ChatColor.RED + reason + ".");
                }
                return true;
            }
        } else if (sender instanceof ConsoleCommandSender) {
            ConsoleCommandSender console = (ConsoleCommandSender) sender;

            if (args.length == 0) {
                String[] help = new String[] {
                        Lang.HEADER.getServerHeader(),
                        Lang.CMD_NAME.getCommandName("ban"),
                        Lang.FOOTER.getMessage(),
                        Lang.CMD_HELP.getCommand("ban", "[player]", "Ban a player"),
                        Lang.CMD_HELP.getCommand("ban", "[player] [reason]", "Ban a player with reason"),
                        Lang.FOOTER.getMessage(),
                };
                console.sendMessage(help);
                return true;
            } else if (args.length == 1) {
                String name = args[0];

                OfflinePlayer target = Bukkit.getOfflinePlayer(name);
                if (NECore.getBanManager().isBanned(target.getUniqueId())) {
                    console.sendMessage(Lang.CHAT_TAG.getServerChatTag() + ChatColor.GRAY + "That player is already banned!");
                    return true;
                }

                NECore.getBanManager().ban(target.getUniqueId(), "Unspecified", NECore.getSettings().consoleUUID);
                console .sendMessage(Lang.CHAT_TAG.getServerChatTag() + ChatColor.GRAY + "You have banned " + ChatColor.BLUE + target.getName() + ChatColor.GRAY + ".");
                if (Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(target.getUniqueId()))) {
                    Player onlineTarget = Bukkit.getPlayer(target.getUniqueId());
                    onlineTarget.kickPlayer(NECore.getBanManager().getBanInfo(onlineTarget.getUniqueId()));
                }
                for (Player players : Bukkit.getOnlinePlayers()) {
                    players.sendMessage(Lang.CHAT_TAG.getServerChatTag() + ChatColor.BLUE + target.getName() + ChatColor.GRAY + " has been banned by " + ChatColor.BLUE
                            + NECore.getSettings().consoleDisplay + ChatColor.GRAY + ".");
                }
                return true;
            } else {
                String name = args[0];

                StringBuilder builder = new StringBuilder();
                for (String string : args) {
                    builder.append(string).append(" ");
                }
                String reason = builder.substring(args[0].length() + 1, builder.length() -1);

                OfflinePlayer target = Bukkit.getOfflinePlayer(name);
                if (NECore.getBanManager().isBanned(target.getUniqueId())) {
                    console.sendMessage(Lang.CHAT_TAG.getServerChatTag() + ChatColor.GRAY + "That player is already banned!");
                    return true;
                }

                NECore.getBanManager().ban(target.getUniqueId(), reason, NECore.getSettings().consoleUUID);
                console.sendMessage(Lang.CHAT_TAG.getServerChatTag() + ChatColor.GRAY + "You have banned " + ChatColor.BLUE + target.getName() + ChatColor.GRAY + " for: "
                        + ChatColor.RED + reason + ".");
                if (Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(target.getUniqueId()))) {
                    Player onlineTarget = Bukkit.getPlayer(target.getUniqueId());
                    onlineTarget.kickPlayer(NECore.getBanManager().getBanInfo(onlineTarget.getUniqueId()));
                }
                for (Player players : Bukkit.getOnlinePlayers()) {
                    players.sendMessage(Lang.CHAT_TAG.getServerChatTag() + ChatColor.BLUE + target.getName() + ChatColor.GRAY + " has been banned by "
                            + ChatColor.BLUE + NECore.getSettings().consoleDisplay + ChatColor.GRAY + " for: " + ChatColor.RED + reason + ".");
                }
                return true;
            }
        }
        return true;
    }
}
