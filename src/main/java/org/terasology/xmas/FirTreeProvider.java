// Copyright 2020 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0

package org.terasology.xmas;

import com.google.common.base.Predicate;
import org.joml.Vector3i;
import org.terasology.core.world.CoreBiome;
import org.terasology.core.world.generator.facetProviders.DefaultTreeProvider;
import org.terasology.core.world.generator.facets.BiomeFacet;
import org.terasology.core.world.generator.facets.TreeFacet;
import org.terasology.world.generation.Border3D;
import org.terasology.world.generation.Facet;
import org.terasology.world.generation.FacetBorder;
import org.terasology.world.generation.FacetProviderPlugin;
import org.terasology.world.generation.GeneratingRegion;
import org.terasology.world.generation.Requires;
import org.terasology.world.generation.Updates;
import org.terasology.world.generation.facets.SeaLevelFacet;
import org.terasology.world.generation.facets.SurfacesFacet;
import org.terasology.world.generator.plugin.RegisterPlugin;

import java.util.List;

@RegisterPlugin
@Updates(@Facet(TreeFacet.class))
@Requires({
        @Facet(value = SeaLevelFacet.class, border = @FacetBorder(sides = 13)),
        @Facet(value = SurfacesFacet.class, border = @FacetBorder(sides = 13 + 1, bottom = 32 + 11)),
        @Facet(value = BiomeFacet.class, border = @FacetBorder(sides = 13))
})
public class FirTreeProvider extends DefaultTreeProvider implements FacetProviderPlugin {

    public FirTreeProvider() {
        register(CoreBiome.MOUNTAINS, XMasTrees.firTree(), 0.05f);
        register(CoreBiome.FOREST, XMasTrees.firTree(), 0.3f);
        register(CoreBiome.SNOW, XMasTrees.firTree(), 0.2f);
        register(CoreBiome.PLAINS, XMasTrees.firTree(), 0.1f);
    }

    @Override
    public void process(GeneratingRegion region) {
        SurfacesFacet surface = region.getRegionFacet(SurfacesFacet.class);
        BiomeFacet biome = region.getRegionFacet(BiomeFacet.class);

        List<Predicate<Vector3i>> filters = getFilters(region);

        // these values are derived from the maximum tree extents as
        // computed by the TreeTests class. Birch is the highest with 32
        // and Pine has 13 radius.
        // These values must be identical in the class annotations.
        int maxRad = 13;
        int maxHeight = 32;
        Border3D borderForTreeFacet = region.getBorderForFacet(TreeFacet.class);
        TreeFacet facet = new TreeFacet(region.getRegion(), borderForTreeFacet.extendBy(1, maxHeight, maxRad));

        populateFacet(facet, surface, biome, filters);

        region.setRegionFacet(TreeFacet.class, facet);
    }
}