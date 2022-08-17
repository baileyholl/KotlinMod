package example.examplemod.client

import com.mojang.blaze3d.vertex.PoseStack
import example.examplemod.entity.VillagerCustomer
import net.minecraft.client.model.geom.ModelLayers
import net.minecraft.client.renderer.entity.EntityRendererProvider
import net.minecraft.client.renderer.entity.MobRenderer
import net.minecraft.client.renderer.entity.layers.CrossedArmsItemLayer
import net.minecraft.client.renderer.entity.layers.CustomHeadLayer
import net.minecraft.resources.ResourceLocation
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn

@OnlyIn(Dist.CLIENT)
class VillRenderer(renderContext: EntityRendererProvider.Context) :
    MobRenderer<VillagerCustomer, VillModel<VillagerCustomer>>(
        renderContext,
        VillModel(renderContext.bakeLayer(ModelLayers.VILLAGER)),
        0.5f
    ) {
    init {
        addLayer(
            CustomHeadLayer<VillagerCustomer, VillModel<VillagerCustomer>>(
                this,
                renderContext.modelSet,
                renderContext.itemInHandRenderer
            )
        )
        addLayer(
            VillProfessionLayer<VillagerCustomer, VillModel<VillagerCustomer>>(
                this,
                renderContext.resourceManager,
                "villager"
            )
        )
        addLayer(CrossedArmsItemLayer<VillagerCustomer, VillModel<VillagerCustomer>>(this, renderContext.itemInHandRenderer))
    }

    /**
     * Returns the location of an entity's texture.
     */
    override fun getTextureLocation(pEntity: VillagerCustomer): ResourceLocation {
        return VILLAGER_BASE_SKIN
    }

    override fun scale(pLivingEntity: VillagerCustomer, pMatrixStack: PoseStack, pPartialTickTime: Float) {
        var f = 0.9375f
        shadowRadius = 0.5f

        pMatrixStack.scale(f, f, f)
    }

    companion object {
        private val VILLAGER_BASE_SKIN = ResourceLocation("textures/entity/villager/villager.png")
    }
}