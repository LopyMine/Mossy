package net.lopymine.mossy.modmenu;

import net.lopymine.mossy.yacl.YACLConfigurationScreen;
import net.lopymine.mossylib.MossyLib;
import net.lopymine.mossylib.modmenu.AbstractModMenuIntegration;
import net.minecraft.client.gui.screen.Screen;

public class ModMenuIntegration extends AbstractModMenuIntegration {

	@Override
	protected String getModId() {
		return MossyLib.MOD_ID;
	}

	@Override
	protected Screen createConfigScreen(Screen screen) {
		return YACLConfigurationScreen.createScreen(screen);
	}
}
