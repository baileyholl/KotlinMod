package example.examplemod.block

import example.examplemod.block.tile.ManagementTile
import example.examplemod.item.Deed
import example.examplemod.item.DeedData
import net.minecraft.core.BlockPos
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.player.Player
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.BlockHitResult

class ManagementDesk (properties: Properties) : Block(properties), ITickableBlock {

    override fun use(
        pState: BlockState,
        pLevel: Level,
        pPos: BlockPos,
        pPlayer: Player,
        pHand: InteractionHand,
        pHit: BlockHitResult
    ): InteractionResult {
        if(pLevel.isClientSide) return InteractionResult.SUCCESS
        val heldStack = pPlayer.getItemInHand(pHand)
        if(!heldStack.isEmpty && heldStack.item is Deed){
            val tile = pLevel.getBlockEntity(pPos) as ManagementTile
            val deedData = DeedData(heldStack)
            if(deedData.uuid != null) {
                tile.setCafe(deedData.uuid!!)
            }
        }
        return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit)
    }

    override fun newBlockEntity(pPos: BlockPos, pState: BlockState): BlockEntity {
        return ManagementTile(pPos, pState)
    }
}