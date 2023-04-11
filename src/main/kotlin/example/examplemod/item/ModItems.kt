package example.examplemod.item

import example.examplemod.ExampleMod
import example.examplemod.block.ModBlocks
import net.minecraft.world.item.BlockItem
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.ForgeRegistries
import thedarkcolour.kotlinforforge.forge.registerObject

object ModItems {
    val ITEM_REGISTERY = DeferredRegister.create(ForgeRegistries.ITEMS, ExampleMod.ID)
    val DEED_TIEM by ITEM_REGISTERY.registerObject("deed"){
        Deed()
    }
}