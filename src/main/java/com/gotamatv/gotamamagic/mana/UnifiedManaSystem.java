package com.gotamatv.gotamamagic.mana;

import net.minecraft.world.entity.player.Player;
import net.minecraft.nbt.CompoundTag;

/**
 * Unified mana system that bridges Iron's and Ars mana pools
 * Priority: Iron's mana is primary, Ars reads from Iron's pool
 */
public class UnifiedManaSystem {
    
    public static final float CONVERSION_RATE = 1.0f; // 1 Iron mana = 1 Ars mana
    public static final String MANA_TAG = "GotamaMagicMana";
    
    /**
     * Get current mana from the unified pool (Iron's primary)
     */
    public static int getCurrentMana(Player player) {
        // Try Iron's mana first
        int ironsMana = getIronsMana(player);
        if (ironsMana > 0) {
            return ironsMana;
        }
        
        // Fall back to Ars mana if Iron's not available
        return getArsMana(player);
    }
    
    /**
     * Drain mana from unified pool
     */
    public static boolean drainMana(Player player, int amount) {
        // Try to drain from Iron's mana first
        if (hasIronsIntegration()) {
            if (drainIronsMana(player, amount)) {
                return true;
            }
        }
        
        // Fall back to Ars mana
        return drainArsMana(player, amount);
    }
    
    /**
     * Restore mana to unified pool
     */
    public static void restoreMana(Player player, int amount) {
        if (hasIronsIntegration()) {
            restoreIronsMana(player, amount);
        } else {
            restoreArsMana(player, amount);
        }
    }
    
    /**
     * Get max mana capacity from active system
     */
    public static int getMaxMana(Player player) {
        if (hasIronsIntegration()) {
            return getIronsMaxMana(player);
        }
        return getArsMaxMana(player);
    }
    
    // ===================== Iron's Spellbooks Integration =====================
    
    private static int getIronsMana(Player player) {
        try {
            CompoundTag tag = player.getPersistentData();
            return tag.getInt("irons_spellbooks:mana");
        } catch (Exception e) {
            return 0;
        }
    }
    
    private static boolean drainIronsMana(Player player, int amount) {
        try {
            CompoundTag tag = player.getPersistentData();
            int current = tag.getInt("irons_spellbooks:mana");
            if (current >= amount) {
                tag.putInt("irons_spellbooks:mana", current - amount);
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }
    
    private static void restoreIronsMana(Player player, int amount) {
        try {
            CompoundTag tag = player.getPersistentData();
            int current = tag.getInt("irons_spellbooks:mana");
            int max = getIronsMaxMana(player);
            tag.putInt("irons_spellbooks:mana", Math.min(current + amount, max));
        } catch (Exception e) {
            // Silently fail
        }
    }
    
    private static int getIronsMaxMana(Player player) {
        try {
            CompoundTag tag = player.getPersistentData();
            return tag.getInt("irons_spellbooks:max_mana");
        } catch (Exception e) {
            return 1000;
        }
    }
    
    private static boolean hasIronsIntegration() {
        try {
            Class.forName("io.redspace.ironspellbooks.api.player.ManaAPI");
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }
    
    // ===================== Ars Nouveau Integration =====================
    
    private static int getArsMana(Player player) {
        try {
            CompoundTag tag = player.getPersistentData();
            return tag.getInt("ars_nouveau:mana");
        } catch (Exception e) {
            return 0;
        }
    }
    
    private static boolean drainArsMana(Player player, int amount) {
        try {
            CompoundTag tag = player.getPersistentData();
            int current = tag.getInt("ars_nouveau:mana");
            if (current >= (int)(amount * CONVERSION_RATE)) {
                tag.putInt("ars_nouveau:mana", (int)(current - (amount * CONVERSION_RATE)));
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }
    
    private static void restoreArsMana(Player player, int amount) {
        try {
            CompoundTag tag = player.getPersistentData();
            int current = tag.getInt("ars_nouveau:mana");
            int max = getArsMaxMana(player);
            tag.putInt("ars_nouveau:mana", Math.min((int)(current + (amount * CONVERSION_RATE)), max));
        } catch (Exception e) {
            // Silently fail
        }
    }
    
    private static int getArsMaxMana(Player player) {
        try {
            CompoundTag tag = player.getPersistentData();
            return tag.getInt("ars_nouveau:max_mana");
        } catch (Exception e) {
            return 1000;
        }
    }
}