package example.examplemod.api

import example.examplemod.extensions.distanceFrom
import net.minecraft.core.BlockPos
import net.minecraft.world.level.Level

object CafeManager {
    val cafeMap: Map<Level, HashMap<BlockPos, CafeData>> = HashMap()

    fun getCafe(level: Level, pos: BlockPos): CafeData? {
        return cafeMap.getOrDefault(level, HashMap())[pos]
    }

    fun addCafe(level: Level, pos: BlockPos, cafe: CafeData) {
        cafeMap.getOrDefault(level, HashMap())[pos] = cafe
    }

    fun nearestCafe(level:Level, pos: BlockPos, maxDistance: Int): CafeData? {
        var nearest: CafeData? = null
        var nearestDist = Double.MAX_VALUE
        for (cafe in cafeMap.getOrDefault(level, HashMap()).values) {
            val dist = pos.distanceFrom(cafe.registerPos)
            if (dist < nearestDist && dist <= maxDistance) {
                nearest = cafe
                nearestDist = dist
            }
        }
        return nearest
    }
}