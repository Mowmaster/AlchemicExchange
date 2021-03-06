package com.mowmaster.alchex.recipes;

import com.google.common.collect.Maps;
import com.mowmaster.alchex.recipes.hashmaps.ProcessorHash;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import java.util.Map;

import static com.mowmaster.alchex.blocks.liquids.LiquidBasic.fluidMoonlight;
import static com.mowmaster.alchex.blocks.liquids.LiquidBasic.fluidSunlight;


public class ProcessorRecipes
{
    private static final ProcessorRecipes PROCESSOR_BASE = new ProcessorRecipes();
    /** The list of processor results. (input item, input fluid,output item) */
    private final Map<ItemStack, ProcessorHash> processorList = Maps.<ItemStack, ProcessorHash>newHashMap();



    /**
     * Returns an instance of ProcessorRecipes.
     */
    public static ProcessorRecipes instance()
    {
        return PROCESSOR_BASE;
    }


    private ProcessorRecipes()
    {
        this.addProcessorRecipe(new ItemStack(Blocks.STONE,1,0),new FluidStack(fluidMoonlight,10),new ItemStack(Blocks.COBBLESTONE,1,0));
        this.addProcessorRecipe(new ItemStack(Blocks.STONE,1,1),new FluidStack(fluidMoonlight,10),new ItemStack(Blocks.COBBLESTONE,1,0));
        this.addProcessorRecipe(new ItemStack(Blocks.STONE,1,3),new FluidStack(fluidMoonlight,10),new ItemStack(Blocks.COBBLESTONE,1,0));
        this.addProcessorRecipe(new ItemStack(Blocks.STONE,1,5),new FluidStack(fluidMoonlight,10),new ItemStack(Blocks.COBBLESTONE,1,0));
        this.addProcessorRecipe(new ItemStack(Blocks.STONE,1,2),new FluidStack(fluidMoonlight,10),new ItemStack(Blocks.STONE,1,0));
        this.addProcessorRecipe(new ItemStack(Blocks.STONE,1,4),new FluidStack(fluidMoonlight,10),new ItemStack(Blocks.STONE,1,0));
        this.addProcessorRecipe(new ItemStack(Blocks.STONE,1,6),new FluidStack(fluidMoonlight,10),new ItemStack(Blocks.STONE,1,0));
        this.addProcessorRecipe(new ItemStack(Blocks.COBBLESTONE,1,0),new FluidStack(fluidMoonlight,10),new ItemStack(Blocks.GRAVEL,1,0));
        this.addProcessorRecipe(new ItemStack(Blocks.GRAVEL,1,0),new FluidStack(fluidMoonlight,10),new ItemStack(Blocks.SAND,1,0));
        this.addProcessorRecipe(new ItemStack(Blocks.NETHERRACK,1,0),new FluidStack(fluidMoonlight,10),new ItemStack(Blocks.SOUL_SAND,1,0));

        this.addProcessorRecipe(new ItemStack(Blocks.LOG,1,0),new FluidStack(fluidMoonlight,20),new ItemStack(Blocks.PLANKS,5,0));
        this.addProcessorRecipe(new ItemStack(Blocks.LOG,1,1),new FluidStack(fluidMoonlight,20),new ItemStack(Blocks.PLANKS,5,1));
        this.addProcessorRecipe(new ItemStack(Blocks.LOG,1,2),new FluidStack(fluidMoonlight,20),new ItemStack(Blocks.PLANKS,5,2));
        this.addProcessorRecipe(new ItemStack(Blocks.LOG,1,3),new FluidStack(fluidMoonlight,20),new ItemStack(Blocks.PLANKS,5,3));
        this.addProcessorRecipe(new ItemStack(Blocks.LOG2,1,0),new FluidStack(fluidMoonlight,20),new ItemStack(Blocks.PLANKS,5,4));
        this.addProcessorRecipe(new ItemStack(Blocks.LOG2,1,1),new FluidStack(fluidMoonlight,20),new ItemStack(Blocks.PLANKS,5,5));
        this.addProcessorRecipe(new ItemStack(Blocks.PLANKS,1,0),new FluidStack(fluidMoonlight,20),new ItemStack(Items.STICK,3,0));
        this.addProcessorRecipe(new ItemStack(Blocks.PLANKS,1,1),new FluidStack(fluidMoonlight,20),new ItemStack(Items.STICK,3,0));
        this.addProcessorRecipe(new ItemStack(Blocks.PLANKS,1,2),new FluidStack(fluidMoonlight,20),new ItemStack(Items.STICK,3,0));
        this.addProcessorRecipe(new ItemStack(Blocks.PLANKS,1,3),new FluidStack(fluidMoonlight,20),new ItemStack(Items.STICK,3,0));
        this.addProcessorRecipe(new ItemStack(Blocks.PLANKS,1,4),new FluidStack(fluidMoonlight,20),new ItemStack(Items.STICK,3,0));
        this.addProcessorRecipe(new ItemStack(Blocks.PLANKS,1,5),new FluidStack(fluidMoonlight,20),new ItemStack(Items.STICK,3,0));



        this.addProcessorRecipe(new ItemStack(Items.WHEAT_SEEDS,1),new FluidStack(fluidSunlight,25),new ItemStack(Items.WHEAT,1));
        this.addProcessorRecipe(new ItemStack(Items.BEETROOT_SEEDS,1),new FluidStack(fluidSunlight,25),new ItemStack(Items.BEETROOT,1));
        this.addProcessorRecipe(new ItemStack(Items.MELON_SEEDS,1),new FluidStack(fluidSunlight,50),new ItemStack(Blocks.MELON_BLOCK,1));
        this.addProcessorRecipe(new ItemStack(Items.PUMPKIN_SEEDS,1),new FluidStack(fluidSunlight,50),new ItemStack(Items.PUMPKIN_SEEDS,1));
        this.addProcessorRecipe(new ItemStack(Items.POISONOUS_POTATO,1),new FluidStack(fluidSunlight,25),new ItemStack(Items.POTATO,1));
        this.addProcessorRecipe(new ItemStack(Items.POTATO,1),new FluidStack(fluidSunlight,25),new ItemStack(Items.POTATO,2));
        this.addProcessorRecipe(new ItemStack(Items.CARROT,1),new FluidStack(fluidSunlight,25),new ItemStack(Items.CARROT,2));

        this.addProcessorRecipe(new ItemStack(Blocks.SAPLING,1,0),new FluidStack(fluidSunlight,50),new ItemStack(Blocks.LOG,2,0));
        this.addProcessorRecipe(new ItemStack(Blocks.SAPLING,1,1),new FluidStack(fluidSunlight,50),new ItemStack(Blocks.LOG,2,1));
        this.addProcessorRecipe(new ItemStack(Blocks.SAPLING,1,2),new FluidStack(fluidSunlight,50),new ItemStack(Blocks.LOG,2,2));
        this.addProcessorRecipe(new ItemStack(Blocks.SAPLING,1,3),new FluidStack(fluidSunlight,50),new ItemStack(Blocks.LOG,2,3));
        this.addProcessorRecipe(new ItemStack(Blocks.SAPLING,1,4),new FluidStack(fluidSunlight,50),new ItemStack(Blocks.LOG2,2,0));
        this.addProcessorRecipe(new ItemStack(Blocks.SAPLING,1,5),new FluidStack(fluidSunlight,50),new ItemStack(Blocks.LOG2,2,1));

        this.addProcessorRecipe(new ItemStack(Items.LEATHER,1),new FluidStack(fluidSunlight,50),new ItemStack(Items.BEEF,1));
        this.addProcessorRecipe(new ItemStack(Blocks.WOOL,1),new FluidStack(fluidSunlight,100),new ItemStack(Items.MUTTON,1));
        this.addProcessorRecipe(new ItemStack(Items.FEATHER,1),new FluidStack(fluidSunlight,50),new ItemStack(Items.CHICKEN,1,0));
        this.addProcessorRecipe(new ItemStack(Items.RABBIT_FOOT,1),new FluidStack(fluidSunlight,50),new ItemStack(Items.RABBIT,2,0));
        this.addProcessorRecipe(new ItemStack(Items.RABBIT_HIDE,1),new FluidStack(fluidSunlight,50),new ItemStack(Items.RABBIT,1,0));



        this.addProcessorRecipe(new ItemStack(Items.IRON_INGOT,1),new FluidStack(fluidSunlight,4000),new ItemStack(Items.GOLD_INGOT,1).setStackDisplayName("Sunlight Ingot"));
        this.addProcessorRecipe(new ItemStack(Items.IRON_SHOVEL,1),new FluidStack(fluidSunlight,4000),new ItemStack(Items.GOLDEN_SHOVEL,1).setStackDisplayName("Sunlight Shovel"));
        this.addProcessorRecipe(new ItemStack(Items.IRON_HOE,1),new FluidStack(fluidSunlight,4000),new ItemStack(Items.GOLDEN_HOE,1).setStackDisplayName("Sunlight Hoe"));
        this.addProcessorRecipe(new ItemStack(Items.IRON_AXE,1),new FluidStack(fluidSunlight,4000),new ItemStack(Items.GOLDEN_AXE,1).setStackDisplayName("Sunlight Axe"));
        this.addProcessorRecipe(new ItemStack(Items.IRON_PICKAXE,1),new FluidStack(fluidSunlight,4000),new ItemStack(Items.GOLDEN_PICKAXE,1).setStackDisplayName("Sunlight Pickaxe"));
        this.addProcessorRecipe(new ItemStack(Items.IRON_SWORD,1),new FluidStack(fluidSunlight,4000),new ItemStack(Items.GOLDEN_SWORD,1).setStackDisplayName("Dawnblade"));//Nerosro's idea
        this.addProcessorRecipe(new ItemStack(Items.IRON_HELMET,1),new FluidStack(fluidSunlight,4000),new ItemStack(Items.GOLDEN_HELMET,1).setStackDisplayName("Sunlight Helmet"));
        this.addProcessorRecipe(new ItemStack(Items.IRON_CHESTPLATE,1),new FluidStack(fluidSunlight,4000),new ItemStack(Items.GOLDEN_CHESTPLATE,1).setStackDisplayName("Sunlight Chestplate"));
        this.addProcessorRecipe(new ItemStack(Items.IRON_LEGGINGS,1),new FluidStack(fluidSunlight,4000),new ItemStack(Items.GOLDEN_LEGGINGS,1).setStackDisplayName("Sunlight Leggings"));
        this.addProcessorRecipe(new ItemStack(Items.IRON_BOOTS,1),new FluidStack(fluidSunlight,4000),new ItemStack(Items.GOLDEN_BOOTS,1).setStackDisplayName("Sunlight Boots"));
    }



