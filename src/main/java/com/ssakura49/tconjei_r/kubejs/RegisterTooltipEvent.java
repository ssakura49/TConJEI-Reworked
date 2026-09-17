package com.ssakura49.tconjei_r.kubejs;

import com.ssakura49.tconjei_r.TConJEI;
import dev.latvian.mods.kubejs.event.EventJS;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import slimeknights.tconstruct.library.materials.stats.MaterialStatsId;

import java.util.List;

public class RegisterTooltipEvent extends EventJS {
    public void add(String idStr, String tooltipKey, List<String> statsIdsStr) {
        new TConJEIEventsBindings().registerTooltip(idStr, tooltipKey, statsIdsStr);
    }
}
