package me.kat.kateroo.client.mcwrapper.mixin;

import me.kat.kateroo.client.config.KaterooModMenu;
import net.minecraft.block.SlimeBlock;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SlimeBlock.class)
public class SlimeBlockMixin {
    @Redirect(
            method = "onSteppedOn",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;bypassesSteppingEffects()Z")
    )
    private boolean handleSlimeConfig(Entity instance){
        if (KaterooModMenu.disableSlimeBlockPlayerMovement.getValue()) {
            return true;
        } else {
            return instance.bypassesSteppingEffects();
        }
    }

    @Inject(
            method = "method_21847",
            at = @At("HEAD"),
            cancellable = true

    )
    private void onMethod21847(CallbackInfo ci) {
        if (KaterooModMenu.disableSlimeBlockPlayerMovement.getValue()) {
            ci.cancel();
        }
    }

    @Redirect(
            method = "onSteppedOn",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;setVelocity(Lnet/minecraft/util/math/Vec3d;)V")
    )
    private void cancelSetVelocity(Entity instance, Vec3d velocity) {
        if (!KaterooModMenu.disableHoneyBlockPlayerMovement.getValue()) {
            instance.setVelocity(velocity);
        }
    }
}


