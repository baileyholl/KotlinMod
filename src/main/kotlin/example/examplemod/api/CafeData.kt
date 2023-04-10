package example.examplemod.api

import net.minecraft.nbt.CompoundTag
import net.minecraft.nbt.ListTag
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.level.saveddata.SavedData
import java.util.UUID

fun from(world: ServerLevel): CafeData {
    return world.server
        .overworld()
        .dataStorage
        .computeIfAbsent({ tag -> CafeData(tag) } , ::CafeData, "CAFE_DATA" )
}


class CafeData : SavedData {

    val cafeMap: HashMap<UUID, Cafe> = HashMap()

    constructor()

    constructor(pCompoundTag: CompoundTag){
        val cafes = pCompoundTag.getList("CafeList", 10)
        for(i in 0 until cafes.size){
            val cafe = Cafe(cafes.getCompound(i))
            cafeMap[cafe.uuid] = cafe
        }
    }

    override fun isDirty(): Boolean {
        return true;
    }

    override fun save(pCompoundTag: CompoundTag): CompoundTag {
        val cafeList = ListTag()
        for(cafe in cafeMap.values){
            cafeList.add(cafe.save())
        }
        pCompoundTag.put("CafeList", cafeList)
        return pCompoundTag
    }
}