package com.github.mconsnow;
import org.bukkit.plugin.java.JavaPlugin;


public class App extends JavaPlugin {

    private ConfigHandler configHandler = new ConfigHandler(this);
    private RestHandler restHandler;

    @Override
    public void onEnable() {
        configHandler.RegisterConfigDefaults();
        this.restHandler = new RestHandler(configHandler);
        getServer().getPluginManager().registerEvents(new DeathListener(this.restHandler), this);
        getServer().getPluginManager().registerEvents(new InventoryCloseListener(this.restHandler), this);
        new CommandHandler(this, configHandler, this.restHandler);


    }
    
    @Override
    public void onDisable() {
        
    }
}
