package src;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Class for pickupshow command
 * @author rleboeu
 */
public class CommandPickupShow implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            String blacklist = PackratData.getBlacklistString(player.getName());

            if (player.getCanPickupItems() && !blacklist.isEmpty()) {
                player.sendMessage("Looting is currently disabled for the following items:");
                player.sendMessage(blacklist);
            } else if (player.getCanPickupItems() && blacklist.isEmpty()) {
                player.sendMessage("Looting is currently enabled for ALL items.");
            } else {
                player.sendMessage("Looting is currently disabled for ALL items.");
            }

        }

        return true;
    }
    
}
