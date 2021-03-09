// Copyright 2020 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0

package org.terasology.xmas.world;

import com.google.common.base.Predicate;
import org.joml.Vector3i;
import org.terasology.core.world.CoreBiome;
import org.terasology.core.world.generator.facetProviders.DefaultTreeProvider;
import org.terasology.core.world.generator.facets.BiomeFacet;
import org.terasology.core.world.generator.facets.TreeFacet;
import org.terasology.engine.world.generation.Border3D;
import org.terasology.engine.world.generation.Facet;
import org.terasology.engine.world.generation.FacetBorder;
import org.terasology.engine.world.generation.FacetProviderPlugin;
import org.terasology.engine.world.generation.GeneratingRegion;
import org.terasology.engine.world.generation.Requires;
import org.terasology.engine.world.generation.Updates;
import org.terasology.engine.world.generation.facets.SeaLevelFacet;
import org.terasology.engine.world.generation.facets.SurfacesFacet;
import org.terasology.engine.world.generator.plugin.RegisterPlugin;

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

        // these values are derived from the implementation of FirTreeGenerator.
        int maxHeight = 15;
        int maxRad = maxHeight / 3;
        Border3D borderForTreeFacet = region.getBorderForFacet(TreeFacet.class);
        TreeFacet facet = new TreeFacet(region.getRegion(), borderForTreeFacet.extendBy(1, maxHeight, maxRad));

        populateFacet(facet, surface, biome, filters);

        region.setRegionFacet(TreeFacet.class, facet);
    }
}
