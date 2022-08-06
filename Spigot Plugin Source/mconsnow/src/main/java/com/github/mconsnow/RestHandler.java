package com.github.mconsnow;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Base64;

import org.bukkit.Bukkit;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class RestHandler {

    private ConfigHandler configHandler;

    private String instanceURL;
    private String apiRoot;
    private String instanceUsername;
    private String instancePassword;


    public RestHandler (ConfigHandler configHandler) {
        this.configHandler = configHandler;
        this.instanceURL = configHandler.getConfigString("instanceURL");
        this.instanceUsername = configHandler.getConfigString("instanceUsername");
        this.instancePassword = configHandler.getConfigString("instancePassword");
        this.apiRoot = configHandler.getConfigString("apiRootPath");

    }

    public void sendPOST(String endpoint, String jsonBody) {
        Bukkit.getLogger().info("[RestHandler] POST: " + endpoint + " - " + jsonBody);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(this.instanceURL + this.apiRoot + endpoint))
            .header("accept", "application/json")
            .header("Authorization", "Basic " + calcB64(this.instanceUsername + ":" + this.instancePassword))
            .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
            .build();
        HttpResponse<String> response;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
            Bukkit.getLogger().info("[RestHandler] Response Code: " + response.statusCode());
            if (response.statusCode() != 201) {
                Bukkit.getLogger().warning("[RestHandler] Invalid Response Code " + response.statusCode() + " received for request " + endpoint);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //TODO: Should I return the response for handling?
    }

    public JsonObject sendGET(String endpoint) {
        Bukkit.getLogger().info("[RestHandler] GET: " + endpoint);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(this.instanceURL + this.apiRoot + endpoint))
            .header("accept", "application/json")
            .header("Authorization", "Basic " + calcB64(this.instanceUsername + ":" + this.instancePassword))
            .GET()
            .build();
        HttpResponse<String> response;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
            Bukkit.getLogger().info("[RestHandler] Response Code: " + response.statusCode());
            if (response.statusCode() != 200) {
                Bukkit.getLogger().warning("[RestHandler] Invalid Response Code " + response.statusCode() + " received for request " + endpoint);
                return new JsonObject();
            }
            JsonObject jsonReturn = JsonParser.parseString(response.body()).getAsJsonObject();
            return jsonReturn;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new JsonObject();
    }

    public void sendDELETE(String endpoint) {
        Bukkit.getLogger().info("[RestHandler] DELETE: " + endpoint);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create(this.instanceURL + this.apiRoot + endpoint))
        .header("accept", "application/json")
        .header("Authorization", "Basic " + calcB64(this.instanceUsername + ":" + this.instancePassword))
        .DELETE()
        .build();
        HttpResponse<String> response;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
            Bukkit.getLogger().info("[RestHandler] Response Code: " + response.statusCode());
            if (response.statusCode() != 200) {
                Bukkit.getLogger().warning("[RestHandler] Invalid Response Code " + response.statusCode() + " received for request " + endpoint);
                return;
            }
            return;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return;
    }

    private String calcB64(String input) {
        return Base64.getEncoder().encodeToString(input.getBytes());
    }
}
