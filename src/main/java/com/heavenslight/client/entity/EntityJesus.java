package com.heavenslight.client.entity;

import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.joml.Matrix4f;

public class EntityJesus {
    private final World world;
    private Vec3d position;
    private float animationTime = 0.0f;
    private float glowIntensity = 0.0f;
    
    public EntityJesus(World world, Vec3d position) {
        this.world = world;
        this.position = position;
    }
    
    public void render(WorldRenderContext context) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null) return;
        
        animationTime += 0.05f;
        glowIntensity = 0.5f + 0.5f * (float)Math.sin(animationTime * 2);
        
        MatrixStack matrices = context.matrixStack();
        VertexConsumerProvider vertexConsumers = context.consumers();
        
        matrices.push();
        
        // Position relative to camera
        Vec3d cameraPos = context.camera().getPos();
        matrices.translate(
            position.x - cameraPos.x,
            position.y - cameraPos.y + Math.sin(animationTime) * 0.1, // Gentle floating
            position.z - cameraPos.z
        );
        
        // Face the player
        Vec3d playerPos = client.player.getPos();
        Vec3d direction = playerPos.subtract(position).normalize();
        float yaw = (float)Math.atan2(direction.x, direction.z);
        matrices.multiply(net.minecraft.util.math.RotationAxis.POSITIVE_Y.rotation(yaw));
        
        // Scale
        matrices.scale(1.2f, 1.8f, 1.2f);
        
        // Render the figure
        renderJesusFigure(matrices, vertexConsumers, context.tickCounter().getTickDelta(false));
        
        matrices.pop();
    }
    
    private void renderJesusFigure(MatrixStack matrices, VertexConsumerProvider vertexConsumers, float tickDelta) {
        // Simple representation using basic shapes
        // This is a placeholder - in a full implementation, you'd use a proper model
        
        // Render glow effect
        renderGlow(matrices, vertexConsumers);
        
        // Render basic humanoid shape
        renderBody(matrices, vertexConsumers);
        renderArms(matrices, vertexConsumers);
        renderHead(matrices, vertexConsumers);
    }
    
    private void renderGlow(MatrixStack matrices, VertexConsumerProvider vertexConsumers) {
        // Render a glowing aura around the figure
        matrices.push();
        matrices.scale(1.5f, 1.5f, 1.5f);
        
        // This would typically use a custom shader or particle system
        // For now, we'll use a simple approach
        
        matrices.pop();
    }
    
    private void renderBody(MatrixStack matrices, VertexConsumerProvider vertexConsumers) {
        matrices.push();
        matrices.translate(0, -0.5f, 0);
        matrices.scale(0.6f, 1.0f, 0.3f);
        
        // Render white robe-like body
        // This would use a proper vertex buffer in a full implementation
        
        matrices.pop();
    }
    
    private void renderArms(MatrixStack matrices, VertexConsumerProvider vertexConsumers) {
        // Gentle open-armed gesture
        float armAngle = (float)Math.sin(animationTime * 0.5) * 0.2f;
        
        // Left arm
        matrices.push();
        matrices.translate(-0.4f, 0.2f, 0);
        matrices.multiply(net.minecraft.util.math.RotationAxis.POSITIVE_Z.rotation(-0.5f - armAngle));
        matrices.scale(0.2f, 0.8f, 0.2f);
        matrices.pop();
        
        // Right arm
        matrices.push();
        matrices.translate(0.4f, 0.2f, 0);
        matrices.multiply(net.minecraft.util.math.RotationAxis.POSITIVE_Z.rotation(0.5f + armAngle));
        matrices.scale(0.2f, 0.8f, 0.2f);
        matrices.pop();
    }
    
    private void renderHead(MatrixStack matrices, VertexConsumerProvider vertexConsumers) {
        matrices.push();
        matrices.translate(0, 0.8f, 0);
        matrices.scale(0.4f, 0.4f, 0.4f);
        
        // Render serene face with gentle glow
        
        matrices.pop();
    }
    
    public Vec3d getPosition() {
        return position;
    }
    
    public void setPosition(Vec3d position) {
        this.position = position;
    }
}