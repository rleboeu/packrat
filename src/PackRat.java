package src;

import java.util.ArrayList;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Main plugin class for PackRat
 * @author rleboeu
 * @version 1.0
 */
public class PackRat extends JavaPlugin {

    // fields
    private static ArrayList<PackratPlayer> register = new ArrayList<PackratPlayer>();

    // Fired when plugin is enabled
    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new PackratListener(), this);

        this.getCommand("packrat").setExecutor(new CommandPackrat());
    }

    // Fired when plugin is disabled
    @Override
    public void onDisable() {

    }

    /**
     * Returns the PackratPlayer in the register that corresponds to the parameter Player
     * @param player Player
     * @return PackratPlayer
     */
    public static PackratPlayer getPackPlayer(Player player) {
        PackratPlayer packratPlayer = null;

        for (PackratPlayer packPlayer : register) {
            if (packPlayer.getPlayer().equals(player)) {
                packratPlayer = packPlayer;
                break;
            }
        }

        return packratPlayer;
    }
    
    /**
     * Returns true iff the Player is present in PackRat's register
     * @param player
     * @return boolean
     */
    private static boolean isPlayerRegistered(Player player) {
        boolean registered = false;

        for (PackratPlayer packplayer : register) {
            if (packplayer.getPlayer().equals(player)) {
                registered = true;
                break;
            }
        }

        return registered;
    }

    /**
     * Adds a Player to the PackRat register
     * @param player Player
     */
    public static void registerPlayer(Player player) {
        if (!isPlayerRegistered(player)) {
            register.add(new PackratPlayer(player));
        }
    }

}