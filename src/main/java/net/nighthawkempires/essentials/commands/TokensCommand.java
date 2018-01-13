package net.nighthawkempires.essentials.commands;

import net.nighthawkempires.core.NECore;
import net.nighthawkempires.core.language.Lang;
import net.nighthawkempires.core.users.UserModel;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class TokensCommand implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            UserModel user = NECore.getUserRegistry().getUser(player.getUniqueId());

            if (!player.hasPermission("ne.tokens")) {
                player.sendMessage(Lang.NO_PERM.getServerMessage());
                return true;
            }

            if (args.length == 0) {
                String[] help = new String[]{
                        Lang.HEADER.getServerHeader(),
                        Lang.CMD_NAME.getCommandName("tokens"),
                        Lang.FOOTER.getMessage(),
                        Lang.CMD_HELP.getCommand("tokens", "show", "Show your tokens"),
                        Lang.CMD_HELP.getCommand("tokens", "add [value]", "Add to your tokens"),
                        Lang.CMD_HELP.getCommand("tokens", "remove [value]", "Remove from your tokens"),
                        Lang.CMD_HELP.getCommand("tokens", "set [value]", "Set your tokens"),
                        Lang.CMD_HELP.getCommand("tokens", "show [player]", "Show a player's tokens"),
                        Lang.CMD_HELP.getCommand("tokens", "add [value] [player]", "Add to a player's tokens"),
                        Lang.CMD_HELP.getCommand("tokens", "remove [value] [player]", "Remove from a player's tokens"),
                        Lang.CMD_HELP.getCommand("tokens", "set [value] [player]", "Set a player's tokens"),
                        Lang.FOOTER.getMessage()
                };
                player.sendMessage(help);
            } else if (args.length == 1) {
                if (args[0].toLowerCase().equals("show")) {
                    player.sendMessage(Lang.CHAT_TAG.getServerMessage(ChatColor.GRAY + "You have " + ChatColor.GOLD + user.getTokens() + ChatColor.GRAY + " tokens."));
                    return true;
                } else {
                    player.sendMessage(Lang.SYNTAX_ERROR.getServerMessage());
                }
            } else if (args.length == 2) {
                if (args[0].toLowerCase().equals("add")) {
                    try {
                        int value = Integer.parseInt(args[1]);

                        user.setTokens(user.getTokens() + value);
                        player.sendMessage(Lang.CHAT_TAG.getServerMessage(ChatColor.GRAY + "You have added " + ChatColor.GOLD + value + ChatColor.GRAY + " to your token balance."));
                        return true;
                    } catch (NumberFormatException e) {
                        player.sendMessage(Lang.CHAT_TAG.getServerMessage(ChatColor.RED + "That is an invalid value."));
                        return true;
                    }
                } else if (args[0].toLowerCase().equals("remove")) {
                    try {
                        int value = Integer.parseInt(args[1]);

                        user.setTokens(user.getTokens()-value);
                        player.sendMessage(Lang.CHAT_TAG.getServerMessage(ChatColor.GRAY + "You have removed " + ChatColor.GOLD + value + ChatColor.GRAY + " from your token balance."));
                        return true;
                    } catch (NumberFormatException e) {
                        player.sendMessage(Lang.CHAT_TAG.getServerMessage(ChatColor.RED + "That is an invalid value."));
                        return true;
                    }
                } else if (args[0].toLowerCase().equals("set")) {
                    try {
                        int value = Integer.parseInt(args[1]);

                        user.setTokens(value);
                        player.sendMessage(Lang.CHAT_TAG.getServerMessage(ChatColor.GRAY + "You set your token balance to " + ChatColor.GOLD + value + ChatColor.GRAY + " tokens."));
                        return true;
                    } catch (NumberFormatException e) {
                        player.sendMessage(Lang.CHAT_TAG.getServerMessage(ChatColor.RED + "That is an invalid value."));
                        return true;
                    }
                } else if (args[0].toLowerCase().equals("show")) {
                    String name = args[1];

                    if (!NECore.getUserRegistry().userExists(Bukkit.getOfflinePlayer(name).getUniqueId())) {
                        player.sendMessage(Lang.PLAYER_NULL.getServerMessage());
                        return true;
                     }

                     if (Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(name))) {
                        Player target = Bukkit.getPlayer(name);
                        UserModel tuser = NECore.getUserRegistry().getUser(target.getUniqueId());
                        player.sendMessage(Lang.CHAT_TAG.getServerMessage(ChatColor.BLUE + target.getName() + ChatColor.GRAY + " has " + ChatColor.GOLD + tuser.getTokens() + ChatColor.GRAY + " tokens."));
                        return true;
                     } else {
                        OfflinePlayer target = Bukkit.getOfflinePlayer(name);
                         UserModel tuser = NECore.getUserRegistry().getUser(target.getUniqueId());
                        player.sendMessage(Lang.CHAT_TAG.getServerMessage(ChatColor.BLUE + target.getName() + ChatColor.GRAY + " has " + ChatColor.GOLD + tuser.getTokens() + ChatColor.GRAY + " tokens."));
                        return true;
                    }
                } else {
                    player.sendMessage(Lang.SYNTAX_ERROR.getServerMessage());
                    return true;
                }
            } else if (args.length == 3) {
                if (args[0].toLowerCase().equals("add")) {
                    try {
                        int value = Integer.parseInt(args[1]);

                        String name = args[2];

                        if (!NECore.getUserRegistry().userExists(Bukkit.getOfflinePlayer(name).getUniqueId())) {
                            player.sendMessage(Lang.PLAYER_NULL.getServerMessage());
                            return true;
                        }

                        if (Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(name))) {
                            Player target = Bukkit.getPlayer(name);
                            UserModel tuser = NECore.getUserRegistry().getUser(target.getUniqueId());
                            tuser.setTokens(tuser.getTokens() + value);
                            player.sendMessage(Lang.CHAT_TAG.getServerMessage(ChatColor.GRAY + "You have added " + ChatColor.GOLD + value + ChatColor.GRAY
                                    + " to " + ChatColor.BLUE + target.getName() + "''s " + ChatColor.GRAY + "token balance."));
                            return true;
                        } else {
                            OfflinePlayer target = Bukkit.getOfflinePlayer(name);
                            UserModel tuser = NECore.getUserRegistry().getUser(target.getUniqueId());
                            tuser.setTokens(tuser.getTokens() + value);
                            player.sendMessage(Lang.CHAT_TAG.getServerMessage(ChatColor.GRAY + "You have added " + ChatColor.GOLD + value + ChatColor.GRAY
                                    + " to " + ChatColor.BLUE + target.getName() + "''s " + ChatColor.GRAY + "token balance."));
                            return true;
                        }
                    } catch (NumberFormatException e) {
                        player.sendMessage(Lang.CHAT_TAG.getServerMessage(ChatColor.RED + "That is an invalid value."));
                        return true;
                    }
                } else if (args[0].toLowerCase().equals("remove")) {
                    try {
                        int value = Integer.parseInt(args[1]);

                        String name = args[2];

                        if (!NECore.getUserRegistry().userExists(Bukkit.getOfflinePlayer(name).getUniqueId())) {
                            player.sendMessage(Lang.PLAYER_NULL.getServerMessage());
                            return true;
                        }

                        if (Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(name))) {
                            Player target = Bukkit.getPlayer(name);
                            UserModel tuser = NECore.getUserRegistry().getUser(target.getUniqueId());
                            tuser.setTokens(tuser.getTokens() - value);
                            player.sendMessage(Lang.CHAT_TAG.getServerMessage(ChatColor.GRAY + "You have removed " + ChatColor.GOLD + value + ChatColor.GRAY
                                    + " from " + ChatColor.BLUE + target.getName() + "''s " + ChatColor.GRAY + "token balance."));
                            return true;
                        } else {
                            OfflinePlayer target = Bukkit.getOfflinePlayer(name);
                            UserModel tuser = NECore.getUserRegistry().getUser(target.getUniqueId());
                            tuser.setTokens(tuser.getTokens()-value);
                            player.sendMessage(Lang.CHAT_TAG.getServerMessage(ChatColor.GRAY + "You have removed " + ChatColor.GOLD + value + ChatColor.GRAY
                                    + " from " + ChatColor.BLUE + target.getName() + "''s " + ChatColor.GRAY + "token balance."));
                            return true;
                        }
                    } catch (NumberFormatException e) {
                        player.sendMessage(Lang.CHAT_TAG.getServerMessage(ChatColor.RED + "That is an invalid value."));
                        return true;
                    }
                } else if (args[0].toLowerCase().equals("set")) {
                    try {
                        int value = Integer.parseInt(args[1]);

                        String name = args[2];

                        if (!NECore.getUserRegistry().userExists(Bukkit.getOfflinePlayer(name).getUniqueId())) {
                            player.sendMessage(Lang.PLAYER_NULL.getServerMessage());
                            return true;
                        }

                        if (Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(name))) {
                            Player target = Bukkit.getPlayer(name);
                            UserModel tuser = NECore.getUserRegistry().getUser(target.getUniqueId());
                            tuser.setTokens(value);
                            player.sendMessage(Lang.CHAT_TAG.getServerMessage(ChatColor.GRAY + "You have set " + ChatColor.BLUE + target.getName() + "'s " + ChatColor.GRAY
                                    + "token balance to " + ChatColor.GOLD + value + ChatColor.GRAY + " tokens."));
                            return true;
                        } else {
                            OfflinePlayer target = Bukkit.getOfflinePlayer(name);
                            UserModel tuser = NECore.getUserRegistry().getUser(target.getUniqueId());
                            tuser.setTokens(value);
                            player.sendMessage(Lang.CHAT_TAG.getServerMessage(ChatColor.GRAY + "You have set " + ChatColor.BLUE + target.getName() + "'s " + ChatColor.GRAY
                                    + "token balance to " + ChatColor.GOLD + value + ChatColor.GRAY + " tokens."));
                            return true;
                        }
                    } catch (NumberFormatException e) {
                        player.sendMessage(Lang.CHAT_TAG.getServerMessage(ChatColor.RED + "That is an invalid value."));
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
        } else if (sender instanceof ConsoleCommandSender) {
            ConsoleCommandSender console = (ConsoleCommandSender) sender;

            if (args.length == 0) {
                String[] help = new String[]{
                        Lang.HEADER.getServerHeader(),
                        Lang.CMD_NAME.getCommandName("tokens"),
                        Lang.FOOTER.getMessage(),
                        Lang.CMD_HELP.getCommand("tokens", "show", "Show your tokens"),
                        Lang.CMD_HELP.getCommand("tokens", "add [value]", "Add to your tokens"),
                        Lang.CMD_HELP.getCommand("tokens", "remove [value]", "Remove from your tokens"),
                        Lang.CMD_HELP.getCommand("tokens", "set [value]", "Set your tokens"),
                        Lang.CMD_HELP.getCommand("tokens", "show [player]", "Show a player's tokens"),
                        Lang.CMD_HELP.getCommand("tokens", "add [value] [player]", "Add to a player's tokens"),
                        Lang.CMD_HELP.getCommand("tokens", "remove [value] [player]", "Remove from a player's tokens"),
                        Lang.CMD_HELP.getCommand("tokens", "set [value] [player]", "Set a player's tokens"),
                        Lang.FOOTER.getMessage()
                };
                console.sendMessage(help);
            } else if (args.length == 1) {
                if (args[0].toLowerCase().equals("show")) {
                    console.sendMessage(Lang.CHAT_TAG.getServerMessage(ChatColor.RED + "The console doesn't have any tokens!"));
                    return true;
                } else {
                    console.sendMessage(Lang.SYNTAX_ERROR.getServerMessage());
                }
            } else if (args.length == 2) {
                if (args[0].toLowerCase().equals("add")) {
                    console.sendMessage(Lang.CHAT_TAG.getServerMessage(ChatColor.RED + "The console doesn't have any tokens!"));
                } else if (args[0].toLowerCase().equals("remove")) {
                    console.sendMessage(Lang.CHAT_TAG.getServerMessage(ChatColor.RED + "The console doesn't have any tokens!"));
                } else if (args[0].toLowerCase().equals("set")) {
                    console.sendMessage(Lang.CHAT_TAG.getServerMessage(ChatColor.RED + "The console doesn't have any tokens!"));
                } else if (args[0].toLowerCase().equals("show")) {
                    String name = args[1];

                    if (!NECore.getUserRegistry().userExists(Bukkit.getOfflinePlayer(name).getUniqueId())) {
                        console.sendMessage(Lang.PLAYER_NULL.getServerMessage());
                        return true;
                    }

                    if (Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(name))) {
                        Player target = Bukkit.getPlayer(name);
                        UserModel tuser = NECore.getUserRegistry().getUser(target.getUniqueId());
                        console.sendMessage(Lang.CHAT_TAG.getServerMessage(ChatColor.BLUE + target.getName() + ChatColor.GRAY + " has " + ChatColor.GOLD + tuser.getTokens() + ChatColor.GRAY + " tokens."));
                        return true;
                    } else {
                        OfflinePlayer target = Bukkit.getOfflinePlayer(name);
                        UserModel tuser = NECore.getUserRegistry().getUser(target.getUniqueId());
                        console.sendMessage(Lang.CHAT_TAG.getServerMessage(ChatColor.BLUE + target.getName() + ChatColor.GRAY + " has " + ChatColor.GOLD + tuser.getTokens() + ChatColor.GRAY + " tokens."));
                        return true;
                    }
                } else {
                    console.sendMessage(Lang.SYNTAX_ERROR.getServerMessage());
                    return true;
                }
            } else if (args.length == 3) {
                if (args[0].toLowerCase().equals("add")) {
                    try {
                        int value = Integer.parseInt(args[1]);

                        String name = args[2];

                        if (!NECore.getUserRegistry().userExists(Bukkit.getOfflinePlayer(name).getUniqueId())) {
                            console.sendMessage(Lang.PLAYER_NULL.getServerMessage());
                            return true;
                        }

                        if (Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(name))) {
                            Player target = Bukkit.getPlayer(name);
                            UserModel tuser = NECore.getUserRegistry().getUser(target.getUniqueId());
                            tuser.setTokens(tuser.getTokens() + value);
                            console.sendMessage(Lang.CHAT_TAG.getServerMessage(ChatColor.GRAY + "You have added " + ChatColor.GOLD + value + ChatColor.GRAY
                                    + " to " + ChatColor.BLUE + target.getName() + "''s " + ChatColor.GRAY + "token balance."));
                            return true;
                        } else {
                            OfflinePlayer target = Bukkit.getOfflinePlayer(name);
                            UserModel tuser = NECore.getUserRegistry().getUser(target.getUniqueId());
                            tuser.setTokens(tuser.getTokens() + value);
                            console.sendMessage(Lang.CHAT_TAG.getServerMessage(ChatColor.GRAY + "You have added " + ChatColor.GOLD + value + ChatColor.GRAY
                                    + " to " + ChatColor.BLUE + target.getName() + "''s " + ChatColor.GRAY + "token balance."));
                            return true;
                        }
                    } catch (NumberFormatException e) {
                        console.sendMessage(Lang.CHAT_TAG.getServerMessage(ChatColor.RED + "That is an invalid value."));
                        return true;
                    }
                } else if (args[0].toLowerCase().equals("remove")) {
                    try {
                        int value = Integer.parseInt(args[1]);

                        String name = args[2];

                        if (!NECore.getUserRegistry().userExists(Bukkit.getOfflinePlayer(name).getUniqueId())) {
                            console.sendMessage(Lang.PLAYER_NULL.getServerMessage());
                            return true;
                        }

                        if (Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(name))) {
                            Player target = Bukkit.getPlayer(name);
                            UserModel tuser = NECore.getUserRegistry().getUser(target.getUniqueId());
                            tuser.setTokens(tuser.getTokens() - value);
                            console.sendMessage(Lang.CHAT_TAG.getServerMessage(ChatColor.GRAY + "You have removed " + ChatColor.GOLD + value + ChatColor.GRAY
                                    + " from " + ChatColor.BLUE + target.getName() + "''s " + ChatColor.GRAY + "token balance."));
                            return true;
                        } else {
                            OfflinePlayer target = Bukkit.getOfflinePlayer(name);
                            UserModel tuser = NECore.getUserRegistry().getUser(target.getUniqueId());
                            tuser.setTokens(tuser.getTokens()-value);
                            console.sendMessage(Lang.CHAT_TAG.getServerMessage(ChatColor.GRAY + "You have removed " + ChatColor.GOLD + value + ChatColor.GRAY
                                    + " from " + ChatColor.BLUE + target.getName() + "''s " + ChatColor.GRAY + "token balance."));
                            return true;
                        }
                    } catch (NumberFormatException e) {
                        console.sendMessage(Lang.CHAT_TAG.getServerMessage(ChatColor.RED + "That is an invalid value."));
                        return true;
                    }
                } else if (args[0].toLowerCase().equals("set")) {
                    try {
                        int value = Integer.parseInt(args[1]);

                        String name = args[2];

                        if (!NECore.getUserRegistry().userExists(Bukkit.getOfflinePlayer(name).getUniqueId())) {
                            console.sendMessage(Lang.PLAYER_NULL.getServerMessage());
                            return true;
                        }

                        if (Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(name))) {
                            Player target = Bukkit.getPlayer(name);
                            UserModel tuser = NECore.getUserRegistry().getUser(target.getUniqueId());
                            tuser.setTokens(value);
                            console.sendMessage(Lang.CHAT_TAG.getServerMessage(ChatColor.GRAY + "You have set " + ChatColor.BLUE + target.getName() + "'s " + ChatColor.GRAY
                                    + "token balance to " + ChatColor.GOLD + value + ChatColor.GRAY + " tokens."));
                            return true;
                        } else {
                            OfflinePlayer target = Bukkit.getOfflinePlayer(name);
                            UserModel tuser = NECore.getUserRegistry().getUser(target.getUniqueId());
                            tuser.setTokens(value);
                            console.sendMessage(Lang.CHAT_TAG.getServerMessage(ChatColor.GRAY + "You have set " + ChatColor.BLUE + target.getName() + "'s " + ChatColor.GRAY
                                    + "token balance to " + ChatColor.GOLD + value + ChatColor.GRAY + " tokens."));
                            return true;
                        }
                    } catch (NumberFormatException e) {
                        console.sendMessage(Lang.CHAT_TAG.getServerMessage(ChatColor.RED + "That is an invalid value."));
                        return true;
                    }
                } else {
                    console.sendMessage(Lang.SYNTAX_ERROR.getServerMessage());
                    return true;
                }
            } else {
                console.sendMessage(Lang.SYNTAX_ERROR.getServerMessage());
                return true;
            }
        }
        return true;
    }
}
