package com.ssakura49.tconjei_r.kubejs;

import dev.latvian.mods.kubejs.KubeJSPlugin;
import dev.latvian.mods.kubejs.script.BindingsEvent;
import dev.latvian.mods.kubejs.script.ScriptType;

public class KubeJSTConJEIPlugin extends KubeJSPlugin {
    @Override
    public void registerEvents() {
        TConJEIEvents.GROUP.register();
    }

    @Override
    public void afterInit() {
        TConJEIEvents.REGISTER_STATS_CATEGORY.post(ScriptType.STARTUP, new RegisterStatsCategoryEvent());
        TConJEIEvents.REGISTER_TOOLTIP.post(ScriptType.STARTUP, new RegisterTooltipEvent());
    }

    @Override
    public void registerBindings(BindingsEvent event) {
        event.add("TConJEIEvents", new TConJEIEventsBindings());
    }
}
