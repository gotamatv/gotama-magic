package com.gotamatv.gotamamagic.spell;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

/**
 * Converts Iron's Spellbook scrolls into Ars Nouveau glyphs
 */
public class SpellToGlyphConverter {
    
    public static final String IRONS_SCROLL_SPELL_TAG = "Spell";
    public static final String IRONS_SCROLL_LEVEL_TAG = "Level";
    public static final String IRONS_SCROLL_SCHOOL_TAG = "School";
    
    public static final String ARS_GLYPH_LIST_TAG = "IronsGlyphs";
    public static final String ARS_GLYPH_SPELL_TAG = "SpellName";
    public static final String ARS_GLYPH_LEVEL_TAG = "SpellLevel";
    public static final String ARS_GLYPH_SCHOOL_TAG = "SpellSchool";
    public static final String ARS_GLYPH_ICON_TAG = "IconTexture";
    public static final String ARS_GLYPH_MANA_COST_TAG = "ManaCost";
    public static final String ARS_GLYPH_COOLDOWN_TAG = "CooldownTicks";
    
    /**
     * Attempts to convert an Iron's scroll to an Ars glyph stored in the spellbook
     * @param ironsScroll The Iron's Spellbooks scroll item
     * @param arsSpellbook The Ars Nouveau spellbook (off-hand)
     * @param player The player performing the conversion
     * @return true if conversion was successful
     */
    public static boolean convertScrollToGlyph(ItemStack ironsScroll, ItemStack arsSpellbook, Player player) {
        if (!isValidIronsScroll(ironsScroll)) {
            return false;
        }
        
        CompoundTag scrollTag = ironsScroll.getTag();
        if (scrollTag == null || !scrollTag.contains(IRONS_SCROLL_SPELL_TAG)) {
            return false;
        }
        
        String spellName = scrollTag.getString(IRONS_SCROLL_SPELL_TAG);
        int spellLevel = scrollTag.getInt(IRONS_SCROLL_LEVEL_TAG);
        String spellSchool = scrollTag.getString(IRONS_SCROLL_SCHOOL_TAG);
        
        // Get glyph list or create new one
        CompoundTag spellbookTag = arsSpellbook.getOrCreateTag();
        ListTag glyphList = spellbookTag.getList(ARS_GLYPH_LIST_TAG, Tag.TAG_COMPOUND);
        
        // Check if spell already exists
        if (glyphExists(glyphList, spellName, spellLevel)) {
            return false; // Spell already in book
        }
        
        // Create new glyph entry
        CompoundTag glyphTag = new CompoundTag();
        glyphTag.putString(ARS_GLYPH_SPELL_TAG, spellName);
        glyphTag.putInt(ARS_GLYPH_LEVEL_TAG, spellLevel);
        glyphTag.putString(ARS_GLYPH_SCHOOL_TAG, spellSchool);
        glyphTag.putString(ARS_GLYPH_ICON_TAG, getScrollIconTexture(ironsScroll));
        glyphTag.putInt(ARS_GLYPH_MANA_COST_TAG, getScrollManaCost(scrollTag));
        glyphTag.putInt(ARS_GLYPH_COOLDOWN_TAG, getScrollCooldown(scrollTag));
        
        // Add to glyph list
        glyphList.add(glyphTag);
        spellbookTag.put(ARS_GLYPH_LIST_TAG, glyphList);
        
        return true;
    }
    
    /**
     * Removes a glyph from the Ars spellbook by name and level
     */
    public static boolean removeGlyphFromBook(ItemStack arsSpellbook, String spellName, int level) {
        CompoundTag tag = arsSpellbook.getTag();
        if (tag == null) return false;
        
        ListTag glyphList = tag.getList(ARS_GLYPH_LIST_TAG, Tag.TAG_COMPOUND);
        for (int i = 0; i < glyphList.size(); i++) {
            CompoundTag glyph = glyphList.getCompound(i);
            if (glyph.getString(ARS_GLYPH_SPELL_TAG).equals(spellName) &&
                glyph.getInt(ARS_GLYPH_LEVEL_TAG) == level) {
                glyphList.remove(i);
                return true;
            }
        }
        return false;
    }
    
    /**
     * Get all stored glyphs from the spellbook
     */
    public static ListTag getStoredGlyphs(ItemStack arsSpellbook) {
        CompoundTag tag = arsSpellbook.getOrCreateTag();
        return tag.getList(ARS_GLYPH_LIST_TAG, Tag.TAG_COMPOUND);
    }
    
    /**
     * Check if a specific spell+level combination already exists in the book
     */
    private static boolean glyphExists(ListTag glyphList, String spellName, int level) {
        for (int i = 0; i < glyphList.size(); i++) {
            CompoundTag glyph = glyphList.getCompound(i);
            if (glyph.getString(ARS_GLYPH_SPELL_TAG).equals(spellName) &&
                glyph.getInt(ARS_GLYPH_LEVEL_TAG) == level) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Validates if an item is an Iron's Spellbooks scroll
     */
    public static boolean isValidIronsScroll(ItemStack item) {
        if (item.isEmpty()) return false;
        if (item.getItem().getRegistryName() == null) return false;
        String registryName = item.getItem().getRegistryName().toString();
        return registryName.startsWith("irons_spellbooks:") && registryName.contains("scroll");
    }
    
    /**
     * Get the texture path for the scroll's icon
     */
    private static String getScrollIconTexture(ItemStack scroll) {
        if (scroll.getItem().getRegistryName() == null) return "unknown";
        String registryName = scroll.getItem().getRegistryName().toString();
        return registryName.replace("irons_spellbooks:", "").replace("_scroll", "");
    }
    
    /**
     * Extract mana cost from scroll NBT data
     */
    private static int getScrollManaCost(CompoundTag scrollTag) {
        return scrollTag.contains("ManaCost") ? scrollTag.getInt("ManaCost") : 100;
    }
    
    /**
     * Extract cooldown from scroll NBT data
     */
    private static int getScrollCooldown(CompoundTag scrollTag) {
        return scrollTag.contains("Cooldown") ? scrollTag.getInt("Cooldown") : 0;
    }
}