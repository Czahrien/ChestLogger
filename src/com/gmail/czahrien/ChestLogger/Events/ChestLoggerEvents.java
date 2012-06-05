package com.gmail.czahrien.ChestLogger.Events;

import net.mctitan.blockstorage.PluginBlockStorage;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import com.gmail.czahrien.ChestLogger.Storage.ChestLog;
import com.gmail.czahrien.ChestLogger.Storage.TemporaryStorage;

/**
 * An event listener containing all events needed by the plugin.
 * 
 * @author Czahrien
 * 
 */
public class ChestLoggerEvents implements Listener {
    /**
     * The PluginBlockStorage instance for saving information to the block.
     */
    PluginBlockStorage<ChestLog> storage;

    /**
     * Creates a new ChestLoggerEvents object using the given PluginBlockStorage
     * 
     * @param s
     *            The PluginBlockStorage instance.
     */
    public ChestLoggerEvents(PluginBlockStorage<ChestLog> s) {
        storage = s;
    }

    /**
     * This event clears an old ChestLog at the given location if one is present
     * when a chest is placed.
     * 
     * @param e
     *            The Event.
     */
    @EventHandler
    public void chestPlace(BlockPlaceEvent e) {
        if (e.getBlock().getType().equals(Material.CHEST)) {
            Location loc = e.getBlock().getLocation();
            ChestLog l = storage.getData(loc);
            if (l != null) {
                storage.setData(loc, new ChestLog());
            }
        }
    }

    /**
     * This event detects when chests are left clicked by either a stick or a
     * log block. When hit by a stick it displays to the player the five most
     * recent players who accessed the chest, when hit by a log block it selects
     * the chest for detailed log viewing.
     * 
     * @param e
     *            The Event.
     */
    @EventHandler
    public void hitChest(PlayerInteractEvent e) {
        if (e.getAction().equals(Action.LEFT_CLICK_BLOCK)
                && e.getClickedBlock().getType().equals(Material.CHEST)) {
            Player p = e.getPlayer();
            Location loc = e.getClickedBlock().getLocation();
            if (p.hasPermission("ChestLogger.SeeLogs")
                    && p.getItemInHand().getType().equals(Material.STICK)) {
                ChestLog l = storage.getData(loc);
                if (l != null) {
                    l.display(p);
                } else {
                    p.sendMessage(ChatColor.AQUA
                            + "There have been no accesses made to this chest.");
                }
            } else if (p.hasPermission("ChestLogger.SeeDetailedLogs")
                    && p.getItemInHand().getType().equals(Material.LOG)) {
                p.sendMessage(ChatColor.AQUA
                        + "Chest selected for detailed log view.");
                TemporaryStorage.setPlayer(p, loc);
            }
        }
    }

    /**
     * Detects when a player opens a chest and adds it to the log.
     * 
     * @param e
     *            The Event.
     */
    @EventHandler
    public void openChest(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)
                && e.getClickedBlock().getType().equals(Material.CHEST)
                && !TemporaryStorage.isHidden(p)) {
            Location loc = e.getClickedBlock().getLocation();
            ChestLog l = storage.getData(loc);
            if (l == null) {
                l = new ChestLog();
            }
            l.addEntry(p);
            storage.setData(loc, l);
        }

    }

    /**
     * Removes the player's chosen chest when they quit the game.
     * 
     * @param p
     */
    @EventHandler
    public void forgetChest(PlayerQuitEvent p) {
        TemporaryStorage.removePlayer(p.getPlayer());
    }
}
