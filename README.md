# Heaven's Light - Minecraft Mod

A peaceful and transcendent experience for Minecraft's death mechanic, introducing a symbolic spiritual encounter focused on light, comfort, and transcendence.

## Features

- **Vision Sequence**: When player health drops to 2 hearts or below, triggers a peaceful vision with golden light overlay and heavenly sounds
- **Death Sequence**: Upon death, replaces the harsh death screen with a serene transition featuring a glowing figure
- **Configurable Entity**: Choose between Jesus figure, Angel, or generic Being of Light
- **Atmospheric Effects**: Golden overlays, particle effects, and gentle glow animations
- **Heavenly Sounds**: Soft choir, ambient sounds, and harp melodies
- **Respectful Representation**: Symbolic and non-preachy approach to spiritual themes

## Installation

### Prerequisites
- Minecraft Java Edition 1.21.1
- Fabric Loader 0.16.5 or later
- Fabric API 0.102.0 or later
- Java 21 or later

### Building from Source
1. Clone this repository
2. Open a terminal in the project directory
3. Run `./gradlew build` (or `gradlew.bat build` on Windows)
4. The compiled mod JAR will be in `build/libs/`

### Installing the Mod
1. Install Fabric Loader for Minecraft 1.21.1
2. Download Fabric API from CurseForge or Modrinth
3. Place both the Fabric API and Heaven's Light JAR files in your `mods` folder
4. Launch Minecraft with the Fabric profile

## Configuration

The mod creates a configuration file at `config/heavens-light.json` with the following options:

```json
{
  "entityType": "JESUS",
  "healthThreshold": 4.0,
  "visionDurationTicks": 100,
  "deathSequenceDurationTicks": 200,
  "enableSounds": true,
  "soundVolume": 0.8,
  "enableVisionOnLowHealth": true,
  "enableDeathSequence": true,
  "overlayIntensity": 0.7,
  "enableParticles": true
}
```

### Configuration Options

- **entityType**: Choose between "JESUS", "ANGEL", or "LIGHT_BEING"
- **healthThreshold**: Health level (in half-hearts) that triggers the vision
- **visionDurationTicks**: Duration of the low-health vision (20 ticks = 1 second)
- **deathSequenceDurationTicks**: Duration of the death sequence
- **enableSounds**: Toggle heavenly sounds on/off
- **soundVolume**: Volume level for mod sounds (0.0 to 1.0)
- **enableVisionOnLowHealth**: Enable/disable low-health visions
- **enableDeathSequence**: Enable/disable death sequence
- **overlayIntensity**: Intensity of visual overlays (0.0 to 1.0)
- **enableParticles**: Enable/disable particle effects

## Sound Files

**Important**: You need to add actual audio files for the mod to have sound effects.

The mod requires three OGG audio files in `src/main/resources/assets/heavens-light/sounds/`:

- `heavenly_choir.ogg`: Soft, reverb-heavy choir for death sequence (10-15 seconds, looping)
- `vision_ambient.ogg`: Gentle ambient sound for visions (5-8 seconds, looping)  
- `gentle_harp.ogg`: Peaceful harp melody (3-5 seconds)

See `src/main/resources/assets/heavens-light/sounds/README.md` for detailed specifications and where to find appropriate sounds.

**The mod will compile and run without these files, but no heavenly sounds will play.**

## Technical Details

### Architecture
- **Fabric Mod**: Built using Fabric modding framework for better compatibility
- **Client-Side Focus**: Most functionality runs on the client for smooth visual effects
- **Mixin Integration**: Uses Mixins to intercept health changes and death events
- **Network Packets**: Server-client communication for multiplayer compatibility

### Performance
- Optimized for low-end systems
- Minimal impact on game performance
- Efficient rendering using existing Minecraft systems
- Configurable effects intensity

## Compatibility

- **Minecraft Version**: 1.21.1 (easily adaptable to other versions)
- **Mod Loader**: Fabric
- **Other Mods**: Should be compatible with most mods
- **Multiplayer**: Fully supported

## Development

### Project Structure
```
src/main/java/com/heavenslight/
├── HeavensLightMod.java              # Main mod class
├── client/
│   ├── HeavensLightClient.java       # Client initialization
│   ├── entity/EntityJesus.java       # Divine entity rendering
│   ├── renderer/HeavenlyOverlayRenderer.java  # Visual effects
│   └── sound/HeavenlySoundManager.java        # Sound management
├── config/HeavensLightConfig.java    # Configuration system
└── mixin/                            # Mixin classes for game integration
```

### Building
```bash
# Clean build
./gradlew clean build

# Development environment
./gradlew runClient

# Generate sources
./gradlew genSources
```

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Disclaimer

This mod presents spiritual themes in a respectful, symbolic manner without promoting any specific religious doctrine. The content is designed to be inclusive and focused on universal themes of peace, comfort, and transcendence.

## Contributing

Contributions are welcome! Please feel free to submit pull requests or open issues for bugs and feature requests.

## Credits

- Built with Fabric modding framework
- Inspired by the desire to add emotional depth to Minecraft's mechanics
- Sound design focused on peaceful, non-denominational spiritual themes