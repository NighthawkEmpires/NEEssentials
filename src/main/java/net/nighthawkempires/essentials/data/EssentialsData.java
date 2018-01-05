package net.nighthawkempires.essentials.data;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.bukkit.Location;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentMap;

public class EssentialsData {

    public HashMap<UUID, Location> lastLocation;
    public ConcurrentMap<UUID, UUID> tpRequests;
    public ConcurrentMap<UUID, UUID> reply;

    public List<UUID> dontMove;
    public List<UUID> godMode;
    public List<UUID> flyMode;
    public List<UUID> vanished;
    public List<UUID> froze;
    public List<UUID> afk;

    public EssentialsData() {
        lastLocation = Maps.newHashMap();
        tpRequests = Maps.newConcurrentMap();
        reply = Maps.newConcurrentMap();

        dontMove = Lists.newArrayList();
        godMode = Lists.newArrayList();
        flyMode = Lists.newArrayList();
        vanished = Lists.newArrayList();
        froze = Lists.newArrayList();
        afk = Lists.newArrayList();
    }
}
