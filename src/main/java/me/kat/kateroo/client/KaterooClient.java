package me.kat.kateroo.client;
import me.kat.kateroo.client.config.ConfigIO;

import net.fabricmc.api.ClientModInitializer;

public class KaterooClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ConfigIO.loadConfig();
    }
}