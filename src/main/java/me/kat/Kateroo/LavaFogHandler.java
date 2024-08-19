package me.kat.kateroo;

import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.FogShape;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.util.math.MatrixStack;

public class LavaFogHandler {

    public static void init() {
        // Register render event to handle fog under lava
        WorldRenderEvents.BEFORE_FOG.register(LavaFogHandler::onRenderFog);
    }

    private static void onRenderFog(Camera camera, FogShape fogShape, float viewDistance, boolean thickFog) {
        if (ConfigHandler.isLavaFogEnabled()) {
            return; // Do nothing if fog is enabled
        }

        // Check if the player is inside the lava
        if (camera.getFocusedEntity().isSubmergedInLava()) {
            // Turn off the fog
            MinecraftClient.getInstance().worldRenderer.setFogBlack();
            WorldRenderer.setFogDensity(0.0f);
        }
    }
}
