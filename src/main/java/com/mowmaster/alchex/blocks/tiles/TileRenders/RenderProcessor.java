package com.mowmaster.alchex.blocks.tiles.TileRenders;

import com.mowmaster.alchex.blocks.liquids.LiquidBasic;
import com.mowmaster.alchex.blocks.tiles.TileCollector;
import com.mowmaster.alchex.blocks.tiles.TileProcessor;
import com.mowmaster.alchex.recipes.ProcessorRecipes;
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
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class RenderProcessor extends TileEntitySpecialRenderer<TileProcessor>
{
    public static ResourceLocation resource = new ResourceLocation("alchex", "textures/blocks/fluids/sunlight_still.png");

    @Override
    public void render(TileProcessor te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        ItemStack itemStackInput = te.itemStackInput;
        ItemStack itemStackOutput = ProcessorRecipes.instance().getProcessorResult(te.itemStackInput, new FluidStack(LiquidBasic.fluidMoonlight,1));
        RenderItem itemRenderer = Minecraft.getMinecraft().getRenderItem();

        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y, z);

        BlockPos pos = te.getPos();
        Tessellator tessellator = Tessellator.getInstance();
        //bindTexture(resource);

        renderItemRotating(itemRenderer,itemStackInput,0.6f,0f,0.6f,0f,0f,0f,0f);
        if(te.itemStackInput.getCount()<16 && te.itemStackInput.getCount()>=0)
        {
            renderItemInput(itemRenderer,itemStackInput,0.28125f,1f,0.28125f,0f,0f,0f,0f);
        }
        else if(te.itemStackInput.getCount()<32 && te.itemStackInput.getCount()>=16)
        {
            renderItemInput(itemRenderer,itemStackInput,0.2f,1f,0.28125f,0f,0f,0f,0f);
            renderItemInput(itemRenderer,itemStackInput,0.3625f,1f,0.28125f,0f,0f,0f,0f);
        }
        else if(te.itemStackInput.getCount()<48 && te.itemStackInput.getCount()>=32)
        {
            renderItemInput(itemRenderer,itemStackInput,0.2f,1f,0.25f,0f,0f,0f,0f);
            renderItemInput(itemRenderer,itemStackInput,0.3625f,1f,0.25f,0f,0f,0f,0f);
            renderItemInput(itemRenderer,itemStackInput,0.28125f,1f,0.375f,0f,0f,0f,0f);
        }
        else if(te.itemStackInput.getCount()>=48)
        {
            renderItemInput(itemRenderer,itemStackInput,0.215f,1f,0.25f,0f,0f,0f,0f);
            renderItemInput(itemRenderer,itemStackInput,0.345f,1f,0.25f,0f,0f,0f,0f);
            renderItemInput(itemRenderer,itemStackInput,0.28125f,1f,0.375f,0f,0f,0f,0f);
            renderItemInput(itemRenderer,itemStackInput,0.28125f,1.125f,0.28125f,0f,0f,0f,0f);
        }

        if(te.itemStackOutput.getCount()<16 && te.itemStackOutput.getCount()>=0)
        {
            renderItemOutput(itemRenderer,itemStackOutput,0.59375f,0.125f,0.59375f,0f,0f,0f,0f);
        }
        else if(te.itemStackOutput.getCount()<32 && te.itemStackOutput.getCount()>=16)
        {
            renderItemOutput(itemRenderer,itemStackOutput,0.50f,0.125f,0.59375f,0f,0f,0f,0f);
            renderItemOutput(itemRenderer,itemStackOutput,0.6875f,0.125f,0.59375f,0f,0f,0f,0f);
        }
        else if(te.itemStackOutput.getCount()<48 && te.itemStackOutput.getCount()>=32)
        {
            renderItemOutput(itemRenderer,itemStackOutput,0.50f,0.125f,0.50f,0f,0f,0f,0f);
            renderItemOutput(itemRenderer,itemStackOutput,0.6875f,0.125f,0.5f,0f,0f,0f,0f);
            renderItemOutput(itemRenderer,itemStackOutput,0.59375f,0.125f,0.6875f,0f,0f,0f,0f);
        }
        else if(te.itemStackOutput.getCount()>=48)
        {
            renderItemOutput(itemRenderer,itemStackOutput,0.50f,0.125f,0.50f,0f,0f,0f,0f);
            renderItemOutput(itemRenderer,itemStackOutput,0.6875f,0.125f,0.5f,0f,0f,0f,0f);
            renderItemOutput(itemRenderer,itemStackOutput,0.5f,0.125f,0.6875f,0f,0f,0f,0f);
            renderItemOutput(itemRenderer,itemStackOutput,0.6875f,0.125f,0.6875f,0f,0f,0f,0f);
        }

        renderFluidTankFront(tessellator,te,pos);


        GlStateManager.popMatrix();
    }

    private void renderFluidTankLeft(Tessellator tessellator, TileProcessor collector, BlockPos pos) {
        if (collector == null) {
            return;
        }

        FluidStack renderFluid = collector.tank.getFluid();
        if (renderFluid==null) {
            return;
        }


        float scale = (collector.tank.getFluidAmount());

        if (scale > 0) {
            BufferBuilder renderer = tessellator.getBuffer();
            ResourceLocation still = renderFluid.getFluid().getStill();
            TextureAtlasSprite sprite = Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(still.toString());

            RenderHelper.disableStandardItemLighting();

            GlStateManager.color(1, 1, 1, .5f);
            renderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_COLOR);

            float u1 = sprite.getMinU();
            float v1 = sprite.getMinV();
            float u2 = sprite.getMaxU();
            float v2 = sprite.getMaxV();

            // Top

            float zero = 0.1875f;
            float one = 0.8125f;
            float yvalue = 0.38f;
            renderer.pos(zero, scale+yvalue, zero).tex(u1, v1).color(255, 255, 255, 128).endVertex();
            renderer.pos(zero, scale+yvalue, one).tex(u1, v2).color(255, 255, 255, 128).endVertex();
            renderer.pos(one, scale+yvalue, one).tex(u2, v2).color(255, 255, 255, 128).endVertex();
            renderer.pos(one, scale+yvalue, zero).tex(u2, v1).color(255, 255, 255, 128).endVertex();

            tessellator.draw();

            RenderHelper.enableStandardItemLighting();
        }
    }

    private void renderFluidTankFront(Tessellator tessellator, TileProcessor processor, BlockPos pos) {
        if (processor == null) {
            return;
        }

        FluidStack renderFluid = processor.tank.getFluid();
        if (renderFluid==null) {
            return;
        }


        float scale = (processor.tank.getFluidAmount());

        if (scale > 0) {
            BufferBuilder renderer = tessellator.getBuffer();
            ResourceLocation still = renderFluid.getFluid().getStill();
            TextureAtlasSprite sprite = Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(still.toString());

            RenderHelper.disableStandardItemLighting();

            GlStateManager.color(1, 1, 1, .5f);
            renderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_COLOR);

            float u1 = sprite.getMinU();
            float v1 = sprite.getMinV();
            float u2 = sprite.getMaxU();
            float v2 = sprite.getMaxV();

            // Top

            float zero = 1.0f;
            float one = 1.0f;
            float yvalue = 1.0f;
            renderer.pos(1.0f, 1.0f, 1.0f).tex(u1, v1).color(255, 255, 255, 128).endVertex();
            renderer.pos(1.0f, 1.0f, 1.0f).tex(u1, v2).color(255, 255, 255, 128).endVertex();
            renderer.pos(1.0f, 1.0f, 1.0f).tex(u2, v2).color(255, 255, 255, 128).endVertex();
            renderer.pos(1.0f, 1.0f, 1.0f).tex(u2, v1).color(255, 255, 255, 128).endVertex();

            tessellator.draw();

            RenderHelper.enableStandardItemLighting();
        }
    }

    public static void renderItemRotating(RenderItem itemRenderer, ItemStack itemStack, float x, float y, float z, float angle, float xr, float yr, float zr)
    {
        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y, z);
        double boop = Minecraft.getSystemTime()/800D;
        GlStateManager.translate(0D, Math.sin(boop%(2*Math.PI))*0.065, 0D);
        GlStateManager.rotate((float)(((boop*40D)%360)), 0f, 1f, 0f);
        if (!itemStack.isEmpty()) {
            renderItem(itemRenderer, itemStack , 0f, 0.60f, 0f, 0f, 0f, 0f, 0f);
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

    public static void renderItemOutput(RenderItem itemRenderer, ItemStack itemStack, float x, float y, float z, float angle, float xr, float yr, float zr) {
        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y, z);
        GlStateManager.rotate(angle, xr, yr, zr);
        GlStateManager.scale(0.25f,0.25f,0.25f);
        if (!itemRenderer.shouldRenderItemIn3D(itemStack)) {GlStateManager.rotate(180f, 0f, 1f, 0f);}
        GlStateManager.pushAttrib();
        RenderHelper.enableStandardItemLighting();
        itemRenderer.renderItem(itemStack, ItemCameraTransforms.TransformType.FIXED);
        RenderHelper.disableStandardItemLighting();
        GlStateManager.popAttrib();
        GlStateManager.popMatrix();
    }

    public static void renderItemInput(RenderItem itemRenderer, ItemStack itemStack, float x, float y, float z, float angle, float xr, float yr, float zr) {
        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y, z);
        GlStateManager.rotate(angle, xr, yr, zr);
        GlStateManager.scale(0.25f,0.25f,0.25f);
        if (!itemRenderer.shouldRenderItemIn3D(itemStack)) {GlStateManager.rotate(180f, 0f, 1f, 0f);}
        GlStateManager.pushAttrib();
        RenderHelper.enableStandardItemLighting();
        itemRenderer.renderItem(itemStack, ItemCameraTransforms.TransformType.FIXED);
        RenderHelper.disableStandardItemLighting();
        GlStateManager.popAttrib();
        GlStateManager.popMatrix();
    }
}
