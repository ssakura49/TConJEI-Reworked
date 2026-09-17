package com.ssakura49.tconjei_r.api;

import com.ssakura49.tconjei_r.jei.AbstractMaterialStatsCategory;
import com.ssakura49.tconjei_r.jei.MaterialStatsWrapper;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import slimeknights.tconstruct.library.materials.stats.MaterialStatsId;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static com.ssakura49.tconjei_r.TConJEI.MOD_ID;

public class TConJEIAPIImpl implements TConJEIAPI {
    public static final List<CategoryRegistration> REGISTRATIONS = new ArrayList<>();
    public static final Map<ResourceLocation, RecipeType<MaterialStatsWrapper>> DYNAMIC_RECIPE_TYPES = new HashMap<>();
    @Override
    public void registerStatsCategory(ResourceLocation id, Component title, ResourceLocation icon,
                                      int iconU, int iconV, List<MaterialStatsId> statsIds,
                                      Function<IGuiHelper, AbstractMaterialStatsCategory> categoryFunction, CatalystType catalystType) {
        RecipeType<MaterialStatsWrapper> recipeType = RecipeType.create(MOD_ID, id.getPath(), MaterialStatsWrapper.class);
        DYNAMIC_RECIPE_TYPES.put(id, recipeType);
        REGISTRATIONS.add(new CategoryRegistration(id, title, icon, iconU, iconV, statsIds, categoryFunction, recipeType, catalystType));
    }

    @Override
    public void registerTooltip(ResourceLocation id, Component tooltipComponent, List<MaterialStatsId> statsIds) {
        TooltipRegistry.registerTooltip(id, tooltipComponent, statsIds);
    }

    public record CategoryRegistration(
            ResourceLocation id,
            Component title,
            ResourceLocation icon,
            int iconU,
            int iconV,
            List<MaterialStatsId> statsIds,
            Function<IGuiHelper, AbstractMaterialStatsCategory> category,
            RecipeType<MaterialStatsWrapper> recipeType,
            CatalystType catalystType
    ) {}
}