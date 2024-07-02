package net.lopymine.mossy.modmenu.yacl.simple;

import dev.isxander.yacl3.api.*;
import dev.isxander.yacl3.api.YetAnotherConfigLib.Builder;
import dev.isxander.yacl3.gui.YACLScreen;
import net.minecraft.client.gui.screen.Screen;

import net.lopymine.mossy.utils.ModMenuUtils;

import java.util.function.Consumer;

public class SimpleYACLScreenBuilder {

	private final Builder builder;
	private final Screen parent;

	public SimpleYACLScreenBuilder(Screen parent, Runnable onSave, Consumer<YACLScreen> onInit) {
		this.builder = YetAnotherConfigLib.createBuilder()
				.title(ModMenuUtils.getModTitle())
				.save(onSave)
				.screenInit(onInit);
		this.parent  = parent;
	}

	public static SimpleYACLScreenBuilder startBuilder(Screen parent, Runnable onSave) {
		return new SimpleYACLScreenBuilder(parent, onSave, (yaclScreen) -> {});
	}

	public static SimpleYACLScreenBuilder startBuilder(Screen parent, Runnable onSave, Consumer<YACLScreen> onInit) {
		return new SimpleYACLScreenBuilder(parent, onSave, onInit);
	}

	public SimpleYACLScreenBuilder categories(ConfigCategory... categories) {
		for (ConfigCategory category : categories) {
			if (category == null) {
				continue;
			}
			this.builder.category(category);
		}
		return this;
	}

	public Screen build() {
		return this.builder.build().generateScreen(this.parent);
	}
}
