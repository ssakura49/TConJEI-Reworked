package com.ssakura49.tconjei_r.jei;

import mezz.jei.api.helpers.IGuiHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import slimeknights.tconstruct.common.TinkerTags;
import slimeknights.tconstruct.tools.stats.GripMaterialStats;
import slimeknights.tconstruct.tools.stats.LimbMaterialStats;
import slimeknights.tconstruct.tools.stats.StatlessMaterialStats;

import java.util.List;

import static com.ssakura49.tconjei_r.TConJEI.MOD_ID;

public class RangedStatsCategory extends AbstractMaterialStatsCategory {
    public RangedStatsCategory(IGuiHelper guiHelper) {
        super(guiHelper);
        this.icon = guiHelper.createDrawable(new ResourceLocation(MOD_ID, "textures/gui/jei.png"), 16, 0, 16, 16);
        this.title = Component.translatable("tconjei.tool_stats.ranged");
        this.recipeType = TConJEIPlugin.RANGED_STATS;
        this.statsIds = List.of(LimbMaterialStats.ID, GripMaterialStats.ID, StatlessMaterialStats.BOWSTRING.getIdentifier());
        this.tag = TinkerTags.Items.RANGED;
    }

}
