package com.example.cheatclient.modules;

import com.example.cheatclient.Module;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderWorldLastEvent;

public class Fullbright extends Module {
    private float oldGamma;
    public Fullbright() { super("Fullbright"); }

    @Override
    public void onEnable() {
        oldGamma = Minecraft.getMinecraft().gameSettings.gammaSetting;
        Minecraft.getMinecraft().gameSettings.gammaSetting = 100.0F;
    }
    @Override
    public void onDisable() {
        Minecraft.getMinecraft().gameSettings.gammaSetting = oldGamma;
    }
    @Override
    protected void render(RenderWorldLastEvent event) { }
}