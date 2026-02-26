package net.lopymine.mossy.yacl;

import lombok.experimental.ExtensionMethod;
import net.lopymine.mossy.Mossy;
import net.lopymine.mossylib.yacl.api.*;
import net.lopymine.mossylib.yacl.extension.SimpleOptionExtension;
import net.minecraft.client.gui.screens.Screen;

import net.lopymine.mossy.config.MossyConfig;

@ExtensionMethod(SimpleOptionExtension.class)
public class YACLConfigurationScreen {

	private YACLConfigurationScreen() {
		throw new IllegalStateException("Screen class");
	}

	public static Screen createScreen(Screen parent) {
		MossyConfig defConfig = MossyConfig.getNewInstance();
		MossyConfig config = MossyConfig.getInstance();

		return SimpleYACLScreen.startBuilder(Mossy.MOD_ID, parent, config::saveAsync)
				.categories(getGeneralCategory(defConfig, config))
				.build();
	}

	private static SimpleCategory getGeneralCategory(MossyConfig defConfig, MossyConfig config) {
		return SimpleCategory.startBuilder("general")
				.groups(getMainGroup(defConfig, config));
	}

	private static SimpleGroup getMainGroup(MossyConfig defConfig, MossyConfig config) {
		return SimpleGroup.startBuilder("main").options(
				SimpleOption.<Boolean>startBuilder("mod_enabled")
						.withBinding(defConfig.isModEnabled(), config::isModEnabled, config::setModEnabled, true)
						.withController()
						.withDescription(SimpleContent.IMAGE)
		);
	}

}


