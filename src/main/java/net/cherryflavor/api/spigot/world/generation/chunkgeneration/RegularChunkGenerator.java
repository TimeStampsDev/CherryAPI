package net.cherryflavor.api.spigot.world.generation.chunkgeneration;

import net.cherryflavor.api.spigot.ServerAPI;
import net.cherryflavor.api.spigot.world.WorldManager;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.util.noise.SimplexOctaveGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created on 2/21/2021
 * Time 9:14 PM
 */

public class RegularChunkGenerator extends ChunkGenerator {

    ServerAPI serverAPI;

    int currentHeight = 0;
    int chunkCount = 0;
    boolean hasStarted = false;

    List<BlockPopulator> blockPopulatorsList;

    //==================================================================================================================
    // CONSTRUCTORS
    //==================================================================================================================

    public RegularChunkGenerator(ServerAPI serverAPI) {
        this.serverAPI = serverAPI;
        this.blockPopulatorsList = new ArrayList<>();
    }

    //==================================================================================================================
    // GETTERS
    //==================================================================================================================

    /**
     * Returns server API
     * @return
     */
    public ServerAPI getAPI() {
        return serverAPI;
    }

    /**
     * Overriden chunk gereration method
     * @param world
     * @param random
     * @param chunkX
     * @param chunkZ
     * @param biomeGrid
     * @return
     */
    @Override
    public ChunkData generateChunkData(World world, Random random, int chunkX, int chunkZ, BiomeGrid biomeGrid) {
        if (!hasStarted) {
            serverAPI.debug("[WorldGen] Generating chunks...");
            hasStarted = true;
        }

        ChunkData chunk = createChunkData(world);
        SimplexOctaveGenerator generator = new SimplexOctaveGenerator(new Random(world.getSeed()), 8);

        generator.setScale(0.005D); // Larger the scale, the deeper the terrain.

        double heightVariation = 15; // changing this randomly makes chunk-sized pits (HOLES)
        double lowestLevel = 50;


        for (int x = 0; x < WorldManager.basicChunkLength; x++) {
            for (int z = 0; z < WorldManager.basicChunkLength; z++) {
                currentHeight = (int) ((generator.noise(chunkX*WorldManager.basicChunkLength +x, chunkZ*WorldManager.basicChunkLength+z, 2.8D, 0.2D, true)+1)*heightVariation+lowestLevel);
                chunk.setBlock(x, currentHeight, z, Material.GRASS_BLOCK);
                chunk.setBlock(x, currentHeight-1, z, Material.DIRT);
                chunk.setBlock(x, currentHeight-2, z, Material.DIRT);
                for (int i = currentHeight-3; i > 0; i--){
                    chunk.setBlock(x, i, z, Material.STONE);
                }
                chunk.setBlock(x, 0, z, Material.BEDROCK);
            }
        }

        return chunk;
    }

    /**
     * Returns block populator list (trees, etc)
     * @param world
     * @return
     */
    @Override
    public List<BlockPopulator> getDefaultPopulators(World world) {
        return blockPopulatorsList;
    }

    //==================================================================================================================
    // SETTERS
    //==================================================================================================================

    /**
     * Adds block populator (trees, etc)
     * @param blockPopulator
     */
    public void addBlockPopulator(BlockPopulator blockPopulator) {
        blockPopulatorsList.add(blockPopulator);
    }


    /**
     * Removes block populator (trees, etc)
     * @param blockPopulator
     */
    public void removeBlockPopulator(BlockPopulator blockPopulator) {
        blockPopulatorsList.remove(blockPopulator);
    }

}
