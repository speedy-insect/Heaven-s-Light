package com.heavenslight.client.renderer;

import com.heavenslight.client.HeavensLightClient;
import com.heavenslight.client.entity.EntityJesus;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix4f;

public class HeavenlyOverlayRenderer {
    private EntityJesus jesusEntity;
    private float overlayAlpha = 0.0f;
    private float glowIntensity = 0.0f;
    private boolean isVisionActive = false;
    private boolean isDeathSequenceActive = false;
    
    public void startVision() {
        isVisionActive = true;
        spawnJesusEntity();
    }
    
    public void startDeathSequence() {
        isDeathSequenceActive = true;
        spawnJesusEntity();
    }
    
    public void endVision() {
        isVisionActive = false;
        despawnJesusEntity();
    }
    
    public void endDeathSequence() {
        isDeathSequenceActive = false;
        despawnJesusEntity();
    }
    
    private void spawnJesusEntity() {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player != null && client.world != null) {
            Vec3d playerPos = client.player.getPos();
            Vec3d spawnPos = playerPos.add(0, 2, -5); // Spawn in front of and above player
            
            jesusEntity = new EntityJesus(client.world, spawnPos);
        }
    }
    
    private void despawnJesusEntity() {
        jesusEntity = null;
    }
    
    public void renderOverlay(DrawContext context, RenderTickCounter tickCounter) {
        if (!isVisionActive && !isDeathSequenceActive) {
            overlayAlpha = Math.max(0.0f, overlayAlpha - 0.02f);
            glowIntensity = Math.max(0.0f, glowIntensity - 0.02f);
            return;
        }
        
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null) return;
        
        // Calculate animation progress
        float progress;
        if (isDeathSequenceActive) {
            progress = Math.min(1.0f, HeavensLightClient.getDeathSequenceTicks() / 100.0f);
        } else {
            progress = Math.min(1.0f, HeavensLightClient.getVisionTicks() / 60.0f);
        }
        
        // Update overlay effects
        overlayAlpha = Math.min(0.7f, progress * 0.7f);
        glowIntensity = Math.min(1.0f, progress);
        
        // Render golden overlay
        renderGoldenOverlay(context, overlayAlpha);
        
        // Render glow effect
        renderGlowEffect(context, glowIntensity);
        
        // Render particles
        renderHeavenlyParticles(context, progress);
    }
    
    private void renderGoldenOverlay(DrawContext context, float alpha) {
        if (alpha <= 0) return;
        
        int width = context.getScaledWindowWidth();
        int height = context.getScaledWindowHeight();
        
        // Golden color with alpha
        int color = (int)(alpha * 255) << 24 | 0xFFD700; // Gold color
        context.fill(0, 0, width, height, color);
    }
    
    private void renderGlowEffect(DrawContext context, float intensity) {
        if (intensity <= 0) return;
        
        int width = context.getScaledWindowWidth();
        int height = context.getScaledWindowHeight();
        
        // Create radial glow from center
        MatrixStack matrices = context.getMatrices();
        matrices.push();
        
        // White glow with varying intensity
        int centerX = width / 2;
        int centerY = height / 2;
        int radius = (int)(Math.min(width, height) * 0.6f * intensity);
        
        // Simple radial gradient approximation
        for (int r = radius; r > 0; r -= 10) {
            float alpha = (intensity * 0.3f) * (1.0f - (float)r / radius);
            int color = (int)(alpha * 255) << 24 | 0xFFFFFF; // White
            context.drawBorder(centerX - r, centerY - r, r * 2, r * 2, color);
        }
        
        matrices.pop();
    }
    
    private void renderHeavenlyParticles(DrawContext context, float progress) {
        if (progress <= 0) return;
        
        MinecraftClient client = MinecraftClient.getInstance();
        long time = client.world != null ? client.world.getTime() : 0;
        
        int width = context.getScaledWindowWidth();
        int height = context.getScaledWindowHeight();
        
        // Render floating light particles
        for (int i = 0; i < 20; i++) {
            float x = (float)(Math.sin(time * 0.01 + i) * width * 0.3 + width * 0.5);
            float y = (float)(Math.cos(time * 0.008 + i * 0.5) * height * 0.2 + height * 0.4);
            
            float alpha = progress * 0.8f * (0.5f + 0.5f * (float)Math.sin(time * 0.05 + i));
            int color = (int)(alpha * 255) << 24 | 0xFFFFFF;
            
            // Draw small glowing dots
            context.fill((int)x - 1, (int)y - 1, (int)x + 1, (int)y + 1, color);
        }
    }
    
    public void renderHeavenlyEntities(WorldRenderContext context) {
        if (jesusEntity != null) {
            jesusEntity.render(context);
        }
    }
}