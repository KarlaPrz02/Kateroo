package me.kat.kateroo;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ConfigHandler {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final File CONFIG_FILE = new File(FabricLoader.getInstance().getConfigDir().toFile(), "kateroo_config.json");

    private static boolean lavaFogEnabled = true;
    private static boolean blockParticlesEnabled = true;

    // Load config from file
    public static void loadConfig() {
        if (CONFIG_FILE.exists()) {
            try (FileReader reader = new FileReader(CONFIG_FILE)) {
                Map<String, Boolean> config = GSON.fromJson(reader, Map.class);
                if (config != null) {
                    lavaFogEnabled = config.getOrDefault("lavaFogEnabled", true);
                    blockParticlesEnabled = config.getOrDefault("blockParticlesEnabled", true);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            saveConfig(); // If config file doesnt exist, creates one
        }
    }

    // Save config on file
    public static void saveConfig() {
        Map<String, Boolean> config = new HashMap<>();
        config.put("lavaFogEnabled", lavaFogEnabled);
        config.put("blockParticlesEnabled", blockParticlesEnabled);

        try (FileWriter writer = new FileWriter(CONFIG_FILE)) {
            GSON.toJson(config, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // alternate lava fog
    public static void toggleLavaFog() {
        lavaFogEnabled = !lavaFogEnabled;
        saveConfig();
    }

    // alternate particles
    public static void toggleBlockParticles() {
        blockParticlesEnabled = !blockParticlesEnabled;
        saveConfig();
    }

    // Get fog status under lava
    public static boolean isLavaFogEnabled() {
        return lavaFogEnabled;
    }

    // Get status of broken block particles
    public static boolean isBlockParticlesEnabled() {
        return blockParticlesEnabled;
    }
}