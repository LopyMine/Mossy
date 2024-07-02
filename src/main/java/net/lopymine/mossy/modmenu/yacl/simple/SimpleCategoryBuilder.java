package net.lopymine.mossy.modmenu.yacl.simple;

import dev.isxander.yacl3.api.*;
import dev.isxander.yacl3.api.ConfigCategory.Builder;
import net.minecraft.text.Text;

import net.lopymine.mossy.utils.ModMenuUtils;

public class SimpleCategoryBuilder {

	private final Builder builder;

	private SimpleCategoryBuilder(String categoryId) {
		String categoryKey = ModMenuUtils.getCategoryKey(categoryId);
		Text categoryName = ModMenuUtils.getName(categoryKey);
		this.builder = ConfigCategory.createBuilder().name(categoryName);
	}

	public static SimpleCategoryBuilder startBuilder(String categoryId) {
		return new SimpleCategoryBuilder(categoryId);
	}

	public SimpleCategoryBuilder groups(OptionGroup... groups) {
		for (OptionGroup group : groups) {
			if (group == null) {
				continue;
			}
			this.builder.group(group);
		}
		return this;
	}

	public SimpleCategoryBuilder options(Option<?>... options) {
		for (Option<?> option : options) {
			if (option == null) {
				continue;
			}
			this.builder.option(option);
		}
		return this;
	}

	public ConfigCategory build() {
		return this.builder.build();
	}
}
