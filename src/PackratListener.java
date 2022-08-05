package src;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * Event listener for the PackRat plugin
 * @author rleboeu
 */
public class PackratListener implements Listener {
    
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        PackRat.registerPlayer(event.getPlayer());
    }

    @EventHandler
    public void onEntityPickupItem(EntityPickupItemEvent event) {
        // prevents players from picking up items on their blacklist

        Entity parent = event.getEntity();
        if (parent.getType().equals(EntityType.PLAYER)) {
            PackratPlayer player = PackRat.getPackPlayer((Player) parent);
            Material material = event.getItem().getItemStack().getType();

            if (player.isMaterialBlacklisted(material)) {
                event.setCancelled(true);
            }
        }
    }

}
