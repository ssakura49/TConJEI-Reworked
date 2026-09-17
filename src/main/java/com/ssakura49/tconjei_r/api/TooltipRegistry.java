package com.ssakura49.tconjei_r.api;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import slimeknights.tconstruct.library.materials.stats.MaterialStatsId;

import java.util.ArrayList;
import java.util.List;

public class TooltipRegistry {
    public static final List<TooltipRegistration> REGISTRATIONS = new ArrayList<>();

    public static void registerTooltip(ResourceLocation id, Component tooltipComponent, List<MaterialStatsId> statsIds) {
        REGISTRATIONS.add(new TooltipRegistration(id, tooltipComponent, statsIds));
    }

    public record TooltipRegistration(
            ResourceLocation id,
            Component tooltipComponent,
            List<MaterialStatsId> statsIds
    ) {}
}
