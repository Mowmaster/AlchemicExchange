package com.mowmaster.alchex.proxies;


import com.mowmaster.alchex.blocks.BlockRegistry;
import com.mowmaster.alchex.blocks.liquids.LiquidBasic;
import com.mowmaster.alchex.blocks.tiles.TileCollector;
import com.mowmaster.alchex.blocks.tiles.TileProcessor;
import com.mowmaster.alchex.blocks.tiles.TileRenders.RenderCollector;
import com.mowmaster.alchex.blocks.tiles.TileRenders.RenderProcessor;
import com.mowmaster.alchex.references.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


public class ClientProxy extends CommonProxy
{

    @Override
    @SideOnly(Side.CLIENT)
    public void PreInit()
    {
        BlockRegistry.registerRenders();


        ClientRegistry.bindTileEntitySpecialRenderer(TileCollector.class,new RenderCollector());
        ClientRegistry.bindTileEntitySpecialRenderer(TileProcessor.class,new RenderProcessor());




    }

    @Override
    public void registerModelBakeryVarients()
    {

    }
/*
    private void registerFluidRenderers() {
        registerFluidRender(LiquidBasic.fluidSunlight);
        registerFluidRender(LiquidBasic.fluidMoonlight);
    }

    private void registerFluidRender(Fluid f) {
        LiquidBasic.FluidCustomModelMapper mapper = new LiquidBasic.FluidCustomModelMapper(f);
        Block block = f.getBlock();
        if(block != null) {
            Item item = Item.getItemFromBlock(block);
            if (item != Items.AIR) {
                ModelLoader.registerItemVariants(item);
                ModelLoader.setCustomMeshDefinition(item, mapper);
            } else {
                ModelLoader.setCustomStateMapper(block, mapper);
            }
        }
    }
    */

}
