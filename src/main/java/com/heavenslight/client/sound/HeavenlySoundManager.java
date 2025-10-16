package com.heavenslight.client.sound;

import com.heavenslight.HeavensLightMod;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class HeavenlySoundManager {
    public static final SoundEvent HEAVENLY_CHOIR = registerSound("heavenly_choir");
    public static final SoundEvent VISION_AMBIENT = registerSound("vision_ambient");
    public static final SoundEvent GENTLE_HARP = registerSound("gentle_harp");
    
    private SoundInstance currentVisionSound;
    private SoundInstance currentDeathSound;
    
    public static void registerSounds() {
        // Sounds are registered automatically when the static fields are accessed
    }
    
    private static SoundEvent registerSound(String name) {
        Identifier id = Identifier.of(HeavensLightMod.MOD_ID, name);
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
    }
    
    public void playVisionSound() {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null) return;
        
        stopVisionSound(); // Stop any existing vision sound
        
        currentVisionSound = PositionedSoundInstance.ambient(
            VISION_AMBIENT,
            1.0f, // volume
            1.0f  // pitch
        );
        
        client.getSoundManager().play(currentVisionSound);
        
        // Also play a gentle harp melody
        PositionedSoundInstance harpSound = PositionedSoundInstance.ambient(
            GENTLE_HARP,
            0.7f, // volume
            1.0f  // pitch
        );
        
        client.getSoundManager().play(harpSound);
    }
    
    public void playDeathSequenceSound() {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null) return;
        
        stopDeathSequenceSound(); // Stop any existing death sound
        
        currentDeathSound = PositionedSoundInstance.ambient(
            HEAVENLY_CHOIR,
            0.8f, // volume
            1.0f  // pitch
        );
        
        client.getSoundManager().play(currentDeathSound);
        
        // Fade out game music
        client.getMusicTracker().stop();
    }
    
    public void stopVisionSound() {
        if (currentVisionSound != null) {
            MinecraftClient.getInstance().getSoundManager().stop(currentVisionSound);
            currentVisionSound = null;
        }
    }
    
    public void stopDeathSequenceSound() {
        if (currentDeathSound != null) {
            MinecraftClient.getInstance().getSoundManager().stop(currentDeathSound);
            currentDeathSound = null;
        }
    }
    
    public void stopAllHeavenlySounds() {
        stopVisionSound();
        stopDeathSequenceSound();
    }
}