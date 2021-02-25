package net.cherryflavor.api.spigot.world.generation.populators;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.TreeType;
import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;

import java.util.Random;

/**
 * Created on 2/21/2021
 * Time 9:51 PM
 */

public class TreePopulator extends BlockPopulator {

    int treeAmount = 4;
    TreeType treeType;

    //==================================================================================================================
    // CONSTRUCTORS
    //==================================================================================================================

    public TreePopulator(int treeAmount, TreeType treeType) {
        this.treeAmount = treeAmount;
        this.treeType = treeType;
    }

    //==================================================================================================================
    // GETTERS
    //==================================================================================================================

    @Override
    public void populate(World world, Random random, Chunk chunk) {
        if (random.nextBoolean()) {
            int amount = random.nextInt(treeAmount) + 1; //amount of trees

            for (int i = 1; i < amount; i++) {
                int X = random.nextInt(15);
                int Z = random.nextInt(15);
                int Y;

                for (Y = world.getMaxHeight()-1; chunk.getBlock(X, Y, Z).getType() == Material.AIR; Y--);
                world.generateTree(chunk.getBlock(X,Y,Z).getLocation(), treeType);
            }

        }
    }

    //==================================================================================================================
    // SETTERS
    //==================================================================================================================


}
