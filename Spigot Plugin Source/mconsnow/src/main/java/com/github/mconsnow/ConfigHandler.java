package com.github.mconsnow;
import org.bukkit.plugin.java.JavaPlugin;

public class ConfigHandler {
    private final JavaPlugin plugin;

    public ConfigHandler(JavaPlugin plugin) {
        this.plugin = plugin;
        
    }

    public void RegisterConfigDefaults() {
        this.plugin.getConfig().addDefault("instanceURL", "https://<CHANGEME>.service-now.com/");
        this.plugin.getConfig().addDefault("instanceUsername", "*USERNAME*");
        this.plugin.getConfig().addDefault("instancePassword", "*PASSWORD*");
        this.plugin.getConfig().addDefault("apiRootPath", "/api/x_176932_minecra_0/minecraftonsnow/");
        this.plugin.getConfig().addDefault("commandHeartbeatSeconds", 30);
        this.plugin.getConfig().options().copyDefaults(true);
        this.plugin.saveConfig();
    }

    public String getConfigString(String settingName) {
        return plugin.getConfig().getString(settingName);
    }

    public Integer getConfigInteger(String settingName) {
        return plugin.getConfig().getInt(settingName);
    }
    
}
