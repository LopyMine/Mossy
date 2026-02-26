package net.lopymine.mossy.entrypoint;

//? if fabric {

import net.lopymine.mossy.Mossy;

import net.fabricmc.api.ModInitializer;

public class CommonEntrypoint implements ModInitializer {

	@Override
	public void onInitialize() {
		Mossy.onInitialize();
	}
}

//?} elif neoforge {
/*import net.lopymine.mossy.Mossy;

import net.neoforged.fml.common.Mod;

@Mod(Mossy.MOD_ID)
public class CommonEntrypoint {

	public CommonEntrypoint() {
		Mossy.onInitialize();
	}

}

*///?} elif forge {
/*import net.lopymine.mossy.Mossy;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;

@Mod(Mossy.MOD_ID)
public class CommonEntrypoint {

	public CommonEntrypoint() {
		Mossy.onInitialize();
		DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> ClientEntrypoint::onInitializeClient);
	}

}

*///?}

