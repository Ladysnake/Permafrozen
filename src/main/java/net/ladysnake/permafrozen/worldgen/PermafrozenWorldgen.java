package net.ladysnake.permafrozen.worldgen;

import net.ladysnake.permafrozen.Permafrozen;
import net.ladysnake.permafrozen.worldgen.PermafrozenChunkGenerator;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class PermafrozenWorldgen {
	public static void init() {
		Registry.register(Registry.CHUNK_GENERATOR, new Identifier(Permafrozen.MOD_ID, "permafrozen"), PermafrozenChunkGenerator.CODEC);
		Registry.register(Registry.BIOME_SOURCE, new Identifier(Permafrozen.MOD_ID, "permafrozen"), PermafrozenBiomeSource.CODEC);
	}
}
