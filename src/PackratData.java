package src;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 * Class to manage Packrat's stored data
 * @author rleboeuf
 */
public abstract class PackratData {
    
    // register of player's blacklists
    private static HashMap<String, ArrayList<Material>> register = new HashMap<String, ArrayList<Material>>();

    private static JSONParser parser = new JSONParser();

    /**
     * Save player from the register into the JSON file
     * @param playerName String
     */
    public static void savePlayerFromRegister(String playerName) {
        JSONArray existingEntries = PackratData.createJSONArrayFrom(PackratDataConstants.FILEPATH);
        JSONObject newEntry = PackratData.convertToJSONObject(playerName);
        
        // search for an existing entry
        JSONObject currentEntry;
        boolean currentMatchesNewEntry = false;
        String currentName;
        int index;
        for (index = 0; index < existingEntries.size(); index++) {
            currentEntry = (JSONObject) existingEntries.get(index);
            currentName = (String) currentEntry.get(PackratDataConstants.NAME);
            if (currentName.equals(playerName)) {
                currentMatchesNewEntry = true;
                break;
            }
        }

        // decide what to do based on if the entry exists
        if (currentMatchesNewEntry) {
            existingEntries.set(index, newEntry);
        } else {
            existingEntries.add(newEntry);
        }

        // save to file
        try {
            FileWriter writer = new FileWriter(PackratDataConstants.FILEPATH);
            writer.write(existingEntries.toJSONString());
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        register.remove(playerName);
    }

    /**
     * Load player from the JSON file into the register
     * @param playerName String
     */
    public static void loadPlayerIntoRegister(String playerName) {
        JSONArray existingEntries = PackratData.createJSONArrayFrom(PackratDataConstants.FILEPATH);

        JSONObject currentEntry = null;
        boolean entryExists = false;
        String currentName;
        for (Object obj : existingEntries) {
            currentEntry = (JSONObject) obj;
            currentName = (String) currentEntry.get(PackratDataConstants.NAME);

            if (currentName.equals(playerName)) {
                entryExists = true;
                break;
            }
        }

        if (entryExists) {
            // create an ArrayList<Material> from the currentEntry
            ArrayList<Material> materials = new ArrayList<Material>();
            JSONArray jsonMaterials = (JSONArray) currentEntry.get(PackratDataConstants.LIST);

            for (Object obj : jsonMaterials) {
                materials.add((Material.valueOf((String) obj)));
            }

            register.putIfAbsent(playerName, materials);
        } else {
            register.putIfAbsent(playerName, new ArrayList<Material>());
        }

    }

    /**
     * Create a JSONArray of all entries present in filepath
     * @param filepath String path to json file
     * @return JSONArray
     */
    private static JSONArray createJSONArrayFrom(String filepath) {
        JSONArray array = new JSONArray();

        try {
            FileReader reader = new FileReader(filepath);
            array = (JSONArray) PackratData.parser.parse(reader);
            reader.close();
        } catch (org.json.simple.parser.ParseException | IOException e) {
            e.printStackTrace();
        }

        return array;
    }

    /**
     * Convert the stored data in the register for a player into a JSONObject
     * @param playerName String
     * @return JSONObject
     */
    private static JSONObject convertToJSONObject(String playerName) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        JSONArray arr = new JSONArray();
        ArrayList<Material> blacklist = register.get(playerName);

        for (Material material : blacklist) {
            arr.add(material.toString());
        }

        map.put(PackratDataConstants.NAME, playerName);
        map.put(PackratDataConstants.LIST, arr);

        return new JSONObject(map);
    }

    /**
     * Create the Packrat JSON file if it is not already present
     */
    public static void createJSONFileIfAbsent() {
        try {
            File packratJson = new File(PackratDataConstants.FILEPATH);
            packratJson.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Check if a player is present in Packrat's register
     * @param playerName String
     * @return boolean
     */
    public static boolean isPlayerRegistered(String playerName) {
        for (String key : register.keySet()) {
            if (key.equals(playerName)) {
                return true;
            }
        }

        return false;
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

    /**
     * Returns the material associated with the materialAsString parameter
     * @param player Player
     * @param materialAsString String (most cases will either be 'hand' or a specific material String)
     * @return Material
     * @throws ItemNotFoundException thrown when the Material cannot be found
     * @throws EmptyHandException thrown when the player's hand is empty
     */
    public static Material selectMaterial(Player player, String materialAsString) throws ItemNotFoundException, EmptyHandException {
        Material material = null;

        if (materialAsString.equalsIgnoreCase("hand")) {    // select material in hand
            material = player.getInventory().getItemInMainHand().getType();
            if (material.equals(Material.AIR)) {
                throw new EmptyHandException();
            }
        } else {
            try {
                material = Material.valueOf(materialAsString.toUpperCase());    // select specific material
            } catch (Exception e) {
                throw new ItemNotFoundException();
            }
        }

        return material;
    }

}
