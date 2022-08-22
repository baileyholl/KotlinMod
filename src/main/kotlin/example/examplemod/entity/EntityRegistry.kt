package example.examplemod.entity

import example.examplemod.ExampleMod
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.Mob
import net.minecraft.world.entity.MobCategory
import net.minecraft.world.entity.ai.attributes.Attributes
import net.minecraftforge.event.entity.EntityAttributeCreationEvent
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.ForgeRegistries
import net.minecraftforge.registries.RegistryObject

object EntityRegistry {
    val ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, ExampleMod.ID)

    fun <T : Entity?> registerEntity(name: String, builder: EntityType.Builder<T>): RegistryObject<EntityType<T>> {
        return ENTITIES.register(name) { builder.build(ExampleMod.ID + ":" + name) }
    }

    val VILLAGER_CUSTOMER = registerEntity<VillagerCustomer>("vill_customer", EntityType.Builder.of(::VillagerCustomer, MobCategory.CREATURE).sized(0.6f, 1.8f))
    val SEAT = registerEntity<SeatEntity>("seat", EntityType.Builder.of(::SeatEntity, MobCategory.MISC).sized(0.25f, 0.35f))

    fun registerAttributes(event: EntityAttributeCreationEvent){
        event.put(VILLAGER_CUSTOMER.get(), Mob.createMobAttributes().add(Attributes.MOVEMENT_SPEED, Attributes.MOVEMENT_SPEED.defaultValue)
            .add(Attributes.MAX_HEALTH, 20.0).build())
    }
}