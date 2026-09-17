package com.ssakura49.tconjei_r;

import com.ssakura49.tconjei_r.jei.AbstractMaterialStatsCategory;
import com.ssakura49.tconjei_r.jei.MaterialStatsWrapper;
import slimeknights.tconstruct.library.materials.MaterialRegistry;

import java.util.List;

public class Utils {
    //添加缓存
    private static List<MaterialStatsWrapper> cachedWrappers;

    public static List<MaterialStatsWrapper> getMaterialWrappers() {
        if (cachedWrappers == null) {
            cachedWrappers = MaterialRegistry.getInstance().getVisibleMaterials()
                    .stream()
                    .map(MaterialStatsWrapper::new)
                    .toList();
        }
        return cachedWrappers;
    }

    public static void clearMaterialWrapperCache() {
        cachedWrappers = null;
    }

    public static boolean inBox(double mX, double mY, float x, float y, float w, float h) {
        return (x <= mX && mX <= x + w && y <= mY && mY <= y + h);
    }

    public static boolean inBox(double mX, double mY, float x, float y, float w) {
        return inBox(mX, mY, x, y, w, AbstractMaterialStatsCategory.LINE_HEIGHT);
    }

}
