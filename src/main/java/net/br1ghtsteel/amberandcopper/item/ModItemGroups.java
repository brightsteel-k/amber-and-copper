package net.br1ghtsteel.amberandcopper.item;

import net.br1ghtsteel.amberandcopper.AmberAndCopper;
import net.br1ghtsteel.amberandcopper.block.ModBlocks;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroups {
    public static final ItemGroup AMBER_AND_COPPER = Registry.register(Registries.ITEM_GROUP,
            new Identifier(AmberAndCopper.MOD_ID, "amber_and_copper"),
            FabricItemGroup.builder().displayName(Text.translatable("itemgroup.amber_and_copper"))
                    .icon(() -> new ItemStack(Items.COPPER_INGOT))
                    .entries((displayContext, entries) -> {
                        entries.add(ModBlocks.COPPER_BAR);
                    }).build());

    public static void registerItemGroups() {
        AmberAndCopper.LOGGER.info("Registering Item Groups for: " + AmberAndCopper.MOD_ID);
    }
}
