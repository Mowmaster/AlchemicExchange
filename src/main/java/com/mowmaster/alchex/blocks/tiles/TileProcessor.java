package com.mowmaster.alchex.blocks.tiles;


import com.mowmaster.alchex.recipes.ProcessorRecipes;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidUtil;

import static com.mowmaster.alchex.blocks.liquids.LiquidBasic.fluidSunlight;


public class TileProcessor extends TileEntity implements ITickable
{

    public FluidTank tank = new FluidTank(4000);
    public ItemStack itemStackInput = ItemStack.EMPTY;
    public ItemStack itemStackOutput = ItemStack.EMPTY;
    public boolean running=false;



    public ItemStack playerOutput=ItemStack.EMPTY;
    private int amount = 0;
    public int itemToShrink = 0;
    public int amountOfFluid=0;


    public ItemStack inputMaterials(ItemStack input)
    {
        return itemStackInput = ProcessorRecipes.instance().getProcessorInput(input);
    }
    public ItemStack outputItem()
    {
        return itemStackOutput = ProcessorRecipes.instance().getProcessorResult(itemStackInput, tank.getFluid());
    }

    public void updateBlock()
    {
        markDirty();
        IBlockState state = world.getBlockState(pos);
        world.notifyBlockUpdate(pos, state, state, 3);
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
                updateBlock();
                return true;
            }
        }
        else if(input.getItem() == itemStackInput.getItem() && input.getMetadata() == itemStackInput.getMetadata())
        {
            if(input.getCount()>itemToFillStack)
            {
                itemStackInput = new ItemStack(ProcessorRecipes.instance().getProcessorInput(input).getItem(),64,input.getItemDamage());
                itemToShrink = itemToFillStack;
                updateBlock();
                return true;
            }
            else
            {
                itemStackInput = new ItemStack(ProcessorRecipes.instance().getProcessorInput(input).getItem(),itemStackInput.getCount()+input.getCount(),input.getItemDamage());
                itemToShrink = input.getCount();
                updateBlock();
                return true;
            }

        }
        return false;
    }
    public ItemStack onItemRemoved()
    {
        ItemStack giveItems = itemStackInput;
        itemStackInput=ItemStack.EMPTY;
        updateBlock();
        return giveItems;
    }


    public boolean onFluidInteract(ItemStack input)
    {
        FluidStack inputFluid= FluidUtil.getFluidContained(input);

        if(FluidUtil.getFluidContained(input)!=null)
        {
            amountOfFluid=FluidUtil.getFluidContained(input).amount;
        }


        if(input.getItem().equals(Items.BUCKET))
        {
            if(tank.getFluidAmount()>Fluid.BUCKET_VOLUME)
            {
                tank.drain(new FluidStack(tank.getFluid(),Fluid.BUCKET_VOLUME),true);
                playerOutput = FluidUtil.getFilledBucket(new FluidStack(tank.getFluid().getFluid(),Fluid.BUCKET_VOLUME));
                world.playSound(null, pos, SoundEvents.ITEM_BUCKET_FILL, SoundCategory.BLOCKS, 0.3F, 0.5F);
                return true;
            }
            else if(tank.getFluidAmount()==Fluid.BUCKET_VOLUME)
            {
                playerOutput = FluidUtil.getFilledBucket(new FluidStack(tank.getFluid().getFluid(),Fluid.BUCKET_VOLUME));
                tank.setFluid(null);
                world.playSound(null, pos, SoundEvents.ITEM_BUCKET_FILL, SoundCategory.BLOCKS, 0.3F, 0.5F);
                return true;
            }

        }
        else if(ProcessorRecipes.instance().getProcessorInputFluid(inputFluid))
        {
                if(tank.getFluid()==null && inputFluid.getFluid()!=null)
                {
                    tank.fill(new FluidStack(inputFluid.getFluid(),amountOfFluid),true);
                    playerOutput = new ItemStack(Items.BUCKET);
                    world.playSound(null, pos, SoundEvents.ITEM_BUCKET_EMPTY, SoundCategory.BLOCKS, 0.3F, 0.5F);

                    return true;
                }
                else if(tank.getCapacity()-tank.getFluidAmount()>=amountOfFluid && tank.getFluid().isFluidEqual(input))
                {
                    tank.fill(new FluidStack(inputFluid.getFluid(),amountOfFluid),true);
                    playerOutput = new ItemStack(Items.BUCKET);
                    world.playSound(null, pos, SoundEvents.ITEM_BUCKET_EMPTY, SoundCategory.BLOCKS, 0.3F, 0.5F);

                    return true;
                }
                else if (tank.getCapacity()-tank.getFluidAmount()<amountOfFluid || !tank.canFill())
                {
                    return false;
                }
        }
        

        return false;
        //ItemStack bucket = FluidUtil.getFilledBucket(new FluidStack(tileCollector.getLiquidOutput(tileCollector.itemStack),Fluid.BUCKET_VOLUME));
    }

    public ItemStack onOutputRemoved()
    {
        if(!itemStackOutput.isEmpty())
        {
            playerOutput = itemStackOutput;

            return playerOutput;
        }
        return ItemStack.EMPTY;
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


    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);
        amount=tank.getFluidAmount();
        compound.setInteger("amount",amount);
        compound.setTag("itemInput",itemStackInput.writeToNBT(new NBTTagCompound()));
        compound.setTag("itemOutput",itemStackOutput.writeToNBT(new NBTTagCompound()));
        NBTTagCompound tankTag = tank.writeToNBT(new NBTTagCompound());
        compound.setTag("tank", tankTag);
        return compound;
    }



    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        this.amount=compound.getInteger("amount");
        NBTTagCompound itemTag = compound.getCompoundTag("itemInput");
        this.itemStackInput = new ItemStack(itemTag);
        NBTTagCompound itemTag2 = compound.getCompoundTag("itemOutput");
        this.itemStackOutput = new ItemStack(itemTag2);
        this.tank.readFromNBT(compound.getCompoundTag("tank"));
        if(amount>0)
        {
            FluidStack thisTank = new FluidStack(tank.getFluid().getFluid(),amount);
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
