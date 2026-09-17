package com.ssakura49.tconjei_r.jei;

import com.ssakura49.tconjei_r.TConJEI;
import com.ssakura49.tconjei_r.Utils;
import com.ssakura49.tconjei_r.api.TConJEIAPIImpl;
import com.ssakura49.tconjei_r.datapack.CategoryJsonLoader;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import slimeknights.tconstruct.smeltery.TinkerSmeltery;
import slimeknights.tconstruct.tables.TinkerTables;
import slimeknights.tconstruct.tools.stats.SkullStats;

import java.util.*;
import java.util.stream.Collectors;

import static com.ssakura49.tconjei_r.TConJEI.MOD_ID;

@SuppressWarnings("unused")
@JeiPlugin
public class TConJEIPlugin implements IModPlugin {

    ResourceLocation UID = new ResourceLocation(MOD_ID, "jei_plugin");
    public static final RecipeType<MaterialStatsWrapper> HARVEST_STATS = RecipeType.create(MOD_ID, "harvest_stats", MaterialStatsWrapper.class);
    public static final RecipeType<MaterialStatsWrapper> RANGED_STATS = RecipeType.create(MOD_ID, "ranged_stats", MaterialStatsWrapper.class);
    public static final RecipeType<MaterialStatsWrapper> ARMOR_STATS = RecipeType.create(MOD_ID, "armor_stats", MaterialStatsWrapper.class);
    public static final RecipeType<MaterialStatsWrapper> SKULL_STATS = RecipeType.create(MOD_ID, "skull_stats", MaterialStatsWrapper.class);


    //public static final RecipeType<MaterialStatsWrapper> CURIO_STATS = RecipeType.create(MOD_ID, "curio_stats", MaterialStatsWrapper.class);
    public static final RecipeType<MaterialStatsWrapper> LASE_GUN_STATS = RecipeType.create(MOD_ID, "laser_gun_stats", MaterialStatsWrapper.class);

    @NotNull
    @Override
    public ResourceLocation getPluginUid() {
        return UID;
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        List<MaterialStatsWrapper> statsWrappers = Utils.getMaterialWrappers();

        List<MaterialStatsWrapper> harvest = new ArrayList<>();
        List<MaterialStatsWrapper> ranged = new ArrayList<>();
        List<MaterialStatsWrapper> armor = new ArrayList<>();
        List<MaterialStatsWrapper> skull = new ArrayList<>();
        List<MaterialStatsWrapper> laserGun = new ArrayList<>();

        Map<RecipeType<MaterialStatsWrapper>, List<MaterialStatsWrapper>> dynamicGroups = new HashMap<>();

        for (MaterialStatsWrapper w : statsWrappers) {
            if (w.hasStats(TConJEI.getHarvestStatIds())) harvest.add(w);
            if (w.hasStats(TConJEI.getRangedStatIds())) ranged.add(w);
            if (w.hasStats(TConJEI.getArmorStatIds())) armor.add(w);
            if (w.hasStats(List.of(SkullStats.ID))) skull.add(w);
            if (w.hasStats(TConJEI.getLaserGunStatIds())) laserGun.add(w);

            for (var reg : TConJEIAPIImpl.REGISTRATIONS) {
                if (w.hasStats(reg.statsIds())) {
                    dynamicGroups.computeIfAbsent(reg.recipeType(), k -> new ArrayList<>()).add(w);
                }
            }
        }

        registration.addRecipes(HARVEST_STATS, harvest);
        registration.addRecipes(RANGED_STATS, ranged);
        registration.addRecipes(ARMOR_STATS, armor);
        registration.addRecipes(SKULL_STATS, skull);
        registration.addRecipes(LASE_GUN_STATS, laserGun);

        for (var entry : dynamicGroups.entrySet()) {
            registration.addRecipes(entry.getKey(), entry.getValue());
        }

    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        final IGuiHelper guiHelper = registration.getJeiHelpers().getGuiHelper();
        try {
            CategoryJsonLoader.load(Minecraft.getInstance().getResourceManager());
        } catch (Exception e) {
            TConJEI.LOGGER.warn("Failed to load tconjei category data from resources", e);
        }
        registration.addRecipeCategories(new HarvestStatsCategory(guiHelper));
        registration.addRecipeCategories(new RangedStatsCategory(guiHelper));
        registration.addRecipeCategories(new ArmorStatsCategory(guiHelper));
        registration.addRecipeCategories(new SlimeskullStatsCategory(guiHelper));

        registration.addRecipeCategories(new LaserGunStatsCategory(guiHelper));

        for (var reg : TConJEIAPIImpl.REGISTRATIONS) {
            try {
                AbstractMaterialStatsCategory cat = reg.category().apply(guiHelper);
                registration.addRecipeCategories(cat);
            } catch (Exception e) {
                TConJEI.LOGGER.error("Failed to register dynamic category {}", reg.id(), e);
            }
        }

    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        List<RecipeType<?>> baseTypes = Arrays.asList(
                HARVEST_STATS, RANGED_STATS, ARMOR_STATS,
                //CURIO_STATS,
                LASE_GUN_STATS
        );

        List<RecipeType<?>> skullTypes = Arrays.asList(SKULL_STATS);

        List<RecipeType<?>> dynamicTypes = TConJEIAPIImpl.REGISTRATIONS.stream()
                .map(TConJEIAPIImpl.CategoryRegistration::recipeType)
                .collect(Collectors.toList());

        List<RecipeType<?>> allWorkstationTypes = new ArrayList<>();
        allWorkstationTypes.addAll(baseTypes);
        allWorkstationTypes.addAll(dynamicTypes);

        registration.addRecipeCatalyst(new ItemStack(TinkerTables.tinkerStation.asItem()), allWorkstationTypes.toArray(new RecipeType[0]));
        registration.addRecipeCatalyst(new ItemStack(TinkerTables.tinkersAnvil.asItem()), allWorkstationTypes.toArray(new RecipeType[0]));
        registration.addRecipeCatalyst(new ItemStack(TinkerTables.scorchedAnvil.asItem()), allWorkstationTypes.toArray(new RecipeType[0]));

        registration.addRecipeCatalyst(new ItemStack(TinkerSmeltery.searedBasin.asItem()), skullTypes.toArray(new RecipeType[0]));
        registration.addRecipeCatalyst(new ItemStack(TinkerSmeltery.scorchedBasin.asItem()), skullTypes.toArray(new RecipeType[0]));
    }

}
