// Copyright 2020 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0

package org.terasology.xmas;

import com.google.common.collect.ImmutableMap;
import org.terasology.core.world.generator.trees.TreeGenerator;
import org.terasology.math.LSystemRule;
import org.terasology.world.block.BlockUri;

public final class XMasTrees {
    private XMasTrees() {
        // no instances!
    }

    public static TreeGenerator firTree() {
        return new FirTreeGenerator().setLeafType("CoreAssets:DarkLeaf").setTrunkType("CoreAssets:PineTrunk");
    }
}
