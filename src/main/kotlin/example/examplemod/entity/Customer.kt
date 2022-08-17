package example.examplemod.entity

import net.minecraft.core.BlockPos
import net.minecraft.world.entity.*
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level

open class Customer(pEntityType: EntityType<out PathfinderMob>, pLevel: Level) : PathfinderMob(pEntityType, pLevel) {
    lateinit var storePos:BlockPos
    var satisfaction = 0


    override fun registerGoals() {
        super.registerGoals()
    }

}