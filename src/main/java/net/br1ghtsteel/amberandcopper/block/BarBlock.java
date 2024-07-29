package net.br1ghtsteel.amberandcopper.block;


import net.br1ghtsteel.amberandcopper.block.properties.BarShape;
import net.minecraft.block.*;
import net.minecraft.block.enums.BlockHalf;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.Property;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

import java.util.stream.IntStream;

public class BarBlock extends Block implements IConductorBlock {
    // public static final BooleanProperty POWERED = Properties.POWERED;
    public static final EnumProperty<Direction.Axis> AXIS;
    public static final EnumProperty<BarShape> BAR_SHAPE = EnumProperty.of("shape", BarShape.class);
    protected static final VoxelShape CORE_SHAPE = Block.createCuboidShape(3.0, 3.0, 3.0, 13.0, 13.0, 13.0);
    protected static final VoxelShape UP_SHAPE = Block.createCuboidShape(3.0, 13.0, 3.0, 13.0, 16.0, 13.0);
    protected static final VoxelShape DOWN_SHAPE = Block.createCuboidShape(3.0, 0.0, 3.0, 13.0, 3.0, 13.0);
    protected static final VoxelShape NORTH_SHAPE = Block.createCuboidShape(3.0, 3.0, 0.0, 13.0, 13.0, 3.0);
    protected static final VoxelShape EAST_SHAPE = Block.createCuboidShape(13.0, 3.0, 3.0, 16.0, 13.0, 13.0);
    protected static final VoxelShape SOUTH_SHAPE = Block.createCuboidShape(3.0, 3.0, 13.0, 13.0, 13.0, 16.0);
    protected static final VoxelShape WEST_SHAPE = Block.createCuboidShape(0.0, 3.0, 3.0, 3.0, 13.0, 13.0);

    protected static final VoxelShape[] DIRECTIONAL_SHAPE_PARTS = new VoxelShape[] { DOWN_SHAPE, UP_SHAPE, NORTH_SHAPE, SOUTH_SHAPE, WEST_SHAPE, EAST_SHAPE };
    protected static final VoxelShape[] BAR_SHAPES = composeShapes(CORE_SHAPE, UP_SHAPE, DOWN_SHAPE, NORTH_SHAPE, EAST_SHAPE, SOUTH_SHAPE, WEST_SHAPE);

