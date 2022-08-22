package example.examplemod.entity

import example.examplemod.block.Seat
import net.minecraft.core.BlockPos
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.network.protocol.Packet
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.TamableAnimal
import net.minecraft.world.entity.animal.Cat
import net.minecraft.world.entity.animal.Parrot
import net.minecraft.world.entity.animal.Wolf
import net.minecraft.world.entity.monster.Creeper
import net.minecraft.world.entity.monster.Skeleton
import net.minecraft.world.entity.monster.Slime
import net.minecraft.world.level.Level
import net.minecraft.world.phys.Vec3
import net.minecraftforge.common.util.FakePlayer
import net.minecraftforge.entity.IEntityAdditionalSpawnData
import net.minecraftforge.network.NetworkHooks

class SeatEntity(p_i48580_1_: EntityType<*>?, p_i48580_2_: Level?) : Entity(p_i48580_1_, p_i48580_2_),
    IEntityAdditionalSpawnData {

    constructor(level: Level, pos: BlockPos) : this(EntityRegistry.SEAT.get(), level) {
        noPhysics = true
    }

    override fun setPos(x: Double, y: Double, z: Double) {
        super.setPos(x, y, z)
        val bb = boundingBox
        val diff = Vec3(x, y, z).subtract(bb.center)
        boundingBox = bb.move(diff)
    }

    override fun setDeltaMovement(p_213317_1_: Vec3) {}

    override fun tick() {
        if (level.isClientSide) return
        val blockPresent = level.getBlockState(blockPosition()).block is Seat
        if (isVehicle && blockPresent)
            return;
        this.discard();
    }

    override fun canRide(entity: Entity): Boolean {
        return entity !is FakePlayer
    }

    override fun removePassenger(entity: Entity) {
        super.removePassenger(entity)
        if (entity is TamableAnimal)
            entity.isInSittingPose = false;
    }

    override fun getDismountLocationForPassenger(pLivingEntity: LivingEntity): Vec3 {
        return super.getDismountLocationForPassenger(pLivingEntity).add(0.0, 0.5, 0.0)
    }

    override fun positionRider(pEntity: Entity, pCallback: MoveFunction) {
        if (!this.hasPassenger(pEntity))
            return
        val d0: Double = this.y + this.passengersRidingOffset + pEntity.myRidingOffset
        pCallback.accept(pEntity, this.x, d0 + getCustomEntitySeatOffset(pEntity), this.z)
    }

    fun getCustomEntitySeatOffset(entity: Entity?): Double {
        if (entity is Slime) return 0.25
        if (entity is Parrot) return (1 / 16f).toDouble()
        if (entity is Skeleton) return (1 / 8f).toDouble()
        if (entity is Creeper) return (1 / 8f).toDouble()
        if (entity is Cat) return (1 / 8f).toDouble()
        return if (entity is Wolf) (1 / 16f).toDouble() else 0.0
    }

    override fun defineSynchedData() {}
    override fun readAdditionalSaveData(p_70037_1_: CompoundTag) {}
    override fun addAdditionalSaveData(p_213281_1_: CompoundTag) {}
    override fun getAddEntityPacket(): Packet<*> {
        return NetworkHooks.getEntitySpawningPacket(this)
    }

    override fun writeSpawnData(buffer: FriendlyByteBuf) {}
    override fun readSpawnData(additionalData: FriendlyByteBuf) {}
}