package com.ssakura49.tconjei_r.kubejs;

import com.ssakura49.tconjei_r.TConJEI;
import com.ssakura49.tconjei_r.api.AbstractAddonStatsCategory;
import com.ssakura49.tconjei_r.api.TConJEIAPI;
import com.ssakura49.tconjei_r.jei.AbstractMaterialStatsCategory;
import com.ssakura49.tconjei_r.jei.MaterialStatsWrapper;
import dev.latvian.mods.kubejs.event.EventJS;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import slimeknights.tconstruct.library.materials.stats.MaterialStatsId;

import java.util.List;

public class RegisterStatsCategoryEvent extends EventJS {
    public void add(String id, String titleKey, String iconPath, int u, int v,
                    List<String> statsIdsStr, String tagStr,
                    String recipeTypeStr, String catalystStr) {
        new TConJEIEventsBindings().registerStatsCategory(id, titleKey, iconPath, u, v, statsIdsStr, tagStr, recipeTypeStr, catalystStr);
    }
}