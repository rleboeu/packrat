package src;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Material;

/**
 * Class to manage Packrat's stored data
 * @author rleboeuf
 */
public abstract class PackratData {
    
    // register of player's blacklists
    private static HashMap<String, ArrayList<Material>> register = new HashMap<String, ArrayList<Material>>();

    /**
     * Add a player to Packrat's register
     * @param playerName String
     */
    public static void addPlayerToRegister(String playerName) {
        register.putIfAbsent(playerName, new ArrayList<Material>());
    }

    /**
     * Remove a player from Packrat's register
     * @param playerName String
     */
    public static void removePlayerFromRegister(String playerName) {
        register.remove(playerName);
    }

    /**
     * Check if a material is present on a player's blacklist
     * @param playerName String
     * @param material Material
     * @return boolean
     */
    public static boolean isMaterialBlacklisted(String playerName, Material material) {
        return register.get(playerName).contains(material);
    }

    /**
     * Add a material to the player's blacklist
     * @param playerName String
     * @param material Material
     */
    public static void addMaterialToBlacklist(String playerName, Material material) {
        if ( !(register.get(playerName).contains(material)) ) {
            register.get(playerName).add(material);
        }
    }

    /**
     * Remove a material from the player's blacklist
     * @param playerName String
     * @param material Material
     */
    public static void removeMaterialFromBlacklist(String playerName, Material material) {
        register.get(playerName).remove(material);
    }

    /**
     * Remove all materials from a player's blacklist
     * @param playerName String
     */
    public static void clearBlacklist(String playerName) {
        register.get(playerName).clear();
    }

    /**
     * Return a String representation of the player's blacklisted materials
     * @param playerName String
     * @return String blacklisted items
     */
    public static String getBlacklistString(String playerName) {
        StringBuffer sb = new StringBuffer();
        ArrayList<Material> blacklist = register.get(playerName);
        
        for (int i = 0; i < blacklist.size(); i++) {
            sb.append(blacklist.get(i).toString());

            if (i < (blacklist.size() - 1)) {
                sb.append(", ");
            }
        }

        return sb.toString();
    }

}
