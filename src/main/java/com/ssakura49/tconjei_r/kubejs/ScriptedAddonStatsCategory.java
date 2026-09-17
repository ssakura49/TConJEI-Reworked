package com.ssakura49.tconjei_r.kubejs;

import com.ssakura49.tconjei_r.jei.AbstractMaterialStatsCategory;
import com.ssakura49.tconjei_r.jei.MaterialStatsWrapper;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import slimeknights.tconstruct.library.materials.stats.MaterialStatsId;

import java.util.List;

public class ScriptedAddonStatsCategory extends AbstractMaterialStatsCategory {
    public ScriptedAddonStatsCategory(IGuiHelper guiHelper,
                                      ResourceLocation iconTexture, int u, int v,
                                      Component title,
                                      List<MaterialStatsId> statsIds,
                                      TagKey<Item> tag,
                                      RecipeType<MaterialStatsWrapper> recipeType) {
        super(guiHelper);
        this.icon = guiHelper.createDrawable(iconTexture, u, v, 16, 16);
        this.title = title;
        this.statsIds = statsIds;
        this.tag = tag;
        this.recipeType = recipeType;
    }
}