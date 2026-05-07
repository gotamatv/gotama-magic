package com.gotamatv.gotamamagic.handler;

import com.gotamatv.gotamamagic.spell.SpellToGlyphConverter;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.InteractionResult;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraft.network.chat.Component;

/**
 * Handles right-click interactions to convert Iron's scrolls to Ars glyphs
 * Hold Ars spellbook in off-hand, Iron's scroll in main hand, right-click to store
 */
@Mod.EventBusSubscriber(modid = "gotama_magic", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class SpellbookInteractionHandler {
    
    @SubscribeEvent
    public static void onPlayerRightClick(PlayerInteractEvent.RightClickItem event) {
        Player player = event.getPlayer();
        ItemStack mainHand = player.getMainHandItem();
        ItemStack offHand = player.getOffhandItem();
        
        // Check if scroll is in main hand and spellbook in off-hand
        if (!SpellToGlyphConverter.isValidIronsScroll(mainHand)) {
            return;
        }
        
        if (!isArsSpellbook(offHand)) {
            return;
        }
        
        // Only process on server side
        if (player.level().isClientSide) {
            return;
        }
        
        // Attempt conversion
        boolean success = SpellToGlyphConverter.convertScrollToGlyph(mainHand, offHand, player);
        
        if (success) {
            // Consume the scroll
            mainHand.shrink(1);
            
            // Send feedback to player
            player.displayClientMessage(
                Component.literal("§a✨ Spell stored in Ars spellbook!"),
                true // Show above hotbar
            );
            
            event.setCanceled(true);
            event.setCancellationResult(InteractionResult.SUCCESS);
        }
    }
    
    /**
     * Check if an item is an Ars Nouveau spellbook
     */
    private static boolean isArsSpellbook(ItemStack item) {
        if (item.isEmpty()) return false;
        if (item.getItem().getRegistryName() == null) return false;
        String registryName = item.getItem().getRegistryName().toString();
        return registryName.startsWith("ars_nouveau:") && registryName.contains("spellbook");
    }
}