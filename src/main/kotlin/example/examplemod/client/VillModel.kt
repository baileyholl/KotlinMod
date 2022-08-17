package example.examplemod.client

import net.minecraft.client.model.HeadedModel
import net.minecraft.client.model.HierarchicalModel
import net.minecraft.client.model.VillagerHeadModel
import net.minecraft.client.model.geom.ModelPart
import net.minecraft.client.model.geom.PartPose
import net.minecraft.client.model.geom.builders.CubeDeformation
import net.minecraft.client.model.geom.builders.CubeListBuilder
import net.minecraft.client.model.geom.builders.MeshDefinition
import net.minecraft.util.Mth
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.npc.AbstractVillager
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn

@OnlyIn(Dist.CLIENT)
open class VillModel<T : Entity>(private val root: ModelPart) :
    HierarchicalModel<T>(), HeadedModel, VillagerHeadModel {
    private val head: ModelPart
    private val hat: ModelPart
    private val hatRim: ModelPart
    private val rightLeg: ModelPart
    private val leftLeg: ModelPart
    protected val nose: ModelPart

    init {
        head = root.getChild("head")
        hat = head.getChild("hat")
        hatRim = hat.getChild("hat_rim")
        nose = head.getChild("nose")
        rightLeg = root.getChild("right_leg")
        leftLeg = root.getChild("left_leg")
    }

    override fun root(): ModelPart {
        return this.root
    }

    /**
     * Sets this entity's model rotation angles
     */
    override fun setupAnim(
        pEntity: T,
        pLimbSwing: Float,
        pLimbSwingAmount: Float,
        pAgeInTicks: Float,
        pNetHeadYaw: Float,
        pHeadPitch: Float
    ) {
        var flag = false
        if (pEntity is AbstractVillager) {
            flag = (pEntity as AbstractVillager).unhappyCounter > 0
        }
        head.yRot = pNetHeadYaw * (Math.PI.toFloat() / 180f)
        head.xRot = pHeadPitch * (Math.PI.toFloat() / 180f)
        if (flag) {
            head.zRot = 0.3f * Mth.sin(0.45f * pAgeInTicks)
            head.xRot = 0.4f
        } else {
            head.zRot = 0.0f
        }
        rightLeg.xRot = Mth.cos(pLimbSwing * 0.6662f) * 1.4f * pLimbSwingAmount * 0.5f
        leftLeg.xRot = Mth.cos(pLimbSwing * 0.6662f + Math.PI.toFloat()) * 1.4f * pLimbSwingAmount * 0.5f
        rightLeg.yRot = 0.0f
        leftLeg.yRot = 0.0f
    }

    override fun getHead(): ModelPart {
        return head
    }

    override fun hatVisible(pVisible: Boolean) {
        head.visible = pVisible
        hat.visible = pVisible
        hatRim.visible = pVisible
    }

    companion object {
        fun createBodyModel(): MeshDefinition {
            val meshdefinition = MeshDefinition()
            val partdefinition = meshdefinition.root
            val f = 0.5f
            val partdefinition1 = partdefinition.addOrReplaceChild(
                "head",
                CubeListBuilder.create().texOffs(0, 0).addBox(-4.0f, -10.0f, -4.0f, 8.0f, 10.0f, 8.0f),
                PartPose.ZERO
            )
            val partdefinition2 = partdefinition1.addOrReplaceChild(
                "hat",
                CubeListBuilder.create().texOffs(32, 0)
                    .addBox(-4.0f, -10.0f, -4.0f, 8.0f, 10.0f, 8.0f, CubeDeformation(0.51f)),
                PartPose.ZERO
            )
            partdefinition2.addOrReplaceChild(
                "hat_rim",
                CubeListBuilder.create().texOffs(30, 47).addBox(-8.0f, -8.0f, -6.0f, 16.0f, 16.0f, 1.0f),
                PartPose.rotation(
                    -Math.PI.toFloat() / 2f, 0.0f, 0.0f
                )
            )
            partdefinition1.addOrReplaceChild(
                "nose",
                CubeListBuilder.create().texOffs(24, 0).addBox(-1.0f, -1.0f, -6.0f, 2.0f, 4.0f, 2.0f),
                PartPose.offset(0.0f, -2.0f, 0.0f)
            )
            val partdefinition3 = partdefinition.addOrReplaceChild(
                "body",
                CubeListBuilder.create().texOffs(16, 20).addBox(-4.0f, 0.0f, -3.0f, 8.0f, 12.0f, 6.0f),
                PartPose.ZERO
            )
            partdefinition3.addOrReplaceChild(
                "jacket",
                CubeListBuilder.create().texOffs(0, 38)
                    .addBox(-4.0f, 0.0f, -3.0f, 8.0f, 20.0f, 6.0f, CubeDeformation(0.5f)),
                PartPose.ZERO
            )
            partdefinition.addOrReplaceChild(
                "arms",
                CubeListBuilder.create().texOffs(44, 22).addBox(-8.0f, -2.0f, -2.0f, 4.0f, 8.0f, 4.0f).texOffs(44, 22)
                    .addBox(4.0f, -2.0f, -2.0f, 4.0f, 8.0f, 4.0f, true).texOffs(40, 38)
                    .addBox(-4.0f, 2.0f, -2.0f, 8.0f, 4.0f, 4.0f),
                PartPose.offsetAndRotation(0.0f, 3.0f, -1.0f, -0.75f, 0.0f, 0.0f)
            )
            partdefinition.addOrReplaceChild(
                "right_leg",
                CubeListBuilder.create().texOffs(0, 22).addBox(-2.0f, 0.0f, -2.0f, 4.0f, 12.0f, 4.0f),
                PartPose.offset(-2.0f, 12.0f, 0.0f)
            )
            partdefinition.addOrReplaceChild(
                "left_leg",
                CubeListBuilder.create().texOffs(0, 22).mirror().addBox(-2.0f, 0.0f, -2.0f, 4.0f, 12.0f, 4.0f),
                PartPose.offset(2.0f, 12.0f, 0.0f)
            )
            return meshdefinition
        }
    }
}