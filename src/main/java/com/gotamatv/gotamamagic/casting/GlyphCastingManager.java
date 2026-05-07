package com.gotamatv.gotamamagic.casting;

import net.minecraft.world.entity.player.Player;
import net.minecraft.nbt.CompoundTag;

/**
 * Manages glyph casting restrictions - prevents switching between glyphs mid-cast
 * Locks out all other glyphs when one is active
 */
public class GlyphCastingManager {
    
    public static final String ACTIVE_GLYPH_TAG = "GotamaMagicActiveGlyph";
    public static final String CAST_START_TIME_TAG = "GotamaMagicCastStartTime";
    
    /**
     * Check if a player can cast a specific glyph
     */
    public static boolean canCastGlyph(Player player, String glyphName) {
        String activeGlyph = getActiveGlyph(player);
        
        // If no glyph is active, allow casting
        if (activeGlyph == null || activeGlyph.isEmpty()) {
            return true;
        }
        
        // If the same glyph is active, allow (continuing cast)
        if (activeGlyph.equals(glyphName)) {
            return true;
        }
        
        // Different glyph is active - check if cast has finished
        return isCastFinished(player);
    }
    
    /**
     * Set the active glyph (called when casting starts)
     */
    public static void setActiveGlyph(Player player, String glyphName, int castDurationTicks) {
        CompoundTag tag = player.getPersistentData();
        tag.putString(ACTIVE_GLYPH_TAG, glyphName);
        tag.putLong(CAST_START_TIME_TAG, player.level().getGameTime() + castDurationTicks);
    }
    
    /**
     * Clear the active glyph (called when cast finishes or is interrupted)
     */
    public static void clearActiveGlyph(Player player) {
        CompoundTag tag = player.getPersistentData();
        tag.remove(ACTIVE_GLYPH_TAG);
        tag.remove(CAST_START_TIME_TAG);
    }
    
    /**
     * Get the currently active glyph name
     */
    public static String getActiveGlyph(Player player) {
        CompoundTag tag = player.getPersistentData();
        return tag.getString(ACTIVE_GLYPH_TAG);
    }
    
    /**
     * Check if the current cast has finished
     */
    private static boolean isCastFinished(Player player) {
        CompoundTag tag = player.getPersistentData();
        if (!tag.contains(CAST_START_TIME_TAG)) {
            return true;
        }
        long finishTime = tag.getLong(CAST_START_TIME_TAG);
        return player.level().getGameTime() >= finishTime;
    }
    
    /**
     * Get time remaining on current cast (in ticks)
     */
    public static int getCastTimeRemaining(Player player) {
        CompoundTag tag = player.getPersistentData();
        if (!tag.contains(CAST_START_TIME_TAG)) {
            return 0;
        }
        long finishTime = tag.getLong(CAST_START_TIME_TAG);
        long remaining = finishTime - player.level().getGameTime();
        return Math.max(0, (int) remaining);
    }
}