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

public class NickNameCommand implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            User user = NECore.getUserManager().getUser(player.getUniqueId());

            if (!player.hasPermission("ne.nickname")) {
                player.sendMessage(Lang.NO_PERM.getServerMessage());
                return true;
            }

            if (args.length == 0) {
                String[] help = new String[] {
                        Lang.HEADER.getServerHeader(),
                        Lang.CMD_NAME.getCommandName("nick"),
                        Lang.FOOTER.getMessage(),
                        Lang.CMD_HELP.getCommand("nick", "show", "Show your nickname"),
                        Lang.CMD_HELP.getCommand("nick", "[name]", "Set your nickname"),
                        Lang.CMD_HELP.getCommand("nick", "show [player]", "Show a player's nickname"),
                        Lang.CMD_HELP.getCommand("nick", "[player] [name]", "Set a player's nickname"),
                        Lang.FOOTER.getMessage()
                };
                player.sendMessage(help);
            } else if (args.length == 1) {
                if (args[0].toLowerCase().equals("show")) {
                    if (user.getDisplayName().equals(player.getName())) {
                        player.sendMessage(Lang.CHAT_TAG.getServerChatTag() + ChatColor.RED + "You do not have a nickname set.");
                        return true;
                    }

                    player.sendMessage(Lang.CHAT_TAG.getServerChatTag() + ChatColor.GRAY + "Your nickname is " + ChatColor.translateAlternateColorCodes(
                            '&', user.getDisplayName()) + ChatColor.GRAY + ".");
                    return true;
                } else {
                    String nick = args[0];
                    String name = ChatColor.translateAlternateColorCodes('&', nick);
                    String stripped = ChatColor.stripColor(name);
                    if (stripped.length() >= 15) {
                        player.sendMessage(Lang.CHAT_TAG.getServerChatTag() + ChatColor.RED + "Sorry, but that nickname is too long.");
                        return true;
                    }
                    player.setDisplayName(name);
                    user.setDisplayName(nick);
                    player.sendMessage(Lang.CHAT_TAG.getServerChatTag() + ChatColor.GRAY + "You have set your nickname to " + name + ChatColor.GRAY + ".");
                    return true;
                }
            } else if (args.length == 2) {
                if (args[0].toLowerCase().equals("show")) {
                    String name = args[1];

                    if (!NECore.getUserManager().userExists(Bukkit.getOfflinePlayer(name).getUniqueId())) {
                        player.sendMessage(Lang.PLAYER_NULL.getServerMessage());
                        return true;
                    }

                    if (!Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(name))) {
                        OfflinePlayer target = Bukkit.getOfflinePlayer(name);
                        User tuser = NECore.getUserManager().getTempUser(target.getUniqueId());
                        if (tuser.getDisplayName().equals(target.getName())) {
                            player.sendMessage(Lang.CHAT_TAG.getServerChatTag() + ChatColor.RED + target.getName() + " does not have a nickname set.");
                            return true;
                        }

                        player.sendMessage(Lang.CHAT_TAG.getServerChatTag() + ChatColor.BLUE + target.getName() + "'s " + ChatColor.GRAY + "nickname is "
                                + ChatColor.translateAlternateColorCodes('&', tuser.getDisplayName()) + ChatColor.GRAY + ".");
                        return true;
                    } else {
                        Player target = Bukkit.getPlayer(name);
                        User tuser = NECore.getUserManager().getTempUser(target.getUniqueId());
                        if (tuser.getDisplayName().equals(target.getName())) {
                            player.sendMessage(Lang.CHAT_TAG.getServerChatTag() + ChatColor.RED + target.getName() + " does not have a nickname set.");
                            return true;
                        }

                        player.sendMessage(Lang.CHAT_TAG.getServerChatTag() + ChatColor.BLUE + target.getName() + "'s " + ChatColor.GRAY + "nickname is "
                                + ChatColor.translateAlternateColorCodes('&', tuser.getDisplayName()) + ChatColor.GRAY + ".");
                        return true;
                    }
                } else {
                    String name = args[0];
                    String nick = args[1];
                    String nname = ChatColor.translateAlternateColorCodes('&', nick);
                    String stripped = ChatColor.stripColor(name);
                    if (stripped.length() >= 15) {
                        player.sendMessage(Lang.CHAT_TAG.getServerChatTag() + ChatColor.RED + "Sorry, but that nickname is too long.");
                        return true;
                    }

                    if (!NECore.getUserManager().userExists(Bukkit.getOfflinePlayer(name).getUniqueId())) {
                        player.sendMessage(Lang.PLAYER_NULL.getServerMessage());
                        return true;
                    }

                    if (!Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(name))) {
                        OfflinePlayer target = Bukkit.getOfflinePlayer(name);
                        User tuser = NECore.getUserManager().getTempUser(target.getUniqueId());

                        tuser.setDisplayName(nick);
                        player.sendMessage(Lang.CHAT_TAG.getServerChatTag() + ChatColor.GRAY + "You set " + ChatColor.BLUE + target.getName() + "'s " + ChatColor.GRAY + "nickname to "
                                + nname + ChatColor.GRAY + ".");
                        return true;
                    } else {
                        Player target = Bukkit.getPlayer(name);
                        User tuser = NECore.getUserManager().getTempUser(target.getUniqueId());

                        tuser.setDisplayName(nick);
                        target.setDisplayName(nname);
                        target.sendMessage(Lang.CHAT_TAG.getServerChatTag() + ChatColor.GRAY + "Your nickname has been set to " + nname + ChatColor.GRAY + ".");
                        player.sendMessage(Lang.CHAT_TAG.getServerChatTag() + ChatColor.GRAY + "You set " + ChatColor.BLUE + target.getName() + "'s " + ChatColor.GRAY + "nickname to "
                                + nname + ChatColor.GRAY + ".");
                        return true;
                    }
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
