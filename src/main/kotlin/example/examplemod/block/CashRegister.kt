package example.examplemod.block

import example.examplemod.block.tile.CashRegisterTile
import net.minecraft.core.BlockPos
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState

class CashRegister(properties: Properties) : Block(properties), ITickableBlock {


    override fun newBlockEntity(pPos: BlockPos, pState: BlockState): BlockEntity {
        return CashRegisterTile(pPos, pState)
    }


}