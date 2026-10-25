package com.example.cheatclient.modules;

import com.example.cheatclient.Module;
import net.minecraftforge.client.event.RenderWorldLastEvent;

public class StaminaBypass extends Module {
    public StaminaBypass() { super("StaminaBypass"); }
    @Override public void onEnable() { }
    @Override public void onDisable() { }
    @Override protected void render(RenderWorldLastEvent event) { }
}