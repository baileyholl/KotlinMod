package example.examplemod.api

import net.minecraft.nbt.CompoundTag
import java.util.UUID

class Cafe {
    val uuid:UUID

    constructor(uuid: UUID){
        this.uuid = uuid;
    }

    constructor(pCompoundTag: CompoundTag){
        this.uuid = pCompoundTag.getUUID("UUID")
    }

    fun save(): CompoundTag {
        val tag = CompoundTag()
        tag.putUUID("UUID", uuid)
        return tag
    }
}