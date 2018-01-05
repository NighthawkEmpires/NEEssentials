package net.nighthawkempires.essentials.listener;

import com.google.common.collect.Maps;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import net.nighthawkempires.core.NECore;
import net.nighthawkempires.core.events.UserDeathEvent;
import net.nighthawkempires.core.language.Lang;
import net.nighthawkempires.core.users.User;
import net.nighthawkempires.core.utils.CooldownUtil;
import net.nighthawkempires.core.utils.LocationUtil;
import net.nighthawkempires.core.utils.MathUtil;
import net.nighthawkempires.core.utils.PotionUtil;
import net.nighthawkempires.essentials.NEEssentials;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Door;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.ConcurrentMap;

import static org.bukkit.event.entity.EntityDamageEvent.DamageCause.ENTITY_ATTACK;
import static org.bukkit.event.entity.EntityDamageEvent.DamageCause.MAGIC;
import static org.bukkit.event.entity.EntityDamageEvent.DamageCause.PROJECTILE;

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

        if (!player.getUniqueId().toString().equals("1310f910-9480-49bb-9955-c122154cfe1d") || !player.getUniqueId().toString().equals("12ea1659-5da0-4ec0-9c8d-15350261e2d5")
                || !player.getUniqueId().toString().equals("575ca289-a191-4078-88b1-a744991d85d9")) {
            if (player.getLocation().getBlockX() >= 15000 || player.getLocation().getBlockX() <= Integer.valueOf("-15000")) {
                if (!CooldownUtil.cooledDown(player.getUniqueId(),"border")) {
                    return;
                }
                player.sendMessage(Lang.CHAT_TAG.getServerChatTag() + ChatColor.RED + "You are not allowed to go past the world's border!");
                player.setVelocity(player.getLocation().getDirection().multiply(-0.5).setX(-1.5).setY(0.5));
                CooldownUtil.setCooldown(player.getUniqueId(), "border", 3);
                if (player.getLocation().getBlockX() >= 15010 || player.getLocation().getBlockX() <= Integer.valueOf("-15010")) {
                    player.teleport(new Location(player.getWorld(), (player.getLocation().getBlockX() >= 15010 ? 14990 : Integer.valueOf("-14990")), player.getLocation().getBlockY(), player.getLocation().getBlockZ()));
                }
            } else if (player.getLocation().getBlockZ() >= 15000 || player.getLocation().getBlockZ() <= Integer.valueOf("-15000")) {
                if (!CooldownUtil.cooledDown(player.getUniqueId(),"border")) {
                    return;
                }
                player.sendMessage(Lang.CHAT_TAG.getServerChatTag() + ChatColor.RED + "You are not allowed to go past the world's border!");
                player.setVelocity(player.getLocation().getDirection().multiply(-0.5).setZ(-1.5).setY(0.5));
                if (player.getLocation().getBlockZ() >= 15010 || player.getLocation().getBlockZ() <= Integer.valueOf("-15010")) {
                    player.teleport(new Location(player.getWorld(), player.getLocation().getBlockX(), player.getLocation().getBlockY(), (player.getLocation().getBlockZ() >= 15010 ? 14990 : Integer.valueOf("-14990"))));
                }
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
        for (Entity entity : player.getNearbyEntities(50, 50, 50)) {
            if (entity instanceof EnderDragon) {
                PotionUtil.applyPotion((LivingEntity) entity, PotionEffectType.SLOW, 255, 255);
            }
        }
    }

    @EventHandler
    public void onDeath(UserDeathEvent event) {
        Player player = event.getPlayer();
        Location location = player.getLocation();
        player.setCanPickupItems(false);

        if (LocationUtil.hasSpawn(player.getWorld())) {
            player.teleport(LocationUtil.getSpawn(player.getWorld()));
        }
        player.setHealth(player.getMaxHealth());
        player.setFireTicks(0);
        player.setFoodLevel(20);

        for (ItemStack itemStack : player.getInventory().getContents()) {
            if (itemStack == null) {
                //nothing
            } else {
                location.getWorld().dropItemNaturally(location, itemStack);
            }
        }

        player.getInventory().clear();
        player.getInventory().setArmorContents(null);
        player.getInventory().setItemInOffHand(null);
        player.getInventory().setItemInMainHand(null);

        player.setCanPickupItems(true);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        User user = NECore.getUserManager().getUser(player.getUniqueId());

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
}
