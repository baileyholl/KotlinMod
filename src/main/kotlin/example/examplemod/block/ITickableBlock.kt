package example.examplemod.block

import net.minecraft.core.BlockPos
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.EntityBlock
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityTicker
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState

interface ITickableBlock : EntityBlock {
    override fun <T : BlockEntity?> getTicker(
        level: Level,
        state: BlockState,
        type: BlockEntityType<T>
    ): BlockEntityTicker<T>? {
        return createTickerHelper(
            type, type
        ) { l: Level?, pos: BlockPos?, s: BlockState?, te: T ->
            (te as ITickable).tick(
                l,
                s,
                pos
            )
        }
    }

    companion object {
        fun <E : BlockEntity?, A : BlockEntity?> createTickerHelper(
            type1: BlockEntityType<A>,
            type2: BlockEntityType<E>,
            ticker: BlockEntityTicker<in E>?
        ): BlockEntityTicker<A>? {
            return if (type2 === type1) ticker as BlockEntityTicker<A>? else null
        }
    }
}