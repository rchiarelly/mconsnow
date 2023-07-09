package com.github.mconsnow;

import com.google.gson.JsonObject;

import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class DeathListener implements Listener{
    RestHandler restHandler;

    public DeathListener(RestHandler RestHandler) {
        this.restHandler = RestHandler;
    }

    @EventHandler
    public void onPlayerDeath(EntityDeathEvent event) {
        if (event.getEntityType() != EntityType.PLAYER) { return; } //Non Player Death, do nothing
        LivingEntity entity = event.getEntity();
        String shortDesc = "Player Death: " + entity.getName();
        String description = String.format("World: %s\nLocation: %fX, %fY, %fZ", entity.getLocation().getWorld().getName(), 
                                                                                      entity.getLocation().getX(), 
                                                                                      entity.getLocation().getY(),
                                                                                      entity.getLocation().getZ());

        Bukkit.getLogger().info(shortDesc);
        Bukkit.getLogger().info(description);
        JsonObject jsonBody = new JsonObject();
        jsonBody.addProperty("shortDesc", shortDesc);
        jsonBody.addProperty("description", description);
        restHandler.sendPOST("incident", jsonBody.toString());

        //TODO: Tell the player their incident #!
    }
}
