package example.examplemod.item

import example.examplemod.ExampleMod
import net.minecraft.network.chat.Component
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Rarity
import net.minecraft.world.item.TooltipFlag
import net.minecraft.world.level.Level
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn

fun defaultItemProperties(): Item.Properties? {
    return Item.Properties().tab(ExampleMod.itemGroup)
}

public open class ModItem @JvmOverloads constructor(properties: Properties? = defaultItemProperties()) : Item(properties) {
    var tooltip: MutableList<Component>? = ArrayList()
    var rarity: Rarity? = null
    fun withTooltip(tip: Component): ModItem {
        tooltip!!.add(tip)
        return this
    }

    fun withTooltip(tip: String?): ModItem {
        tooltip!!.add(Component.translatable(tip))
        return this
    }

    fun withRarity(rarity: Rarity?): ModItem {
        this.rarity = rarity
        return this
    }

    override fun getRarity(stack: ItemStack): Rarity {
        return if (rarity != null) rarity!! else super.getRarity(stack)
    }

    /**
     * allows items to add custom lines of information to the mouseover description
     */
    @OnlyIn(Dist.CLIENT)
    override fun appendHoverText(
        stack: ItemStack,
        worldIn: Level?,
        tooltip2: MutableList<Component>,
        flagIn: TooltipFlag
    ) {
        if (tooltip != null && !tooltip!!.isEmpty()) {
            tooltip2.addAll(tooltip!!)
        }
    }
}