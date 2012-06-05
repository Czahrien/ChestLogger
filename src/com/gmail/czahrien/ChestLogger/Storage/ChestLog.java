package com.gmail.czahrien.ChestLogger.Storage;

import java.util.LinkedList;
import java.util.ListIterator;
import java.io.Serializable;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

/**
 * Stores all relevant access information for a single chest.
 * 
 * @author Czahrien
 * 
 */
public class ChestLog implements Serializable {
    /**
     * This value will change if an updated version becomes incompatible with
     * old versions.
     */
    private static final long serialVersionUID = 4304556522922661511L;

    /**
     * The complete access history for a chest with timestamps.
     */

    private LinkedList<LogEntry> myAccesses;
    /**
     * The most recent five players to access the chest.
     */
    private LinkedList<String> myLastFive;

    /**
     * Creates a new ChestLog.
     */
    public ChestLog() {
        myAccesses = new LinkedList<>();
        myLastFive = new LinkedList<>();
    }

    /**
     * Adds a LogEntry for a Player's access to the chest.
     * 
     * @param p
     *            The Player.
     */
    public void addEntry(Player p) {
        myAccesses.addFirst(new LogEntry(p));
        String name = p.getDisplayName();

        // If the name is in the access list, we want to remove it first so we
        // can place it at the front.
        if (!myLastFive.contains(name)) {
            if (myLastFive.size() == 5) {
                myLastFive.removeLast();
            }
        } else {
            myLastFive.removeFirstOccurrence(name);
        }
        myLastFive.addFirst(name);
    }

    /**
     * Displays a page of the access logs to a given player.
     * 
     * @param p
     *            The Player to display the page to.
     * @param page
     *            The page to display.
     */
    public void displayPage(Player p, int page) {
        int numPages = ((myAccesses.size() - 1) / 5 + 1);

        if (5 * (page - 1) >= myAccesses.size()) {
            p.sendMessage(ChatColor.RED + "You chose page " + ChatColor.GOLD
                    + page + ChatColor.RED + " but there are only "
                    + ChatColor.GOLD + numPages + ChatColor.RED + " page(s)!");
        } else {
            p.sendMessage(ChatColor.GOLD + "Page " + page + " of " + numPages
                    + ":");

            ListIterator<LogEntry> l = myAccesses.listIterator();
            int i = 0;
            // Throw away the unneeded values...
            while (i < 5 * (page - 1)) {
                l.next();
                ++i;
            }
            i = 0;
            // Grab the next five and display them.
            while (i < 5 && l.hasNext()) {
                l.next().display(p);
                ++i;
            }
        }
    }

    /**
     * Displays the last five players to access the chest.
     * 
     * @param p
     *            The Player to display the access list to.
     */
    public void display(Player p) {
        p.sendMessage(ChatColor.GOLD + "Last five players to access chest: ");
        ListIterator<String> l = myLastFive.listIterator();
        while (l.hasNext()) {
            p.sendMessage(ChatColor.GREEN + "    " + l.next());
        }

    }
}
