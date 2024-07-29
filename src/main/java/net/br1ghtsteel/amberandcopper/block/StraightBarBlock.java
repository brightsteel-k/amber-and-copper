package net.br1ghtsteel.amberandcopper.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.Property;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;

public class StraightBarBlock extends BarBlock {

    public static final EnumProperty<Direction.Axis> AXIS;
    protected static final VoxelShape X_SHAPE = Block.createCuboidShape(0.0, 3.0, 3.0, 16.0, 13.0, 13.0);
    protected static final VoxelShape Y_SHAPE = Block.createCuboidShape(3.0, 0.0, 3.0, 13.0, 16.0, 13.0);
    protected static final VoxelShape Z_SHAPE = Block.createCuboidShape(3.0, 3.0, 0.0, 13.0, 13.0, 16.0);

    public StraightBarBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(AXIS, Direction.Axis.Y));
    }

    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        switch (state.get(AXIS)) {
            case X:
                return X_SHAPE;
            case Y:
            default:
                return Y_SHAPE;
            case Z:
                return Z_SHAPE;
        }
    }

    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return changeRotation(state, rotation);
    }

    public static BlockState changeRotation(BlockState state, BlockRotation rotation) {
        switch (rotation) {
            case COUNTERCLOCKWISE_90:
            case CLOCKWISE_90:
                switch (state.get(AXIS)) {
                    case X:
                        return state.with(AXIS, Direction.Axis.Z);
                    case Z:
                        return state.with(AXIS, Direction.Axis.X);
                    default:
                        return state;
                }
            default:
                return state;
        }
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(new Property[]{AXIS});
    }

    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(AXIS, ctx.getSide().getAxis());
    }

    /*@Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (direction.getAxis() == state.get(AXIS)) {
            return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
        }

        if (neighborState.getBlock() == ModBlocks.COPPER_BAR_STRAIGHT && neighborState.get(AXIS) == direction.getAxis()) {
            Direction axisDirection = getDirectionFromAxis(state.get(AXIS));
            if (hasConductionFromDirection(world, pos, axisDirection)) {

            }
            // return ModBlocks.COPPER_BAR_CORNER.getCornerShape()
        }
    }*/

    @Override
    public boolean conductsInDirection(BlockState blockstate, Direction direction) {
        return blockstate.get(AXIS) == direction.getAxis();
    }

    static {
        AXIS = Properties.AXIS;
    }
}
