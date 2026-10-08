package com.ssakura49.tconjei_r;

import net.minecraft.network.chat.TextColor;
import slimeknights.tconstruct.library.client.materials.MaterialTooltipCache;
import slimeknights.tconstruct.library.materials.definition.MaterialId;
import slimeknights.tconstruct.library.tools.stat.ToolStats;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ColorProvider {
    public static int TEXT = 0x3F3F3F;
    public static final int DURABILITY = ToolStats.DURABILITY.getColor().getValue();
    public static final int ARMOR = ToolStats.ARMOR.getColor().getValue();

    private static final Map<Integer, Integer> TIER_COLORS = new HashMap<>();
    private static final Map<MaterialId, Integer> MATERIAL_COLORS = new HashMap<>();

    public static void setTierColor(int tier, int rgb) {
        TIER_COLORS.put(tier, rgb);
    }

    public static void setMaterialColor(MaterialId id, int rgb) {
        MATERIAL_COLORS.put(id, rgb);
    }

    public static Optional<TextColor> getMaterialTextColor(MaterialId materialId) {
        if (materialId != null) {
            Integer custom = MATERIAL_COLORS.get(materialId);
            if (custom != null) {
                return Optional.of(TextColor.fromRgb(custom));
            }
        }
        return materialId != null ? Optional.of(MaterialTooltipCache.getColor(materialId)) : Optional.empty();
    }

    public static Optional<TextColor> getTierTextColor(int i) {
        Integer custom = TIER_COLORS.get(i);
        if (custom != null) {
            return Optional.of(TextColor.fromRgb(custom));
        }
        MaterialId id = switch (i) {
            case 0, 1 -> MaterialId.tryParse("tconstruct:rock");
            case 2 -> MaterialId.tryParse("tconstruct:slimewood");
            case 3 -> MaterialId.tryParse("tconstruct:cobalt");
            case 4 -> MaterialId.tryParse("tconstruct:manyullyn");
            case 5 -> MaterialId.tryParse("tconstruct:knightslime");
            default -> null;
        };
        return id != null ? Optional.of(MaterialTooltipCache.getColor(id)) : Optional.empty();
    }

    public static Optional<Integer> getTierColor(int i) {
        return getTierTextColor(i).map(TextColor::getValue);
    }
}