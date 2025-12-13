package net.lopymine.mossy.entrypoint;

//? if fabric {

import net.fabricmc.api.ClientModInitializer;

import net.lopymine.mossy.client.MossyClient;

public class MossyClientEntrypoint implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		MossyClient.onInitializeClient();
	}
}

//?} elif neoforge {
/*import net.lopymine.mossy.Mossy;

import net.lopymine.mossy.client.MossyClient;
import net.lopymine.mossy.modmenu.MossyModMenuIntegration;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;

@Mod(value = Mossy.MOD_ID, dist = Dist.CLIENT)
public class MossyClientEntrypoint {

	public MossyClientEntrypoint(ModContainer container) {
		MossyClient.onInitializeClient();
		MossyModMenuIntegration integration = new MossyModMenuIntegration();
		integration.register(container);
	}

}

*///?} elif forge {

/*import net.lopymine.mossy.client.MossyClient;
import net.lopymine.mossy.modmenu.MossyModMenuIntegration;
import net.minecraftforge.fml.ModLoadingContext;

public class MossyClientEntrypoint {

	public static void onInitializeClient() {
		MossyClient.onInitializeClient();
		MossyModMenuIntegration integration = new MossyModMenuIntegration();
		integration.register(ModLoadingContext.get().getActiveContainer());
	}

}

*///?}
