// Copyright 2020 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0

package org.terasology.xmas;

import org.terasology.core.world.generator.trees.AbstractTreeGenerator;
import org.terasology.utilities.random.Random;
import org.terasology.world.block.Block;
import org.terasology.world.block.BlockManager;
import org.terasology.world.chunks.CoreChunk;

public class FirTreeGenerator extends AbstractTreeGenerator {

    public int smallestHeight;
    public int tallestHeight;
    public String trunkType;
    public String leafType;

    public FirTreeGenerator()
    {
        this(5, 7);
    }

    public FirTreeGenerator(int minHeight, int maxHeight)
    {
        if (minHeight > maxHeight) minHeight = maxHeight;
        smallestHeight = minHeight;
        tallestHeight = maxHeight;
    }

    public FirTreeGenerator setTrunkType(String trunk)
    {
        trunkType = trunk;
        return this;
    }

    public FirTreeGenerator setLeafType(String leaf)
    {
        leafType = leaf;
        return this;
    }

    @Override
    public void generate(BlockManager blockManager, CoreChunk view, Random rand, int posX, int posY, int posZ) {
        int height = rand.nextInt(smallestHeight, tallestHeight + 1);
        Block trunk = blockManager.getBlock(trunkType);
        Block leaf = blockManager.getBlock(leafType);
        for (int q = 0; q <= height; q++)
        {
            safelySetBlock(view, posX, posY + q, posZ, trunk);
            int rad = (int) Math.floor(height - q / 4f);
            if (rand.nextBoolean() || rand.nextBoolean()) {
                for (int z = 0; z < rad; z++) {
                    for (int x = 0; x * x + z * z <= (rad + 0.5) * (rad + 0.5); x++) {
                        safelySetBlock(view, posX + x, posY + q, posZ + z, leaf);
                        safelySetBlock(view, posX - x, posY + q, posZ + z, leaf);
                        safelySetBlock(view, posX - x, posY + q, posZ - z, leaf);
                        safelySetBlock(view, posX + x, posY + q, posZ - z, leaf);
                    }
                }
            }
        }
        safelySetBlock(view, posX, posY + height + 1, posZ, leaf);
    }
}
