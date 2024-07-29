package net.br1ghtsteel.amberandcopper.block;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.Direction;

public interface IConductorBlock {
    boolean conductsInDirection(BlockState blockstate, Direction direction);
}
