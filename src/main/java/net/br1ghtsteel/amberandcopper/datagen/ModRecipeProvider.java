package net.br1ghtsteel.amberandcopper.datagen;

import net.br1ghtsteel.amberandcopper.block.ModBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.Registries;

import java.util.function.Consumer;

public class ModRecipeProvider extends FabricRecipeProvider {

    public ModRecipeProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generate(Consumer<RecipeJsonProvider> exporter) {

        /*ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.COPPER_BAR, 2)
                .pattern("C")
                .pattern("C")
                .pattern("C")
                .input('C', Items.COPPER_INGOT)
                .criterion(hasItem(Items.COPPER_INGOT), conditionsFromItem(Items.COPPER_INGOT))
                .offerTo(exporter, Registries.BLOCK.getId(ModBlocks.COPPER_BAR));*/

    }
}
