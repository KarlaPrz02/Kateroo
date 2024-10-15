package me.kat.kateroo.client.mcwrapper.mixin;

import me.kat.kateroo.client.config.KaterooModMenu;
import net.minecraft.block.Block;
import net.minecraft.block.HoneyBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Block.class)
public class BlockMixin {

    @Inject(
            method = "getJumpVelocityMultiplier",
            at = @At("HEAD"),
            cancellable = true
    )
    private void jumpVelocityGetterCallback(CallbackInfoReturnable<Float> cir) {
        if (((Block) (Object) this) instanceof HoneyBlock) {
            cir.setReturnValue(1.0f);
        }
    }

    @Inject(
            method = "getVelocityMultiplier",
            at = @At("HEAD"),
            cancellable = true
    )
    private void velocityGetterCallback(CallbackInfoReturnable<Float> cir) {
        if (((Block) (Object) this) instanceof HoneyBlock) {
            if (KaterooModMenu.disableHoneyBlockPlayerMovement.getValue()) {
                cir.setReturnValue(1.0f);
            }
        }
    }

}