    /**
     * Adds a smelting recipe, where the input item is an instance of Block.
     */
    public void addProcessorRecipeForBlock(Block input, FluidStack fluidInput, ItemStack output)
    {
        this.addProcessorRecipeForItem(Item.getItemFromBlock(input), fluidInput, output);
    }

    /**
     * Adds a smelting recipe using an Item as the input item.
     */
    public void addProcessorRecipeForItem(Item input, FluidStack fluidInput,ItemStack output)
    {
        this.addProcessorRecipe(new ItemStack(input, 1, 32767), fluidInput,output);
    }

    /**
     * Adds a processor recipe using an ItemStack as the input for the recipe.
     */
    public void addProcessorRecipe(ItemStack input, FluidStack fluidInput, ItemStack output)
    {
        if (getProcessorInput(input) != ItemStack.EMPTY) { net.minecraftforge.fml.common.FMLLog.log.info("Ignored smelting recipe with conflicting input: {} = {}", input, fluidInput,output); return; }
        this.processorList.put(input,new ProcessorHash(input,fluidInput,output));

    }


    public ItemStack getProcessorInput(ItemStack stack)
    {
        for (Map.Entry<ItemStack, ProcessorHash> entry : this.processorList.entrySet())
        {
            if (this.compareItemStacks(stack, entry.getValue().getInput()))
            {
                return entry.getValue().getInput();
            }
        }

        return ItemStack.EMPTY;
    }

