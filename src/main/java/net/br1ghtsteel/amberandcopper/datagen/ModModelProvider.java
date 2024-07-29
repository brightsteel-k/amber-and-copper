package net.br1ghtsteel.amberandcopper.datagen;

import net.br1ghtsteel.amberandcopper.block.ModBlocks;
import net.br1ghtsteel.amberandcopper.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.data.client.*;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;

public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        registerCopperBar(blockStateModelGenerator);
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        // itemModelGenerator.register(ModItems.RAW_TIN, Models.GENERATED);
    }

    private void registerCopperBar(BlockStateModelGenerator blockStateModelGenerator) {
        // blockStateModelGenerator.registerAxisRotated(ModBlocks.COPPER_BAR, TexturedModel.END_FOR_TOP_CUBE_COLUMN, TexturedModel.END_FOR_TOP_CUBE_COLUMN_HORIZONTAL);
        // registerAxisRotated(Blocks.PURPUR_PILLAR, TexturedModel.END_FOR_TOP_CUBE_COLUMN, TexturedModel.END_FOR_TOP_CUBE_COLUMN_HORIZONTAL);
    }
}
