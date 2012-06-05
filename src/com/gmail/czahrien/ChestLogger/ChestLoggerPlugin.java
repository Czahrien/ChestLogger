package com.gmail.czahrien.ChestLogger;

import java.util.logging.Logger;

import net.mctitan.blockstorage.BlockStorage;
import net.mctitan.blockstorage.PluginBlockStorage;

import org.bukkit.plugin.java.JavaPlugin;

import com.gmail.czahrien.ChestLogger.Commands.ChestLoggerCommands;
import com.gmail.czahrien.ChestLogger.Events.ChestLoggerEvents;
import com.gmail.czahrien.ChestLogger.Storage.ChestLog;

/**
 * The class for the Plugin itself.
 * 
 * @author Czahrien
 * 
 */
public class ChestLoggerPlugin extends JavaPlugin {

    private Logger log;
    private PluginBlockStorage<ChestLog> storage;
    private ChestLoggerCommands myCommands;

    /**
     * Run when the plugin is enabled.
     */
    public void onEnable() {
        storage = ((BlockStorage) (getServer().getPluginManager()
                .getPlugin("BlockStorage"))).getPluginBlockStorage(this);
        myCommands = new ChestLoggerCommands(storage);
        log = this.getLogger();
        log.info("ChestLogger enabled!");
        getServer().getPluginManager().registerEvents(
                new ChestLoggerEvents(storage), this);
        getCommand("chesthide").setExecutor(myCommands);
        getCommand("chestunhide").setExecutor(myCommands);
        getCommand("chesthistory").setExecutor(myCommands);
    }

    /**
     * Run when the plugin is disabled.
     */
    public void onDisable() {
        log.info("ChestLogger disabled.");
    }
}
