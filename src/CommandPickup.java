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

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        boolean isValidUsage = true;
        
        if (sender instanceof Player) {
            Player player = (Player) sender;
            player.setCanPickupItems(true);
            PackratPlayer packPlayer = PackRat.getPackPlayer(player);

            if ( args.length > 0 && !(args[0].isEmpty()) ) {
                Material material = null;
                try {
                    material = PackRat.selectMaterial(player, args[0]);
                } catch (ItemNotFoundException infe) {
                    player.sendMessage(infe.getMessage());
                    isValidUsage = false;
                } catch (EmptyHandException ehe) {
                    player.sendMessage(ehe.getMessage());
                    isValidUsage = false;
                }
                
                if (material != null) {
                    packPlayer.removeFromBlacklist(material);
                    player.sendMessage("Looting enabled for " + material.toString());
                }

            } else {
                isValidUsage = false;
            }
        }

        return isValidUsage;
    }
    
}
