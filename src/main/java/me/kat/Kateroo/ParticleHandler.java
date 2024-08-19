package me.kat.kateroo;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class ParticleHandler {

    public static void init() {
        // Log block breaking event to handle particles
        PlayerBlockBreakEvents.AFTER.register(ParticleHandler::onBlockBreak);
    }

    private static void onBlockBreak(World world, BlockPos pos, Direction direction) {
        // Check if broken block particles are enabled
        if (!ConfigHandler.isBlockParticlesEnabled()) {
            return; // Do nothing if particles are disabled
        }

        // Generate particles at the position of the broken block
        MinecraftClient.getInstance().particleManager.addBlockBreakingParticles(pos, direction);
    }
}

