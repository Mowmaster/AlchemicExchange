package com.mowmaster.alchex.recipes;

import com.google.common.collect.Maps;
import com.mowmaster.alchex.blocks.liquids.LiquidBasic;
import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import java.util.Map;


public class CollectorRecipes
{


    private static final CollectorRecipes COLLECTOR_BASE = new CollectorRecipes();
    /** The list of collector results. (Input item, Output Fluid) */
    private final Map<ItemStack, FluidStack> collectorList = Maps.<ItemStack, FluidStack>newHashMap();

    /**
     * Returns an instance of CollectorRecipes.
     */
    public static CollectorRecipes instance()
    {
        return COLLECTOR_BASE;
    }


    private CollectorRecipes()
    {
        //this.addCollectorRecipeForItem(Items.NETHERBRICK, new FluidStack(FluidRegistry.LAVA,1));
        //this.addCollectorRecipeForItem(Items.APPLE, new FluidStack(FluidRegistry.WATER,1));
        this.addCollectorRecipeForItem(Items.GLOWSTONE_DUST, new FluidStack(LiquidBasic.fluidSunlight,1));
        this.addCollectorRecipeForItem(Items.QUARTZ, new FluidStack(LiquidBasic.fluidMoonlight,1));
    }



    /**
     * Adds a smelting recipe, where the input item is an instance of Block.
     */
    public void addCollectorRecipeForBlock(Block input, FluidStack fluidStack)
    {
        this.addCollectorRecipeForItem(Item.getItemFromBlock(input), fluidStack);
    }

    /**
     * Adds a smelting recipe using an Item as the input item.
     */
    public void addCollectorRecipeForItem(Item input, FluidStack fluidStack)
    {
        this.addCollectorRecipe(new ItemStack(input, 1, 32767), fluidStack);
    }

    /**
     * Adds a collector recipe using an ItemStack as the input for the recipe.
     */
    public void addCollectorRecipe(ItemStack input, FluidStack fluidStack)
    {
        if (getCollectorInput(input) != ItemStack.EMPTY) { net.minecraftforge.fml.common.FMLLog.log.info("Ignored smelting recipe with conflicting input: {} = {}", input, fluidStack); return; }
        this.collectorList.put(input, fluidStack);

    }


    public ItemStack getCollectorInput(ItemStack stack)
    {
        for (Map.Entry<ItemStack, FluidStack> entry : this.collectorList.entrySet())
        {
            if (this.compareItemStacks(stack, entry.getKey()))
            {
                return entry.getKey();
            }
        }

        return ItemStack.EMPTY;
    }

    /**
     * Returns the collector result of an liquid.
     */
    public FluidStack getCollectorResult(ItemStack itemStack)
    {
        for (Map.Entry<ItemStack, FluidStack> entry : this.collectorList.entrySet())
        {
            if (this.compareItemStacks(itemStack, entry.getKey()))
            {
                return entry.getValue();
            }
        }

        return null;
    }

    /**
     * Compares two itemstacks to ensure that they are the same. This checks both the item and the metadata of the item.
     */
    private boolean compareItemStacks(ItemStack stack1, ItemStack stack2)
    {
        return stack2.getItem() == stack1.getItem() && (stack2.getMetadata() == 32767 || stack2.getMetadata() == stack1.getMetadata());
    }

    public Map<ItemStack, FluidStack> getCollectorList()
    {
        return this.collectorList;
    }
}