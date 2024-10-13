package me.kat.kateroo.client.mcwrapper.mixin;

import me.kat.kateroo.client.config.KaterooModMenu;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public class GameRendererMixin {
    @Inject(
            method = "bobViewWhenHurt",
            at = @At("HEAD"),
            cancellable = true
    )
    private void meowmeow(MatrixStack matrixStack, float f, CallbackInfo ci) {
        if (KaterooModMenu.disableCameraMovementWhenDamage.getValue()) {
            ci.cancel();
        }
    }
}
