package example.examplemod.api

import net.minecraft.nbt.CompoundTag
import net.minecraft.world.item.ItemStack

abstract class ItemstackData(var stack: ItemStack) : AbstractData(stack.orCreateTag) {
    /**
     * This must be called to save the tag to the itemstack. Manipulating this object
     * only will not save the tag to the itemstack.
     */
    fun writeItem() {
        val tag = CompoundTag()
        writeToNBT(tag)
        stack.orCreateTag.put(tagString, tag)
    }

    fun getItemTag(stack: ItemStack): CompoundTag? {
        return stack.orCreateTag.getCompound(tagString)
    }

    abstract val tagString: String
}