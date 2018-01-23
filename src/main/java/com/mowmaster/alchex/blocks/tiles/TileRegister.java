package com.mowmaster.alchex.blocks.tiles;

import com.mowmaster.alchex.references.Reference;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Created by KingMowmaster on 1/22/2018.
 */
public class TileRegister
{
    public static void registerTile()
    {
        GameRegistry.registerTileEntity(TileCollector.class, Reference.MODID + "tilecollector");
    }
}
