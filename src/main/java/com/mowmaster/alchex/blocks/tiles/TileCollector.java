package com.mowmaster.alchex.blocks.tiles;

import com.mowmaster.alchex.recipes.CollectorRecipes;
import net.minecraft.block.BlockCauldron;
import net.minecraft.block.BlockFurnace;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityBrewingStand;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.ITickable;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;

import javax.annotation.Nullable;
import java.util.Random;


public class TileCollector extends TileEntity implements ITickable
{

    public final int maxliquid = 1000;
    public static int liquidstored;
    public int rando;//item wear value
    public final int timepassedmax=12;//ticks per mb
    public boolean running=false;
    public ItemStack itemNamed= ItemStack.EMPTY;
    public FluidStack fluidNamed;
    public ItemStack isItemUsed = CollectorRecipes.instance().getCollectorInput(itemNamed);
    public FluidStack fluidOutput = CollectorRecipes.instance().getCollectorResult(fluidNamed);



    public ItemStack getItemnamed(){return isItemUsed;}
    public FluidStack getFluid(){return fluidOutput;}
    public int getLiquidStored()
    {
        return liquidstored;
    }
    public int getItemWear()
    {
        return rando;
    }
    public boolean getOnStatus()
    {
        return running;
    }

    private int ticker=0;
    public void update()
    {

        if (!this.world.isRemote)
        {
            if(running==true)
            {
                if(ticker<=timepassedmax)
                {
                    ticker++;
                }
                else{ticker=0;addFluid();rando--;}
            }
            else{ticker=0;}

            if(rando<=0)
            {
                breakItem();
            }
        }

    }


    public boolean addItem(ItemStack itemname)
    {
        ItemStack itemStack = CollectorRecipes.instance().getCollectorInput(itemname);
        if(itemname.isItemEqual(itemStack))
        {
            itemNamed=itemname;
            Random rn = new Random();
            rando = rn.nextInt(875)+125;
            running=true;
            markDirty();
            IBlockState state = world.getBlockState(pos);
            world.notifyBlockUpdate(pos,state,state,3);
            return true;
        }
        return false;
    }

    public boolean addFluid()
    {

        if(world.isDaytime())
        {
            liquidstored++;
        }
        markDirty();
        IBlockState state = world.getBlockState(pos);
        world.notifyBlockUpdate(pos,state,state,3);
        return true;
    }

    public boolean breakItem()
    {
        running=false;
        rando = 0;
        markDirty();
        IBlockState state = world.getBlockState(pos);
        world.notifyBlockUpdate(pos,state,state,3);
        return true;
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);
        //compound.setString("itemname",itemname);
        compound.setInteger("liquidstored",liquidstored);
        compound.setInteger("rando",rando);
        compound.setBoolean("running",running);
        return compound;
    }



    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        //this.itemname = compound.getString("itemname");
        this.liquidstored = compound.getInteger("liquidstored");
        this.rando = compound.getInteger("rando");
        this.running = compound.getBoolean("running");
    }


    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
        super.onDataPacket(net, pkt);
        readFromNBT(pkt.getNbtCompound());
    }

    @Override
    public NBTTagCompound getUpdateTag() {
        return writeToNBT(new NBTTagCompound());
    }

    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        return new SPacketUpdateTileEntity(pos, 0, getUpdateTag());
    }






}
