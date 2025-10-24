package net.lopymine.mossy.client;

import net.lopymine.mossylib.logger.MossyLogger;

import net.fabricmc.api.ClientModInitializer;

import net.lopymine.mossy.Mossy;

public class MossyClient implements ClientModInitializer {

	public static MossyLogger LOGGER = Mossy.LOGGER.extend("Client");

	@Override
	public void onInitializeClient() {
		LOGGER.info("{} Client Initialized", Mossy.MOD_NAME);
	}
}
