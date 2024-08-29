package me.kat.kateroo.client.mcwrapper.mixin;

import me.kat.kateroo.client.feature.KatMiningRewardManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.hud.InGameHud;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public abstract class InGameHudMixin extends DrawableHelper {

    @Shadow @Final private MinecraftClient client;

    @Inject(
            method = "render",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/mojang/blaze3d/systems/RenderSystem;color4f(FFFF)V",
                    ordinal = 0,
                    shift = At.Shift.AFTER
            )
    )
    private void injectKatMiningReward(CallbackInfo ci) {
        KatMiningRewardManager.render(this.client, this.getBlitOffset());
    }

}
