package src;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Main plugin class for Packrat
 * @author rleboeu
 * @version 1.0
 */
public class Packrat extends JavaPlugin {

    
    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new PackratListener(), this);

        this.getCommand("nopickup").setExecutor(new CommandNoPickup());
        this.getCommand("pickup").setExecutor(new CommandPickup());
        this.getCommand("pickupall").setExecutor(new CommandPickupAll());
        this.getCommand("pickupnone").setExecutor(new CommandPickupNone());
        this.getCommand("pickupshow").setExecutor(new CommandPickupShow());
    }

    @Override
    public void onDisable() {

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