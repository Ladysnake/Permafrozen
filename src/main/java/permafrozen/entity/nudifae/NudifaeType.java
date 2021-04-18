package permafrozen.entity.nudifae;

import net.minecraft.util.Util;
import net.minecraft.util.WeightedList;
import net.minecraft.world.biome.Biome;

import javax.annotation.Nullable;
import java.util.Random;

public enum NudifaeType {

    BLUE(0, 35),
    PINK(1, 30),
    PURPLE(2, 25),
    GREEN(3, 10),
    ALBINO(4, 1);

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

    public static NudifaeType getRandom(Random rand) {

        WeightedList<NudifaeType> possibleRarityTypes = Util.make(new WeightedList<>(), (list) -> {
            for (NudifaeType type : NudifaeType.values()) list.func_226313_a_(type, type.weight);
        });

        NudifaeType type = possibleRarityTypes.func_226318_b_(rand);

        return type;

    }

}