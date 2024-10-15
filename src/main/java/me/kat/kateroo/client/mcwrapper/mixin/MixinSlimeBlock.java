package me.kat.kateroo.client.mcwrapper.mixin;

import me.kat.kateroo.client.config.KaterooModMenu;
import net.minecraft.block.Block;
import net.minecraft.block.SlimeBlock;
import net.minecraft.block.TransparentBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SlimeBlock.class)
public abstract class MixinSlimeBlock extends TransparentBlock
{
    public MixinSlimeBlock(Block.Settings settings)
    {
        super(settings);
    }

    @Inject(method = "onSteppedOn", at =@At("HEAD"), cancellable = true)
private void onEntityWalkOnSlime(World worldIn, BlockPos pos, Entity entityIn, CallbackInfo ci)
    {
     if (KaterooModMenu.disableSlimeBlockPlayerMovement.getDefaultValue()  && entityIn instanceof PlayerEntity)
     {
         super.onSteppedOn(worldIn, pos, entityIn);
         ci.cancel();
     }
    }
}