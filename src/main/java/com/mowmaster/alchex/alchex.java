package com.mowmaster.alchex;


import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import com.mowmaster.alchex.proxies.CommonProxy;
import com.mowmaster.alchex.references.Reference;

import java.io.File;


@Mod(modid = Reference.MODID, name = Reference.MODNAME, version = Reference.VERSION)
public class alchex {
    @Mod.Instance(Reference.MODID)
    public static alchex instance;

    @SidedProxy(serverSide = Reference.SERVER_SIDE, clientSide = Reference.CLIENT_SIDE)
    public static CommonProxy proxy;


    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {

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
    public void postInit(FMLPostInitializationEvent event) {
    }

}

