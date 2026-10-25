package com.example.cheatclient.modules;

import com.example.cheatclient.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import org.lwjgl.opengl.GL11;

public class PlayerESP extends Module {
    public static float red = 1.0f, green = 0.0f, blue = 0.0f;
    public boolean drawSkeleton = false;

    public PlayerESP() { super("PlayerESP"); }
    @Override public void onEnable() { }
    @Override public void onDisable() { }

    @Override
    protected void render(RenderWorldLastEvent event) {
        Minecraft mc = Minecraft.getMinecraft();
        if (mc.theWorld == null || mc.thePlayer == null) return;
        
        double renderX = RenderManager.renderPosX;
        double renderY = RenderManager.renderPosY;
        double renderZ = RenderManager.renderPosZ;

        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(false);
        GL11.glLineWidth(2.0f);

        for (Object obj : mc.theWorld.playerEntities) {
            EntityPlayer player = (EntityPlayer) obj;
            if (player == mc.thePlayer || player.isDead) continue;

            double x = player.lastTickPosX + (player.posX - player.lastTickPosX) * event.partialTicks - renderX;
            double y = player.lastTickPosY + (player.posY - player.lastTickPosY) * event.partialTicks - renderY;
            double z = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * event.partialTicks - renderZ;

            if (drawSkeleton) {
                GL11.glColor4f(red, green, blue, 1.0f);
                GL11.glBegin(GL11.GL_LINES);
                GL11.glVertex3d(x, y + player.height, z);
                GL11.glVertex3d(x, y, z);
                GL11.glEnd();
            } else {
                AxisAlignedBB bb = player.boundingBox.copy();
                bb.offset(-player.posX, -player.posY, -player.posZ);
                bb.offset(x, y, z);
                GL11.glColor4f(red, green, blue, 1.0f);
                RenderGlobal.drawOutlinedBoundingBox(bb, -1, -1, -1);
            }
            renderText(player, x, y, z);
        }

        GL11.glDepthMask(true);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glPopMatrix();
    }

    private void renderText(EntityPlayer player, double x, double y, double z) {
        Minecraft mc = Minecraft.getMinecraft();
        String text = player.getCommandSenderName() + " §c" + (int)player.getHealth() + "HP";
        GL11.glPushMatrix();
        GL11.glTranslated(x, y + player.height + 0.5, z);
        GL11.glRotatef(-RenderManager.instance.playerViewY, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(RenderManager.instance.playerViewX, 1.0F, 0.0F, 0.0F);
        GL11.glScalef(-0.025F, -0.025F, 0.025F);
        mc.fontRenderer.drawStringWithShadow(text, -mc.fontRenderer.getStringWidth(text) / 2, 0, 0xFFFFFF);
        GL11.glPopMatrix();
    }
}