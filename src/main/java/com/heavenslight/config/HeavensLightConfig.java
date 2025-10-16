package com.heavenslight.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.heavenslight.HeavensLightMod;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class HeavensLightConfig {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path CONFIG_PATH = FabricLoader.getInstance().getConfigDir().resolve("heavens-light.json");
    
    public enum EntityType {
        JESUS("Jesus Figure"),
        ANGEL("Angel"),
        LIGHT_BEING("Being of Light");
        
        private final String displayName;
        
        EntityType(String displayName) {
            this.displayName = displayName;
        }
        
        public String getDisplayName() {
            return displayName;
        }
    }
    
    // Configuration fields with defaults
    public EntityType entityType = EntityType.JESUS;
    public float healthThreshold = 4.0f; // 2 hearts
    public int visionDurationTicks = 100; // 5 seconds
    public int deathSequenceDurationTicks = 200; // 10 seconds
    public boolean enableSounds = true;
    public float soundVolume = 0.8f;
    public boolean enableVisionOnLowHealth = true;
    public boolean enableDeathSequence = true;
    public float overlayIntensity = 0.7f;
    public boolean enableParticles = true;
    
    private static HeavensLightConfig instance;
    
    public static HeavensLightConfig getInstance() {
        if (instance == null) {
            instance = load();
        }
        return instance;
    }
    
    public static HeavensLightConfig load() {
        if (Files.exists(CONFIG_PATH)) {
            try {
                String json = Files.readString(CONFIG_PATH);
                HeavensLightConfig config = GSON.fromJson(json, HeavensLightConfig.class);
                if (config != null) {
                    HeavensLightMod.LOGGER.info("Loaded Heaven's Light configuration");
                    return config;
                }
            } catch (IOException e) {
                HeavensLightMod.LOGGER.error("Failed to load config file", e);
            }
        }
        
        // Return default config and save it
        HeavensLightConfig defaultConfig = new HeavensLightConfig();
        defaultConfig.save();
        return defaultConfig;
    }
    
    public void save() {
        try {
            Files.createDirectories(CONFIG_PATH.getParent());
            String json = GSON.toJson(this);
            Files.writeString(CONFIG_PATH, json);
            HeavensLightMod.LOGGER.info("Saved Heaven's Light configuration");
        } catch (IOException e) {
            HeavensLightMod.LOGGER.error("Failed to save config file", e);
        }
    }
    
    // Getters for easy access
    public EntityType getEntityType() {
        return entityType;
    }
    
    public float getHealthThreshold() {
        return healthThreshold;
    }
    
    public int getVisionDurationTicks() {
        return visionDurationTicks;
    }
    
    public int getDeathSequenceDurationTicks() {
        return deathSequenceDurationTicks;
    }
    
    public boolean isSoundsEnabled() {
        return enableSounds;
    }
    
    public float getSoundVolume() {
        return soundVolume;
    }
    
    public boolean isVisionOnLowHealthEnabled() {
        return enableVisionOnLowHealth;
    }
    
    public boolean isDeathSequenceEnabled() {
        return enableDeathSequence;
    }
    
    public float getOverlayIntensity() {
        return overlayIntensity;
    }
    
    public boolean areParticlesEnabled() {
        return enableParticles;
    }
}