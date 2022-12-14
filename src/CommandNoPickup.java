package src;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Class for the nopickup command
 * @author rleboeu
 */
public class CommandNoPickup implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        boolean isValidUsage = true;
        
        if (sender instanceof Player) {
            Player player = (Player) sender;
            player.setCanPickupItems(true);

            if ( args.length > 0 && !(args[0].isEmpty()) ) {

                if (args[0].equalsIgnoreCase("all")) {
                    player.setCanPickupItems(false);
                    player.sendMessage("Looting disabled for ALL items.");
                } else {
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
                        PackratData.addMaterialToBlacklist(player.getName(), material);
                        player.sendMessage("Looting disabled for " + material.toString());
                    }
                }

            } else {
                isValidUsage = false;
            }
        }

        return isValidUsage;
    }
    
}
