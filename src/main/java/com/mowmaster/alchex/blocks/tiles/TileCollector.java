package com.mowmaster.alchex.blocks.tiles;

import com.mowmaster.alchex.recipes.CollectorRecipes;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

import java.util.Random;


public class TileCollector extends TileEntity implements ITickable
{

    public FluidTank tank = new FluidTank(1000);
    public ItemStack itemStack = ItemStack.EMPTY;
    public int liquidstored = tank.getFluidAmount();
    private int ammount=0;
    public boolean running=false;

    public final int fluidTickrate=6;//ticks per mb --- include in recipe handler??? ___ Default 12
    public int durability=0;


    public Fluid getLiquidOutput(ItemStack itemStack)
    {
        if(tank.getFluid()!=null)
        {
            return CollectorRecipes.instance().getCollectorResult(itemStack).getFluid();
        }
        return null;
    }
    public ItemStack getItemInBlock(){return itemStack;}
    public boolean areFluidsEqual(ItemStack itemStack){return tank.getFluid().getFluid()==getLiquidOutput(itemStack);}



    private int itemWearValue()
    {
        Random rn = new Random();
        int durr = rn.nextInt(875)+125;//875
        return durr;
    }

    public boolean addItem(ItemStack itemname)
    {
        itemStack = CollectorRecipes.instance().getCollectorInput(itemname);
        if(!itemStack.isEmpty() && tank.canFill() && running==false && tank.getFluidAmount()==0)
        {
            running=true;
            durability=itemWearValue();
            itemWearValue();
            return true;
        }
        else if (!itemStack.isEmpty() && tank.canFill() && running==false)
        {
            if(this.areFluidsEqual(itemStack))
            {
                running=true;
                durability=itemWearValue();
                return true;
            }
            return false;

        }
        return false;
    }

    public boolean removeItem()
    {
        if(!(this.areFluidsEqual(itemStack)))
        {
            //Return item from here and just check if stack is empty instead
            //worldIn.spawnEntity(new EntityItem(worldIn, pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5, itemStack));
            itemStack=ItemStack.EMPTY;
            running=false;
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
        if(!itemStack.isEmpty())
        {
            tank.drain(new FluidStack(getLiquidOutput(itemStack),1000),true);
            running=true;
            markDirty();
            IBlockState state = world.getBlockState(pos);
            world.notifyBlockUpdate(pos,state,state,3);
        }
        else
        {
            tank.drain(new FluidStack(getLiquidOutput(itemStack),1000),true);
            tank.setFluid(null);
            running=false;
        }
        markDirty();
        IBlockState state = world.getBlockState(pos);
        world.notifyBlockUpdate(pos,state,state,3);
        return true;
    }
    private int ticker=0;
    public void update()
    {
        if (tank.getFluidAmount() >= 1000) {
            world.spawnParticle(EnumParticleTypes.WATER_BUBBLE, pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5, 0.2, 0.2, 0.2, new int[0]);
        }
        else if (tank.getFluidAmount() < 1000 && running==true && ticker>0) {
            world.spawnParticle(EnumParticleTypes.WATER_SPLASH, pos.getX() + 0.5, pos.getY() + 0.9, pos.getZ() + 0.5, 0.2, 0.2, 0.2, new int[0]);
        }
        if (!this.world.isRemote)
        {
            if (itemStack.getItem().equals(Items.GLOWSTONE_DUST) && world.canSeeSky(pos) && world.isDaytime())
            {
                if(running==true && !(tank.getFluidAmount()>=1000))
                {
                    if (durability>0)
                    {
                        ticker++;
                        if(ticker>=fluidTickrate)
                        {

                            durability--;
                            tank.fill(new FluidStack(getLiquidOutput(itemStack),1),true);
                            ticker=0;
                            markDirty();
                            IBlockState state = world.getBlockState(pos);
                            world.notifyBlockUpdate(pos,state,state,3);
                        }
                    }
                    else {
                        breakItem();
                        markDirty();
                        IBlockState state = world.getBlockState(pos);
                        world.notifyBlockUpdate(pos,state,state,3);}
                }
            }
            else if(itemStack.getItem().equals(Items.QUARTZ) && world.canSeeSky(pos) && !(world.isDaytime()))
            {
                if(running==true && !(tank.getFluidAmount()>=1000))
                {
                    if (durability>0)
                    {
                        ticker++;
                        if(ticker>=fluidTickrate)
                        {

                            durability--;
                            tank.fill(new FluidStack(getLiquidOutput(itemStack),1),true);
                            ticker=0;
                            markDirty();
                            IBlockState state = world.getBlockState(pos);
                            world.notifyBlockUpdate(pos,state,state,3);
                        }
                    }
                    else {
                        breakItem();
                        markDirty();
                        IBlockState state = world.getBlockState(pos);
                        world.notifyBlockUpdate(pos,state,state,3);}
                }
            }
            else if(!itemStack.getItem().equals(Items.QUARTZ) || !itemStack.getItem().equals(Items.GLOWSTONE_DUST) && world.canSeeSky(pos)) {
                if (running == true && !(tank.getFluidAmount() >= 1000)) {
                    if (durability > 0) {
                        ticker++;
                        if (ticker >= fluidTickrate) {

                            durability--;
                            tank.fill(new FluidStack(getLiquidOutput(itemStack), 1), true);
                            ticker = 0;
                            markDirty();
                            IBlockState state = world.getBlockState(pos);
                            world.notifyBlockUpdate(pos, state, state, 3);
                        }
                    } else {
                        breakItem();
                        markDirty();
                        IBlockState state = world.getBlockState(pos);
                        world.notifyBlockUpdate(pos, state, state, 3);
                    }
                } else {
                    running = false;
                    markDirty();
                    IBlockState state = world.getBlockState(pos);
                    world.notifyBlockUpdate(pos, state, state, 3);
                }
            }

        }

    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);
        ammount=tank.getFluidAmount();
        compound.setInteger("ammount",ammount);
        compound.setTag("item",itemStack.writeToNBT(new NBTTagCompound()));
        compound.setInteger("liquidstored",liquidstored);
        compound.setInteger("durability",durability);
        compound.setBoolean("running",running);
        NBTTagCompound tankTag = tank.writeToNBT(new NBTTagCompound());
        compound.setTag("tank", tankTag);
        return compound;
    }



    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        this.ammount=compound.getInteger("ammount");
        NBTTagCompound itemTag = compound.getCompoundTag("item");
        this.itemStack = new ItemStack(itemTag);
        this.liquidstored = compound.getInteger("liquidstored");
        this.durability = compound.getInteger("durability");
        this.running = compound.getBoolean("running");
        this.tank.readFromNBT(compound.getCompoundTag("tank"));
        FluidStack thisTank = new FluidStack(tank.getFluid().getFluid(),ammount);
        this.tank.setFluid(thisTank);
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
