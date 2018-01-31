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
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
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


    public FluidStack getLiquidOutput(ItemStack itemStack)
    {
        return CollectorRecipes.instance().getCollectorResult(itemStack);
    }
    public ItemStack getItemInBlock(){return itemStack;}
    public boolean areFluidsEqual(ItemStack itemStack){return tank.getFluid().isFluidEqual(getLiquidOutput(itemStack));}

    public void updateBlock()
    {
        running=true;
        markDirty();
        IBlockState state = world.getBlockState(pos);
        world.notifyBlockUpdate(pos, state, state, 3);
    }

    private int itemWearValue()
    {
        Random rn = new Random();
        int durr = rn.nextInt(875)+125;//875
        return durr;
    }

    public boolean addItem(ItemStack itemname)
    {
        itemStack = CollectorRecipes.instance().getCollectorInput(itemname);
        if(!itemStack.isEmpty())
        {
            if(tank.getFluid()==null)
            {
                durability=itemWearValue();
                running=true;
                updateBlock();
                return true;
            }
            else if (tank.getFluid().equals(getLiquidOutput(itemStack)) && tank.canFill())
            {
                durability=itemWearValue();
                running=true;
                updateBlock();
                return true;
            }
            else
            {
                return false;
            }
        }
        return false;
    }

    public boolean removeItem()
    {
        if(!(this.areFluidsEqual(itemStack)))
        {
            itemStack=ItemStack.EMPTY;
            running=false;
            updateBlock();
            return true;
        }
        return false;
    }

    public boolean breakItem()
    {
        itemStack=ItemStack.EMPTY;
        running=false;
        updateBlock();
        return true;
    }

    public boolean removeFluid()
    {
        if(!itemStack.isEmpty())
        {
            tank.drain(new FluidStack(getLiquidOutput(itemStack).getFluid(),1000),true);
            running=true;
            updateBlock();
            return true;
        }
        else
        {
            tank.drain(new FluidStack(getLiquidOutput(itemStack).getFluid(),1000),true);
            tank.setFluid(null);
            running=false;
            updateBlock();
            return true;
        }
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
                if(running==true && !(tank.getFluidAmount()>=1000))
                {
                    if (durability>0)
                    {
                        if(world.canSeeSky(pos))
                        {

                            if(world.isDaytime())
                            {
                                if(itemStack.getItem().equals(Items.GLOWSTONE_DUST))
                                {
                                    ticker++;
                                    if(ticker>=fluidTickrate)
                                    {
                                        durability--;
                                        tank.fill(new FluidStack(getLiquidOutput(itemStack).getFluid(),getLiquidOutput(itemStack).amount),true);
                                        ticker=0;
                                        //System.out.println("GGGGGGLLLLOOOOOWWWWSSSSTTTOOONNNEEE");
                                        updateBlock();
                                    }
                                }
                            }
                            else if(!world.isDaytime())
                            {
                                if(itemStack.getItem().equals(Items.QUARTZ))
                                {
                                    ticker++;
                                    if(ticker>=fluidTickrate)
                                    {
                                        durability--;
                                        tank.fill(new FluidStack(getLiquidOutput(itemStack).getFluid(),getLiquidOutput(itemStack).amount),true);
                                        ticker=0;
                                        //System.out.println("QQQQQUUUUAAAAARRRRTTTTTTZZZZZ");
                                        updateBlock();
                                    }
                                }
                            }
                            else
                            {
                                if(itemStack.getItem()!=Items.QUARTZ || itemStack.getItem()!=Items.GLOWSTONE_DUST)
                                {
                                    ticker++;
                                    if(ticker>=fluidTickrate)
                                    {
                                        durability--;
                                        tank.fill(new FluidStack(getLiquidOutput(itemStack).getFluid(),getLiquidOutput(itemStack).amount),true);
                                        ticker=0;
                                        updateBlock();
                                    }
                                }
                            }

                        }
                    }
                    else {
                        breakItem();
                        updateBlock();
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
        if(ammount>0)
        {
            FluidStack thisTank = new FluidStack(tank.getFluid().getFluid(),ammount);
            this.tank.setFluid(thisTank);
        }

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
