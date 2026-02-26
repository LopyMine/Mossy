package net.lopymine.mossy.entrypoint;

//? if fabric {

import net.fabricmc.api.ClientModInitializer;

import net.lopymine.mossy.client.MossyClient;

public class ClientEntrypoint implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		MossyClient.onInitializeClient();
	}
}

//?} elif neoforge {
/*import net.lopymine.mossy.Mossy;

import net.lopymine.mossy.client.MossyClient;
import net.lopymine.mossy.modmenu.ModMenuIntegration;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;

@Mod(value = Mossy.MOD_ID, dist = Dist.CLIENT)
public class ClientEntrypoint {

	public ClientEntrypoint(ModContainer container) {
		MossyClient.onInitializeClient();
		ModMenuIntegration integration = new ModMenuIntegration();
		integration.register(container);
	}

}

*///?} elif forge {

/*import net.lopymine.mossy.client.MossyClient;
import net.lopymine.mossy.modmenu.ModMenuIntegration;
import net.minecraftforge.fml.ModLoadingContext;

public class ClientEntrypoint {

	public static void onInitializeClient() {
		MossyClient.onInitializeClient();
		ModMenuIntegration integration = new ModMenuIntegration();
		integration.register(ModLoadingContext.get().getActiveContainer());
	}

}

*///?}
