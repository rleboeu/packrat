package src;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Main plugin class for Packrat
 * @author rleboeu
 * @version 1.0
 */
public class Packrat extends JavaPlugin {

    
    @Override
    public void onEnable() {
        this.getServer().getPluginManager().registerEvents(new PackratListener(), this);

        this.getCommand("nopickup").setExecutor(new CommandNoPickup());
        this.getCommand("pickup").setExecutor(new CommandPickup());
        
        PackratData.createJSONFileIfAbsent();
    }

    @Override
    public void onDisable() {
        // save all players currently present in the register
        for (Player player : Bukkit.getOnlinePlayers()) {
            PackratData.savePlayerFromRegister(player.getName());
        }
    }

}