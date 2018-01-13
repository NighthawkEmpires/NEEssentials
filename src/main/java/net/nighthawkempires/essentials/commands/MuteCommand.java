package net.nighthawkempires.essentials.commands;

import net.nighthawkempires.core.NECore;
import net.nighthawkempires.core.language.Lang;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class MuteCommand implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (!player.hasPermission("ne.mute")) {
                player.sendMessage(Lang.NO_PERM.getServerMessage());
                return true;
            }

            if (args.length == 0) {
                String[] help = new String[] {
                        Lang.HEADER.getServerHeader(),
                        Lang.CMD_NAME.getCommandName("mute"),
                        Lang.FOOTER.getMessage(),
                        Lang.CMD_HELP.getCommand("mute", "[player]", "Mute a player"),
                        Lang.CMD_HELP.getCommand("mute", "[player] [reason]", "Mute a player with reason"),
                        Lang.FOOTER.getMessage(),
                };
                player.sendMessage(help);
            } else if (args.length == 1) {
                String name = args[0];
                if (!Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(name))) {
                    player.sendMessage(Lang.PLAYER_NULL.getServerMessage());
                    return true;
                }

                Player target = Bukkit.getPlayer(name);
                NECore.getMuteManager().mute(target.getUniqueId(), "Reason not stated", player.getUniqueId());
                player.sendMessage(Lang.CHAT_TAG.getServerChatTag() + ChatColor.GRAY + "You have muted " + ChatColor.BLUE + target.getName() + ChatColor.GRAY + ".");
                target.sendMessage(Lang.CHAT_TAG.getServerChatTag() + ChatColor.GRAY + "You have been muted.");
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
                String reason = builder.substring(args[0].length() + 1, builder.length() -1);

                Player target = Bukkit.getPlayer(name);
                NECore.getMuteManager().mute(target.getUniqueId(), reason, player.getUniqueId());
                player.sendMessage(Lang.CHAT_TAG.getServerChatTag() + ChatColor.GRAY + "You have muted " + ChatColor.BLUE + target.getName() + ChatColor.GRAY + " for: " + ChatColor.RED
                                + reason + ChatColor.GRAY + ".");
                target.sendMessage(Lang.CHAT_TAG.getServerChatTag() + ChatColor.GRAY + "You have been muted for: " + ChatColor.RED + reason + ChatColor.GRAY + ".");
                return true;
            }
        } else if (sender instanceof ConsoleCommandSender) {
            ConsoleCommandSender console = (ConsoleCommandSender) sender;

            if (args.length == 0) {
                String[] help = new String[] {
                        Lang.HEADER.getServerHeader(),
                        Lang.CMD_NAME.getCommandName("mute"),
                        Lang.FOOTER.getMessage(),
                        Lang.CMD_HELP.getCommand("mute", "[player]", "Mute a player"),
                        Lang.CMD_HELP.getCommand("mute", "[player] [reason]", "Mute a player with reason"),
                        Lang.FOOTER.getMessage(),
                };
                console.sendMessage(help);
            } else if (args.length == 1) {
                String name = args[0];
                if (!Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(name))) {
                    console.sendMessage(Lang.PLAYER_NULL.getServerMessage());
                    return true;
                }

                Player target = Bukkit.getPlayer(name);
                NECore.getMuteManager().mute(target.getUniqueId(), "Reason not stated", NECore.getSettings().consoleUUID);
                console.sendMessage(Lang.CHAT_TAG.getServerChatTag() + ChatColor.GRAY + "You have muted " + ChatColor.BLUE + target.getName() + ChatColor.GRAY + ".");
                target.sendMessage(Lang.CHAT_TAG.getServerChatTag() + ChatColor.GRAY + "You have been muted.");
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
                String reason = builder.substring(args[0].length() + 1, builder.length() -1);

                Player target = Bukkit.getPlayer(name);
                NECore.getMuteManager().mute(target.getUniqueId(), reason, NECore.getSettings().consoleUUID);
                console.sendMessage(Lang.CHAT_TAG.getServerChatTag() + ChatColor.GRAY + "You have muted " + ChatColor.BLUE + target.getName() + ChatColor.GRAY + " for: " + ChatColor.RED
                        + reason + ChatColor.GRAY + ".");
                target.sendMessage(Lang.CHAT_TAG.getServerChatTag() + ChatColor.GRAY + "You have been muted for: " + ChatColor.RED + reason + ChatColor.GRAY + ".");
                return true;
            }
        }
        return true;
    }
}
