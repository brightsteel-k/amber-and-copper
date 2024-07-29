package net.br1ghtsteel.amberandcopper.block;

import net.br1ghtsteel.amberandcopper.AmberAndCopper;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlocks {

    public static final Block COPPER_BAR_STRAIGHT = registerBlock("copper_bar_straight",
            new CopperStraightBarBlock(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK)),
            false);
    public static final Block COPPER_BAR_CORNER = registerBlock("copper_bar_corner",
            new CopperCornerBarBlock(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK)),
            false);

    public static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, new Identifier(AmberAndCopper.MOD_ID, name), block);
    }

    public static Block registerBlock(String name, Block block, boolean shouldMakeBlockItem) {
        if (shouldMakeBlockItem) {
            registerBlockItem(name, block);
        }
        return Registry.register(Registries.BLOCK, new Identifier(AmberAndCopper.MOD_ID, name), block);
    }

    public static Item registerBlockItem(String name, Block block) {
        return Registry.register(Registries.ITEM, new Identifier(AmberAndCopper.MOD_ID, name),
                new BlockItem(block, new FabricItemSettings()));
    }

    public static void registerModBlocks() {
        AmberAndCopper.LOGGER.info("Registering Mod Blocks for: " + AmberAndCopper.MOD_ID);
    }
}
