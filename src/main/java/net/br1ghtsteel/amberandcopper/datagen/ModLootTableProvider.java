package net.br1ghtsteel.amberandcopper.datagen;

import net.br1ghtsteel.amberandcopper.block.ModBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;

public class ModLootTableProvider extends FabricBlockLootTableProvider {
    public ModLootTableProvider(FabricDataOutput dataOutput) {
        super(dataOutput);
    }

    @Override
    public void generate() {
        // addDrop(ModBlocks.COPPER_BAR_STRAIGHT);
    }
}
