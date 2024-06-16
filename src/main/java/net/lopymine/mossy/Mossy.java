package net.lopymine.mossy;

import net.fabricmc.api.ModInitializer;

import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Mossy implements ModInitializer {
	public static String MOD_ID = "mossy";
    public static final Logger LOGGER = LoggerFactory.getLogger("mossy");

	@Override
	public void onInitialize() {
		LOGGER.info("Mossy Initialized");
	}

	public static Identifier id(String path) {
		return Identifier.of(MOD_ID, path);
	}
}