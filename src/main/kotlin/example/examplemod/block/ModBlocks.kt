package example.examplemod.block

import example.examplemod.ExampleMod
import example.examplemod.block.tile.CashRegisterTile
import example.examplemod.block.tile.ManagementTile
import example.examplemod.block.tile.SeatTile
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.SoundType
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockBehaviour
import net.minecraft.world.level.material.Material
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.ForgeRegistries
import thedarkcolour.kotlinforforge.forge.registerObject

object ModBlocks {
    val BLOCK_REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCKS, ExampleMod.ID)


    val CASH_REGISTER by BLOCK_REGISTRY.registerObject("cash_register") {
        CashRegister(defaultProperties())
    }

    val MANAGEMENT_DESK by BLOCK_REGISTRY.registerObject("management_desk") {
        ManagementDesk(defaultProperties())
    }

    val SEAT by BLOCK_REGISTRY.registerObject("seat") {
        Seat(defaultProperties())
    }

    val TILE_REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, ExampleMod.ID)

    val CASH_REGISTER_TILE by TILE_REGISTRY.registerObject("cash_register") {
        tileBuilder(::CashRegisterTile, CASH_REGISTER)
    }

    val MANAGEMENT_TILE by TILE_REGISTRY.registerObject("management_desk") {
        tileBuilder(::ManagementTile, MANAGEMENT_DESK)
    }

    val SEAT_TILE by TILE_REGISTRY.registerObject("seat") {
        tileBuilder(::SeatTile, SEAT)
    }

    val BLOCK_ITEM_REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, ExampleMod.ID)

    val CASH_REGISTER_ITEM by BLOCK_ITEM_REGISTRY.registerObject("cash_register") {
        BlockItem(CASH_REGISTER, defaultItemProperties())
    }

    val SEAT_ITEM by BLOCK_ITEM_REGISTRY.registerObject("seat"){
        BlockItem(SEAT, defaultItemProperties())
    }

    val DESK_ITEM by BLOCK_ITEM_REGISTRY.registerObject("management_desk"){
        BlockItem(MANAGEMENT_DESK, defaultItemProperties())
    }

    fun <T : BlockEntity?> tileBuilder(factory:BlockEntityType.BlockEntitySupplier<T>, block:Block):BlockEntityType<T> {
        return BlockEntityType.Builder.of(factory, block).build(null)
    }

    fun defaultProperties(): BlockBehaviour.Properties {
        return BlockBehaviour.Properties.of(Material.STONE).sound(SoundType.STONE).strength(2.0f, 6.0f)
    }

    fun defaultItemProperties(): Item.Properties {
        return Item.Properties().tab(ExampleMod.itemGroup)
    }
}