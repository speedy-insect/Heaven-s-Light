package com.heavenslight.mixin;

import com.heavenslight.client.HeavensLightClient;
import net.minecraft.client.gui.screen.DeathScreen;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DeathScreen.class)
public class DeathScreenMixin {
    
    @Inject(method = "init", at = @At("HEAD"))
    private void onInit(CallbackInfo ci) {
        // Trigger death sequence when death screen is about to show
        if (!HeavensLightClient.isInDeathSequence()) {
            HeavensLightClient.triggerDeathSequence();
        }
    }
    
    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    private void onRender(CallbackInfo ci) {
        // Hide the death screen during the heavenly sequence
        if (HeavensLightClient.isInDeathSequence() && 
            HeavensLightClient.getDeathSequenceTicks() < 150) { // Show for 7.5 seconds
            ci.cancel();
        }
    }
}