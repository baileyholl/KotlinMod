package example.examplemod.block.tile

import example.examplemod.block.ITickable
import example.examplemod.block.ModBlocks
import net.minecraft.core.BlockPos
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState

class CashRegisterTile (type: BlockEntityType<CashRegisterTile>, pos:BlockPos, state:BlockState): BlockEntity(type, pos, state), ITickable {

    constructor(pos:BlockPos, state:BlockState): this(ModBlocks.CASH_REGISTER_TILE, pos, state)

}