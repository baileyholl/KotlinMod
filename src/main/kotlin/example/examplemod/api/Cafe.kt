package example.examplemod.api

import example.examplemod.block.tile.CashRegisterTile
import example.examplemod.extensions.getPos
import example.examplemod.extensions.putPos
import net.minecraft.core.BlockPos
import net.minecraft.nbt.CompoundTag
import net.minecraft.nbt.ListTag
import net.minecraft.world.level.Level

class Cafe (val registerPos:BlockPos, val name:String){
    var additions = ArrayList<BlockPos>()

    constructor(tag:CompoundTag) : this(tag.getPos("registerPos")!!, tag.getString("name")){
        val additionList = tag.getList("additions", 9) as ListTag
        for(addition in additionList){
            if(addition !is CompoundTag)
                continue
            val pos = addition.getPos("addition")
            if(pos != null) {
                additions.add(pos)
            }
        }
    }
    
    fun tick(level:Level){

    }

    fun isValid(level:Level):Boolean{
        return level.getBlockEntity(registerPos) is CashRegisterTile
    }

    fun toCompoundTag():CompoundTag{
        val tag = CompoundTag()
        tag.putString("name", name)
        tag.putPos("registerPos", registerPos)
        val additionList = ListTag()
        for(addition in additions){
            val posTag = CompoundTag()
            posTag.putPos("addition", addition)
            additionList.add(posTag)
        }
        tag.put("additions", additionList)
        return tag
    }
}