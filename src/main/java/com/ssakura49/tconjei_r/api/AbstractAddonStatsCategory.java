package com.ssakura49.tconjei_r.api;

import com.ssakura49.tconjei_r.jei.AbstractMaterialStatsCategory;
import com.ssakura49.tconjei_r.jei.MaterialStatsWrapper;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.NotNull;
import slimeknights.tconstruct.library.materials.stats.MaterialStatsId;

import java.util.List;

/** 需要继承此类来注册数据类型 */
public abstract class AbstractAddonStatsCategory extends AbstractMaterialStatsCategory {
    public AbstractAddonStatsCategory(IGuiHelper guiHelper) {
        super(guiHelper);
        this.icon = createIcon(guiHelper);
        this.title = createTitle();
        this.statsIds = createStatIds();
        this.recipeType = createRecipeType();
        this.tag = createTag();
    }
    
    protected abstract IDrawable createIcon(IGuiHelper guiHelper);
    protected abstract Component createTitle();
    protected abstract List<MaterialStatsId> createStatIds();
    protected abstract TagKey<Item> createTag();
    protected abstract RecipeType<MaterialStatsWrapper> createRecipeType();
}