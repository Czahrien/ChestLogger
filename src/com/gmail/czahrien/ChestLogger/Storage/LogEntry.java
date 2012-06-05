package com.gmail.czahrien.ChestLogger.Storage;

import java.io.Serializable;
import java.util.Date;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

/**
 * A LogEntry containing a timestamp and a player's name.
 * 
 * @author Czahrien
 * 
 */
public class LogEntry implements Serializable {
    /**
     * This value will change if an update breaks compatibility with older
     * versions
     */
    private static final long serialVersionUID = 6662102412228391105L;

    /**
     * The timestamp of the event.
     */
    private Date myDate;
    /**
     * The name of the player.
     */
    private String myPlayer;

    /**
     * Creates a LogEntry using the current time and the given Player.
     * 
     * @param p
     */
    public LogEntry(Player p) {
        myDate = new Date();
        myPlayer = p.getDisplayName();
    }

    /**
     * Displays the LogEntry with a timestamp.
     * 
     * @param p
     *            The player to send the output to.
     */
    public void display(Player p) {
        display(p, true);
    }

    /**
     * Displays the LogEntry with or without a timestamp.
     * 
     * @param p
     *            The player to send the output to.
     * @param verbose
     *            If true a timestamp is printed, otherwise the timestamp is
     *            omitted.
     */
    public void display(Player p, boolean verbose) {

        p.sendMessage("    "
                + (verbose ? ChatColor.LIGHT_PURPLE + "[" + myDate.toString()
                        + "] " + ChatColor.GREEN + myPlayer : ChatColor.GREEN
                        + myPlayer));
    }
}
