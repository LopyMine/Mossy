package net.lopymine.mossy.modmenu.yacl.simple;

import dev.isxander.yacl3.api.*;
import dev.isxander.yacl3.api.OptionDescription.Builder;
import dev.isxander.yacl3.api.controller.*;

import net.lopymine.mossy.utils.ModMenuUtils;

import java.util.function.*;

public class SimpleOptionBuilder {

	public static Option.Builder<Boolean> getBooleanOption(String optionId, boolean defValue, Supplier<Boolean> getter, Consumer<Boolean> setter, ValueFormatter<Boolean> formatter, SimpleContent content) {
		return getOption(optionId, defValue, getter, setter, content)
				.controller(o -> BooleanControllerBuilder.create(o).coloured(true).formatValue(formatter));
	}

	public static Option.Builder<Float> getFloatOptionAsSlider(String optionId, float min, float max, float step, float defValue, Supplier<Float> getter, Consumer<Float> setter) {
		return getOption(optionId, defValue, getter, setter, SimpleContent.NONE)
				.controller(o -> FloatSliderControllerBuilder.create(o).range(min, max).step(step));
	}

	public static <C> Option.Builder<C> getOption(String optionId, C defValue, Supplier<C> getter, Consumer<C> setter, SimpleContent content) {
		String optionKey = ModMenuUtils.getOptionKey(optionId);

		Builder descriptionBuilder = OptionDescription.createBuilder().text(ModMenuUtils.getDescription(optionKey));
		if (content == SimpleContent.IMAGE) {
			descriptionBuilder.image(ModMenuUtils.getContentId(content, optionId), 600, 600);
		}
		if (content == SimpleContent.WEBP) {
			descriptionBuilder.webpImage(ModMenuUtils.getContentId(content, optionId));
		}

		return Option.<C>createBuilder()
				.name(ModMenuUtils.getName(optionKey))
				.description(descriptionBuilder.build())
				.binding(defValue, getter, setter);
	}

	public Option<?>[] collect(Option<?>... options) {
		return options;
	}

	public <T> Option<T> getIf(Option<T> option, BooleanSupplier condition) {
		if (condition.getAsBoolean()) {
			return option;
		}
		return null;
	}
}
