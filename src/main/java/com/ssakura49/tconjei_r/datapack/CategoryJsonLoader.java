package com.ssakura49.tconjei_r.datapack;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ssakura49.tconjei_r.TConJEI;
import com.ssakura49.tconjei_r.api.TConJEIAPI;
import com.ssakura49.tconjei_r.client.ClientConfig;
import com.ssakura49.tconjei_r.jei.MaterialStatsWrapper;
import com.ssakura49.tconjei_r.kubejs.ScriptedAddonStatsCategory;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.tags.TagKey;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.Item;
import slimeknights.tconstruct.library.materials.stats.MaterialStatsId;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public final class CategoryJsonLoader {
    private CategoryJsonLoader() {}

    private static final Supplier<Boolean> EXAMPLE = ClientConfig.ENABLE_EXAMPLE;

    public static void load(ResourceManager manager) {
        if (manager == null) {
            TConJEI.LOGGER.warn("CategoryJsonLoader: no resource manager available, skipping data-driven categories");
            return;
        }
        Map<ResourceLocation, Resource> files = manager.listResources("jei/categories", loc -> loc.getPath().endsWith(".json"));
        if (files.isEmpty()) return;
        int loaded = 0;
        for (Map.Entry<ResourceLocation, Resource> entry : files.entrySet()) {
            ResourceLocation loc = entry.getKey();
            if (!EXAMPLE.get() && isBuiltinExample(loc)) continue;
            try {
                if (parseCategory(entry.getValue())) loaded++;
            } catch (Exception e) {
                TConJEI.LOGGER.warn("Failed to load tconjei category data file {}", loc, e);
            }
        }
        TConJEI.LOGGER.info("Loaded {} tconjei category JSON file(s) from data-driven registration", loaded);
    }

    private static boolean isBuiltinExample(ResourceLocation loc) {
        return loc.getNamespace().equals(TConJEI.MOD_ID)
                && loc.getPath().startsWith("jei/categories/")
                && loc.getPath().endsWith("_demo.json");
    }

    private static boolean parseCategory(Resource resource) throws Exception {
        try (InputStream in = resource.open()) {
            String json = new String(in.readAllBytes(), StandardCharsets.UTF_8);
            JsonObject obj = JsonParser.parseString(json).getAsJsonObject();

            ResourceLocation id = new ResourceLocation(GsonHelper.getAsString(obj, "id"));
            ResourceLocation icon = new ResourceLocation(GsonHelper.getAsString(obj, "icon"));
            Component title = Component.translatable(GsonHelper.getAsString(obj, "titleKey"));
            int iconU = GsonHelper.getAsInt(obj, "iconU", 0);
            int iconV = GsonHelper.getAsInt(obj, "iconV", 0);

            JsonArray statsArray = GsonHelper.getAsJsonArray(obj, "statsIds");
            List<MaterialStatsId> statsIds = new ArrayList<>();
            for (JsonElement el : statsArray) {
                statsIds.add(new MaterialStatsId(new ResourceLocation(GsonHelper.convertToString(el, "statsIds[]"))));
            }

            TagKey<Item> tag = TagKey.create(Registries.ITEM, new ResourceLocation(GsonHelper.getAsString(obj, "tag")));

            TConJEIAPI.CatalystType catalyst = "smeltery".equalsIgnoreCase(GsonHelper.getAsString(obj, "catalyst", "tinker_station"))
                    ? TConJEIAPI.CatalystType.SMELTERY
                    : TConJEIAPI.CatalystType.TINKER_STATION;
            RecipeType<MaterialStatsWrapper> recipeType = RecipeType.create(TConJEI.MOD_ID, id.getPath(), MaterialStatsWrapper.class);

            TConJEI.API().registerStatsCategory(
                    id, title, icon, iconU, iconV, statsIds,
                    gui -> new ScriptedAddonStatsCategory(gui, icon, iconU, iconV, title, statsIds, tag, recipeType),
                    catalyst
            );
            return true;
        }
    }
}