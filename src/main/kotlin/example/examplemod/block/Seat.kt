package example.examplemod.block

import example.examplemod.entity.SeatEntity
import net.minecraft.core.BlockPos
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.Mob
import net.minecraft.world.entity.TamableAnimal
import net.minecraft.world.entity.monster.Shulker
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.DyeColor
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.pathfinder.BlockPathTypes
import net.minecraft.world.level.pathfinder.PathComputationType
import net.minecraft.world.phys.AABB
import net.minecraft.world.phys.BlockHitResult
import net.minecraft.world.phys.shapes.CollisionContext
import net.minecraft.world.phys.shapes.VoxelShape
import java.util.*

class Seat(properties: Properties) : Block(properties) {


    override fun fallOn(
        p_152426_: Level?,
        p_152427_: BlockState?,
        p_152428_: BlockPos?,
        p_152429_: Entity?,
        p_152430_: Float
    ) {
        super.fallOn(p_152426_, p_152427_, p_152428_, p_152429_, p_152430_ * 0.5f)
    }

    override fun updateEntityAfterFallOn(reader: BlockGetter, entity: Entity) {
        val pos = entity.blockPosition()
        if (entity is Player || entity !is LivingEntity || !canBePickedUp(
                entity
            )
            || isSeatOccupied(entity.level, pos)
        ) {
            if (entity.isSuppressingBounce) {
                super.updateEntityAfterFallOn(reader, entity)
                return
            }
            val vec3 = entity.deltaMovement
            if (vec3.y < 0.0) {
                val d0 = if (entity is LivingEntity) 1.0 else 0.8
                entity.setDeltaMovement(vec3.x, -vec3.y * 0.66 * d0, vec3.z)
            }
            return
        }
        if (reader.getBlockState(pos)
                .block !== this
        ) return
        sitDown(entity.level, pos, entity)
    }

    override fun getBlockPathType(state: BlockState?, level: BlockGetter?, pos: BlockPos?, mob: Mob?): BlockPathTypes? {
        return BlockPathTypes.RAIL
    }

    override fun getShape(
        p_220053_1_: BlockState?, p_220053_2_: BlockGetter?, p_220053_3_: BlockPos?,
        p_220053_4_: CollisionContext?
    ): VoxelShape {
        return SEAT
    }

    override fun getCollisionShape(
        p_220071_1_: BlockState?, p_220071_2_: BlockGetter?, p_220071_3_: BlockPos?,
        p_220071_4_: CollisionContext?
    ): VoxelShape {
        return SEAT_COLLISION
    }


    override fun use(
        state: BlockState?, world: Level, pos: BlockPos, player: Player, hand: InteractionHand?,
        p_225533_6_: BlockHitResult?
    ): InteractionResult {
        if (player.isShiftKeyDown)
            return InteractionResult.PASS
        val heldItem = player.getItemInHand(hand)
        val color = DyeColor.getColor(heldItem)
//        if (color != null && color != color) {
//            if (world.isClientSide) return InteractionResult.SUCCESS
//            val newState: BlockState = BlockHelper.copyProperties(
//                state, AllBlocks.SEATS.get(color)
//                    .getDefaultState()
//            )
//            world.setBlockAndUpdate(pos, newState)
//            return InteractionResult.SUCCESS
//        }
        val seats: List<SeatEntity> = world.getEntitiesOfClass(SeatEntity::class.java, AABB(pos))
        if (!seats.isEmpty()) {
            val seatEntity: SeatEntity = seats[0]
            val passengers: List<Entity> = seatEntity.getPassengers()
            if (!passengers.isEmpty() && passengers[0] is Player) return InteractionResult.PASS
            if (!world.isClientSide) {
                seatEntity.ejectPassengers()
                player.startRiding(seatEntity)
            }
            return InteractionResult.SUCCESS
        }
        if (world.isClientSide) return InteractionResult.SUCCESS
        sitDown(
            world,
            pos,
            getLeashed(world, player).orElse(player)
        )
        return InteractionResult.SUCCESS
    }

    fun getLeashed(level: Level?, player: Player): Optional<Entity> {
        val entities = player.level.getEntities(null, player.boundingBox.inflate(10.0)) { true }
        for (entity in entities) {
            if (entity is Mob && entity.leashHolder == player && canBePickedUp(entity)) {
                return Optional.of(entity)
            }
        }
        return Optional.empty()
    }

    fun isSeatOccupied(world: Level, pos: BlockPos): Boolean {
        return !world.getEntitiesOfClass(SeatEntity::class.java, AABB(pos))
            .isEmpty()
    }

    fun canBePickedUp(passenger: Entity?): Boolean {
        if (passenger is Shulker) return false
        return if (passenger is Player) false else passenger is LivingEntity
    }

    fun sitDown(world: Level, pos: BlockPos, entity: Entity) {
        if (world.isClientSide)
            return
        val seat = SeatEntity(world, pos)
        seat.setPos(pos.x + .5, pos.y.toDouble(), pos.z + .5)
        world.addFreshEntity(seat)
        entity.startRiding(seat, true)
        if (entity is TamableAnimal)
            entity.isInSittingPose = true
    }

    override fun isPathfindable(
        state: BlockState,
        reader: BlockGetter,
        pos: BlockPos,
        type: PathComputationType
    ): Boolean {
        return false
    }

    companion object {
        val SEAT: VoxelShape = box(0.0, 0.0, 0.0, 16.0, 8.0, 16.0)

        // Static Block Shapes
        val SEAT_COLLISION: VoxelShape = box(0.0, 0.0, 0.0, 16.0, 6.0, 16.0)
    }

}