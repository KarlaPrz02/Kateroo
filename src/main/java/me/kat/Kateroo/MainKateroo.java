package me.kat.kateroo;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

public class ModMain implements ModInitializer {
    // Definition of configurable keys
    private static KeyBinding openMenuKey;

    @Override
    public void onInitialize() {
        // Load config
        ConfigHandler.loadConfig();

        // Register key to open menu
        openMenuKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.kateroo.openmenu",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_M,
                "category.kateroo.general"
        ));

        // Register a listener for the client tick event
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (openMenuKey.wasPressed()) {
                if (client.currentScreen == null) {
                    client.setScreen(new MenuScreen(Text.literal("Mod Settings")));
                }
            }
        });
    }
}
