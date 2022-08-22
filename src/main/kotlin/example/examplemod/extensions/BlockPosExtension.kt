package example.examplemod.extensions

import net.minecraft.core.BlockPos
import kotlin.math.pow
import kotlin.math.sqrt


fun BlockPos.distanceFrom(other: BlockPos): Double {
    return sqrt(
        (this.x - other.x.toDouble()).pow(2.0) + (this.y - other.y.toDouble()).pow(2.0) +
                (this.z - other.z.toDouble()).pow(2.0)
    )
}
