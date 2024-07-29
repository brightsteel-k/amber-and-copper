package net.br1ghtsteel.amberandcopper.item;

import net.br1ghtsteel.amberandcopper.AmberAndCopper;
import net.br1ghtsteel.amberandcopper.block.BarBlock;
import net.br1ghtsteel.amberandcopper.block.ModBlocks;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {

    /*public static final Item COPPER_BAR = registerItem("copper_bar",
            new BarBlockItem(ModBlocks.COPPER_BAR_STRAIGHT, ModBlocks.COPPER_BAR_CORNER, new FabricItemSettings()));*/

    private static void addItemsToIngredientItemGroup(FabricItemGroupEntries entries) {
        // entries.add(COPPER_BAR);
    }

    public static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(AmberAndCopper.MOD_ID, name), item);
    }

    public static void registerModItems() {
        AmberAndCopper.LOGGER.info("Registering Mod Items for: " + AmberAndCopper.MOD_ID);

        // ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(ModItems::addItemsToIngredientItemGroup);
    }
}
