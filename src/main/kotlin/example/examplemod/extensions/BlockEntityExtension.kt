package example.examplemod.extensions

import net.minecraft.core.BlockPos
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.entity.BlockEntity
import kotlin.math.pow
import kotlin.math.sqrt

fun BlockEntity.isClientSide(): Boolean {
    return this.level!!.isClientSide
}
