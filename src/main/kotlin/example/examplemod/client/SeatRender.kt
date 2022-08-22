package example.examplemod.client

import example.examplemod.entity.SeatEntity
import net.minecraft.client.renderer.culling.Frustum
import net.minecraft.client.renderer.entity.EntityRenderer
import net.minecraft.client.renderer.entity.EntityRendererProvider
import net.minecraft.resources.ResourceLocation

class SeatRender(context: EntityRendererProvider.Context?) : EntityRenderer<SeatEntity?>(context) {
    override fun shouldRender(
        p_225626_1_: SeatEntity?, p_225626_2_: Frustum, p_225626_3_: Double, p_225626_5_: Double,
        p_225626_7_: Double
    ): Boolean {
        return false
    }

    override fun getTextureLocation(p_110775_1_: SeatEntity?): ResourceLocation? {
        return null
    }
}