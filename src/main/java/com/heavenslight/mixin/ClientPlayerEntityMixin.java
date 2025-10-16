package com.heavenslight.mixin;

import com.heavenslight.client.HeavensLightClient;
import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerEntity.class)
public class ClientPlayerEntityMixin {
    private float lastHealth = 20.0f;
    
    @Inject(method = "tick", at = @At("HEAD"))
    private void onTick(CallbackInfo ci) {
        ClientPlayerEntity player = (ClientPlayerEntity)(Object)this;
        float currentHealth = player.getHealth();
        
        // Check for health changes that might trigger vision
        if (currentHealth <= 4.0f && lastHealth > 4.0f && currentHealth > 0.0f) {
            // Health just dropped to critical level
            if (!HeavensLightClient.isInVision() && !HeavensLightClient.isInDeathSequence()) {
                HeavensLightClient.triggerVision();
            }
        }
        
        lastHealth = currentHealth;
    }
}