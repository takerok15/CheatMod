package com.example.cheatclient.modules;

import com.example.cheatclient.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import org.lwjgl.opengl.GL11;

public class ItemESP extends Module {
    public float red = 1.0f, green = 1.0f, blue = 0.0f;
    public ItemESP() { super("ItemESP"); }
    @Override public void onEnable() { }
    @Override public void onDisable() { }

    @Override
    protected void render(RenderWorldLastEvent event) {
        Minecraft mc = Minecraft.getMinecraft();
        if (mc.theWorld == null) return;
        
        double renderX = RenderManager.renderPosX;
        double renderY = RenderManager.renderPosY;
        double renderZ = RenderManager.renderPosZ;

        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(false);
        GL11.glLineWidth(2.0f);

        for (Object obj : mc.theWorld.loadedEntityList) {
            if (!(obj instanceof EntityItem)) continue;
            EntityItem item = (EntityItem) obj;

            double x = item.lastTickPosX + (item.posX - item.lastTickPosX) * event.partialTicks - renderX;
            double y = item.lastTickPosY + (item.posY - item.lastTickPosY) * event.partialTicks - renderY;
            double z = item.lastTickPosZ + (item.posZ - item.lastTickPosZ) * event.partialTicks - renderZ;

            AxisAlignedBB bb = item.boundingBox.copy();
            bb.offset(-item.posX, -item.posY, -item.posZ);
            bb.offset(x, y, z);

            GL11.glColor4f(red, green, blue, 1.0f);
            RenderGlobal.drawOutlinedBoundingBox(bb, -1, -1, -1);

            if (item.getEntityItem() != null) {
                renderText(item.getEntityItem().getDisplayName(), x, y + 0.5, z);
            }
        }
        GL11.glDepthMask(true);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glPopMatrix();
    }

    private void renderText(String text, double x, double y, double z) {
        Minecraft mc = Minecraft.getMinecraft();
        GL11.glPushMatrix();
        GL11.glTranslated(x, y + 0.5, z);
        GL11.glRotatef(-RenderManager.instance.playerViewY, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(RenderManager.instance.playerViewX, 1.0F, 0.0F, 0.0F);
        GL11.glScalef(-0.025F, -0.025F, 0.025F);
        mc.fontRenderer.drawStringWithShadow(text, -mc.fontRenderer.getStringWidth(text) / 2, 0, 0xFFFF00);
        GL11.glPopMatrix();
    }
}