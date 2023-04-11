package example.examplemod.block.tile;

import example.examplemod.api.Cafe
import example.examplemod.api.CafeData
import example.examplemod.block.ITickable
import example.examplemod.block.ModBlocks
import example.examplemod.extensions.isClientSide
import net.minecraft.core.BlockPos
import net.minecraft.nbt.CompoundTag
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState
import java.util.*

class ManagementTile (type:BlockEntityType<ManagementTile>, pos:BlockPos, state:BlockState): BlockEntity(type, pos, state), ITickable {

    private var uuid: UUID? = null

    constructor(pos:BlockPos, state:BlockState): this(ModBlocks.MANAGEMENT_TILE, pos, state)

    override fun tick() {
        super.tick()
        if(isClientSide())
            return
        val cafe = getCafe()
        cafe?.tick(this)
        if(level!!.gameTime % 20 == 0L) {
            println(cafe)
            println(uuid)
        }
    }

    fun setCafe(uuid: UUID){
        this.uuid = uuid
        println("set cafe: $uuid")
    }

    fun getCafe(): Cafe?{
        if(uuid != null && level is ServerLevel){
            return CafeData.from(level as ServerLevel).getCafe(uuid!!)
        }
        return null
    }

    override fun saveAdditional(pTag: CompoundTag) {
        super.saveAdditional(pTag)
        println(uuid)
        uuid?.let { pTag.putUUID("uuid", it) }
    }

    override fun load(pTag: CompoundTag) {
        super.load(pTag)
        if(pTag.contains("uuid")){
            uuid = pTag.getUUID("uuid")
        }
    }

}