    public BarBlock(AbstractBlock.Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(AXIS, Direction.Axis.Y).with(BAR_SHAPE, BarShape.STRAIGHT));
    }

    private static VoxelShape[] composeShapes(VoxelShape core, VoxelShape up, VoxelShape down, VoxelShape north, VoxelShape east, VoxelShape south, VoxelShape west) {
        return (VoxelShape[]) IntStream.range(0, 15).mapToObj(i -> composeShape(i, core, up, down, north, east, south, west)).toArray(VoxelShape[]::new);
    }

    private static VoxelShape composeShape(int i, VoxelShape core, VoxelShape up, VoxelShape down, VoxelShape north, VoxelShape east, VoxelShape south, VoxelShape west) {
        VoxelShape voxelShape = core;
        Direction.Axis barAxis = Direction.Axis.VALUES[i / 5];
        BarShape barShape = BarShape.VALUES[i % 5];

        // First connection direction
        int directionId = getFirstConnectedDirection(barAxis, barShape).getId();
        voxelShape = VoxelShapes.union(voxelShape, DIRECTIONAL_SHAPE_PARTS[directionId]);

        // Second connection direction
        directionId = getSecondConnectedDirection(barAxis, barShape).getId();
        voxelShape = VoxelShapes.union(voxelShape, DIRECTIONAL_SHAPE_PARTS[directionId]);

        return voxelShape;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return BAR_SHAPES[state.get(AXIS).ordinal() * 5 + state.get(BAR_SHAPE).getId()];
    }

    protected boolean hasConductionFromDirection(WorldAccess worldAccess, BlockPos pos, Direction direction) {
        BlockState otherState = worldAccess.getBlockState(pos.offset(direction));
        Block otherBlock = otherState.getBlock();
        return (otherBlock instanceof IConductorBlock)
                && ((IConductorBlock) otherBlock).conductsInDirection(otherState, direction.getOpposite());
    }

    protected static Direction.Axis getFirstCornerAxis(Direction.Axis barAxis) {
        return (barAxis == Direction.Axis.X) ? Direction.Axis.Y : Direction.Axis.X;
    }

    protected static Direction.Axis getSecondCornerAxis(Direction.Axis barAxis) {
        return (barAxis == Direction.Axis.Z) ? Direction.Axis.Y : Direction.Axis.Z;
    }

    protected static Direction getFirstConnectedDirection(Direction.Axis barAxis, BarShape barShape) {
        Direction.Axis axis = barShape == BarShape.STRAIGHT ? barAxis : getFirstCornerAxis(barAxis);
        return Direction.get(barShape.getFirstAxisDirection(), axis);
    }

    protected static Direction getSecondConnectedDirection(Direction.Axis barAxis, BarShape barShape) {
        Direction.Axis axis = barShape == BarShape.STRAIGHT ? barAxis : getSecondCornerAxis(barAxis);
        return Direction.get(barShape.getSecondAxisDirection(), axis);
    }

    protected static boolean isDirectionConnected(Direction.Axis barAxis, BarShape barShape, Direction direction) {
        if (barShape == BarShape.STRAIGHT || direction.getAxis() == barAxis) {
            return barShape == BarShape.STRAIGHT && direction.getAxis() == barAxis;
        }

        return (direction.getAxis() == getFirstCornerAxis(barAxis) ? barShape.getFirstAxisDirection() : barShape.getSecondAxisDirection())
                == direction.getDirection();
    }

    protected static Direction.Axis getPerpendicularAxis(Direction.Axis firstAxis, Direction.Axis secondAxis) {
        switch (firstAxis) {
            case X:
            default:
                return secondAxis == Direction.Axis.Y ? Direction.Axis.Z : Direction.Axis.Y;
            case Y:
                return secondAxis == Direction.Axis.X ? Direction.Axis.Z : Direction.Axis.X;
            case Z:
                return secondAxis == Direction.Axis.X ? Direction.Axis.Y : Direction.Axis.X;
        }
    }

    // REQUIRES: axes of firstDirection and secondDirection should come in proper X, Y, Z order based on their axes
    protected BlockState setBarShapeFromConnections(BlockState blockState, Direction firstDirection, Direction secondDirection) {
        if (firstDirection.getAxis() == secondDirection.getAxis()) {
            return blockState.with(AXIS, firstDirection.getAxis()).with(BAR_SHAPE, BarShape.STRAIGHT);
        }

        // Corner bar
        Direction.Axis axis = getPerpendicularAxis(firstDirection.getAxis(), secondDirection.getAxis());
        BarShape barShape = BarShape.getCornerFromAxisDirections(firstDirection.getDirection(), secondDirection.getDirection());
        return blockState.with(AXIS, axis).with(BAR_SHAPE, barShape);
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(new Property[]{AXIS, BAR_SHAPE});
    }

    @Override
    public boolean conductsInDirection(BlockState blockstate, Direction direction) {
        return isDirectionConnected(blockstate.get(AXIS), blockstate.get(BAR_SHAPE), direction);
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        Direction direction1 = ctx.getSide().getOpposite();
        BlockPos blockPos = ctx.getBlockPos();
        BlockState blockState = this.getDefaultState();
        // FluidState fluidState = ctx.getWorld().getFluidState(blockPos);

        // Check sides perpendicular to first direction
        Direction direction2 = null;
        for (Direction testDirection : Direction.values()) {
            if (testDirection.getAxis() == direction1.getAxis()) {
                continue;
            }

            if (hasConductionFromDirection(ctx.getWorld(), blockPos, testDirection)) {
                direction2 = testDirection;
                break;
            }
        }

        // No perpendicular connections
        if (direction2 == null) {
            return blockState.with(BAR_SHAPE, BarShape.STRAIGHT)
                    .with(AXIS, direction1.getAxis());
        }

        // Corner connection
        if (direction1.getAxis().ordinal() < direction2.getAxis().ordinal()) {
            return setBarShapeFromConnections(blockState, direction1, direction2);
        } else {
            return setBarShapeFromConnections(blockState, direction2, direction1);
        }
    }

    static {
        AXIS = Properties.AXIS;
    }
}

