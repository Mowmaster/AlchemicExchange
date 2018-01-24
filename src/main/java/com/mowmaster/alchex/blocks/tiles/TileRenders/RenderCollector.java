package com.mowmaster.alchex.blocks.tiles.TileRenders;

import com.mowmaster.alchex.blocks.tiles.TileCollector;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;


public class RenderCollector extends TileEntitySpecialRenderer<TileCollector>
{


    @Override
    public void render(TileCollector te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        Item item = te.getItemInBlock().getItem();
        ItemStack itemStack = new ItemStack(item,1);
        RenderItem itemRenderer = Minecraft.getMinecraft().getRenderItem();


        GlStateManager.pushMatrix();
        GlStateManager.translate(x+0.5f, y, z+0.5f);
        double boop = Minecraft.getSystemTime()/800D;
        GlStateManager.translate(0D, Math.sin(boop%(2*Math.PI))*0.065, 0D);
        GlStateManager.rotate((float)(((boop*40D)%360)), 0f, 1f, 0f);
        if (!itemStack.isEmpty()) {
            renderItem(itemRenderer, itemStack , 0f, 1.25f, 0f, 0f, 0f, 0f, 0f);
        }
        GlStateManager.popMatrix();
    }




    public static void renderItem(RenderItem itemRenderer, ItemStack itemStack, float x, float y, float z, float angle, float xr, float yr, float zr) {
        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y, z);
        GlStateManager.rotate(angle, xr, yr, zr);
        GlStateManager.scale(0.5f,0.5f,0.5f);
        if (!itemRenderer.shouldRenderItemIn3D(itemStack)) {GlStateManager.rotate(180f, 0f, 1f, 0f);}
        GlStateManager.pushAttrib();
        RenderHelper.enableStandardItemLighting();
        itemRenderer.renderItem(itemStack, ItemCameraTransforms.TransformType.FIXED);
        RenderHelper.disableStandardItemLighting();
        GlStateManager.popAttrib();
        GlStateManager.popMatrix();
    }
}
