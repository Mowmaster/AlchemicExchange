package com.mowmaster.alchex.blocks.liquids;

import com.mowmaster.alchex.references.Reference;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.particle.ParticleDrip;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.gen.feature.WorldGenHellLava;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.capability.wrappers.BlockLiquidWrapper;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.ForgeRegistry;
import net.minecraftforge.registries.RegistryManager;


public class LiquidBasic
{
    public LiquidBasic()
    {
        fluidSunlight.canBePlacedInWorld();
        fluidMoonlight.canBePlacedInWorld();
    }

    public static Fluid fluidSunlight;
    public static Fluid fluidMoonlight;
    public static BlockFluidClassic blockFluidSunlight;
    public static BlockFluidClassic blockFluidMoonlight;



    public static void regFluidBlocks()
    {
        blockFluidSunlight = new BlockFluidClassic(fluidSunlight,Material.WATER);
        ForgeRegistries.BLOCKS.register(blockFluidSunlight.setUnlocalizedName("sunlight").setRegistryName("sunlight"));
        fluidSunlight.setBlock(blockFluidSunlight);
        FluidRegistry.addBucketForFluid(fluidSunlight);

        blockFluidMoonlight = new BlockFluidClassic(fluidMoonlight,Material.WATER);
        ForgeRegistries.BLOCKS.register(blockFluidMoonlight.setUnlocalizedName("moonlight").setRegistryName("moonlight"));
        fluidMoonlight.setBlock(blockFluidMoonlight);
        FluidRegistry.addBucketForFluid(fluidMoonlight);
    }

    public static void  regFluids()
    {
        fluidSunlight = new Fluid("sunlight",new ResourceLocation("alchex:blocks/fluids/sunlight_still"),new ResourceLocation("alchex:blocks/fluids/sunlight_flowing")).setDensity(1000).setViscosity(2000);
        FluidRegistry.registerFluid(fluidSunlight);
        fluidSunlight = FluidRegistry.getFluid("sunlight");

        fluidMoonlight = new Fluid("moonlight",new ResourceLocation("alchex:blocks/fluids/moonlight_still"),new ResourceLocation("alchex:blocks/fluids/moonlight_flowing")).setDensity(1000).setViscosity(2000);
        FluidRegistry.registerFluid(fluidMoonlight);
        fluidMoonlight = FluidRegistry.getFluid("moonlight");

    }

}
