package com.gmail.czahrien.ChestLogger.Commands;

import net.mctitan.blockstorage.PluginBlockStorage;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;

import com.gmail.czahrien.ChestLogger.Storage.ChestLog;
import com.gmail.czahrien.ChestLogger.Storage.TemporaryStorage;

/**
 * Handles invocation of commands for this plugin.
 * 
 * @author Czahrien
 * 
 */
public class ChestLoggerCommands implements CommandExecutor {
    PluginBlockStorage<ChestLog> storage;

    /**
     * Creates a command class using the given PluginBlockStorage.
     * 
     * @param s
     *            The given PluginBlockStorage.
     */
    public ChestLoggerCommands(PluginBlockStorage<ChestLog> s) {
        storage = s;
    }

    /**
     * Handles commands for this plugin.
     * 
     * @param sender
     *            How the command was invoked.
     * @param cmd
     *            The command itself.
     * @param commandLabel
     *            Unused.
     * @param args
     *            The arguments to the command.
     */
    public boolean onCommand(CommandSender sender, Command cmd,
            String commandLabel, String[] args) {
        Player p = null;
        if (sender instanceof Player) {
            p = (Player) sender;
            switch (cmd.getName().toLowerCase()) {
            case "chesthistory":
                if (p != null && p.hasPermission("ChestLogger.SeeDetailedLogs")) {
                    int page = 1;
                    if (args.length == 1) {
                        try {
                            page = Integer.parseInt(args[0]);
                            if (page <= 0) {
                                p.sendMessage(ChatColor.RED
                                        + "Page numbers must be positive integers!");
                                return false;
                            }
                        } catch (NumberFormatException e) {
                            return false;
                        }
                    }

                    Location l = TemporaryStorage.getChest(p);

                    // The player should have selected a block.
                    if (l == null) {
                        p.sendMessage(ChatColor.RED
                                + "You must select a chest by hitting it with a wood block!");
                        return true;
                    }

                    // The block should be a chest.
                    if (!l.getBlock().getType().equals(Material.CHEST)) {
                        p.sendMessage(ChatColor.RED
                                + "The chosen block is no longer a chest!");
                        return true;
                    }

                    ChestLog log = storage.getData(l);
                    // Has anybody accessed this chest?
                    if (log == null) {
                        p.sendMessage(ChatColor.AQUA
                                + "There have been no accesses of this chest.");
                        return true;
                    }

                    log.displayPage(p, page);
                    return true;
                }
                break;
            case "chesthide":
                if (p != null && p.hasPermission("ChestLogger.HideUsage")) {
                    TemporaryStorage.hide(p);
                    p.sendMessage(ChatColor.AQUA
                            + "You will no longer appear on chest access lists.");
                    return true;
                }
                break;
            case "chestunhide":
                if (p != null && p.hasPermission("ChestLogger.HideUsage")) {
                    TemporaryStorage.unhide(p);
                    p.sendMessage(ChatColor.AQUA
                            + "You will now appear on chest access lists.");
                    return true;
                }
                break;
            }
        }
        return false;
    }
}
