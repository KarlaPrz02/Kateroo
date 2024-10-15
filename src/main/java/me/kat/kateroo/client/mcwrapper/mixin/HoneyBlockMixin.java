package me.kat.kateroo.client.mcwrapper.mixin;

import me.kat.kateroo.client.config.KaterooModMenu;
import net.minecraft.block.Block;
import net.minecraft.block.HoneyBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HoneyBlock.class)
public class HoneyBlockMixin {
    @Inject(
            method = "updateSlidingVelocity",
            at = @At("HEAD"),
            cancellable = true
    )
    private void HoneyBlockThing(CallbackInfo ci) {
        ci.cancel();
    }
    @ModifyVariable(
            method = "<init>",
            at = @At("HEAD"),
            ordinal = 0,
            argsOnly = true)
    private static Block.Settings honeyBlockTop(Block.Settings value) {
        return value.velocityMultiplier(1.0f).jumpVelocityMultiplier(1.0f);
    }
}
