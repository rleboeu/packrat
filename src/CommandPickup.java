package src;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Class for the pickup command
 * @author rleboeu
 */
public class CommandPickup implements CommandExecutor {

    private static final String ALL_DISABLED = "Looting is DISABLED for ALL items.";
    private static final String ALL_ENABLED  = "Looting is ENABLED for ALL items.";

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        boolean isValidUsage = true;

        if (sender instanceof Player) {
            Player player = (Player) sender;
            player.setCanPickupItems(true);

            if ( args.length > 0 && !(args[0].isEmpty()) ) {
                
                if (args[0].equalsIgnoreCase("all")) {            // enable pickups for all items
                    PackratData.clearBlacklist(player.getName());
                    player.setCanPickupItems(true);
                    player.sendMessage(CommandPickup.ALL_ENABLED);
                } else if (args[0].equalsIgnoreCase("list")) {    // list currently disabled pickups
                    String blacklist = PackratData.getBlacklistString(player.getName());

                    if (player.getCanPickupItems() && !blacklist.isEmpty()) {
                        player.sendMessage("Looting is currently disabled for the following items:");
                        player.sendMessage(blacklist);
                    } else if (player.getCanPickupItems() && blacklist.isEmpty()) {
                        player.sendMessage(CommandPickup.ALL_ENABLED);
                    } else {
                        player.sendMessage(CommandPickup.ALL_DISABLED);
                    }
                } else {                                        // ITEM_NAME, hand, or invalid
                    Material material = null;
                    try {
                        material = PackratData.selectMaterial(player, args[0]);
                    } catch (ItemNotFoundException infe) {
                        player.sendMessage(infe.getMessage());
                        isValidUsage = false;
                    } catch (EmptyHandException ehe) {
                        player.sendMessage(ehe.getMessage());
                        isValidUsage = false;
                    }
                    
                    if (material != null) {
                        PackratData.removeMaterialFromBlacklist(player.getName(), material);
                        player.sendMessage("Looting enabled for " + material.toString());
                    }
                }
            } else {
                isValidUsage = false;
            }
        }

        return isValidUsage;
    }
    
}
