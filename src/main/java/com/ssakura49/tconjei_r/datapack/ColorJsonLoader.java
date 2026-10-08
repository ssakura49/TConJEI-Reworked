package com.ssakura49.tconjei_r.datapack;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ssakura49.tconjei_r.ColorProvider;
import com.ssakura49.tconjei_r.TConJEI;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import slimeknights.tconstruct.library.materials.definition.MaterialId;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public final class ColorJsonLoader {
    private ColorJsonLoader() {}

    public static void load(ResourceManager manager) {
        if (manager == null) {
            TConJEI.LOGGER.warn("ColorJsonLoader: no resource manager available, skipping colours");
            return;
        }
        Map<ResourceLocation, Resource> files = manager.listResources(TConJEI.MOD_ID, loc -> loc.getPath().equals("colors.json"));
        if (files.isEmpty()) {
            return;
        }
        int parsed = 0;
        for (Map.Entry<ResourceLocation, Resource> entry : files.entrySet()) {
            try (InputStream in = entry.getValue().open()) {
                String json = new String(in.readAllBytes(), StandardCharsets.UTF_8);
                JsonObject root = JsonParser.parseString(json).getAsJsonObject();
                if (root.has("tierColors")) {
                    JsonObject tier = root.getAsJsonObject("tierColors");
                    for (Map.Entry<String, JsonElement> e : tier.entrySet()) {
                        try {
                            int tierIdx = Integer.parseInt(e.getKey());
                            int rgb = parseColor(e.getValue().getAsString());
                            ColorProvider.setTierColor(tierIdx, rgb);
                        } catch (Exception ex) {
                            TConJEI.LOGGER.warn("ColorJsonLoader: bad tierColors entry '{}' in {}", e.getKey(), entry.getKey());
                        }
                    }
                }
                if (root.has("materialColors")) {
                    JsonObject mat = root.getAsJsonObject("materialColors");
                    for (Map.Entry<String, JsonElement> e : mat.entrySet()) {
                        try {
                            int rgb = parseColor(e.getValue().getAsString());
                            MaterialId materialId = MaterialId.tryParse(e.getKey());
                            if (materialId != null) ColorProvider.setMaterialColor(materialId, rgb);
                        } catch (Exception ex) {
                            TConJEI.LOGGER.warn("ColorJsonLoader: bad materialColors entry '{}' in {}", e.getKey(), entry.getKey());
                        }
                    }
                }
                parsed++;
            } catch (Exception e) {
                TConJEI.LOGGER.warn("Failed to load tconjei colors file {}", entry.getKey(), e);
            }
        }
        if (parsed > 0) {
            TConJEI.LOGGER.info("Loaded tconjei colour data from {} file(s)", parsed);
        }
    }

    /**Parses #RRGGBB*/
    private static int parseColor(String s) {
        String hex = s.trim();
        if (hex.startsWith("#")) hex = hex.substring(1);
        if (hex.length() != 6) throw new IllegalArgumentException("expected #RRGGBB, got '" + s + "'");
        return 0xFF000000 | Integer.parseInt(hex, 16);
    }
}
