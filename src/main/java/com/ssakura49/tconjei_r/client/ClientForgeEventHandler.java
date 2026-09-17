package com.ssakura49.tconjei_r.client;

import com.ssakura49.tconjei_r.ColorProvider;
import com.ssakura49.tconjei_r.Utils;
import com.ssakura49.tconjei_r.api.TooltipRegistry;
import com.ssakura49.tconjei_r.jei.MaterialStatsWrapper;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RecipesUpdatedEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import slimeknights.tconstruct.library.events.MaterialsLoadedEvent;
import slimeknights.tconstruct.tools.item.RepairKitItem;
import slimeknights.tconstruct.tools.stats.SkullStats;

import java.util.ArrayList;
import java.util.List;

import static com.ssakura49.tconjei_r.TConJEI.*;

@Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ClientForgeEventHandler {

    @SubscribeEvent
    public static void onLogin(RecipesUpdatedEvent event) {
        Utils.clearMaterialWrapperCache();
        allMaterialsTooltip.clear();

        if (!ClientConfig.ENABLE_TOOLTIP.get()) return;

        for (MaterialStatsWrapper wrapper : Utils.getMaterialWrappers()) {
            List<Component> components = new ArrayList<>();

            if (wrapper.hasStats(getHarvestStatIds())) components.add(Component.translatable("tconjei_r.tooltip.harvest"));
            if (wrapper.hasStats(getRangedStatIds())) components.add(Component.translatable("tconjei_r.tooltip.ranged"));
            if (wrapper.hasStats(getArmorStatIds())) components.add(Component.translatable("tconjei_r.tooltip.armor"));
            //if (wrapper.hasStats(CURIO_STAT_IDS)) components.add(Component.translatable("tconjei.tooltip.curio"));
            if (wrapper.hasStats(getLaserGunStatIds())) components.add(Component.translatable("tconjei_r.tooltip.laser_gun"));
            if (wrapper.hasStats(List.of(SkullStats.ID))) components.add(Component.translatable("tconjei_r.tooltip.skull"));

            for (var reg : TooltipRegistry.REGISTRATIONS) {
                if (wrapper.hasStats(reg.statsIds())) {
                    components.add(reg.tooltipComponent());
                }
            }

            if (components.isEmpty()) continue;

            MutableComponent child = Component.literal(" ") ;

            if (components.size() == 1) {
                child.append(components.get(0));
            } else {
                int i = 0;
                for (; i < components.size() - 1; i++) {
                    child.append(components.get(i))
                            .append(Component.translatable("tconjei.tooltip.separator"));
                }
                child.append(Component.translatable("tconjei.tooltip.and"))
                        .append(components.get(i));
            }

            child.append(" ")
                    .append(Component.translatable("tconjei.tooltip.material"))
                    .withStyle(ChatFormatting.GRAY);

            int tier = wrapper.material().getTier();
            MutableComponent component = Component.translatable("tconjei.tooltip.tier", tier)
                    .withStyle(style -> style.withColor(ColorProvider.getTierColor(tier).orElse(0xAAAAAA)))
                    .append(child);

            for (ItemStack stack : wrapper.getInputs()) {
                if (!(stack.getItem() instanceof RepairKitItem)) {
                    allMaterialsTooltip.put(stack.getItem(), component);
                }
            }
        }
    }
//
//            for (ItemStack stack : wrapper.getInputs()) {
//                if (!(stack.getItem() instanceof RepairKitItem)) {
//                    allMaterialsTooltip.put(stack.getItem(), component);
//                }
//            }
//        }
//    }

    // TODO: might have problems if server changes and valid materials change too
    // runs on reload too
/*
    @SubscribeEvent
    public static void onLogin(RecipesUpdatedEvent event) {
        allMaterialsTooltip.clear();

        if (!ClientConfig.ENABLE_TOOLTIP.get()) return;

        for (MaterialStatsWrapper wrapper : Utils.getMaterialWrappers()) {
            List<Component> components = new ArrayList<>();

            if (wrapper.hasStats(getHarvestStatIds())) components.add(Component.translatable("tconjei.tooltip.harvest"));
            if (wrapper.hasStats(getRangedStatIds())) components.add(Component.translatable("tconjei.tooltip.ranged"));
            if (wrapper.hasStats(getArmorStatIds())) components.add(Component.translatable("tconjei.tooltip.armor"));
            //if (wrapper.hasStats(CURIO_STAT_IDS)) components.add(Component.translatable("tconjei.tooltip.curio"));
            if (wrapper.hasStats(getLaserGunStatIds())) components.add(Component.translatable("tconjei.tooltip.laser_gun"));
            if (wrapper.hasStats(List.of(SkullStats.ID))) components.add(Component.translatable("tconjei.tooltip.skull"));

            for (var reg : TooltipRegistry.REGISTRATIONS) {
                if (wrapper.hasStats(reg.statsIds())) {
                    components.add(reg.tooltipComponent());
                }
            }

            if (components.isEmpty()) continue;

            MutableComponent child = Component.literal(" ") ;

            if (components.size() == 1) {
                child.append(components.get(0));
            } else {
                int i = 0;
                for (; i < components.size() - 1; i++) {
                    child.append(components.get(i))
                            .append(Component.translatable("tconjei.tooltip.separator"));
                }
                child.append(Component.translatable("tconjei.tooltip.and"))
                        .append(components.get(i));
            }

            child.append(" ")
                    .append(Component.translatable("tconjei.tooltip.material"))
                    .withStyle(ChatFormatting.GRAY);

            int tier = wrapper.material().getTier();
            MutableComponent component = Component.translatable("tconjei.tooltip.tier", tier)
                    .withStyle(style -> style.withColor(ColorProvider.getTierColor(tier).orElse(0xAAAAAA)))
                    .append(child);

            for (ItemStack stack : wrapper.getInputs()) {
                if (!(stack.getItem() instanceof RepairKitItem)) {
                    allMaterialsTooltip.put(stack.getItem(), component);
                }
            }
        }
    }
*/
    @SubscribeEvent
    public static void onToolTip(ItemTooltipEvent event) {
        if (!ClientConfig.ENABLE_TOOLTIP.get()) return;
        Item key = event.getItemStack().getItem();
        if (allMaterialsTooltip.containsKey(key)) {
            event.getToolTip().add(allMaterialsTooltip.get(key));
        }
    }

}