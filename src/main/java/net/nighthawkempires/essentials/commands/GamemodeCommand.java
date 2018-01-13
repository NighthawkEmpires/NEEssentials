package net.nighthawkempires.essentials.commands;

import net.nighthawkempires.core.NECore;
import net.nighthawkempires.core.language.Lang;
import net.nighthawkempires.core.users.UserModel;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class GamemodeCommand implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (!player.hasPermission("ne.gamemode")) {
                player.sendMessage(Lang.NO_PERM.getServerMessage());
                return true;
            }

            if (args.length == 0) {
                player.sendMessage(Lang.CHAT_TAG.getServerChatTag() + ChatColor.GRAY + "You are currently in gamemode " + ChatColor.BLUE + player.getGameMode().name() + ChatColor.GRAY + ".");
                return true;
            } else if (args.length == 1) {
                if (args[0].toLowerCase().equals("survival") || args[0].toLowerCase().equals("s") || args[0].toLowerCase().equals("0")) {
                    if (player.getGameMode().equals(GameMode.SURVIVAL)) {
                        player.sendMessage(Lang.CHAT_TAG.getServerChatTag() + ChatColor.RED + "You are already in that gamemode!");
                        return true;
                    }

                    player.setGameMode(GameMode.SURVIVAL);
                    player.sendMessage(Lang.CHAT_TAG.getServerChatTag() + ChatColor.GRAY + "You have set your gamemode to " + ChatColor.BLUE + player.getGameMode().name() + ChatColor.GRAY + ".");
                    return true;
                } else if (args[0].toLowerCase().equals("creative") || args[0].toLowerCase().equals("c") || args[0].toLowerCase().equals("1")) {
                    if (player.getGameMode().equals(GameMode.CREATIVE)) {
                        player.sendMessage(Lang.CHAT_TAG.getServerChatTag() + ChatColor.RED + "You are already in that gamemode!");
                        return true;
                    }

                    player.setGameMode(GameMode.CREATIVE);
                    player.sendMessage(Lang.CHAT_TAG.getServerChatTag() + ChatColor.GRAY + "You have set your gamemode to " + ChatColor.BLUE + player.getGameMode().name() + ChatColor.GRAY + ".");
                    return true;
                } else if (args[0].toLowerCase().equals("adventure") || args[0].toLowerCase().equals("a") || args[0].toLowerCase().equals("2")) {
                    if (player.getGameMode().equals(GameMode.ADVENTURE)) {
                        player.sendMessage(Lang.CHAT_TAG.getServerChatTag() + ChatColor.RED + "You are already in that gamemode!");
                        return true;
                    }

                    player.setGameMode(GameMode.ADVENTURE);
                    player.sendMessage(Lang.CHAT_TAG.getServerChatTag() + ChatColor.GRAY + "You have set your gamemode to " + ChatColor.BLUE + player.getGameMode().name() + ChatColor.GRAY + ".");
                    return true;
                } else if (args[0].toLowerCase().equals("spectator") || args[0].toLowerCase().equals("spec") || args[0].toLowerCase().equals("3")) {
                    if (player.getGameMode().equals(GameMode.SPECTATOR)) {
                        player.sendMessage(Lang.CHAT_TAG.getServerChatTag() + ChatColor.RED + "You are already in that gamemode!");
                        return true;
                    }

                    player.setGameMode(GameMode.SPECTATOR);
                    player.sendMessage(Lang.CHAT_TAG.getServerChatTag() + ChatColor.GRAY + "You have set your gamemode to " + ChatColor.BLUE + player.getGameMode().name() + ChatColor.GRAY + ".");
                    return true;
                } else {
                    player.sendMessage(Lang.CHAT_TAG.getServerChatTag() + ChatColor.RED + "That is not a valid gamemode!");
                    return true;
                }
            } else if (args.length == 2) {
                String name = args[1];

                if (!Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(name))) {
                    player.sendMessage(Lang.PLAYER_NULL.getServerMessage());
                    return true;
                }

                Player target = Bukkit.getPlayer(name);
                if (args[0].toLowerCase().equals("survival") || args[0].toLowerCase().equals("s") || args[0].toLowerCase().equals("0")) {
                    if (target.getGameMode().equals(GameMode.SURVIVAL)) {
                        player.sendMessage(Lang.CHAT_TAG.getServerChatTag() + ChatColor.RED + target.getName() + " is already in that gamemode!");
                        return true;
                    }

                    target.setGameMode(GameMode.SURVIVAL);
                    target.sendMessage(Lang.CHAT_TAG.getServerChatTag() + ChatColor.GRAY + "Your gamemode has been set to " + ChatColor.BLUE + target.getGameMode().name() + ChatColor.GRAY + ".");
                    player.sendMessage(Lang.CHAT_TAG.getServerChatTag() + ChatColor.GRAY + "You have set " + ChatColor.BLUE + target.getName() + "'s " + ChatColor.GRAY + "gamemode to "
                            + ChatColor.BLUE + target.getGameMode().name() + ChatColor.GRAY + ".");
                    return true;
                } else if (args[0].toLowerCase().equals("creative") || args[0].toLowerCase().equals("c") || args[0].toLowerCase().equals("1")) {
                    if (target.getGameMode().equals(GameMode.CREATIVE)) {
                        player.sendMessage(Lang.CHAT_TAG.getServerChatTag() + ChatColor.RED + target.getName() + " is already in that gamemode!");
                        return true;
                    }

                    target.setGameMode(GameMode.CREATIVE);
                    target.sendMessage(Lang.CHAT_TAG.getServerChatTag() + ChatColor.GRAY + "Your gamemode has been set to " + ChatColor.BLUE + target.getGameMode().name() + ChatColor.GRAY + ".");
                    player.sendMessage(Lang.CHAT_TAG.getServerChatTag() + ChatColor.GRAY + "You have set " + ChatColor.BLUE + target.getName() + "'s " + ChatColor.GRAY + "gamemode to "
                            + ChatColor.BLUE + target.getGameMode().name() + ChatColor.GRAY + ".");
                    return true;
                } else if (args[0].toLowerCase().equals("adventure") || args[0].toLowerCase().equals("a") || args[0].toLowerCase().equals("2")) {
                    if (target.getGameMode().equals(GameMode.ADVENTURE)) {
                        player.sendMessage(Lang.CHAT_TAG.getServerChatTag() + ChatColor.RED + target.getName() + " is already in that gamemode!");
                        return true;
                    }

                    target.setGameMode(GameMode.ADVENTURE);
                    target.sendMessage(Lang.CHAT_TAG.getServerChatTag() + ChatColor.GRAY + "Your gamemode has been set to " + ChatColor.BLUE + target.getGameMode().name() + ChatColor.GRAY + ".");
                    player.sendMessage(Lang.CHAT_TAG.getServerChatTag() + ChatColor.GRAY + "You have set " + ChatColor.BLUE + target.getName() + "'s " + ChatColor.GRAY + "gamemode to "
                            + ChatColor.BLUE + target.getGameMode().name() + ChatColor.GRAY + ".");
                    return true;
                } else if (args[0].toLowerCase().equals("spectator") || args[0].toLowerCase().equals("spec") || args[0].toLowerCase().equals("3")) {
                    if (target.getGameMode().equals(GameMode.CREATIVE)) {
                        player.sendMessage(Lang.CHAT_TAG.getServerChatTag() + ChatColor.RED + target.getName() + " is already in that gamemode!");
                        return true;
                    }

                    target.setGameMode(GameMode.SPECTATOR);
                    target.sendMessage(Lang.CHAT_TAG.getServerChatTag() + ChatColor.GRAY + "Your gamemode has been set to " + ChatColor.BLUE + target.getGameMode().name() + ChatColor.GRAY + ".");
                    player.sendMessage(Lang.CHAT_TAG.getServerChatTag() + ChatColor.GRAY + "You have set " + ChatColor.BLUE + target.getName() + "'s " + ChatColor.GRAY + "gamemode to "
                            + ChatColor.BLUE + target.getGameMode().name() + ChatColor.GRAY + ".");
                    return true;
                } else {
                    player.sendMessage(Lang.CHAT_TAG.getServerChatTag() + ChatColor.RED + "That is not a valid gamemode!");
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
