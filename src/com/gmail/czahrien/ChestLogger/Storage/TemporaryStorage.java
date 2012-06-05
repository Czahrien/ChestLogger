package com.gmail.czahrien.ChestLogger.Storage;

import org.bukkit.entity.Player;
import org.bukkit.Location;
import java.util.HashMap;
import java.util.HashSet;

/**
 * This class implements a temporary storage solution for storing information
 * that does not require saving to a disk between reboots.
 * 
 * @author Czahrien
 * 
 */
public class TemporaryStorage {

    /**
     * This HashMap maps players to the location of their selected chest.
     */
    private static HashMap<Player, Location> chests = new HashMap<>();

    /**
     * This HashSet contains players whose access to chests will be hidden.
     */
    private static HashSet<Player> hidden = new HashSet<>();

    /**
     * Set the player's selected chest to the chest at the given location.
     * 
     * @param p
     *            The Player.
     * @param l
     *            The chest's Location.
     */
    public static void setPlayer(Player p, Location l) {
        chests.put(p, l);
    }

    /**
     * Deselect the player's selected chest.
     * 
     * @param p
     *            The Player.
     */
    public static void removePlayer(Player p) {
        chests.remove(p);
    }

    /**
     * Obtain the location of the player's selected chest.
     * 
     * @param p
     *            The Player
     * @return The Location of the chest.
     */
    public static Location getChest(Player p) {
        return chests.get(p);
    }

    /**
     * Hides the player from chest logs.
     * 
     * @param p
     *            The player to hide.
     */
    public static void hide(Player p) {
        hidden.add(p);
    }

    /**
     * Unhides the player from chest logs.
     * 
     * @param p
     *            The player to unhide.
     */
    public static void unhide(Player p) {
        hidden.remove(p);
    }

    /**
     * Is the player is hidden from chest logs?
     * 
     * @param p
     *            The player.
     * @return True if the player's access to chests is hidden, false otherwise.
     */
    public static boolean isHidden(Player p) {
        return hidden.contains(p);
    }

}
