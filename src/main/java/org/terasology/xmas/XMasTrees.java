// Copyright 2020 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0

package org.terasology.xmas;

import org.terasology.core.world.generator.trees.TreeGenerator;

public final class XMasTrees {
    private XMasTrees() {
        // no instances!
    }

    public static TreeGenerator firTree() {
        return new FirTreeGenerator().setLeafType("CoreAssets:DarkLeaf").setTrunkType("CoreAssets:PineTrunk");
    }
}
