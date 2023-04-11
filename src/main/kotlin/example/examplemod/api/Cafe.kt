package example.examplemod.api

import example.examplemod.block.tile.ManagementTile
import net.minecraft.nbt.CompoundTag
import java.util.*

class Cafe {
    val uuid:UUID

    constructor(uuid: UUID = UUID.randomUUID()){
        this.uuid = uuid;
    }

    constructor(pCompoundTag: CompoundTag){
        this.uuid = pCompoundTag.getUUID("UUID")
    }

    fun tick(tile:ManagementTile){

    }

    fun save(): CompoundTag {
        val tag = CompoundTag()
        tag.putUUID("UUID", uuid)
        return tag
    }
}