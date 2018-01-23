package com.mowmaster.alchex.blocks;

import com.mowmaster.alchex.blocks.tiles.TileCollector;
import com.mowmaster.alchex.references.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.Random;


public class BlockCollector extends Block implements ITileEntityProvider
{
    public BlockCollector(String unloc, String registryName, Material material)
    {
        super(material);
        this.setUnlocalizedName(unloc);
        this.setRegistryName(new ResourceLocation(Reference.MODID, registryName));
    }

    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    public boolean isTopSolid(IBlockState state) {
        return false;
    }

    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
            return new AxisAlignedBB(0.0625D, 0.0D, 0.0625D, 0.9375D, 1.0D, 0.9375D);
    }


    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this);
    }



    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        return this.getDefaultState();
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        IBlockState state = this.getDefaultState();
        return state;
    }



    public boolean canPlaceBlockAt(World worldIn, BlockPos pos)
    {
        return true;
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if(!worldIn.isRemote)
        {
            TileEntity tileEntity = worldIn.getTileEntity(pos);
            if (tileEntity instanceof TileCollector) {
                TileCollector tileCollector = (TileCollector) tileEntity;




                if (playerIn.getHeldItem(hand).isEmpty())
                {
                    System.out.println(tileCollector.getItemInBlock().getDisplayName());
                    System.out.println(tileCollector.getItemWear());
                    System.out.println(tileCollector.getLiquidStored());
                    System.out.println(tileCollector.getOnStatus());
                }
                else
                {
                    ItemStack itemInHand = playerIn.getHeldItem(hand);
                    if(tileCollector.addItem(itemInHand))
                    {
                        itemInHand.shrink(1);
                    }

                }


            }
        }
        return true;
    }

    @Override
    public Item getItemDropped(IBlockState state, Random random, int fortune)
    {
        return Item.getItemFromBlock(this);
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileCollector();
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileCollector();
    }

}
