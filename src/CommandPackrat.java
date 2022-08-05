package src;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * The /packrat command class
 * @author rleboeu
 */
public class CommandPackrat implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // only players are allowed to use this command
        if ( !(sender instanceof Player) )
            return false;
        
        boolean validUsage = true;
        Player player = (Player) sender;
        PackratPlayer packPlayer = PackRat.getPackPlayer(player);

        if (args[0].equalsIgnoreCase("nopickup")) {    // -- nopickup
            if ( !(args[1].isEmpty()) ) {
                Material material = null;
                try {   // select a material and then add it to the blacklist
                    material = this.selectMaterial(player, args[1]);
                    packPlayer.addToBlacklist(material);
                    player.sendMessage("You will no longer pick up " + material.toString());
                } catch (ItemNotFoundException e) {
                    validUsage = false;
                    player.sendMessage(e.getMessage());
                }
            } else {
                validUsage = false;
            }
        } else if (args[0].equalsIgnoreCase("pickup")) {    // -- pickup
            if ( !(args[1].isEmpty()) ) {
                Material material = null;

                try {   // select a material and then remove it from the blacklist
                    material = this.selectMaterial(player, args[1]);
                    packPlayer.removeFromBlacklist(material);
                    player.sendMessage("You will now pick up " + material.toString());
                } catch (ItemNotFoundException e) {
                    validUsage = false;
                    player.sendMessage(e.getMessage());
                }
            } else {
                validUsage = false;
            }
        } else if (args[0].equalsIgnoreCase("clear")) {
            // clear the blacklist of all materials
            packPlayer.clearBlacklist();
            player.sendMessage("You will now pick up all items as normal.");
        } else if (args[0].equalsIgnoreCase("status")) {
            // show the player's current blacklist
            player.sendMessage("You are currently NOT picking up these blocks: ");
            player.sendMessage(packPlayer.getBlacklistString());
        }
        
        return validUsage;
    }
    
    /**
     * Returns the material associated with the flag parameter
     * @param player Player
     * @param flag String (most cases will either be 'hand' or a specific material String)
     * @return Material
     * @throws ItemNotFoundException thrown when the Material cannot be found
     */
    private Material selectMaterial(Player player, String flag) throws ItemNotFoundException {
        Material material = null;

        if (flag.equalsIgnoreCase("hand")) {    // select material in hand
            material = player.getInventory().getItemInMainHand().getType();
        } else {
            try {
                material = Material.valueOf(flag.toUpperCase());    // select specific material
            } catch (Exception e) {
                throw new ItemNotFoundException();
            }
        }

        return material;
    }

}
