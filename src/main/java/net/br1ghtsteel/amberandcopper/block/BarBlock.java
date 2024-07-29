package net.br1ghtsteel.amberandcopper.block;


import net.minecraft.block.*;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.Property;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldAccess;

public abstract class BarBlock extends Block implements IConductorBlock {
    public static final BooleanProperty POWERED = Properties.POWERED;

    public BarBlock(AbstractBlock.Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(POWERED, false));
    }

    protected boolean hasConductionFromDirection(WorldAccess worldAccess, BlockPos pos, Direction direction) {
        BlockState otherState = worldAccess.getBlockState(pos.offset(direction));
        return (otherState instanceof IConductorBlock)
                && ((IConductorBlock) otherState).conductsInDirection(otherState, direction.getOpposite());
    }

    protected Direction getDirectionFromAxis(Direction.Axis axis) {
        switch (axis) {
            case X:
            default:
                return Direction.EAST;
            case Y:
                return Direction.UP;
            case Z:
                return Direction.SOUTH;
        }
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(new Property[]{POWERED});
    }
}

