package net.lopymine.mossy.client;

import net.lopymine.mossylib.logger.MossyLogger;

import net.lopymine.mossy.Mossy;

public class MossyClient {

	public static MossyLogger LOGGER = Mossy.LOGGER.extend("Client");

	public static void onInitializeClient() {
		LOGGER.info("{} Client Initialized", Mossy.MOD_NAME);
	}
}
