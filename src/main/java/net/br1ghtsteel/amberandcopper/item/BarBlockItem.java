package net.br1ghtsteel.amberandcopper.item;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;

public class BarBlockItem extends BlockItem {

    protected final Block cornerBarBlock;

    public BarBlockItem(Block straightBarBlock, Block cornerBarBlock, Settings settings) {
        super(straightBarBlock, settings);
        this.cornerBarBlock = cornerBarBlock;
    }

    @Override
    public String getTranslationKey() {
        return this.getOrCreateTranslationKey();
    }
}
