package com.mowmaster.alchex.blocks.tiles;

import com.mowmaster.alchex.recipes.CollectorRecipes;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;

import java.util.Random;


public class TileCollector extends TileEntity implements ITickable
{

    private ItemStack itemStack = ItemStack.EMPTY;
    public final int maxliquid = 1000;
    public static int liquidstored = 0;
    public int rando;//item wear value
    public final int timepassedmax=12;//ticks per mb
    public boolean running=false;

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



    public ItemStack getItemInBlock(){return itemStack;}
    public boolean addItem(ItemStack itemname)
    {
        itemStack = CollectorRecipes.instance().getCollectorInput(itemname);

            if(!(CollectorRecipes.instance().getCollectorInput(itemname).isEmpty())&&running==false)
            {
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

        if(world.canSeeSky(pos))
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
        compound.setTag("item",itemStack.writeToNBT(new NBTTagCompound()));
        compound.setInteger("liquidstored",liquidstored);
        compound.setInteger("rando",rando);
        compound.setBoolean("running",running);
        return compound;
    }



    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        NBTTagCompound itemTag = compound.getCompoundTag("item");
        this.itemStack = new ItemStack(itemTag);
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
