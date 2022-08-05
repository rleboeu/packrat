package src;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.entity.Player;

/**
 * PackRat's wrapper class for Player
 * @author rleboeu
 */
public class PackratPlayer {
    
    // fields
    private Player player;
    private ArrayList<Material> blacklist;

    /**
     * Default constructor
     * @param player reference to the Player entity
     */
    public PackratPlayer(Player player) {
        this.player = player;
        this.blacklist = new ArrayList<Material>();
    }

    /**
     * Accessor for player reference
     * @return Player
     */
    public Player getPlayer() {
        return this.player;
    }

    /**
     * Check if a material is on the player's blacklist
     * @param material Material
     * @return boolean
     */
    public boolean isMaterialBlacklisted(Material material) {
        boolean exists = false;
        
        for (Material i : this.blacklist) {
            if (i.equals(material)) {
                exists = true;
                break;
            }
        }

        return exists;
    }

    /**
     * Clears the blacklist of all elements
     */
    public void clearBlacklist() {
        this.blacklist.clear();
    }

    /**
     * Add a Material to the player's blacklist
     * @param material Material
     */
    public void addToBlacklist(Material material) {
        if (!isMaterialBlacklisted(material)) {
            this.blacklist.add(material);
        }
    }

    /**
     * Remove a Material from the player's blacklist
     * @param material Material
     */
    public void removeFromBlacklist(Material material) {
        if (isMaterialBlacklisted(material)) {
            this.blacklist.remove(material);
        }
    }

    /**
     * Returns the player's blacklisted materials as a String
     * @return String
     */
    public String getBlacklistString() {
        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < this.blacklist.size(); i++) {
            sb.append(this.blacklist.get(i).toString());

            // append comma+space if there are more elements
            if (i < this.blacklist.size() - 1) {
                sb.append(", ");
            }
        }

        return sb.toString();
    }

}
