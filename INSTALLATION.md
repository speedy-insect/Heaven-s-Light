# Heaven's Light - Installation Guide

## Quick Installation (Pre-built JAR)

### Prerequisites
1. **Minecraft Java Edition 1.21.1**
2. **Fabric Loader** - Download from [fabricmc.net](https://fabricmc.net/use/installer/)
3. **Fabric API** - Download from [CurseForge](https://www.curseforge.com/minecraft/mc-mods/fabric-api) or [Modrinth](https://modrinth.com/mod/fabric-api)

### Installation Steps
1. Install Fabric Loader for Minecraft 1.21.1 using the official installer
2. Download the latest Fabric API JAR file
3. Download the Heaven's Light mod JAR file
4. Place both JAR files in your Minecraft `mods` folder:
   - Windows: `%appdata%\.minecraft\mods\`
   - macOS: `~/Library/Application Support/minecraft/mods/`
   - Linux: `~/.minecraft/mods/`
5. Launch Minecraft using the Fabric profile

## Building from Source

### Prerequisites
- **Java 21 or later** - Download from [Adoptium](https://adoptium.net/) or [Oracle](https://www.oracle.com/java/technologies/downloads/)
- **Git** (optional) - For cloning the repository

### Windows Build Instructions
1. Download or clone this repository
2. Open Command Prompt or PowerShell in the project directory
3. Run the build script:
   ```batch
   build.bat
   ```
4. The compiled mod will be in `build\libs\heavens-light-1.0.0.jar`

### Manual Build (All Platforms)
1. Open terminal/command prompt in the project directory
2. Make the Gradle wrapper executable (Linux/macOS only):
   ```bash
   chmod +x gradlew
   ```
3. Build the mod:
   ```bash
   # Windows
   gradlew.bat build
   
   # Linux/macOS
   ./gradlew build
   ```

## Development Setup

### Setting up the Development Environment
1. Clone the repository
2. Run the development setup:
   ```batch
   # Windows
   dev-setup.bat
   
   # Linux/macOS
   ./gradlew genSources
   ```

### IDE Setup

#### IntelliJ IDEA
1. Open IntelliJ IDEA
2. Choose "Open" and select the project folder
3. Import as a Gradle project
4. Wait for indexing to complete
5. Use the "Minecraft Client" run configuration to test

#### Visual Studio Code
1. Install the "Extension Pack for Java"
2. Open the project folder
3. VS Code should automatically detect it as a Gradle project

#### Eclipse
1. Run `gradlew genEclipseRuns` first
2. Import the project as an existing Gradle project

### Testing the Mod
```bash
# Run Minecraft client with the mod loaded
gradlew runClient

# Run dedicated server with the mod loaded
gradlew runServer
```

## Configuration

After first launch, the mod creates a configuration file at:
- `config/heavens-light.json`

Edit this file to customize the mod's behavior. See the main README for configuration options.

## Troubleshooting

### Common Issues

#### "Mod failed to load"
- Ensure you have the correct Minecraft version (1.21.1)
- Verify Fabric Loader is installed
- Check that Fabric API is in your mods folder

#### "Java version error"
- Make sure you're using Java 21 or later
- Update your Java installation if necessary

#### "Build failed"
- Ensure you have Java 17+ installed
- Check your internet connection (Gradle needs to download dependencies)
- Try running `gradlew clean` first

#### "Sounds not playing"
- Check your Minecraft sound settings
- Verify the mod's sound configuration in the config file
- Make sure you have the complete mod JAR (not just source code)

### Performance Issues
- Lower the `overlayIntensity` in the config file
- Disable particles by setting `enableParticles` to false
- Reduce `soundVolume` if audio is causing lag

### Multiplayer Issues
- Ensure all players have the mod installed
- The mod works on both client and server, but visual effects are client-side only

## Uninstallation

To remove the mod:
1. Delete the Heaven's Light JAR file from your mods folder
2. Optionally delete the configuration file: `config/heavens-light.json`
3. Restart Minecraft

## Support

If you encounter issues:
1. Check this troubleshooting guide
2. Look at the console/log output for error messages
3. Create an issue on the project repository with:
   - Your Minecraft version
   - Fabric Loader version
   - Other mods installed
   - Full error log

## Version Compatibility

| Minecraft Version | Mod Version | Status |
|-------------------|-------------|---------|
| 1.20.4           | 1.0.0       | ✅ Supported |
| 1.20.3           | 1.0.0       | ⚠️ May work |
| 1.20.2           | 1.0.0       | ⚠️ May work |
| 1.20.1           | 1.0.0       | ❌ Not supported |

For other Minecraft versions, you may need to update the `minecraft_version` in `gradle.properties` and rebuild.