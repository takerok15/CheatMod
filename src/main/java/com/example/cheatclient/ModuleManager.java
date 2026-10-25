package com.example.cheatclient;

import com.example.cheatclient.modules.*;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import java.util.ArrayList;
import java.util.List;

public class ModuleManager {
    public final PlayerESP playerESP;
    public final ItemESP itemESP;
    public final Fullbright fullbright;
    public final StaminaBypass staminaBypass;

    private final List<Module> modules = new ArrayList<>();

    public ModuleManager() {
        playerESP = new PlayerESP();
        itemESP = new ItemESP();
        fullbright = new Fullbright();
        staminaBypass = new StaminaBypass();
        modules.add(playerESP);
        modules.add(itemESP);
        modules.add(fullbright);
        modules.add(staminaBypass);
    }

    public List<Module> getModules() {
        return modules;
    }

    @SubscribeEvent
    public void onRenderWorldLast(RenderWorldLastEvent event) {
        for (Module module : modules) {
            if (module.isEnabled()) {
                module.onRender(event);
            }
        }
    }
}