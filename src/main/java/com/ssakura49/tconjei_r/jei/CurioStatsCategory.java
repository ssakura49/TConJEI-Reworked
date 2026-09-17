package com.ssakura49.tconjei_r.jei;

import com.ssakura49.sakuratinker_tools.library.tools.stats.CharmChainMaterialStats;
import com.ssakura49.sakuratinker_tools.library.tools.stats.STTStatlessMaterialStats;
import com.ssakura49.tconjei_r.api.AbstractAddonStatsCategory;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.NotNull;
import slimeknights.tconstruct.common.TinkerTags;
import slimeknights.tconstruct.library.materials.stats.MaterialStatsId;

import java.util.List;

import static com.ssakura49.tconjei_r.TConJEI.MOD_ID;

public class CurioStatsCategory extends AbstractAddonStatsCategory {
    public CurioStatsCategory(IGuiHelper guiHelper) {
        super(guiHelper);
    }

    @Override
    protected @NotNull IDrawable createIcon(IGuiHelper guiHelper) {
        return guiHelper.createDrawable(
                new ResourceLocation(MOD_ID, "textures/gui/jei.png"),
                48, 0, 16, 16
        );
    }

    @Override
    protected Component createTitle() {
        return Component.translatable("tconjei.tool_stats.curio");
    }

    @Override
    protected List<MaterialStatsId> createStatIds() {
        return List.of(
                CharmChainMaterialStats.ID,
                STTStatlessMaterialStats.CHARM_CORE.getIdentifier()
        );
    }

    @Override
    protected TagKey<Item> createTag() {
        return TinkerTags.Items.ARMOR;
    }

    @Override
    protected RecipeType<MaterialStatsWrapper> createRecipeType() {
        return RecipeType.create(MOD_ID, "curio_stats", MaterialStatsWrapper.class);
    }
}