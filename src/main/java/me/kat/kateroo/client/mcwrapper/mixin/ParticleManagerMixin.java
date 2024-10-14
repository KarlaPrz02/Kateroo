package me.kat.kateroo.client.mcwrapper.mixin;

import me.kat.kateroo.client.config.KaterooModMenu;
import net.minecraft.block.BlockState;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ParticleManager.class)
public class ParticleManagerMixin {
    @Inject(
            method = "addBlockBreakParticles",
            at = @At("HEAD"),
            cancellable = true)
    private void disableBlockBreakParticles(BlockPos pos, BlockState state, CallbackInfo ci) {
        if (KaterooModMenu.disableBlockBreakingParticles.getValue()) {
            ci.cancel();
        }
    }
}
