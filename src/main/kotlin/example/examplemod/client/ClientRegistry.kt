package example.examplemod.client

import example.examplemod.entity.EntityRegistry
import net.minecraftforge.client.event.EntityRenderersEvent

object ClientRegistry {
    fun renderEvent(entityRender:EntityRenderersEvent.RegisterRenderers){
        entityRender.registerEntityRenderer(EntityRegistry.VILLAGER_CUSTOMER.get(), ::VillRenderer)
        entityRender.registerEntityRenderer(EntityRegistry.SEAT.get(), ::SeatRender)
    }
}