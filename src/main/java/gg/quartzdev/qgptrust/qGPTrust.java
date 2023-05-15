package gg.quartzdev.qgptrust;

import org.bukkit.plugin.java.JavaPlugin;
import gg.quartzdev.qgptrust.metrics.Metrics;

import gg.quartzdev.qgptrust.listener.ClaimCreate;
public final class qGPTrust extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic

//        bStats Metrics
        getLogger().info("Enabling bStats Metrics");
        int pluginId = 18477;
        Metrics metrics = new Metrics(this, pluginId);

//        register ClaimCreate event from grief prevention
        getLogger().info("Hooking into GriefPrevention");
        try {
            getServer().getPluginManager().registerEvents(new ClaimCreate(), this);
        } catch(Exception e){
            getLogger().warning("Failed hooking into GriefPrevention: ");
            getLogger().warning(e.getMessage());
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }



}
