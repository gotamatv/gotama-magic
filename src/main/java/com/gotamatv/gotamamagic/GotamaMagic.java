package com.gotamatv.gotamamagic;

import com.gotamatv.gotamamagic.handler.SpellbookInteractionHandler;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafxmod.FMLJavaModLoadingContext;
import net.minecraftforge.common.MinecraftForge;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod("gotama_magic")
public class GotamaMagic {
    
    public static final String MODID = "gotama_magic";
    public static final Logger LOGGER = LoggerFactory.getLogger("Gotama Magic");
    
    public GotamaMagic() {
        IEventBus modEventBus = FMLJavaModLoadingContext.getInstance().getModEventBus();
        
        // Register event handlers
        MinecraftForge.EVENT_BUS.register(SpellbookInteractionHandler.class);
        
        LOGGER.info("========================================");
        LOGGER.info("Gotama Magic - Irons Ars Unified loaded!");
        LOGGER.info("Iron's Spellbooks scrolls can now be stored as glyphs in Ars Nouveau spellbooks");
        LOGGER.info("========================================");
    }
}