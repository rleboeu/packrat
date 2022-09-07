package src;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * Event listener for the PackRat plugin
 * @author rleboeu
 */
public class PackratListener implements Listener {
    
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        PackratData.loadPlayerIntoRegister(event.getPlayer().getName());
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        PackratData.savePlayerFromRegister(event.getPlayer().getName());
    }

    @EventHandler
    public void onEntityPickupItem(EntityPickupItemEvent event) {
        // prevents players from picking up items on their blacklist

        Entity parent = event.getEntity();
        if (parent.getType().equals(EntityType.PLAYER)) {
            Material material = event.getItem().getItemStack().getType();

            if (!PackratData.isPlayerRegistered(parent.getName())) {
                event.setCancelled(true);
            } else if (PackratData.isMaterialBlacklisted(parent.getName(), material)) {
                event.setCancelled(true);
            }
        }
    }

}
