package com.mowmaster.alchex.blocks;

import com.mowmaster.alchex.blocks.tiles.TileCollector;
import com.mowmaster.alchex.references.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCauldron;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.event.entity.player.FillBucketEvent;
import net.minecraftforge.fluids.*;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.fluids.capability.wrappers.FluidBucketWrapper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.wrapper.InvWrapper;

import javax.annotation.Nonnull;
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

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
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
                    if(playerIn.isSneaking())
                    {
                        if(tileCollector.removeItem())
                        {
                            playerIn.inventory.addItemStackToInventory(new ItemStack(tileCollector.itemStack.getItem(),1));
                        }


                    }
                    System.out.println("Fluid Ammount: " + tileCollector.tank.getFluidAmount());
                    System.out.println("Item Durability: " + tileCollector.durability);

                }
                else if(tileCollector.tank.getFluidAmount()>=1000 && playerIn.getHeldItem(hand).getItem().equals(Items.BUCKET))
                {
                    //ItemStack heldItem = playerIn.getHeldItem(hand);
                    //FluidActionResult far = FluidUtil.tryFillContainerAndStow(heldItem, tileCollector.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, EnumFacing.DOWN), new InvWrapper(playerIn.inventory), 1000, playerIn);

                    ItemStack bucket = FluidUtil.getFilledBucket(new FluidStack(tileCollector.getLiquidOutput(tileCollector.itemStack),Fluid.BUCKET_VOLUME));
                    playerIn.getHeldItem(hand).shrink(1);
                    if (playerIn.getHeldItem(hand).isEmpty())
                    {


                            playerIn.setHeldItem(hand, bucket);
                            tileCollector.removeFluid();

                    }
                    else if (!playerIn.getHeldItem(hand).isEmpty())
                    {
                        //if(far.isSuccess()) {
                        playerIn.inventory.addItemStackToInventory(bucket);
                        tileCollector.removeFluid();
                        //}

                    }
                    else if (!playerIn.inventory.addItemStackToInventory(bucket))
                    {
                        playerIn.dropItem(bucket, false);
                        tileCollector.removeFluid();
                    }
                }
                else if(tileCollector.tank.getFluidAmount()==0)
                {
                    if(tileCollector.addItem(playerIn.getHeldItem(hand)))
                    {
                        playerIn.getHeldItem(hand).shrink(1);
                    }
                }
                else if(tileCollector.areFluidsEqual(playerIn.getHeldItem(hand)))
                {
                    if(tileCollector.addItem(playerIn.getHeldItem(hand)))
                    {
                        playerIn.getHeldItem(hand).shrink(1);
                    }
                }
            }
        }
        return false;
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
