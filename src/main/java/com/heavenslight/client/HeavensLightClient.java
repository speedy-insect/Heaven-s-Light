package com.heavenslight.client;

import com.heavenslight.HeavensLightMod;
import com.heavenslight.client.entity.EntityJesus;
import com.heavenslight.client.renderer.HeavenlyOverlayRenderer;
import com.heavenslight.client.sound.HeavenlySoundManager;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;

public class HeavensLightClient implements ClientModInitializer {
    private static HeavenlyOverlayRenderer overlayRenderer;
    private static HeavenlySoundManager soundManager;
    private static boolean isInVision = false;
    private static boolean isInDeathSequence = false;
    private static int visionTicks = 0;
    private static int deathSequenceTicks = 0;
    
    @Override
    public void onInitializeClient() {
        HeavensLightMod.LOGGER.info("Heaven's Light client initializing...");
        
        overlayRenderer = new HeavenlyOverlayRenderer();
        soundManager = new HeavenlySoundManager();
        
        // Register network packet handlers
        ClientPlayNetworking.registerGlobalReceiver(HeavensLightMod.TRIGGER_VISION_PACKET, 
            (client, handler, buf, responseSender) -> {
                client.execute(() -> triggerVision());
            });
            
        ClientPlayNetworking.registerGlobalReceiver(HeavensLightMod.TRIGGER_DEATH_SEQUENCE_PACKET,
            (client, handler, buf, responseSender) -> {
                client.execute(() -> triggerDeathSequence());
            });
        
        // Register client tick event for health monitoring
        ClientTickEvents.END_CLIENT_TICK.register(this::onClientTick);
        
        // Register HUD rendering
        HudRenderCallback.EVENT.register(overlayRenderer::renderOverlay);
        
        // Register world rendering for entities
        WorldRenderEvents.AFTER_ENTITIES.register(overlayRenderer::renderHeavenlyEntities);
        
        HeavensLightMod.LOGGER.info("Heaven's Light client initialized successfully!");
    }
    
    private void onClientTick(MinecraftClient client) {
        if (client.player == null || client.world == null) return;
        
        PlayerEntity player = client.player;
        float health = player.getHealth();
        
        // Check for low health vision trigger using config
        float threshold = com.heavenslight.config.HeavensLightConfig.getInstance().getHealthThreshold();
        if (health <= threshold && health > 0.0f && !isInVision && !isInDeathSequence) {
            if (com.heavenslight.config.HeavensLightConfig.getInstance().isVisionOnLowHealthEnabled()) {
                triggerVision();
            }
        }
        
        // Update vision state
        if (isInVision) {
            visionTicks++;
            int visionDuration = com.heavenslight.config.HeavensLightConfig.getInstance().getVisionDurationTicks();
            if (visionTicks > visionDuration) {
                endVision();
            }
        }
        
        // Update death sequence state
        if (isInDeathSequence) {
            deathSequenceTicks++;
            int deathDuration = com.heavenslight.config.HeavensLightConfig.getInstance().getDeathSequenceDurationTicks();
            if (deathSequenceTicks > deathDuration) {
                endDeathSequence();
            }
        }
    }
    
    public static void triggerVision() {
        if (isInDeathSequence) return; // Don't trigger vision during death sequence
        
        isInVision = true;
        visionTicks = 0;
        overlayRenderer.startVision();
        soundManager.playVisionSound();
        HeavensLightMod.LOGGER.info("Vision sequence triggered");
    }
    
    public static void triggerDeathSequence() {
        if (!com.heavenslight.config.HeavensLightConfig.getInstance().isDeathSequenceEnabled()) {
            return;
        }
        
        isInDeathSequence = true;
        isInVision = false; // Override any existing vision
        deathSequenceTicks = 0;
        overlayRenderer.startDeathSequence();
        soundManager.playDeathSequenceSound();
        HeavensLightMod.LOGGER.info("Death sequence triggered");
    }
    
    private void endVision() {
        isInVision = false;
        visionTicks = 0;
        overlayRenderer.endVision();
        soundManager.stopVisionSound();
    }
    
    private void endDeathSequence() {
        isInDeathSequence = false;
        deathSequenceTicks = 0;
        overlayRenderer.endDeathSequence();
        soundManager.stopDeathSequenceSound();
    }
    
    public static boolean isInVision() {
        return isInVision;
    }
    
    public static boolean isInDeathSequence() {
        return isInDeathSequence;
    }
    
    public static int getVisionTicks() {
        return visionTicks;
    }
    
    public static int getDeathSequenceTicks() {
        return deathSequenceTicks;
    }
}