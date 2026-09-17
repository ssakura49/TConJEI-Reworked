package com.ssakura49.tconjei_r.client;

import net.minecraftforge.common.ForgeConfigSpec;

public class ClientConfig {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static final ForgeConfigSpec.BooleanValue ENABLE_TOOLTIP;

    public static final ForgeConfigSpec.BooleanValue ENABLE_EXAMPLE;

    static{
        BUILDER.push("TConJEI client config");
        BUILDER.pop();

        ENABLE_TOOLTIP = BUILDER
                .comment("Enable tooltip under Tinker's materials","")
                .define("enable_tooltip", true);
        ENABLE_EXAMPLE = BUILDER
                .comment("Enable loading examples, path is assets/tconjei_r/jei/categories/***.json","允许加载示例，路径为assets/tconjei_r/jei/categories/***.json")
                .define("enable_example", false);

        SPEC = BUILDER.build();
    }
}
