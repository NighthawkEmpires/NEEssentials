package net.nighthawkempires.essentials.commands;

import net.nighthawkempires.core.NECore;
import net.nighthawkempires.core.language.Lang;
import net.nighthawkempires.core.users.User;
import net.nighthawkempires.core.utils.PotionUtil;
import net.nighthawkempires.essentials.NEEssentials;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

public class FreezeCommand implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            User user = NECore.getUserManager().getUser(player.getUniqueId());

            if (!player.hasPermission("ne.freeze")) {
                player.sendMessage(Lang.NO_PERM.getServerMessage());
                return true;
            }

            if (args.length == 0) {
                player.sendMessage(Lang.CHAT_TAG.getServerMessage(ChatColor.AQUA + "/freeze " + ChatColor.DARK_AQUA + "[player]"));
                return true;
            } else if (args.length == 1) {
                String name = args[0];
                if (!Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(name))) {
                    player.sendMessage(Lang.PLAYER_NULL.getServerMessage());
                    return true;
                }

                Player target = Bukkit.getPlayer(name);
                if (NEEssentials.getData().froze.contains(target.getUniqueId())) {
                    target.sendMessage(Lang.CHAT_TAG.getServerMessage(ChatColor.GRAY + "You have been unfroze, you can move now."));
                    PotionUtil.removePotion(target, PotionEffectType.BLINDNESS);
                    PotionUtil.removePotion(target, PotionEffectType.SLOW);
                    player.sendMessage(Lang.CHAT_TAG.getServerMessage(ChatColor.GRAY + "You have unfroze " + ChatColor.BLUE + target.getName() + ChatColor.GRAY + ", they can move now."));
                    NEEssentials.getData().froze.remove(target.getUniqueId());
                } else {
                    target.sendMessage(Lang.CHAT_TAG.getServerMessage(ChatColor.GRAY + "You have been frozen, you are not allowed to move."));
                    PotionUtil.applyPotion(target, PotionEffectType.BLINDNESS, 0, 100000);
                    PotionUtil.applyPotion(target, PotionEffectType.SLOW, 2, 100000);
                    player.sendMessage(Lang.CHAT_TAG.getServerMessage(ChatColor.GRAY + "You have froze " + ChatColor.BLUE + target.getName() + ChatColor.GRAY + ", they can no longer move."));
                    NEEssentials.getData().froze.add(target.getUniqueId());
                }
            } else {
                player.sendMessage(Lang.SYNTAX_ERROR.getServerMessage());
                return true;
            }
        } else if (sender instanceof ConsoleCommandSender) {
            ConsoleCommandSender console = (ConsoleCommandSender) sender;

            if (args.length == 0) {
                console.sendMessage(Lang.CHAT_TAG.getServerMessage(ChatColor.AQUA + "/freeze " + ChatColor.DARK_AQUA + "[player]"));
                return true;
            } else if (args.length == 1) {
                String name = args[0];
                if (!Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(name))) {
                    console.sendMessage(Lang.PLAYER_NULL.getServerMessage());
                    return true;
                }

                Player target = Bukkit.getPlayer(name);
                if (NEEssentials.getData().froze.contains(target.getUniqueId())) {
                    target.sendMessage(Lang.CHAT_TAG.getServerMessage(ChatColor.GRAY + "You have been unfroze, you can move now."));
                    PotionUtil.removePotion(target, PotionEffectType.BLINDNESS);
                    PotionUtil.removePotion(target, PotionEffectType.SLOW);
                    console.sendMessage(Lang.CHAT_TAG.getServerMessage(ChatColor.GRAY + "You have unfroze " + ChatColor.BLUE + target.getName() + ChatColor.GRAY + ", they can move now."));
                    NEEssentials.getData().froze.remove(target.getUniqueId());
                } else {
                    target.sendMessage(Lang.CHAT_TAG.getServerMessage(ChatColor.GRAY + "You have been frozen, you are not allowed to move."));
                    PotionUtil.applyPotion(target, PotionEffectType.BLINDNESS, 0, 100000);
                    PotionUtil.applyPotion(target, PotionEffectType.SLOW, 2, 100000);
                    console.sendMessage(Lang.CHAT_TAG.getServerMessage(ChatColor.GRAY + "You have froze " + ChatColor.BLUE + target.getName() + ChatColor.GRAY + ", they can no longer move."));
                    NEEssentials.getData().froze.add(target.getUniqueId());
                }
            } else {
                console.sendMessage(Lang.SYNTAX_ERROR.getServerMessage());
                return true;
            }
        }
        return true;
    }
}
