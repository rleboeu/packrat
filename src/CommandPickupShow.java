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
            PackratPlayer packPlayer = PackRat.getPackPlayer(player);

            if (player.getCanPickupItems() && packPlayer.getBlackListSize() > 0) {
                player.sendMessage("Looting disabled for the following items:");
                player.sendMessage(packPlayer.getBlacklistString());
            } else if (player.getCanPickupItems() && packPlayer.getBlackListSize() == 0) {
                player.sendMessage("Looting is currently enabled for ALL items.");
            } else {
                player.sendMessage("Looting is currently disabled for ALL items.");
            }

        }

        return true;
    }
    
}
