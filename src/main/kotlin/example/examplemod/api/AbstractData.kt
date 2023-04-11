package example.examplemod.api

import net.minecraft.nbt.CompoundTag

abstract class AbstractData(val initTag: CompoundTag) {

    abstract fun writeToNBT(tag: CompoundTag)
}