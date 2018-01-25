package com.mowmaster.alchex.blocks.tiles.TileRenders;

import com.mowmaster.alchex.blocks.liquids.LiquidBasic;
import com.mowmaster.alchex.blocks.tiles.TileCollector;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class RenderCollector extends TileEntitySpecialRenderer<TileCollector>
{
    //public static ResourceLocation resource = new ResourceLocation("alchex", "textures/blocks/fluids/sunlight_still.png");

    @Override
    public void render(TileCollector te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        Item item = te.getItemInBlock().getItem();
        ItemStack itemStack = new ItemStack(item,1);
        RenderItem itemRenderer = Minecraft.getMinecraft().getRenderItem();

        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y, z);

        BlockPos pos = te.getPos();
        Tessellator tessellator = Tessellator.getInstance();
        //bindTexture(resource);
        renderFluid(tessellator,te,pos);
        renderItemRotating(itemRenderer,itemStack,0f,0f,0f,0f,0f,0f,0f);

        GlStateManager.popMatrix();
    }

    private void renderFluid(Tessellator tessellator, TileCollector collector, BlockPos pos) {
        if (collector == null) {
            return;
        }

        Fluid renderFluid = collector.getFluidBeingStored();
        if (renderFluid == null) {
            return;
        }


        float scale = (collector.getLiquidStored()/10) * 0.005675f;

        if (scale > 0) {
            BufferBuilder renderer = tessellator.getBuffer();
            ResourceLocation still = renderFluid.getStill();
            TextureAtlasSprite sprite = Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(still.toString());

            net.minecraft.client.renderer.RenderHelper.disableStandardItemLighting();

            GlStateManager.color(1, 1, 1, .5f);
            renderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_COLOR);

            float u1 = sprite.getMinU();
            float v1 = sprite.getMinV();
            float u2 = sprite.getMaxU();
            float v2 = sprite.getMaxV();

            // Top

            float zero = 0.1875f;
            float one = 0.8125f;
            float yvalue = 0.37f;
            renderer.pos(zero, scale+yvalue, zero).tex(u1, v1).color(255, 255, 255, 128).endVertex();
            renderer.pos(zero, scale+yvalue, one).tex(u1, v2).color(255, 255, 255, 128).endVertex();;
            renderer.pos(one, scale+yvalue, one).tex(u2, v2).color(255, 255, 255, 128).endVertex();;
            renderer.pos(one, scale+yvalue, zero).tex(u2, v1).color(255, 255, 255, 128).endVertex();;

            tessellator.draw();

            net.minecraft.client.renderer.RenderHelper.enableStandardItemLighting();
        }
    }

    public static void renderItemRotating(RenderItem itemRenderer, ItemStack itemStack, float x, float y, float z, float angle, float xr, float yr, float zr)
    {
        GlStateManager.translate(0.5f, y, 0.5f);
        double boop = Minecraft.getSystemTime()/800D;
        GlStateManager.translate(0D, Math.sin(boop%(2*Math.PI))*0.065, 0D);
        GlStateManager.rotate((float)(((boop*40D)%360)), 0f, 1f, 0f);
        if (!itemStack.isEmpty()) {
            renderItem(itemRenderer, itemStack , 0f, 1.25f, 0f, 0f, 0f, 0f, 0f);
        }
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
