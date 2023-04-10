package example.examplemod.block.tile;

import example.examplemod.block.ITickable
import example.examplemod.block.ModBlocks
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class ManagementTile (type:BlockEntityType<ManagementTile>, pos:BlockPos, state:BlockState): BlockEntity(type, pos, state), ITickable {

        constructor(pos:BlockPos, state:BlockState): this(ModBlocks.MANAGEMENT_TILE, pos, state)

}