    public FluidStack getProcessorInputFluidFromInputItem(ItemStack stack)
    {
        for (Map.Entry<ItemStack, ProcessorHash> entry : this.processorList.entrySet())
        {
            if (this.compareItemStacks(stack, entry.getValue().getInput()))
            {
                return entry.getValue().getFluidInput();
            }
        }

        return null;
    }

    public Boolean getProcessorInputFluid(FluidStack stack)
    {
        for (Map.Entry<ItemStack, ProcessorHash> entry : this.processorList.entrySet())
        {
            if (stack.isFluidEqual(entry.getValue().getFluidInput()))
            {
                return true;
            }
        }

        return false;
    }

    /**
     * Returns the processor result.
     */
    public ItemStack getProcessorResult(ItemStack itemStack, FluidStack fluidStack)
    {
        for (Map.Entry<ItemStack, ProcessorHash> entry : this.processorList.entrySet())
        {
            if (compareItemStacks(itemStack, entry.getKey()) && fluidStack.isFluidEqual(entry.getValue().getFluidInput()))
            {
                return entry.getValue().getOutput();
            }
        }

        return ItemStack.EMPTY;
    }

    /**
     * Compares two itemstacks to ensure that they are the same. This checks both the item and the metadata of the item.
     */
    private boolean compareItemStacks(ItemStack stack1, ItemStack stack2)
    {
        return stack2.getItem() == stack1.getItem() && (stack2.getMetadata() == 32767 || stack2.getMetadata() == stack1.getMetadata());
    }
}