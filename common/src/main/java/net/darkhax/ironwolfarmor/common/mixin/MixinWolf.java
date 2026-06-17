package net.darkhax.ironwolfarmor.common.mixin;

import net.darkhax.ironwolfarmor.common.Content;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.animal.wolf.Wolf;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Wolf.class)
public class MixinWolf {

    @Inject(method = "canArmorAbsorb", at = @At("HEAD"), cancellable = true)
    private void canArmorAbsorb(DamageSource source, CallbackInfoReturnable<Boolean> cir) {
        final Wolf wolf = (Wolf) (Object) this;
        if (!source.is(DamageTypeTags.BYPASSES_WOLF_ARMOR) && wolf.getBodyArmorItem().is(Content.ABSORBS_WOLF_DAMAGE)) {
            cir.setReturnValue(true);
        }
    }
}