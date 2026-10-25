package com.example.cheatclient;

import com.example.cheatclient.modules.PlayerESP;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import org.lwjgl.input.Keyboard;
import java.io.IOException;
import java.util.List;

public class GuiCheatMenu extends GuiScreen {
    private GuiTextField redField, greenField, blueField;
    private GuiButton modeButton;

    @Override
    public void initGui() {
        buttonList.clear();
        int y = 30;
        int id = 0;
        
        for (Module module : CheatClient.moduleManager.getModules()) {
            GuiButton btn = new GuiButton(id, this.width / 2 - 100, y, 200, 20, 
                (module.isEnabled() ? "§a[ON] " : "§c[OFF] ") + module.getName());
            buttonList.add(btn);
            y += 24;
            id++;
        }

        y += 10;
        PlayerESP esp = CheatClient.moduleManager.playerESP;
        modeButton = new GuiButton(100, this.width / 2 - 100, y, 200, 20, 
            "Режим: " + (esp.drawSkeleton ? "Скелеты" : "Боксы"));
        buttonList.add(modeButton);
        y += 24;

        redField = new GuiTextField(fontRendererObj, this.width / 2 - 90, y, 50, 18);
        redField.setText(String.valueOf((int)(esp.red * 255)));
        greenField = new GuiTextField(fontRendererObj, this.width / 2 - 30, y, 50, 18);
        greenField.setText(String.valueOf((int)(esp.green * 255)));
        blueField = new GuiTextField(fontRendererObj, this.width / 2 + 30, y, 50, 18);
        blueField.setText(String.valueOf((int)(esp.blue * 255)));
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if (button.id == 100) {
            PlayerESP esp = CheatClient.moduleManager.playerESP;
            esp.drawSkeleton = !esp.drawSkeleton;
            modeButton.displayString = "Режим: " + (esp.drawSkeleton ? "Скелеты" : "Боксы");
            return;
        }
        List<Module> modules = CheatClient.moduleManager.getModules();
        if (button.id >= 0 && button.id < modules.size()) {
            Module m = modules.get(button.id);
            m.toggle();
            button.displayString = (m.isEnabled() ? "§a[ON] " : "§c[OFF] ") + m.getName();
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        drawCenteredString(fontRendererObj, "§lCheat Client", this.width / 2, 10, 0xFFFFFF);
        
        int textY = 30 + 24 * (CheatClient.moduleManager.getModules().size() + 1) + 10;
        drawString(fontRendererObj, "R:", this.width / 2 - 105, textY, 0xFFFFFF);
        drawString(fontRendererObj, "G:", this.width / 2 - 45, textY, 0xFFFFFF);
        drawString(fontRendererObj, "B:", this.width / 2 + 15, textY, 0xFFFFFF);
        
        redField.drawTextBox();
        greenField.drawTextBox();
        blueField.drawTextBox();
        super.drawScreen(mouseX, mouseY, partialTick);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if (keyCode == Keyboard.KEY_ESCAPE) {
            mc.displayGuiScreen(null);
            return;
        }
        redField.textboxKeyTyped(typedChar, keyCode);
        greenField.textboxKeyTyped(typedChar, keyCode);
        blueField.textboxKeyTyped(typedChar, keyCode);
        updateColors();
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        redField.mouseClicked(mouseX, mouseY, mouseButton);
        greenField.mouseClicked(mouseX, mouseY, mouseButton);
        blueField.mouseClicked(mouseX, mouseY, mouseButton);
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }
    
    private void updateColors() {
        try {
            int r = Math.min(255, Math.max(0, Integer.parseInt(redField.getText())));
            int g = Math.min(255, Math.max(0, Integer.parseInt(greenField.getText())));
            int b = Math.min(255, Math.max(0, Integer.parseInt(blueField.getText())));
            PlayerESP esp = CheatClient.moduleManager.playerESP;
            esp.red = r / 255.0f; esp.green = g / 255.0f; esp.blue = b / 255.0f;
        } catch (Exception ignored) { }
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
}