package net.lopymine.mossy.modmenu.yacl;

import dev.isxander.yacl3.api.*;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

import net.lopymine.mossy.client.MossyClient;
import net.lopymine.mossy.config.MossyConfig;
import net.lopymine.mossy.modmenu.yacl.simple.*;
import net.lopymine.mossy.utils.ModMenuUtils;

import java.util.function.Function;

public class YACLConfigurationScreen {

	private static final Function<Boolean, Text> ENABLED_OR_DISABLE_FORMATTER = ModMenuUtils.getEnabledOrDisabledFormatter();

	private YACLConfigurationScreen() {
		throw new IllegalStateException("Screen class");
	}

	public static Screen createScreen(Screen parent) {
		MossyConfig defConfig = new MossyConfig();
		MossyConfig config = MossyClient.getConfig();

		return SimpleYACLScreenBuilder.startBuilder(parent, config::save)
				.categories(
						getGeneralCategory(defConfig, config),
						SimpleCollector.getIf(
								getSecretCategory(defConfig, config),
								config::isMossy
						))
				.build();
	}

	private static ConfigCategory getSecretCategory(MossyConfig defConfig, MossyConfig config) {
		return SimpleCategoryBuilder.startBuilder("secret_category")
				.groups(getSecretGroup(defConfig, config))
				.build();
	}

	private static ConfigCategory getGeneralCategory(MossyConfig defConfig, MossyConfig config) {
		return SimpleCategoryBuilder.startBuilder("general")
				.groups(getMossyGroup(defConfig, config))
				.build();
	}

	private static OptionGroup getSecretGroup(MossyConfig defConfig, MossyConfig config) {
		return SimpleGroupBuilder.createBuilder("secret_group").options(
				SimpleOptionBuilder.getFloatOptionAsSlider(
						"secret_option",
						-180.0F, 180.0F, 1.0F,
						defConfig.getSecret(), config::getSecret, config::setSecret
				).build()
		).build();
	}

	private static OptionGroup getMossyGroup(MossyConfig defConfig, MossyConfig config) {
		return SimpleGroupBuilder.createBuilder("mossy_group").options(
				SimpleOptionBuilder.getBooleanOption(
						"mossy_option",
						defConfig.isMossy(), config::isMossy, config::setMossy,
						ENABLED_OR_DISABLE_FORMATTER::apply, SimpleContent.IMAGE
				).build()
		).build();
	}

}


