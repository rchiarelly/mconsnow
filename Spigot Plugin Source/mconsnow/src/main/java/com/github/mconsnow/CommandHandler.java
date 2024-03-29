package com.github.mconsnow;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Callable;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class CommandHandler {
    //Checks for pending MC Commands on SNOW. I would rather the commands be pushed form SNOW -> MC Server but this setup avoids needing a midserver or an Inbound REST Listener running on the MC Server. This is a bit hacky way of doing it
    private Integer heartBeat;
    private Plugin plugin;
    private RestHandler restHandler;

    public CommandHandler(Plugin plugin, ConfigHandler configHandler, RestHandler restHandler) {
        this.restHandler = restHandler;
        this.plugin = plugin;
        this.heartBeat = configHandler.getConfigInteger("commandHeartbeatSeconds");
        if (this.heartBeat != 0) {
            //Start the heartbeat for command queue
            TimerTask task = new TimerTask() {public void run() {checkTaskQueue();}};
            Timer timer = new Timer();
            timer.schedule(task, this.heartBeat * 1000, this.heartBeat * 1000);
        }
    }

    private void checkTaskQueue() {
        try {
        JsonObject cmdRestObj = restHandler.sendGET("commandQueue");
        Bukkit.getLogger().info(cmdRestObj.get("result").toString());
        Command[] cmdArr = new Gson().fromJson(cmdRestObj.get("result"), Command[].class); 
        for (Command cmd : cmdArr) {
            restHandler.sendDELETE("commandQueue/" + cmd.sys_id); 
            Bukkit.getScheduler().callSyncMethod(plugin, new Callable<Boolean>() {
                @Override
                public Boolean call() {
                    String command = cmd.command + " " + cmd.arguments;
                    Bukkit.getLogger().info(command);
                    return Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
                }
            });
        }
        }
        catch (Exception e) {
            Bukkit.getLogger().severe("Failed to check the remote task queue!\n" + e.getMessage());
        }
    }
}