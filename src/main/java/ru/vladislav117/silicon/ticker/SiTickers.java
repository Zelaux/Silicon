package ru.vladislav117.silicon.ticker;

import com.destroystokyo.paper.event.server.ServerTickEndEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import ru.vladislav117.silicon.event.SiEvents;

import java.util.ArrayList;

public final class SiTickers {
    static ArrayList<SiTicker> tickers = new ArrayList<>();

    public static void addTicker(SiTicker ticker) {
        tickers.add(ticker);
    }

    public static void tick() {
        ArrayList<SiTicker> tickersToRemove = new ArrayList<>();

        for (SiTicker ticker : tickers) {
            ticker.tick();
            if (ticker.getTicks() >= ticker.getTicksThreshold()) tickersToRemove.add(ticker);
        }

        tickers.removeAll(tickersToRemove);
    }

    public static void init() {
        SiEvents.registerBukkitEvents(new Listener() {
            @EventHandler
            public void onServerTickEndEvent(ServerTickEndEvent event) {
                tick();
            }
        });
    }
}
