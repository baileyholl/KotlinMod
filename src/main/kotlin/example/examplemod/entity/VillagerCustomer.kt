package example.examplemod.entity

import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.PathfinderMob
import net.minecraft.world.entity.npc.VillagerData
import net.minecraft.world.entity.npc.VillagerDataHolder
import net.minecraft.world.entity.npc.VillagerProfession
import net.minecraft.world.entity.npc.VillagerType
import net.minecraft.world.level.Level

class VillagerCustomer(pEntityType: EntityType<out PathfinderMob>, pLevel: Level) : Customer(pEntityType, pLevel),
    VillagerDataHolder {

    override fun getVillagerData(): VillagerData {
        return VillagerData(VillagerType.DESERT, VillagerProfession.ARMORER, 1)
    }

    override fun setVillagerData(pData: VillagerData) {}
}