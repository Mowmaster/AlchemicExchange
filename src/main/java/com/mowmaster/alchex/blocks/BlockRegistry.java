package com.mowmaster.alchex.blocks;

import com.mowmaster.alchex.blocks.liquids.LiquidBasic;
import com.mowmaster.alchex.references.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fml.common.registry.ForgeRegistries;


public class BlockRegistry
{

    //public static Block redOre;
    public static Block basicCollector;
    public static BlockFluidClassic liquidSunlight;
    public static BlockFluidClassic liquidMoonlight;


    public static void init() {
        //redOre = new BlockCrystalOre("redore", "red/redore");
        basicCollector = new BlockCollector("stone_collector","stone_collector", Material.ROCK);
    }

    public static void register()
    {
        //registerBlock(redOre);
        registerBlock(basicCollector);
    }

    public static void registerRenders()
    {
        //registerRender(redOre);
        registerRender(basicCollector);
    }





    public static void registerBlock(Block block)
    {
        ForgeRegistries.BLOCKS.register(block);
        ForgeRegistries.ITEMS.register(new ItemBlock(block).setRegistryName(block.getRegistryName()));
    }

    public static void registerBlock(Block block, ItemBlock itemBlock)
    {

        ForgeRegistries.BLOCKS.register(block);
        ForgeRegistries.ITEMS.register(itemBlock.setRegistryName(block.getRegistryName()));
    }

    //Regular Block regRender
    public static void registerRender(Block block)
    {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, new ModelResourceLocation(new ResourceLocation(Reference.MODID, block.getUnlocalizedName().substring(5)), "inventory"));
    }



    public static void registerRender(Block block, int meta, String fileName)
    {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), meta, new ModelResourceLocation(new ResourceLocation(Reference.MODID, fileName), "inventory"));
    }
}
