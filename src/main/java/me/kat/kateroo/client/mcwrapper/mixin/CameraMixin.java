package me.kat.kateroo.client.mcwrapper.mixin;

import me.kat.kateroo.client.config.KaterooModMenu;
import net.minecraft.client.render.Camera;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Camera.class)
public class CameraMixin {
    @Inject(
            method = "getSubmergedFluidState",
            at = @At("RETURN"),
            cancellable = true

    )
    private void miaumiau(CallbackInfoReturnable<FluidState> cir) {
        FluidState fluid = cir.getReturnValue();
        if (fluid.getFluid() == Fluids.LAVA || fluid.getFluid() == Fluids.FLOWING_LAVA) {
            if (KaterooModMenu.noLavaFog.getValue()) {
                cir.setReturnValue(Fluids.EMPTY.getDefaultState());
            }
        }
    }

}
