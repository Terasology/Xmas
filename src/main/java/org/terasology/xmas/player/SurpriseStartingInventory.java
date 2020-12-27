// Copyright 2020 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0

package org.terasology.xmas.player;

import com.google.common.collect.Lists;
import org.terasology.entitySystem.entity.EntityManager;
import org.terasology.entitySystem.entity.EntityRef;
import org.terasology.entitySystem.event.EventPriority;
import org.terasology.entitySystem.event.ReceiveEvent;
import org.terasology.entitySystem.prefab.PrefabManager;
import org.terasology.entitySystem.systems.BaseComponentSystem;
import org.terasology.entitySystem.systems.RegisterMode;
import org.terasology.entitySystem.systems.RegisterSystem;
import org.terasology.logic.common.DisplayNameComponent;
import org.terasology.logic.inventory.InventoryManager;
import org.terasology.logic.inventory.StartingInventoryComponent;
import org.terasology.logic.inventory.StartingInventoryComponent.InventoryItem;
import org.terasology.logic.players.event.OnPlayerSpawnedEvent;
import org.terasology.registry.In;
import org.terasology.utilities.random.FastRandom;

import java.util.Arrays;
import java.util.List;

@RegisterSystem(RegisterMode.AUTHORITY)
public class SurpriseStartingInventory extends BaseComponentSystem {

    private static final FastRandom RNG = new FastRandom("MerryGooeyChristmas".hashCode());

    @In
    InventoryManager inventoryManager;

    @In
    EntityManager entityManager;

    @In
    PrefabManager prefabManager;

    private EntityRef santaGooey = EntityRef.NULL;

    private final List<String> presents;

    public SurpriseStartingInventory() {
        presents = Lists.newArrayList(
                "CoreAssets:pickaxeImproved",
                "CoreAssets:axeImproved",
                "CoreAssets:shovelImproved");
    }

    @Override
    public void postBegin() {
        final DisplayNameComponent name = new DisplayNameComponent();
        name.name = "Santa Gooey";
        santaGooey = entityManager.create(name);
    }

    /**
     * Give a surprise present to each player spawning.
     *
     * @param event notification that a player spawned
     * @param player the player that has been spawned
     */
    @ReceiveEvent(priority = EventPriority.PRIORITY_HIGH)
    public void onPlayerSpawn(OnPlayerSpawnedEvent event, EntityRef player) {
        player.upsertComponent(StartingInventoryComponent.class, maybeComponent -> {
            StartingInventoryComponent startingInventory = maybeComponent.orElse(new StartingInventoryComponent());
            startingInventory.items.add(chest(randomItem()));
            return startingInventory;
        });
    }

    private InventoryItem chest(InventoryItem... content) {
        final InventoryItem chest = new InventoryItem();
        chest.uri = "CoreAdvancedAssets:Chest";
        chest.items.addAll(Arrays.asList(content));
        return chest;
    }

    private InventoryItem randomItem() {
        String uri = presents.get(RNG.nextInt(presents.size()));

        final InventoryItem inventoryItem = new InventoryItem();
        inventoryItem.uri = uri;
        return inventoryItem;
    }

}
