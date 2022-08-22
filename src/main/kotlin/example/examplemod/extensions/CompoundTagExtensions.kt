package example.examplemod.extensions

import net.minecraft.core.BlockPos
import net.minecraft.nbt.CompoundTag

fun CompoundTag.putPos(key:String, pos:BlockPos){
    this.putInt(key + "X", pos.x)
    this.putInt(key + "Y", pos.y)
    this.putInt(key + "Z", pos.z)
}

fun CompoundTag.getPos(key:String):BlockPos?{
    if(!this.contains(key + "X")) return null
    return BlockPos(this.getInt(key + "X"), this.getInt(key + "Y"), this.getInt(key + "Z"))
}
