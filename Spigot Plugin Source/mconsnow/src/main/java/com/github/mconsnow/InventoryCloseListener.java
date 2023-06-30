package com.github.mconsnow;

import com.google.gson.JsonObject;
import com.google.gson.Gson;
import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.block.Chest;
import org.bukkit.block.DoubleChest;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

public class InventoryCloseListener implements Listener {
    RestHandler restHandler;

    public InventoryCloseListener(RestHandler RestHandler) {
        this.restHandler = RestHandler;
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        if (!(event.getInventory().getHolder() instanceof DoubleChest || event.getInventory().getHolder() instanceof Chest )) {
            return; //We only care about inventory changes of Chests (for Now :)... )
        }
        Location chestLoc =  event.getInventory().getLocation();
        JsonObject jsonBody = new JsonObject();
        jsonBody.addProperty("xPos", chestLoc.getX());
        jsonBody.addProperty("yPos", chestLoc.getY());
        jsonBody.addProperty("zPos", chestLoc.getZ());
        jsonBody.addProperty("world", chestLoc.getWorld().toString());
        jsonBody.addProperty("items", ItemStackDeserializer(event.getInventory().getContents()));
        restHandler.sendPOST("chest", jsonBody.toString());
    }

    private String ItemStackDeserializer(ItemStack[] itemStackArr) {
        HashMap<String, Integer> items = new HashMap<String, Integer>();
        for (ItemStack itemStack : itemStackArr) {
            if (itemStack == null) {continue;} //skip empty slots
            if (items.containsKey(itemStack.getType().name())) {
                items.put(itemStack.getType().name(), items.get(itemStack.getType().name()) + itemStack.getAmount()); //Update existing count
            }
            else {
                items.put(itemStack.getType().name(), itemStack.getAmount()); //New Entry
            }
        }
        String json = new Gson().toJson(items);
        return json;
    }
    
}
