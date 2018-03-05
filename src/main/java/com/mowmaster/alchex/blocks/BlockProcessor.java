package com.mowmaster.alchex.blocks;

import com.mowmaster.alchex.blocks.tiles.TileProcessor;
import com.mowmaster.alchex.recipes.ProcessorRecipes;
import com.mowmaster.alchex.references.Reference;
import net.minecraft.block.Block;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.UniversalBucket;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.Sys;

import javax.annotation.Nullable;
import java.util.Random;



public class BlockProcessor extends Block implements ITileEntityProvider
{
    public BlockProcessor(String unloc, String registryName, Material material)
    {
        super(material);
        this.setUnlocalizedName(unloc);
        this.setRegistryName(new ResourceLocation(Reference.MODID, registryName));
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

    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.CUTOUT;
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
            if (tileEntity instanceof TileProcessor) {
                TileProcessor tileProcessor = (TileProcessor) tileEntity;
                ItemStack input = playerIn.getHeldItem(hand);

                if(playerIn.getHeldItem(hand).getItem() instanceof ItemBucket || playerIn.getHeldItem(hand).getItem() instanceof UniversalBucket)
                {
                    if(input.getItem()!=Items.BUCKET && tileProcessor.onFluidInteract(input))
                    {
                        playerIn.getHeldItem(hand).shrink(1);
                        if (playerIn.getHeldItem(hand).isEmpty())
                        {
                            playerIn.setHeldItem(hand, tileProcessor.playerOutput);
                            return true;
                        }
                        else if (!playerIn.getHeldItem(hand).isEmpty())
                        {
                            playerIn.inventory.addItemStackToInventory(tileProcessor.playerOutput);
                            return true;
                        }
                        else if (!playerIn.inventory.addItemStackToInventory(tileProcessor.playerOutput))
                        {
                            playerIn.dropItem(tileProcessor.playerOutput, false);
                            return true;
                        }

                    }
                    else
                    {
                        tileProcessor.onFluidInteract(input);
                        playerIn.getHeldItem(hand).shrink(1);
                        if (playerIn.getHeldItem(hand).isEmpty())
                        {
                            playerIn.setHeldItem(hand, tileProcessor.playerOutput);
                            return true;
                        }
                        else if (!playerIn.getHeldItem(hand).isEmpty())
                        {
                            playerIn.inventory.addItemStackToInventory(tileProcessor.playerOutput);
                            return true;
                        }
                        else if (!playerIn.inventory.addItemStackToInventory(tileProcessor.playerOutput))
                        {
                            playerIn.dropItem(tileProcessor.playerOutput, false);
                            return true;
                        }
                    }
                }
                //if player is sneaking and have an empty hand they get the items out of the input
                else if(playerIn.isSneaking())
                {
                    if (playerIn.getHeldItem(hand).isEmpty())
                    {
                        playerIn.setHeldItem(hand, tileProcessor.onItemRemoved());
                        return true;
                    }

                }
                //if player has items in hand, check if they work in machine and if so input them
                else if (!playerIn.getHeldItem(hand).isEmpty() && facing.equals(EnumFacing.UP))
                {
                    if(tileProcessor.onItemAdded(input))
                    {
                        playerIn.getHeldItem(hand).shrink(tileProcessor.itemToShrink);
                    }
                }
                //if empty hand get all output items available
                else if(playerIn.getHeldItem(hand).isEmpty() && !facing.equals(EnumFacing.UP))
                {
                    playerIn.setHeldItem(hand, tileProcessor.onOutputRemoved());
                    return true;
                }
//Fluid Handling

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
        return new TileProcessor();
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileProcessor();
    }
}
