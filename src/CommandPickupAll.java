package src;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Class for the pickupall command
 * @author rleboeu
 */
public class CommandPickupAll implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        boolean isValidUsage = true;

        if (sender instanceof Player) {
            Player player = (Player) sender;

            PackratData.clearBlacklist(player.getName());
            player.setCanPickupItems(true);

            player.sendMessage("Looting enabled for all items.");
        }

        return isValidUsage;
    }
    
}
