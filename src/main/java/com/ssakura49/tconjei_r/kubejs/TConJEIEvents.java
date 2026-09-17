package com.ssakura49.tconjei_r.kubejs;

import dev.latvian.mods.kubejs.event.EventGroup;
import dev.latvian.mods.kubejs.event.EventHandler;

public interface TConJEIEvents {
    EventGroup GROUP = EventGroup.of("TConJEIEvents");

    EventHandler REGISTER_STATS_CATEGORY = GROUP.startup("registerStatsCategory", () -> RegisterStatsCategoryEvent.class);
    EventHandler REGISTER_TOOLTIP = GROUP.startup("registerTooltip", () -> RegisterTooltipEvent.class);
}