package com.ssakura49.tconjei_r.kubejs;

import com.ssakura49.tconjei_r.TConJEI;
import com.ssakura49.tconjei_r.api.TConJEIAPI;
import com.ssakura49.tconjei_r.jei.MaterialStatsWrapper;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import slimeknights.tconstruct.library.materials.stats.MaterialStatsId;

import java.util.List;


@SuppressWarnings("removal")
public class TConJEIEventsBindings {
    public void registerStatsCategory(String id, String titleKey, String iconPath, int u, int v,
                                      List<String> statsIdsStr, String tagStr,
                                      String recipeTypeStr, String catalystStr) {
        ResourceLocation idLoc = new ResourceLocation(id);
        ResourceLocation iconLoc = new ResourceLocation(iconPath);
        Component title = Component.translatable(titleKey);
        List<MaterialStatsId> statsIds = statsIdsStr.stream()
                .map(s -> new MaterialStatsId(new ResourceLocation(s)))
                .toList();
        TagKey<Item> tag = TagKey.create(Registries.ITEM, new ResourceLocation(tagStr));
        RecipeType<MaterialStatsWrapper> recipeType = RecipeType.create(TConJEI.MOD_ID, recipeTypeStr, MaterialStatsWrapper.class);
        TConJEIAPI.CatalystType catalyst = "smeltery".equalsIgnoreCase(catalystStr) ?
                TConJEIAPI.CatalystType.SMELTERY :
                TConJEIAPI.CatalystType.TINKER_STATION;
        TConJEI.API().registerStatsCategory(
                idLoc, title, iconLoc, u, v,
                statsIds,
                gui -> new ScriptedAddonStatsCategory(gui, iconLoc, u, v, title, statsIds, tag, recipeType),
                catalyst
        );
    }

    public void registerTooltip(String idStr, String tooltipKey, List<String> statsIdsStr) {
        ResourceLocation id = new ResourceLocation(idStr);
        Component tooltip = Component.translatable(tooltipKey);
        List<MaterialStatsId> statsIds = statsIdsStr.stream()
                .map(s -> new MaterialStatsId(new ResourceLocation(s)))
                .toList();
        TConJEI.API().registerTooltip(id, tooltip, statsIds);
    }
}
