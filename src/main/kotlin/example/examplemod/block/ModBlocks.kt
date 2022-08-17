package example.examplemod.block

import example.examplemod.ExampleMod
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.SoundType
import net.minecraft.world.level.block.state.BlockBehaviour
import net.minecraft.world.level.material.Material
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.ForgeRegistries
import thedarkcolour.kotlinforforge.forge.registerObject

object ModBlocks {
    val BLOCK_REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCKS, ExampleMod.ID)

    // the returned ObjectHolderDelegate can be used as a property delegate
    // this is automatically registered by the deferred registry at the correct times
    val CASH_REGISTER by BLOCK_REGISTRY.registerObject("cash_register") {
        CashRegister(defaultProperties())
    }

    val TILE_REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, ExampleMod.ID)


    val ITEM_REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, ExampleMod.ID)

    val CASH_REGISTER_ITEM by ITEM_REGISTRY.registerObject("cash_register") {
        BlockItem(CASH_REGISTER, defaultItemProperties())
    }


    fun defaultProperties(): BlockBehaviour.Properties {
        return BlockBehaviour.Properties.of(Material.STONE).sound(SoundType.STONE).strength(2.0f, 6.0f)
    }

    fun defaultItemProperties(): Item.Properties {
        return Item.Properties().tab(ExampleMod.itemGroup)
    }
}