package com.ssakura49.tconjei_r.api;

import com.ssakura49.tconjei_r.jei.AbstractMaterialStatsCategory;
import mezz.jei.api.helpers.IGuiHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import slimeknights.tconstruct.library.materials.stats.MaterialStatsId;

import java.util.List;
import java.util.function.Function;

public interface TConJEIAPI {
    void registerStatsCategory(ResourceLocation id, Component title, ResourceLocation icon,
                               int iconU, int iconV, List<MaterialStatsId> statsIds,
                               Function<IGuiHelper, AbstractMaterialStatsCategory> category, CatalystType catalystType);
    default void registerStatsCategory(ResourceLocation id, Component title, ResourceLocation icon, List<MaterialStatsId> statsIds,
                                       Function<IGuiHelper, AbstractMaterialStatsCategory> category) {
        registerStatsCategory(id, title, icon, 0, 0, statsIds, category, CatalystType.TINKER_STATION);

    }

    void registerTooltip(ResourceLocation id, Component tooltipComponent, List<MaterialStatsId> statsIds);

    enum CatalystType {
        TINKER_STATION("tinker_station"),
        SMELTERY("smeltery");

        CatalystType(String string) {
        }
    }
}
