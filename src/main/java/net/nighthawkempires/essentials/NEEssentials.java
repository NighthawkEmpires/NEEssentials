package net.nighthawkempires.essentials;

import net.nighthawkempires.essentials.commands.*;
import net.nighthawkempires.essentials.data.EssentialsData;
import net.nighthawkempires.essentials.listener.PlayerListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class NEEssentials extends JavaPlugin {

    private static EssentialsData data;
    private static Plugin plugin;
    private static NEEssentials instance;
    private static PluginManager pluginManager;

    public void onEnable() {
        plugin = this;
        instance = this;
        data = new EssentialsData();
        pluginManager = Bukkit.getPluginManager();
        registerCommands();
        registerListeners();
    }

    public void onDisable() {

    }

    private void registerCommands() {
        this.getCommand("afk").setExecutor(new AFKCommand());
        this.getCommand("back").setExecutor(new BackCommand());
        this.getCommand("ban").setExecutor(new BanCommand());
        this.getCommand("broadcast").setExecutor(new BroadcastCommand());
        this.getCommand("clearinventory").setExecutor(new ClearInventoryCommand());
        this.getCommand("craft").setExecutor(new CraftCommand());
        this.getCommand("deop").setExecutor(new DeOPCommand());
        this.getCommand("disableenddragon").setExecutor(new DisableEndDragonCommand());
        this.getCommand("enderchest").setExecutor(new EnderChestCommand());
        this.getCommand("feed").setExecutor(new FeedCommand());
        this.getCommand("fly").setExecutor(new FlyCommand());
        this.getCommand("freeze").setExecutor(new FreezeCommand());
        this.getCommand("gamemode").setExecutor(new GamemodeCommand());
        this.getCommand("godmode").setExecutor(new GodCommand());
        this.getCommand("heal").setExecutor(new HealCommand());
        this.getCommand("help").setExecutor(new HelpCommand());
        this.getCommand("home").setExecutor(new HomeCommand());
        this.getCommand("inventory").setExecutor(new InventoryCommand());
        this.getCommand("kick").setExecutor(new KickCommand());
        this.getCommand("kill").setExecutor(new KillCommand());
        this.getCommand("kits").setExecutor(new KitsCommand());
        this.getCommand("me").setExecutor(new MeCommand());
        this.getCommand("message").setExecutor(new MessageCommand());
        this.getCommand("mute").setExecutor(new MuteCommand());
        this.getCommand("nickname").setExecutor(new NickNameCommand());
        this.getCommand("op").setExecutor(new OPCommand());
        this.getCommand("rename").setExecutor(new RenameCommand());
        this.getCommand("reply").setExecutor(new ReplyCommand());
        this.getCommand("sayas").setExecutor(new SayAsCommand());
        this.getCommand("scoreboards").setExecutor(new ScoreboardsCommand());
        this.getCommand("spawn").setExecutor(new SpawnCommand());
        this.getCommand("spawnmob").setExecutor(new SpawnMobCommand());
        this.getCommand("speed").setExecutor(new SpeedCommand());
        this.getCommand("sudo").setExecutor(new SudoCommand());
        this.getCommand("time").setExecutor(new TimeCommand());
        this.getCommand("tokens").setExecutor(new TokensCommand());
        this.getCommand("tpaccept").setExecutor(new TPAcceptCommand());
        this.getCommand("tpa").setExecutor(new TPACommand());
        this.getCommand("tp").setExecutor(new TPCommand());
        this.getCommand("unban").setExecutor(new UnbanCommand());
        this.getCommand("unmute").setExecutor(new UnmuteCommand());
        this.getCommand("vanish").setExecutor(new VanishCommand());
        this.getCommand("warp").setExecutor(new WarpCommand());
        this.getCommand("weather").setExecutor(new WeatherCommand());
    }

    private void registerListeners() {
        getPluginManager().registerEvents(new PlayerListener(), this);
        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
    }

    public static Plugin getPlugin() {
        return plugin;
    }

    public static NEEssentials getInstance() {
        return instance;
    }

    public static EssentialsData getData() {
        return data;
    }

    public static PluginManager getPluginManager() {
        return pluginManager;
    }
}
