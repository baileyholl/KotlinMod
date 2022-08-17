package example.examplemod.client

import com.mojang.blaze3d.vertex.PoseStack
import it.unimi.dsi.fastutil.ints.Int2ObjectMap
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap
import it.unimi.dsi.fastutil.objects.Object2ObjectFunction
import it.unimi.dsi.fastutil.objects.Object2ObjectMap
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap
import net.minecraft.Util
import net.minecraft.client.model.EntityModel
import net.minecraft.client.model.VillagerHeadModel
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.entity.RenderLayerParent
import net.minecraft.client.renderer.entity.layers.RenderLayer
import net.minecraft.client.resources.metadata.animation.VillagerMetaDataSection
import net.minecraft.client.resources.metadata.animation.VillagerMetaDataSection.Hat
import net.minecraft.core.DefaultedRegistry
import net.minecraft.core.Registry
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.packs.resources.Resource
import net.minecraft.server.packs.resources.ResourceManager
import net.minecraft.util.Mth
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.npc.VillagerDataHolder
import net.minecraft.world.entity.npc.VillagerProfession
import net.minecraft.world.entity.npc.VillagerType
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn
import java.io.IOException
import java.util.*

@OnlyIn(Dist.CLIENT)
class VillProfessionLayer<T, M>(
    pRenderer: RenderLayerParent<T, M>,
    private val resourceManager: ResourceManager,
    private val path: String
) : RenderLayer<T, M>(pRenderer) where T : LivingEntity, T : VillagerDataHolder, M : EntityModel<T>, M : VillagerHeadModel {

    private val typeHatCache: Object2ObjectMap<VillagerType, Hat> = Object2ObjectOpenHashMap()
    private val professionHatCache: Object2ObjectMap<VillagerProfession, Hat> = Object2ObjectOpenHashMap()

    override fun render(
        pMatrixStack: PoseStack,
        pBuffer: MultiBufferSource,
        pPackedLight: Int,
        pLivingEntity: T,
        pLimbSwing: Float,
        pLimbSwingAmount: Float,
        pPartialTicks: Float,
        pAgeInTicks: Float,
        pNetHeadYaw: Float,
        pHeadPitch: Float
    ) {
        if(pLivingEntity.isInvisible) {
            return
        }
        val villagerdata = pLivingEntity.villagerData
        val villagertype = villagerdata.type
        val villagerprofession = villagerdata.profession
        val hatData = getHatData(typeHatCache, "type", Registry.VILLAGER_TYPE, villagertype)
        val professionHat = getHatData(professionHatCache, "profession", Registry.VILLAGER_PROFESSION, villagerprofession)
        val m = this.parentModel
        m.hatVisible(professionHat == Hat.NONE || professionHat == Hat.PARTIAL && hatData != Hat.FULL)
        val resourcelocation = getResourceLocation("type", Registry.VILLAGER_TYPE.getKey(villagertype))
        renderColoredCutoutModel(
            m,
            resourcelocation,
            pMatrixStack,
            pBuffer,
            pPackedLight,
            pLivingEntity,
            1.0f,
            1.0f,
            1.0f
        )
        m.hatVisible(true)
        if (villagerprofession !== VillagerProfession.NONE && !pLivingEntity.isBaby) {
            val resourcelocation1 =
                getResourceLocation("profession", Registry.VILLAGER_PROFESSION.getKey(villagerprofession))
            renderColoredCutoutModel(
                m,
                resourcelocation1,
                pMatrixStack,
                pBuffer,
                pPackedLight,
                pLivingEntity,
                1.0f,
                1.0f,
                1.0f
            )
            if (villagerprofession !== VillagerProfession.NITWIT) {
                val resourcelocation2 = getResourceLocation(
                    "profession_level", LEVEL_LOCATIONS[Mth.clamp(
                        villagerdata.level,
                        1,
                        LEVEL_LOCATIONS.size
                    )]
                )
                renderColoredCutoutModel(
                    m,
                    resourcelocation2,
                    pMatrixStack,
                    pBuffer,
                    pPackedLight,
                    pLivingEntity,
                    1.0f,
                    1.0f,
                    1.0f
                )
            }
        }
    }

    private fun getResourceLocation(p_117669_: String, p_117670_: ResourceLocation): ResourceLocation {
        return ResourceLocation(
            p_117670_.namespace,
            "textures/entity/" + path + "/" + p_117669_ + "/" + p_117670_.path + ".png"
        )
    }

    fun <K> getHatData(
        p_117659_: Object2ObjectMap<K, Hat>,
        p_117660_: String,
        p_117661_: DefaultedRegistry<K>,
        p_117662_: K
    ): Hat {
        return p_117659_.computeIfAbsent(p_117662_, Object2ObjectFunction { p_234880_: Any? ->
            resourceManager.getResource(
                getResourceLocation(p_117660_, p_117661_.getKey(p_117662_))
            ).flatMap { p_234875_: Resource ->
                try {
                    return@flatMap p_234875_.metadata().getSection(VillagerMetaDataSection.SERIALIZER)
                        .map { obj: VillagerMetaDataSection -> obj.hat }
                } catch (ioexception: IOException) {
                    return@flatMap Optional.empty<Hat>()
                }
            }.orElse(Hat.NONE)
        })
    }

    companion object {
        private val LEVEL_LOCATIONS: Int2ObjectMap<ResourceLocation> =
            Util.make(Int2ObjectOpenHashMap()) { p_117657_: Int2ObjectOpenHashMap<ResourceLocation> ->
                p_117657_.put(1, ResourceLocation("stone"))
                p_117657_.put(2, ResourceLocation("iron"))
                p_117657_.put(3, ResourceLocation("gold"))
                p_117657_.put(4, ResourceLocation("emerald"))
                p_117657_.put(5, ResourceLocation("diamond"))
            }
    }
}