package net.permafrozen.permafrozen.entity.nudifae;

import net.minecraft.util.Util;
import net.minecraft.util.collection.WeightedList;
import net.minecraft.world.biome.Biome;
import org.jetbrains.annotations.Nullable;

public enum NudifaeType {
    BLUE(0, 35),
    PINK(1, 30),
    PURPLE(2, 25),
    GREEN(3, 10),
    ALBINO(4, 1),
    MELANISTIC(5, 1);

    @Nullable
    private final Biome.Category biomeCategory = Biome.Category.OCEAN;

    public final int id;
    public final int weight;

    NudifaeType(int id, int weight) {

        this.id = id;
        this.weight = weight;

    }

    public static NudifaeType getTypeById(int id) {
        for (NudifaeType type : values()) {
            if (type.id == id) return type;
        }
        return NudifaeType.BLUE;
    }

    public static NudifaeType getRandom() {

        WeightedList<NudifaeType> possibleRarityTypes = Util.make(new WeightedList<>(), (list) -> {
            for (NudifaeType type : NudifaeType.values()) list.add(type, type.weight);
        });
        assert possibleRarityTypes.shuffle().stream().findFirst().isPresent();
        return possibleRarityTypes.shuffle().stream().findFirst().get();

    }
}
