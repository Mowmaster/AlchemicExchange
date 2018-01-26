package com.mowmaster.alchex.blocks.tiles;

import com.mowmaster.alchex.recipes.CollectorRecipes;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraftforge.client.event.sound.PlaySoundEvent;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

import java.util.Random;


public class TileCollector extends TileEntity implements ITickable
{

    public FluidTank tank = new FluidTank(1000);
    public ItemStack itemStack = ItemStack.EMPTY;
    public int ammount = 0;
    public int liquidstored = tank.getFluidAmount();
    public boolean running=false;

    public final int fluidTickrate=12;//ticks per mb --- include in recipe handler???
    public int durability=0;


    public Fluid getLiquidOutput(){return CollectorRecipes.instance().getCollectorResult(itemStack).getFluid();}
    public ItemStack getItemInBlock(){return itemStack;}
    public boolean areFluidsEqual(){return tank.getFluid().getFluid()==getLiquidOutput();}



    private int itemWearValue()
    {
        Random rn = new Random();
        int durr = rn.nextInt(875)+125;
        return durr;
    }

    public boolean addItem(ItemStack itemname)
    {
        itemStack = CollectorRecipes.instance().getCollectorInput(itemname);
        if(!itemStack.isEmpty() && tank.canFill() && running==false && tank.getFluidAmount()<=0)
        {
            running=true;
            durability=itemWearValue();
            itemWearValue();
            return true;
        }
        else if (!itemStack.isEmpty() && tank.canFill() && running==false && areFluidsEqual())
        {
            running=true;
            durability=itemWearValue();
            return true;
        }
        return false;
    }

    public boolean removeItem()
    {
        if(running=false)
        {
            itemStack=ItemStack.EMPTY;
            return true;
        }
        return false;
    }

    public boolean breakItem()
    {
        itemStack=ItemStack.EMPTY;
        running=false;
        return true;
    }

    public boolean removeFluid()
    {
        if(tank.getFluidAmount()>=tank.getCapacity())
        {
            tank.drain(new FluidStack(getLiquidOutput(),1000),true);
            return true;
        }
        return false;
    }
    private int ticker=0;
    public void update()
    {
        if (!this.world.isRemote)
        {
            if(running==true && durability>0)
            {
                ticker++;
                if(ticker>=fluidTickrate)
                {
                    ammount++;
                    durability--;
                    tank.fill(new FluidStack(getLiquidOutput(),ammount),true);
                    ticker=0;
                    markDirty();
                    IBlockState state = world.getBlockState(pos);
                    world.notifyBlockUpdate(pos,state,state,3);
                }
            }
            else {breakItem();}

        }

    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);
        compound.setTag("item",itemStack.writeToNBT(new NBTTagCompound()));
        compound.setInteger("liquidstored",liquidstored);
        compound.setInteger("durability",durability);
        compound.setInteger("ammount",ammount);
        compound.setBoolean("running",running);
        NBTTagCompound tankTag = tank.writeToNBT(new NBTTagCompound());
        compound.setTag("tank", tankTag);
        return compound;
    }



    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        NBTTagCompound itemTag = compound.getCompoundTag("item");
        this.itemStack = new ItemStack(itemTag);
        this.liquidstored = compound.getInteger("liquidstored");
        this.durability = compound.getInteger("durability");
        this.ammount = compound.getInteger("ammount");
        this.running = compound.getBoolean("running");
        tank.readFromNBT(compound.getCompoundTag("tank"));
        tank.setFluid(new FluidStack(tank.getFluid().getFluid(),ammount));
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
