package com.ssakura49.tconjei_r;

import com.ssakura49.tconjei_r.api.TConJEIAPI;
import com.ssakura49.tconjei_r.api.TConJEIAPIImpl;
import com.ssakura49.tconjei_r.client.ClientConfig;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import slimeknights.tconstruct.library.materials.stats.MaterialStatsId;
import slimeknights.tconstruct.tools.stats.*;

import java.util.HashMap;
import java.util.List;

import static com.ssakura49.tconjei_r.TConJEI.MOD_ID;

@Mod(MOD_ID)
public class TConJEI {
    public static final String MOD_ID = "tconjei_r";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);
    //public static TConJEIAPI API = new TConJEIAPIImpl();
    private static TConJEIAPI api;
    public static TConJEIAPI API() {
        if (api == null) {
            api = new TConJEIAPIImpl();
        }
        return api;
    }

    public TConJEI(FMLJavaModLoadingContext context) {
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, ClientConfig.SPEC, MOD_ID + "-client.toml");
        IEventBus modBusEvent = context.getModEventBus();
        modBusEvent.addListener(this::onCommonSetup);

    }

    @SubscribeEvent
    public void onCommonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
        });
    }

    private static final class StatIds {
        static final List<MaterialStatsId> HARVEST = List.of(
                HeadMaterialStats.ID,
                StatlessMaterialStats.BINDING.getIdentifier(),
                HandleMaterialStats.ID
        );
        static final List<MaterialStatsId> RANGED = List.of(
                LimbMaterialStats.ID,
                GripMaterialStats.ID,
                StatlessMaterialStats.BOWSTRING.getIdentifier()
        );
        static final List<MaterialStatsId> ARMOR = List.of(
                PlatingMaterialStats.HELMET.getId(),
                PlatingMaterialStats.CHESTPLATE.getId(),
                PlatingMaterialStats.LEGGINGS.getId(),
                PlatingMaterialStats.BOOTS.getId(),
                PlatingMaterialStats.SHIELD.getId(),
                StatlessMaterialStats.MAILLE.getIdentifier(),
                StatlessMaterialStats.SHIELD_CORE.getIdentifier()
        );
        static final List<MaterialStatsId> LASER_GUN = List.of(
                new MaterialStatsId("sakuratinker", "laser_medium"),
                new MaterialStatsId("sakuratinker", "energy_unit")
        );
    }

    public static List<MaterialStatsId> getHarvestStatIds() {
        return StatIds.HARVEST;
    }

    public static List<MaterialStatsId> getRangedStatIds() {
        return StatIds.RANGED;
    }

    public static List<MaterialStatsId> getArmorStatIds() {
        return StatIds.ARMOR;
    }

    public static List<MaterialStatsId> getLaserGunStatIds() {
        return StatIds.LASER_GUN;
    }

    public static HashMap<Item, Component> allMaterialsTooltip = new HashMap<>();

}
