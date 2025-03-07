package net.lopymine.mossy.manager;

import dev.kikugie.stonecutter.StonecutterProject;
import dev.kikugie.stonecutter.build.StonecutterBuild;
import lombok.experimental.ExtensionMethod;
import org.gradle.api.Project;

import net.lopymine.mossy.MossyPlugin;

import java.util.*;
import org.jetbrains.annotations.NotNull;

@ExtensionMethod(MossyPlugin.class)
public class MossyStonecutterManager {

	public static void apply(@NotNull Project project) {
		StonecutterBuild stonecutter = project.getStonecutter();

		addSwap(stonecutter, project, "data.mod_version");
		addSwap(stonecutter, project, "data.mod_id");
		addSwap(stonecutter, project, "data.mod_name");
		addSwap(stonecutter, project, "dep.yacl");

		String currentProject = project.getCurrentProject();
		String[] multiLoaders = project.getMultiLoaders();
		List<String> allProjectVersions = stonecutter.getVersions().stream().map(StonecutterProject::getProject).toList();
		List<String> allMcVersions = stonecutter.getVersions().stream().map(StonecutterProject::getVersion).toList();

		stonecutter.consts(currentProject, allProjectVersions);
		stonecutter.consts(currentProject.substringBefore("+"), multiLoaders);
		stonecutter.consts(currentProject.substringSince("+"), allMcVersions);
	}

	private static void addSwap(StonecutterBuild stonecutter, @NotNull Project project, String propertyId) {
		String property = project.getProperty(propertyId);
		stonecutter.swap(propertyId.substringSince("."), getFormatted(property));
	}

	private static @NotNull String getFormatted(String modVersion) {
		return "\"%s\";".formatted(modVersion);
	}
}
