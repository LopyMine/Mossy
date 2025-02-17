package net.lopymine.mossy;

import dev.kikugie.stonecutter.*;
import lombok.experimental.ExtensionMethod;
import org.gradle.api.*;
import org.gradle.api.plugins.PluginContainer;
import org.gradle.api.provider.Provider;

import net.fabricmc.loom.LoomGradleExtension;
import net.fabricmc.loom.api.LoomGradleExtensionAPI;

import net.lopymine.mossy.tasks.*;

import java.io.*;
import java.util.*;
import org.jetbrains.annotations.NotNull;

public class MossyPlugin implements Plugin<Project> {

	@Override
	public void apply(@NotNull Project project) {
		PluginContainer plugins = project.getPlugins();
		plugins.apply("fabric-loom");
		plugins.apply("me.modmuss50.mod-publish-plugin");


		project.getExtensions().configure(LoomGradleExtensionAPI.class, (loom) -> {
			MossyLoomManager.apply(project, loom);
		});

		project.getTasks().register("generatePublishWorkflowsForEachVersion", GeneratePublishWorkflowsForEachVersionTask.class, (task) -> {
			task.setGroup("mossy");
		});
		project.getTasks().register("generatePersonalProperties", GeneratePersonalPropertiesTask.class, (task) -> {
			task.setGroup("mossy");
		});
		project.getTasks().register("regenerateRunConfigurations", RegenerateRunConfigsTask.class, (task) -> {
			task.setGroup("mossy");
			task.finalizedBy("ideaSyncTask");
		});
		project.getTasks().register("buildAndCollect", BuildAndCollectTask.class, (task) -> {
			task.setGroup("build");
			task.dependsOn("rebuildLibs");
		});
		project.getTasks().register("rebuildLibs", RebuildLibsTask.class, (task) -> {
			task.setGroup("build");
			task.finalizedBy("build");
		});
	}

	public static Properties getPersonalProperties(@NotNull Project project) {
		File file = project.getRootProject().file("personal/personal.properties");
		return getProperties(file);
	}

	private static @NotNull Properties getProperties(File file) {
		Properties properties = new Properties();

		if (file.exists()) {
			try {
				properties.load(new FileInputStream(file));
			} catch (Exception ignored) {
			}
		}

		return properties;
	}

	public static String getCurrentVersion(@NotNull Project project) {
		return ((StonecutterBuild) project.getExtensions().getByName("stonecutter")).getCurrent().getProject();
	}

	public static String getProperty(@NotNull Project project, String id) {
		return project.getProperties().get(id).toString();
	}

	public static String[] getMultiVersions(@NotNull Project project) {
		return getProperty(project, "multi_versions").split(" ");
	}

	public static File getRootFile(@NotNull Project project, String path) {
		return project.getRootProject().file(path);
	}

}
