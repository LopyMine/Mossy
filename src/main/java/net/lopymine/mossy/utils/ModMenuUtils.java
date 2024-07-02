package net.lopymine.mossy.utils;

import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import net.lopymine.mossy.Mossy;
import net.lopymine.mossy.modmenu.yacl.simple.SimpleContent;

public class ModMenuUtils {

	private ModMenuUtils() {
		throw new IllegalStateException("Utility class");
	}

	public static String getOptionKey(String optionId) {
		return String.format("%s.modmenu.option.%s", Mossy.MOD_ID, optionId);
	}

	public static String getCategoryKey(String optionId) {
		return String.format("%s.modmenu.category.%s", Mossy.MOD_ID, optionId);
	}

	public static String getGroupKey(String optionId) {
		return String.format("%s.modmenu.group.%s", Mossy.MOD_ID, optionId);
	}

	public static Text getName(String key) {
		return Text.translatable(key + ".name");
	}

	public static Text getDescription(String key) {
		return Text.translatable(key + ".description");
	}

	public static Identifier getContentId(SimpleContent content, String optionId) {
		return Mossy.id(String.format("textures/config/%s/%s.%s", content.getFolder(), optionId, content.getFileExtension()));
	}

	public static Text getModTitle() {
		return Text.translatable(String.format("%s.modmenu.title", Mossy.MOD_ID));
	}
}
