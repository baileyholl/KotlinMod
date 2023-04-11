package example.examplemod.item

import example.examplemod.api.Cafe
import example.examplemod.api.CafeData
import example.examplemod.api.ItemstackData
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.chat.Component
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResultHolder
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.TooltipFlag
import net.minecraft.world.level.Level
import java.util.*

class Deed : ModItem() {

    override fun use(pLevel: Level, pPlayer: Player, pUsedHand: InteractionHand): InteractionResultHolder<ItemStack> {
        val deedData = DeedData(pPlayer.getItemInHand(pUsedHand))
        if(pLevel is ServerLevel && deedData.uuid == null){
            deedData.uuid = UUID.randomUUID()
            CafeData.from(pLevel).addCafe(Cafe(deedData.uuid!!))
        }
        return super.use(pLevel, pPlayer, pUsedHand)
    }

    override fun appendHoverText(
        stack: ItemStack,
        worldIn: Level?,
        tooltip2: MutableList<Component>,
        flagIn: TooltipFlag
    ) {
        super.appendHoverText(stack, worldIn, tooltip2, flagIn)
        val deedData = DeedData(stack)
        tooltip2.add(Component.literal("UUID: ${deedData.uuid}"))
    }
}

class DeedData(stack:ItemStack) : ItemstackData(stack){

    var uuid: UUID? = null
        set(value) {
            field = value
            writeItem()
        }

    init{
        val itemTag = getItemTag(stack)
        if(itemTag != null){
            if(itemTag.contains("uuid")) {
                uuid = itemTag.getUUID("uuid")
            }
        }
    }

    override fun writeToNBT(tag: CompoundTag) {
        tag.putUUID("uuid", uuid)

    }

    override val tagString: String get() = "deed_data"
}