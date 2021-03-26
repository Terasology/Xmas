// Copyright 2020 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0

package org.terasology.xmas.world;

import org.terasology.core.world.generator.trees.AbstractTreeGenerator;
import org.terasology.engine.utilities.random.Random;
import org.terasology.engine.world.block.Block;
import org.terasology.engine.world.block.BlockManager;
import org.terasology.engine.world.chunks.Chunk;

public class FirTreeGenerator extends AbstractTreeGenerator {

    public int smallestHeight;
    public int tallestHeight;
    public String trunkType;
    public String leafType;

    public FirTreeGenerator() {
        this(6, 15);
    }

    public FirTreeGenerator(int minHeight, int maxHeight) {
        smallestHeight = Math.min(minHeight, maxHeight);
        tallestHeight = maxHeight;
    }

    public FirTreeGenerator setTrunkType(String trunk) {
        trunkType = trunk;
        return this;
    }

    public FirTreeGenerator setLeafType(String leaf) {
        leafType = leaf;
        return this;
    }

    /**
     * Generate a simple fir tree from <i>segments</i>.
     *
     * <pre>
     *  □ □ □ ■ □ □ □
     *  □ □ □ ■ □ □ □    ----
     *  □ □ ■ ⛝ ■ □ □
     *  □ □ ■ ⛝ ■ □ □
     *  □ □ ■ ⛝ ■ □ □   ----
     *  □ ■ ■ ⛝ ■ ■ □
     *  □ ■ ■ ⛝ ■ ■ □
     *  □ ■ ■ ⛝ ■ ■ □   ----
     *  ■ ■ ■ ⛝ ■ ■ ■
     *  ■ ■ ■ ⛝ ■ ■ ■
     *  ■ ■ ■ ⛝ ■ ■ ■   ----
     *        ...
     *  □ □ □ ⛝ □ □ □
     * </pre>
     *
     * @param blockManager the block manager to resolve the block uris
     * @param view Chunk view
     * @param rand The random number generator
     * @param posX Relative position on the x-axis (wrt. the chunk)
     * @param posY Relative position on the y-axis (wrt. the chunk)
     * @param posZ Relative position on the z-axis (wrt. the chunk)
     */
    @Override
    public void generate(BlockManager blockManager, Chunk view, Random rand, int posX, int posY, int posZ) {
        int height = rand.nextInt(smallestHeight, tallestHeight + 1);

        Block trunk = blockManager.getBlock(trunkType);
        Block leaf = blockManager.getBlock(leafType);

        safelySetBlock(view, posX, posY, posZ, trunk);
        // ----
        int top = height - 2;   // top block for placing segments (without the two top-most foliage blocks)
        int rad = 0;            // the radius will increase every 3 levels by one
        for (int layer = 0; layer < top; layer++) {
            rad = (int) Math.floor(layer / 3f) + 1;
            // place the foliage
            if (rand.nextFloat() > 0.2f) {
                circle(posX, posY + top - layer, posZ, rad, view, leaf);
            }
            // set the trunk
            safelySetBlock(view, posX, posY + top - layer, posZ, trunk);
        }
        // ----
        safelySetBlock(view, posX, posY + height - 1, posZ, leaf);
        safelySetBlock(view, posX, posY + height, posZ, leaf);
    }

    private void circle(int posX, int posY, int posZ, int rad, Chunk view, Block block) {
        for (int z = 0; z <= rad; z++) {
            for (int x = 0; x * x + z * z <= (rad + 0.5) * (rad + 0.5); x++) {
                safelySetBlock(view, posX + x, posY, posZ + z, block);
                safelySetBlock(view, posX - x, posY, posZ + z, block);
                safelySetBlock(view, posX - x, posY, posZ - z, block);
                safelySetBlock(view, posX + x, posY, posZ - z, block);
            }
        }
    }
}
