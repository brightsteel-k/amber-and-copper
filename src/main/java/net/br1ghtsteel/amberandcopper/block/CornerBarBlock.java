package net.br1ghtsteel.amberandcopper.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.enums.BlockHalf;
import net.minecraft.block.enums.StairShape;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.Property;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

import java.util.stream.IntStream;

public class CornerBarBlock extends BarBlock {

    public static final DirectionProperty HORIZONTAL_FACING = DirectionProperty.of("facing", Direction.Type.HORIZONTAL);
    public static final BooleanProperty UPWARD_FACING = BooleanProperty.of("up");
    protected static final VoxelShape UP_SHAPE = Block.createCuboidShape(3.0, 3.0, 3.0, 13.0, 16.0, 13.0);
    protected static final VoxelShape DOWN_SHAPE = Block.createCuboidShape(3.0, 0.0, 3.0, 13.0, 13.0, 13.0);
    protected static final VoxelShape NORTH_SHAPE = Block.createCuboidShape(3.0, 3.0, 0.0, 13.0, 13.0, 3.0);
    protected static final VoxelShape EAST_SHAPE = Block.createCuboidShape(13.0, 3.0, 3.0, 16.0, 13.0, 13.0);
    protected static final VoxelShape SOUTH_SHAPE = Block.createCuboidShape(3.0, 3.0, 13.0, 13.0, 13.0, 16.0);
    protected static final VoxelShape WEST_SHAPE = Block.createCuboidShape(0.0, 3.0, 3.0, 3.0, 13.0, 13.0);

    protected static final VoxelShape[] BAR_SHAPES = composeShapes(UP_SHAPE, DOWN_SHAPE, NORTH_SHAPE, EAST_SHAPE, SOUTH_SHAPE, WEST_SHAPE);

    public CornerBarBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(HORIZONTAL_FACING, Direction.NORTH)
                .with(UPWARD_FACING, false));
    }

    private static VoxelShape[] composeShapes(VoxelShape up, VoxelShape down, VoxelShape north, VoxelShape east, VoxelShape south, VoxelShape west) {
        return (VoxelShape[]) IntStream.range(0, 8).mapToObj(i -> composeShape(i, up, down, north, east, south, west)).toArray(VoxelShape[]::new);
    }

    private static VoxelShape composeShape(int i, VoxelShape up, VoxelShape down, VoxelShape north, VoxelShape east, VoxelShape south, VoxelShape west) {
        VoxelShape voxelShape = ((i & 4) == 0) ? down : up;
        switch (i & 0x3) {
            case 0:
                VoxelShapes.union(voxelShape, south);
                break;
            case 1:
                VoxelShapes.union(voxelShape, west);
                break;
            case 2:
            default:
                VoxelShapes.union(voxelShape, north);
                break;
            case 3:
                VoxelShapes.union(voxelShape, east);
                break;
        }

        return voxelShape;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return BAR_SHAPES[(state.get(UPWARD_FACING) ? 4 : 0) + state.get(HORIZONTAL_FACING).getHorizontal()];
    }

    @Override
    public boolean conductsInDirection(BlockState blockstate, Direction direction) {
        if (blockstate.get(UPWARD_FACING)) {
            if (direction == Direction.UP) { return true; }
        } else {
            if (direction == Direction.DOWN) { return true; }
        }

        return blockstate.get(HORIZONTAL_FACING) == direction;
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(new Property[]{HORIZONTAL_FACING, UPWARD_FACING});
    }
}
