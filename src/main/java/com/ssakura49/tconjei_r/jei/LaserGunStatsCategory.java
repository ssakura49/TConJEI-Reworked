package com.ssakura49.tconjei_r.jei;

import com.ssakura49.sakuratinker_tools.library.tools.stats.EnergyUnitMaterialStats;
import com.ssakura49.sakuratinker_tools.library.tools.stats.LaserMediumMaterialStats;
import mezz.jei.api.helpers.IGuiHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import slimeknights.tconstruct.common.TinkerTags;
import slimeknights.tconstruct.tools.stats.HandleMaterialStats;
import slimeknights.tconstruct.tools.stats.HeadMaterialStats;

import java.util.List;

import static com.ssakura49.tconjei_r.TConJEI.MOD_ID;

public class LaserGunStatsCategory extends AbstractMaterialStatsCategory {
    public LaserGunStatsCategory(IGuiHelper guiHelper) {
        super(guiHelper);
        this.icon = guiHelper.createDrawable(new ResourceLocation(MOD_ID, "textures/gui/jei.png"), 64, 0, 16, 16);
        this.title = Component.translatable("tconjei.tool_stats.laser_gun");
        this.recipeType = TConJEIPlugin.LASE_GUN_STATS;
        this.statsIds = List.of(HeadMaterialStats.ID, EnergyUnitMaterialStats.ID, LaserMediumMaterialStats.ID, HandleMaterialStats.ID);
        this.tag = TinkerTags.Items.HARVEST;
    }

}
