package src;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Class for the pickupnone command
 * @author rleboeu
 */
public class CommandPickupNone implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        boolean isValidUsage = true;

        if (sender instanceof Player) {
            Player player = (Player) sender;
            
            player.setCanPickupItems(false);
            player.sendMessage("Looting disabled for ALL items.");
        }

        return isValidUsage;
    }
    
}
