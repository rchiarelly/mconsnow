package com.github.mconsnow;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.google.gson.JsonObject;

public class PlayerJoinListener implements Listener {
    RestHandler restHandler;

    public PlayerJoinListener(RestHandler restHandler) {
        this.restHandler = restHandler;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        JsonObject jsonBody = new JsonObject();
        jsonBody.addProperty("playerName", event.getPlayer().getDisplayName());
        restHandler.sendPOST("newPlayer", jsonBody.toString());
    }
}
