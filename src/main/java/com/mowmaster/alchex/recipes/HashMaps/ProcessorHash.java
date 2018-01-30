package com.mowmaster.alchex.recipes.HashMaps;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class ProcessorHash
{
    public ItemStack input;
    public FluidStack fluidInput;
    public ItemStack output;

    public ProcessorHash(ItemStack input, FluidStack fluidInput,ItemStack output)
    {
        this.input=input;
        this.fluidInput=fluidInput;
        this.output=output;
    }

    public ItemStack getInput()
    {
        return input;
    }

    public FluidStack getFluidInput()
    {
        return fluidInput;
    }

    public ItemStack getOutput()
    {
        return output;
    }

    @Override
    public String toString() {
        return "ProcessorHash [input=" + input + ", fluidInput=" + fluidInput
                + ", output=" + output + "]";
    }
}