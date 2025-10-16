package com.heavenslight;

import com.heavenslight.client.sound.HeavenlySoundManager;
import com.heavenslight.config.HeavensLightConfig;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HeavensLightMod implements ModInitializer {
    public static final String MOD_ID = "heavens-light";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    
    // Network identifiers
    public static final Identifier TRIGGER_VISION_PACKET = Identifier.of(MOD_ID, "trigger_vision");
    public static final Identifier TRIGGER_DEATH_SEQUENCE_PACKET = Identifier.of(MOD_ID, "trigger_death_sequence");
    
    @Override
    public void onInitialize() {
        LOGGER.info("Heaven's Light mod initializing...");
        
        // Initialize configuration
        HeavensLightConfig.getInstance();
        
        // Register sounds
        HeavenlySoundManager.registerSounds();
        
        // Register server-side event handlers
        ServerLivingEntityEvents.ALLOW_DEATH.register(this::onEntityDeath);
        
        LOGGER.info("Heaven's Light mod initialized successfully!");
    }
    
    private boolean onEntityDeath(LivingEntity entity, DamageSource damageSource, float damageAmount) {
        if (entity instanceof ServerPlayerEntity player) {
            // Send packet to client to trigger death sequence
            ServerPlayNetworking.send(player, TRIGGER_DEATH_SEQUENCE_PACKET, 
                ServerPlayNetworking.createS2CPacket(TRIGGER_DEATH_SEQUENCE_PACKET, buf -> {}));
        }
        return true; // Allow death to proceed normally
    }
}