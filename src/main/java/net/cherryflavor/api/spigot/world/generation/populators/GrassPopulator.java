package net.cherryflavor.api.spigot.world.generation.populators;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;

import java.util.Random;

/**
 * Created on 2/21/2021
 * Time 10:00 PM
 */

public class GrassPopulator extends BlockPopulator {

    int grassAmount = 0;

    //==================================================================================================================
    // CONSTRUCTORS
    //==================================================================================================================

    public GrassPopulator(int grassAmount) {
        this.grassAmount = grassAmount;
    }

    //==================================================================================================================
    // GETTERS
    //==================================================================================================================

    @Override
    public void populate(World world, Random random, Chunk chunk) {
        if (random.nextBoolean()) {
            int amount = random.nextInt(grassAmount) + 1;

            for (int i = 0; i < amount; i++) {
                int X = random.nextInt(15);
                int Z = random.nextInt(15);
                int Y;

                for (Y = world.getMaxHeight()-1; chunk.getBlock(X, Y, Z).getType() == Material.AIR; Y--);
                chunk.getBlock(X, Y+1, Z).setType(Material.GRASS);
            }

        }
    }

    //==================================================================================================================
    // SETTERS
    //==================================================================================================================


}
