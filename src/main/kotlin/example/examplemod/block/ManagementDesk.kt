package example.examplemod.block

import example.examplemod.api.CafeData
import example.examplemod.api.CafeManager
import example.examplemod.block.tile.ManagementTile
import net.minecraft.core.BlockPos
import net.minecraft.server.level.ServerLevel
import net.minecraft.util.RandomSource
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState

class ManagementDesk (properties: Properties) : Block(properties), ITickableBlock {


    override fun newBlockEntity(pPos: BlockPos, pState: BlockState): BlockEntity {
        return ManagementTile(pPos, pState)
    }

    override fun tick(pState: BlockState, pLevel: ServerLevel, pPos: BlockPos, pRandom: RandomSource) {
        super.tick(pState, pLevel, pPos, pRandom)
        if(pLevel.isClientSide) return
        if (CafeManager.getCafe(pLevel, pPos) == null) {
            CafeManager.addCafe(pLevel, pPos, CafeData(pPos, "Cafe"))
        }
        CafeManager.getCafe(pLevel, pPos)?.tick(pLevel)
    }


}