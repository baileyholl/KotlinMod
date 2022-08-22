package example.examplemod.block.tile

import example.examplemod.block.ITickable
import example.examplemod.block.ModBlocks
import net.minecraft.core.BlockPos
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState

class SeatTile (type: BlockEntityType<SeatTile>, pos:BlockPos, state:BlockState): BlockEntity(type, pos, state), ITickable {

    constructor(pos:BlockPos, state:BlockState): this(ModBlocks.SEAT_TILE, pos, state)

}