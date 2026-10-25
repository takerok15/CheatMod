package com.example.cheatclient;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import org.lwjgl.input.Keyboard;

@Mod(modid = "cheatclient", name = "Cheat Client", version = "1.0")
public class CheatClient {
    public static CheatClient instance;
    public static ModuleManager moduleManager;

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        instance = this;
        moduleManager = new ModuleManager();
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(moduleManager);
    }

    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        if (Keyboard.getEventKey() == Keyboard.KEY_INSERT && Keyboard.getEventKeyState()) {
            if (Minecraft.getMinecraft().currentScreen == null) {
                Minecraft.getMinecraft().displayGuiScreen(new GuiCheatMenu());
            }
        }
    }
}