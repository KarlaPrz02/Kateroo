package me.kat.kateroo.client;

import me.kat.kateroo.client.config.ConfigIO;
import me.kat.kateroo.client.feature.KatMiningRewardManager;
import net.fabricmc.api.ClientModInitializer;

public class KaterooBaseClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ConfigIO.loadConfig();
        KatMiningRewardManager.registerListeners();
    }
}
