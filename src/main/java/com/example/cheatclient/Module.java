package com.example.cheatclient;

import net.minecraftforge.client.event.RenderWorldLastEvent;

public abstract class Module {
    protected final String name;
    protected boolean enabled;

    public Module(String name) {
        this.name = name;
        this.enabled = false;
    }

    public String getName() { return name; }
    public boolean isEnabled() { return enabled; }

    public void toggle() { setEnabled(!enabled); }

    public void setEnabled(boolean state) {
        if (this.enabled == state) return;
        this.enabled = state;
        if (state) { onEnable(); } else { onDisable(); }
    }

    public abstract void onEnable();
    public abstract void onDisable();
    public void onRender(RenderWorldLastEvent event) { if (enabled) render(event); }
    protected abstract void render(RenderWorldLastEvent event);
}