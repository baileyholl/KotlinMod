package example.examplemod.block

import net.minecraft.core.BlockPos
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.state.BlockState

/**
 * Interface for tickable things.
 */
interface ITickable {
    /**
     * Tick the tickable with parameters.
     *
     * @param level the world its ticking in.
     * @param state its state.
     * @param pos   the position its ticking at.
     */
    fun tick(level: Level?, state: BlockState?, pos: BlockPos?) {
        tick()
    }

    /**
     * Default parameterless ticking implementation.
     */
    fun tick() {}
}
