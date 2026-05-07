# Gotama Magic - Irons Ars Unified

A Minecraft 1.20.1 Forge mod that creates a unified spell integration between **Iron's Spellbooks** and **Ars Nouveau**.

## Features

✨ **Store Iron's Scrolls as Ars Glyphs**
- Convert any Iron's Spellbook scroll into an Ars Nouveau glyph
- Store unlimited spells in a single unified spellbook
- Each spell level is stored as a separate glyph with its original icon

🔄 **Unified Mana System**
- Single shared mana pool used by both mods
- Iron's Spellbooks is the primary mana source
- Seamless mana conversion between systems

⚡ **Casting Restrictions**
- Once a glyph is selected/active, other glyphs are locked out
- Prevents mixing or switching spells mid-cast
- Ensures clean, focused spell casting

## How to Use

1. **Hold** an Ars Nouveau spellbook in your **off-hand**
2. **Hold** an Iron's Spellbook scroll in your **main hand**
3. **Right-click** to convert the scroll into a glyph
4. The scroll is consumed and stored permanently in your spellbook
5. Access all your spells from one unified book!

## Installation

### Requirements
- Minecraft 1.20.1 (Forge)
- Forge 47.2.0 or higher
- Ars Nouveau 4.12.7+
- Iron's Spellbooks 3.15.x+ (optional, but recommended)

### Installation Steps

1. Download the latest `gotama-magic-1.0.0.jar` from the [Releases](https://github.com/gotamatv/gotama-magic/releases) page
2. Place the JAR file in your Minecraft `mods` folder
3. Launch Minecraft with Forge
4. Enjoy unlimited spell storage!

## Building from Source

```bash
git clone https://github.com/gotamatv/gotama-magic.git
cd gotama-magic
./gradlew build
```

The compiled JAR will be located at: `build/libs/gotama-magic-1.0.0.jar`

## Project Structure

```
src/main/java/com/gotamatv/gotamamagic/
├── GotamaMagic.java              (Main mod class)
├── spell/
│   └── SpellToGlyphConverter.java (Scroll→Glyph conversion logic)
├── handler/
│   └── SpellbookInteractionHandler.java (Right-click interaction)
├── mana/
│   └── UnifiedManaSystem.java     (Mana bridge between mods)
└── casting/
    └── GlyphCastingManager.java   (Casting restrictions)
```

## Configuration

All features are enabled by default. Customize behavior by modifying the Java files and recompiling.

## Known Limitations

- Iron's Spellbooks is optional but recommended for full functionality
- Glyph icons are auto-generated from scroll textures
- Some advanced Iron's features may not be fully replicated in Ars glyphs

## Support

If you encounter any issues, please open an issue on the [GitHub repository](https://github.com/gotamatv/gotama-magic/issues).

## License

MIT License - Feel free to use and modify as needed.

## Credits

Created by: **gotamatv**  
Based on: Iron's Spellbooks and Ars Nouveau integration concept
