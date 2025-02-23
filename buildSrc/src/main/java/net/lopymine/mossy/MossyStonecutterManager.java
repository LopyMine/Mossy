package net.lopymine.mossy;

import dev.kikugie.stonecutter.StonecutterBuild;
import lombok.experimental.ExtensionMethod;
import org.gradle.api.Project;

import org.jetbrains.annotations.NotNull;

@ExtensionMethod(MossyPlugin.class)
public class MossyStonecutterManager {

	public static void apply(@NotNull Project project) {
		StonecutterBuild stonecutter = project.getStonecutter();

		addSwap(stonecutter, project, "mod_version");
		addSwap(stonecutter, project, "mod_id");
		addSwap(stonecutter, project, "mod_name");
	}

	private static void addSwap(StonecutterBuild stonecutter, @NotNull Project project, String propertyId) {
		String property = project.getProperty(propertyId);
		stonecutter.swap(propertyId, getFormatted(property));
	}

	private static @NotNull String getFormatted(String modVersion) {
		return "\"%s\";".formatted(modVersion);
	}
}
