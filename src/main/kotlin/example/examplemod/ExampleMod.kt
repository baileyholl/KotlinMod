package example.examplemod

import example.examplemod.block.ModBlocks
import example.examplemod.client.ClientRegistry
import example.examplemod.entity.EntityRegistry
import example.examplemod.item.ModItems
import net.minecraft.client.Minecraft
import net.minecraft.world.item.CreativeModeTab
import net.minecraft.world.item.ItemStack
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent
import net.minecraftforge.fml.event.lifecycle.FMLDedicatedServerSetupEvent
import org.apache.logging.log4j.Level
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import thedarkcolour.kotlinforforge.forge.MOD_BUS
import thedarkcolour.kotlinforforge.forge.runForDist

/**
 * Main mod class. Should be an `object` declaration annotated with `@Mod`.
 * The modid should be declared in this object and should match the modId entry
 * in mods.toml.
 *
 * An example for blocks is in the `blocks` package of this mod.
 */
@Mod(ExampleMod.ID)
object ExampleMod {
    const val ID = "examplemod"

    // the logger for our mod
    val LOGGER: Logger = LogManager.getLogger(ID)
    var itemGroup: CreativeModeTab =
        object : CreativeModeTab(getGroupCountSafe(), ID) {
            override fun makeIcon(): ItemStack {
                return ModBlocks.CASH_REGISTER.asItem().defaultInstance
            }
        }

    init {
        LOGGER.log(Level.INFO, "Hello world!")

        // Register the KDeferredRegister to the mod-specific event bus
        ModBlocks.BLOCK_REGISTRY.register(MOD_BUS)
        ModBlocks.TILE_REGISTRY.register(MOD_BUS)
        ModBlocks.BLOCK_ITEM_REGISTRY.register(MOD_BUS)
        ModItems.ITEM_REGISTERY.register(MOD_BUS)
        EntityRegistry.ENTITIES.register(MOD_BUS)
        MOD_BUS.addListener(EntityRegistry::registerAttributes)
        val obj = runForDist(
            clientTarget = {
                MOD_BUS.addListener(::onClientSetup)
                MOD_BUS.addListener(ClientRegistry::renderEvent)
                Minecraft.getInstance()
            },
            serverTarget = {
                MOD_BUS.addListener(::onServerSetup)
                "test"
            })
    }

    /**
     * This is used for initializing client specific
     * things such as renderers and keymaps
     * Fired on the mod specific event bus.
     */
    private fun onClientSetup(event: FMLClientSetupEvent) {
        LOGGER.log(Level.INFO, "Initializing client...")
    }

    /**
     * Fired on the global Forge bus.
     */
    private fun onServerSetup(event: FMLDedicatedServerSetupEvent) {
        LOGGER.log(Level.INFO, "Server starting...")
    }
}