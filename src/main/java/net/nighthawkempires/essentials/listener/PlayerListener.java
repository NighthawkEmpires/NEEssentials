package net.nighthawkempires.essentials.listener;

import com.google.common.collect.Maps;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import net.nighthawkempires.core.NECore;
import net.nighthawkempires.core.language.Lang;
import net.nighthawkempires.core.users.UserModel;
import net.nighthawkempires.essentials.NEEssentials;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.*;

import java.util.UUID;
import java.util.concurrent.ConcurrentMap;


public class PlayerListener implements Listener {

    private ConcurrentMap<UUID, Integer> taskMap;

    public PlayerListener() {
        taskMap = Maps.newConcurrentMap();
    }

    @EventHandler
    public void onInteract(PlayerInteractEntityEvent event) {
        if (event.getRightClicked().getType() == EntityType.POLAR_BEAR) {
            event.getRightClicked().setPassenger(event.getPlayer());
            event.getRightClicked().setGlowing(true);
        }
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        if (event.getTo().getBlockX() != event.getFrom().getBlockX() || event.getTo().getBlockY() != event.getFrom().getBlockY() || event.getTo().getBlockZ() != event.getFrom().getBlockZ()) {
            if (NEEssentials.getData().dontMove.contains(player.getUniqueId())) {
                NEEssentials.getData().dontMove.remove(player.getUniqueId());
                player.sendMessage(Lang.CHAT_TAG.getServerChatTag() + ChatColor.RED + "Warm ups cancelled due to movement.");
            }

            if (NEEssentials.getData().afk.contains(player.getUniqueId())) {
                NEEssentials.getData().afk.remove(player.getUniqueId());
                for (Player players : Bukkit.getOnlinePlayers()) {
                    players.sendMessage(Lang.CHAT_TAG.getServerMessage(ChatColor.BLUE + "" + ChatColor.BOLD + player.getName() + ChatColor.GRAY + "" + ChatColor.BOLD + " is no longer afk!"));
                }
            }

            if (NEEssentials.getData().froze.contains(player.getUniqueId())) {
                event.setCancelled(true);
                player.sendMessage(Lang.CHAT_TAG.getServerMessage(ChatColor.RED + "You can not move while frozen!"));
            }
        }

        int task = Bukkit.getScheduler().scheduleSyncDelayedTask(NEEssentials.getPlugin(), () -> {
            if (event.getTo() == event.getFrom()) {
                NEEssentials.getData().afk.add(player.getUniqueId());
                for (Player players : Bukkit.getOnlinePlayers()) {
                    players.sendMessage(Lang.CHAT_TAG.getServerMessage(ChatColor.BLUE + "" + ChatColor.BOLD + player.getName() + ChatColor.GRAY + "" + ChatColor.BOLD + " is afk!"));
                }
            }
        }, 3000);
        if (taskMap.containsKey(player.getUniqueId())) {
            Bukkit.getScheduler().cancelTask(taskMap.get(player.getUniqueId()));
        }
        taskMap.put(player.getUniqueId(), task);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        UserModel user = NECore.getUserRegistry().getUser(player.getUniqueId());

        if (NEEssentials.getData().flyMode.contains(player.getUniqueId())) {
            player.setFlying(true);
            player.setAllowFlight(true);
        }

        for (UUID uuid : NEEssentials.getData().vanished) {
            if (Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(uuid))) {
                Player vanish = Bukkit.getPlayer(uuid);
                player.hidePlayer(vanish);
            }
        }
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();

        if (event.getMessage().toLowerCase().startsWith("/restart") || event.getMessage().toLowerCase().startsWith("/stop")) {
            if (!player.hasPermission("ne.stop")) {
                player.sendMessage(Lang.NO_PERM.getServerMessage());
                event.setCancelled(true);
                return;
            }
            event.setCancelled(true);

            for (Player players : Bukkit.getOnlinePlayers()) {
                ByteArrayDataOutput out = ByteStreams.newDataOutput();
                out.writeUTF("Connect");
                out.writeUTF("HUB-1");
                players.sendPluginMessage(NEEssentials.getPlugin(), "BungeeCord", out.toByteArray());
            }

            if (event.getMessage().toLowerCase().startsWith("/restart")) {
                Bukkit.getScheduler().scheduleSyncRepeatingTask(NEEssentials.getPlugin(), () -> {
                    if (Bukkit.getOnlinePlayers().size() == 0) {
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "restart");
                    }
                }, 20, 20);
            } else {
                Bukkit.getScheduler().scheduleSyncRepeatingTask(NEEssentials.getPlugin(), () -> {
                    if (Bukkit.getOnlinePlayers().size() == 0) {
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "stop");
                    }
                }, 20, 20);
            }
            player.sendMessage(Lang.CHAT_TAG.getServerMessage(ChatColor.GRAY + "Sending all players to hub!"));
        }
    }

    @EventHandler
    public void onChange(SignChangeEvent event) {
        Player player = event.getPlayer();

        if (event.getLine(0) != null) {
            event.setLine(0, ChatColor.translateAlternateColorCodes('&', event.getLine(0)));
        }

        if (event.getLine(1) != null) {
            event.setLine(1, ChatColor.translateAlternateColorCodes('&', event.getLine(1)));
        }

        if (event.getLine(2) != null) {
            event.setLine(2, ChatColor.translateAlternateColorCodes('&', event.getLine(2)));
        }

        if (event.getLine(3) != null) {
            event.setLine(3, ChatColor.translateAlternateColorCodes('&', event.getLine(3)));
        }
    }
}
