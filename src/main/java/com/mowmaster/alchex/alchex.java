package com.mowmaster.alchex;


import com.mowmaster.alchex.blocks.BlockRegistry;
import com.mowmaster.alchex.blocks.liquids.LiquidBasic;
import com.mowmaster.alchex.blocks.tiles.TileRegister;
import com.mowmaster.alchex.recipes.CollectorRecipes;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import com.mowmaster.alchex.proxies.CommonProxy;
import com.mowmaster.alchex.references.Reference;


@Mod(modid = Reference.MODID, name = Reference.MODNAME, version = Reference.VERSION)
public class alchex {
    @Mod.Instance(Reference.MODID)
    public static alchex instance;

    @SidedProxy(serverSide = Reference.SERVER_SIDE, clientSide = Reference.CLIENT_SIDE)
    public static CommonProxy proxy;

    static {
        FluidRegistry.enableUniversalBucket();
    }


    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {

        LiquidBasic.regFluids();
        LiquidBasic.regFluidBlocks();
        BlockRegistry.init();
        BlockRegistry.register();
        TileRegister.registerTile();

        proxy.PreInit();
        proxy.registerTile();

    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init();
        proxy.registerModelBakeryVarients();
        MinecraftForge.EVENT_BUS.register(this);

    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {

    }

}

