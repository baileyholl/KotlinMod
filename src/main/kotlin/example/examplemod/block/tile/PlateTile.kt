package example.examplemod.block.tile

import net.minecraft.core.BlockPos
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState

class PlateTile(type:BlockEntityType<PlateTile>, pos:BlockPos, state:BlockState) : BlockEntity(type, pos, state){

}