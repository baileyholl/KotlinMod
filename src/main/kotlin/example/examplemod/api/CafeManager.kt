package example.examplemod.api

import example.examplemod.extensions.distanceFrom
import net.minecraft.core.BlockPos
import net.minecraft.world.level.Level

object CafeManager {
    val cafeMap: HashMap<BlockPos, Cafe> = HashMap()

    fun getCafe(pos: BlockPos): Cafe? {
        return cafeMap[pos]
    }

    fun tick(level:Level) {
        val invalidCafes = ArrayList<Cafe>()
        cafeMap.values.forEach {
            if(!it.isValid(level)) {
                invalidCafes.add(it)
            }else{
                it.tick(level)
            }
        }
        invalidCafes.forEach { cafeMap.remove(it.registerPos) }
    }

    fun nearestCafe(pos: BlockPos, maxDistance: Int): Cafe? {
        var nearest: Cafe? = null
        var nearestDist = Double.MAX_VALUE
        for (cafe in cafeMap.values) {
            val dist = pos.distanceFrom(cafe.registerPos)
            if (dist < nearestDist && dist <= maxDistance) {
                nearest = cafe
                nearestDist = dist
            }
        }
        return nearest
    }
}