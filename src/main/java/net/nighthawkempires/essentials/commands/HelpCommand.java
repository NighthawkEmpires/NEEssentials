package net.nighthawkempires.essentials.commands;

import net.nighthawkempires.core.NECore;
import net.nighthawkempires.core.language.Lang;
import net.nighthawkempires.core.users.UserModel;
import net.nighthawkempires.core.utils.HelpUtil;
import org.apache.commons.lang.math.NumberUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class HelpCommand implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (!player.hasPermission("ne.help")) {
                player.sendMessage(Lang.NO_PERM.getServerMessage());
                return true;
            }

            if (args.length == 0) {
                HelpUtil.sendPage(player, 1);
                return true;
            } else if (args.length == 1) {
                for (String string : HelpUtil.getPlugins(player)) {
                    if (string.toLowerCase().equals(args[0].toLowerCase())) {
                        Plugin plugin = null;
                        for (Plugin plugin1 : Bukkit.getPluginManager().getPlugins()) {
                            if (plugin1.getName().toLowerCase().equals(args[0].toLowerCase())) {
                                plugin = plugin1;
                            }
                        }

                        if (plugin == null) {
                            player.sendMessage(Lang.CHAT_TAG.getServerMessage(ChatColor.RED + "That is not a valid help topic!"));
                            return true;
                        }

                        StringBuilder authors = new StringBuilder();
                        if (plugin.getDescription().getAuthors() != null && plugin.getDescription().getAuthors().size() != 0) {
                            for (String author : plugin.getDescription().getAuthors()) {
                                authors.append(ChatColor.BLUE).append(author).append(ChatColor.DARK_GRAY).append(", ");
                            }
                        } else {
                            authors.append(ChatColor.GRAY).append("Unspecifieddd");
                        }


                        StringBuilder commands = new StringBuilder();
                        if (plugin.getDescription().getCommands() != null) {
                            for (String string1 : plugin.getDescription().getCommands().keySet()) {
                                commands.append(ChatColor.GREEN).append(string1).append(ChatColor.DARK_GRAY).append(", ");
                            }
                        } else {
                            commands.append(ChatColor.GRAY).append("NaNNN");
                        }

                        String[] help = new String[] {
                                Lang.HEADER.getServerHeader(),
                                Lang.HELP.getHelpTopic(string),
                                Lang.FOOTER.getMessage(),
                                ChatColor.DARK_GRAY + "Plugin Name" + ChatColor.GRAY + ": " + ChatColor.BLUE + plugin.getName(),
                                ChatColor.DARK_GRAY + "Version" + ChatColor.GRAY + ": " + ChatColor.GOLD + plugin.getDescription().getVersion(),
                                ChatColor.DARK_GRAY + "Author" + ChatColor.GRAY + ": " + authors.substring(0, authors.length() - 2),
                                ChatColor.DARK_GRAY + "Description" + ChatColor.GRAY + ": " + ChatColor.GRAY + (plugin.getDescription().getDescription() != null ? plugin.getDescription().getDescription() : ""),
                                ChatColor.DARK_GRAY + "Commands" + ChatColor.GRAY + ": " + commands.substring(0, commands.length() - 2),
                                Lang.FOOTER.getMessage()
                        };
                        player.sendMessage(help);
                        return true;
                    }
                } for (String string : HelpUtil.getCommands(player)) {
                    if (string.toLowerCase().equals(args[0].toLowerCase())) {
                        PluginCommand cmd = Bukkit.getPluginCommand(string);

                        StringBuilder aliases = new StringBuilder();
                        if (cmd.getAliases() != null && cmd.getAliases().size() != 0) {
                            for (String string1 : cmd.getAliases()) {
                                aliases.append(ChatColor.GREEN).append(string1).append(ChatColor.DARK_GRAY).append(", ");
                            }
                        } else {
                            aliases.append(ChatColor.GRAY).append("NaNNN");
                        }

                        String[] help = new String[] {
                                Lang.HEADER.getServerHeader(),
                                Lang.HELP.getHelpTopic(string),
                                Lang.FOOTER.getMessage(),
                                ChatColor.DARK_GRAY + "Name" + ChatColor.GRAY + ": " + ChatColor.GREEN + cmd.getName(),
                                ChatColor.DARK_GRAY + "Description" + ChatColor.GRAY + ": " + cmd.getDescription(),
                                ChatColor.DARK_GRAY + "Aliases" + ChatColor.GRAY + ": " + aliases.substring(0, aliases.length() -2),
                                Lang.FOOTER.getMessage(),
                        };
                        player.sendMessage(help);
                        return true;
                    }
                }

                if (NumberUtils.isDigits(args[0])) {
                    int page = Integer.parseInt(args[0]);
                    if (page > 0 && page <= HelpUtil.getTotalPages(player)) {
                        HelpUtil.sendPage(player, page);
                    } else {
                        player.sendMessage(Lang.CHAT_TAG.getServerMessage(ChatColor.RED + "That is an invalid help topic!"));
                        return true;
                    }
                } else {
                    player.sendMessage(Lang.CHAT_TAG.getServerMessage(ChatColor.RED + "That is an invalid help topic!"));
                    return true;
                }

            }
        } else if (sender instanceof ConsoleCommandSender) {
            ConsoleCommandSender console = (ConsoleCommandSender) sender;
            if (args.length == 0) {
                HelpUtil.sendPage(console, 1);
                return true;
            } else if (args.length == 1) {
                for (String string : HelpUtil.getPlugins(null)) {
                    if (string.toLowerCase().equals(args[0].toLowerCase())) {
                        Plugin plugin = null;
                        for (Plugin plugin1 : Bukkit.getPluginManager().getPlugins()) {
                            if (plugin1.getName().toLowerCase().equals(args[0].toLowerCase())) {
                                plugin = plugin1;
                            }
                        }

                        if (plugin == null) {
                            console.sendMessage(Lang.CHAT_TAG.getServerMessage(ChatColor.RED + "That is not a valid help topic!"));
                            return true;
                        }

                        StringBuilder authors = new StringBuilder();
                        if (plugin.getDescription().getAuthors() != null && plugin.getDescription().getAuthors().size() != 0) {
                            for (String author : plugin.getDescription().getAuthors()) {
                                authors.append(ChatColor.BLUE).append(author).append(ChatColor.DARK_GRAY).append(", ");
                            }
                        } else {
                            authors.append(ChatColor.GRAY).append("Unspecifieddd");
                        }


                        StringBuilder commands = new StringBuilder();
                        if (plugin.getDescription().getCommands() != null) {
                            for (String string1 : plugin.getDescription().getCommands().keySet()) {
                                commands.append(ChatColor.GREEN).append(string1).append(ChatColor.DARK_GRAY).append(", ");
                            }
                        } else {
                            commands.append(ChatColor.GRAY).append("NaNNN");
                        }

                        String[] help = new String[] {
                                Lang.HEADER.getServerHeader(),
                                Lang.HELP.getHelpTopic(string),
                                Lang.FOOTER.getMessage(),
                                ChatColor.DARK_GRAY + "Plugin Name" + ChatColor.GRAY + ": " + ChatColor.BLUE + plugin.getName(),
                                ChatColor.DARK_GRAY + "Version" + ChatColor.GRAY + ": " + ChatColor.GOLD + plugin.getDescription().getVersion(),
                                ChatColor.DARK_GRAY + "Author" + ChatColor.GRAY + ": " + authors.substring(0, authors.length() - 2),
                                ChatColor.DARK_GRAY + "Description" + ChatColor.GRAY + ": " + ChatColor.GRAY + (plugin.getDescription().getDescription() != null ? plugin.getDescription().getDescription() : ""),
                                ChatColor.DARK_GRAY + "Commands" + ChatColor.GRAY + ": " + commands.substring(0, commands.length() - 2),
                                Lang.FOOTER.getMessage()
                        };
                        console.sendMessage(help);
                        return true;
                    }
                } for (String string : HelpUtil.getCommands(null)) {
                    if (string.toLowerCase().equals(args[0].toLowerCase())) {
                        PluginCommand cmd = Bukkit.getPluginCommand(string);

                        StringBuilder aliases = new StringBuilder();
                        if (cmd.getAliases() != null && cmd.getAliases().size() != 0) {
                            for (String string1 : cmd.getAliases()) {
                                aliases.append(ChatColor.GREEN).append(string1).append(ChatColor.DARK_GRAY).append(", ");
                            }
                        } else {
                            aliases.append(ChatColor.GRAY).append("NaNNN");
                        }

                        String[] help = new String[] {
                                Lang.HEADER.getServerHeader(),
                                Lang.HELP.getHelpTopic(string),
                                Lang.FOOTER.getMessage(),
                                ChatColor.DARK_GRAY + "Name" + ChatColor.GRAY + ": " + ChatColor.GREEN + cmd.getName(),
                                ChatColor.DARK_GRAY + "Description" + ChatColor.GRAY + ": " + cmd.getDescription(),
                                ChatColor.DARK_GRAY + "Aliases" + ChatColor.GRAY + ": " + aliases.substring(0, aliases.length() -2),
                                Lang.FOOTER.getMessage(),
                        };
                        console.sendMessage(help);
                        return true;
                    }
                }

                if (NumberUtils.isDigits(args[0])) {
                    int page = Integer.parseInt(args[0]);
                    if (page > 0 && page <= HelpUtil.getTotalPages(null)) {
                        HelpUtil.sendPage(console, page);
                    } else {
                        console.sendMessage(Lang.CHAT_TAG.getServerMessage(ChatColor.RED + "That is an invalid help topic!"));
                        return true;
                    }
                } else {
                    console.sendMessage(Lang.CHAT_TAG.getServerMessage(ChatColor.RED + "That is an invalid help topic!"));
                    return true;
                }

            }
        }
        return true;
    }
}
