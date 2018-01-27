package com.mowmaster.alchex.blocks.tiles;


import com.mowmaster.alchex.blocks.liquids.LiquidBasic;
import com.mowmaster.alchex.recipes.CollectorRecipes;
import com.mowmaster.alchex.recipes.ProcessorRecipes;
import com.sun.org.apache.xalan.internal.xslt.Process;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.ITickable;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

public class TileProcessor extends TileEntity implements ITickable
{

    public FluidTank tank = new FluidTank(4000);
    public ItemStack itemStackInput = ItemStack.EMPTY;
    public ItemStack itemStackOutput = ItemStack.EMPTY;
    public int itemToShrink = 0;
    public int liquidstored = tank.getFluidAmount();
    private int ammount=0;
    public boolean running=false;

    public ItemStack inputMaterials(ItemStack input)
    {
        return itemStackInput = ProcessorRecipes.instance().getProcessorInput(input);
    }
    public ItemStack outputItem()
    {
        return itemStackOutput = ProcessorRecipes.instance().getProcessorResult(itemStackInput, tank.getFluid());
    }


    public boolean onItemAdded(ItemStack input)
    {
        int itemToFillStack = 64 - itemStackInput.getCount();
        if(itemStackInput.isEmpty())
        {
            itemStackInput = new ItemStack(ProcessorRecipes.instance().getProcessorInput(input).getItem(),input.getCount(),input.getItemDamage());
            if(!itemStackInput.isEmpty())
            {
                itemToShrink=input.getCount();
                return true;
            }
        }
        else if(input.getItem() == itemStackInput.getItem() && input.getMetadata() == itemStackInput.getMetadata())
        {
            if(input.getCount()>itemToFillStack)
            {
                itemStackInput = new ItemStack(ProcessorRecipes.instance().getProcessorInput(input).getItem(),64,input.getItemDamage());
                itemToShrink = itemToFillStack;
                return true;
            }
            else
            {
                itemStackInput = new ItemStack(ProcessorRecipes.instance().getProcessorInput(input).getItem(),itemStackInput.getCount()+input.getCount(),input.getItemDamage());
                itemToShrink = input.getCount();
                return true;
            }
        }
        return false;
    }
    public ItemStack onItemRemoved()
    {
        ItemStack giveItems = itemStackInput;
        itemStackInput=ItemStack.EMPTY;
        return giveItems;
    }

    public boolean onFluidAdded(FluidStack input)
    {
        if(tank.getFluid()==null)
        {
            tank.fill(new FluidStack(input.getFluid(),1000),true);
        }
        return false;
    }




    @Override
    public void update()
    {
        int ticker=0;

        if(!itemStackInput.isEmpty())
        {
            ticker++;
        }

        if(ticker>=20)
        {
            ticker=0;
            outputItem();
        }
    }

/*
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);
        //ammount=tank.getFluidAmount();
        //compound.setInteger("ammount",ammount);
        //compound.setTag("item",itemStack.writeToNBT(new NBTTagCompound()));
        //NBTTagCompound tankTag = tank.writeToNBT(new NBTTagCompound());
        //compound.setTag("tank", tankTag);
        return compound;
    }



    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        //this.ammount=compound.getInteger("ammount");
        //NBTTagCompound itemTag = compound.getCompoundTag("item");
        //this.itemStack = new ItemStack(itemTag);
        //this.tank.readFromNBT(compound.getCompoundTag("tank"));
        //FluidStack thisTank = new FluidStack(tank.getFluid().getFluid(),ammount);
        //this.tank.setFluid(thisTank);
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
    */
}
