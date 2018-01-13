package net.nighthawkempires.essentials.commands;

import net.nighthawkempires.core.language.Lang;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class RenameCommand implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (!player.hasPermission("ne.rename")) {
                player.sendMessage(Lang.NO_PERM.getServerMessage());
                return true;
            }

            if (args.length == 0) {
                player.sendMessage(Lang.CHAT_TAG.getServerMessage(ChatColor.AQUA + "/rename " + ChatColor.DARK_AQUA + "[name]" + ChatColor.GRAY + "."));
                return true;
            } else {
                StringBuilder builder = new StringBuilder();
                for (String string : args) {
                    builder.append(string).append(" ");
                }
                String name = ChatColor.translateAlternateColorCodes('&', builder.toString().substring(0, builder.length()-1));
                String stripped = ChatColor.stripColor(name);
                if (stripped.length() >= 25) {
                    player.sendMessage(Lang.CHAT_TAG.getServerChatTag() + ChatColor.RED + "Sorry, but that name is too long.");
                    return true;
                }

                ItemStack itemStack = player.getItemInHand();
                ItemMeta meta = itemStack.getItemMeta();
                meta.setDisplayName(name);
                itemStack.setItemMeta(meta);
                player.setItemInHand(itemStack);
                player.sendMessage(Lang.CHAT_TAG.getServerMessage(ChatColor.GRAY + "You have changed the name of the item in your hand to " + name + ChatColor.GRAY + "."));
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
