package net.lopymine.mossy;

import dev.kikugie.stonecutter.*;
import lombok.Getter;
import me.modmuss50.mpp.ModPublishExtension;
import org.gradle.api.*;
import org.gradle.api.plugins.PluginContainer;

import net.fabricmc.loom.api.LoomGradleExtensionAPI;

import net.lopymine.mossy.multi.MultiVersion;
import net.lopymine.mossy.tasks.*;

import java.io.*;
import java.util.*;
import org.jetbrains.annotations.NotNull;

@Getter
public class MossyPlugin implements Plugin<Project> {

	private MultiVersion projectMultiVersion;
	private int javaVersionIndex;
	private JavaVersion javaVersion;

	@Override
	public void apply(@NotNull Project project) {
		PluginContainer plugins = project.getPlugins();
		plugins.apply("fabric-loom");
		plugins.apply("me.modmuss50.mod-publish-plugin");

		this.projectMultiVersion = getProjectMultiVersion(project);
		this.javaVersionIndex = getJavaVersion(project);
		this.javaVersion = JavaVersion.toVersion(this.javaVersionIndex);

		MossyDependenciesManager.apply(project);
		MossyJavaManager.apply(project, this);
		MossyStonecutterManager.apply(project);

		project.getExtensions().configure(LoomGradleExtensionAPI.class, (loom) -> {
			MossyLoomManager.apply(project, loom);
		});

		project.getExtensions().configure(ModPublishExtension.class, (mpe) -> {
			MossyModPublishManager.apply(project, mpe, this);
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

	public static int getJavaVersion(Project project) {
		String currentMCVersion = getCurrentMCVersion(project);
		StonecutterBuild stonecutter = getStonecutter(project);
		return stonecutter.compare("1.20.5", currentMCVersion) == 1 ?
				stonecutter.compare("1.18", currentMCVersion) == 1 ?
						stonecutter.compare("1.16.5", currentMCVersion) == 1 ?
								8
								:
								16
						:
						17
				:
				21;
	}


	public static MultiVersion getProjectMultiVersion(@NotNull Project currentProject) {
		String currentMCVersion = getCurrentMCVersion(currentProject);

		String[] versions = getProperty(currentProject, "publication_versions").split(" ");
		for (String version : versions) {
			String[] split = version.substring(0, version.length()-1).split("\\[");
			String project = split[0];
			if (Objects.equals(project, currentMCVersion)) {
				String supportedVersionsString = split[1];
				if (supportedVersionsString.contains("-")) {
					String[] supportedVersions = supportedVersionsString.split("-");
					return new MultiVersion(currentMCVersion, supportedVersions[0], supportedVersions[1]);
				} else if (supportedVersionsString.contains(".")) {
					return new MultiVersion(currentMCVersion, currentMCVersion, supportedVersionsString);
				} else {
					int a = project.indexOf(".");
					int i = project.lastIndexOf(".");
					if (a == i) {
						i = project.length();
					}
					String p = project.substring(0, i);
					String supportedMaxVersion = "%s.%s".formatted(p, supportedVersionsString);
					return new MultiVersion(currentMCVersion, currentMCVersion, supportedMaxVersion);
				}
			}
		}
		return new MultiVersion(currentMCVersion, currentMCVersion, currentMCVersion);
	}

	public static Properties getPersonalProperties(@NotNull Project project) {
		File file = project.getRootProject().file("personal/personal.properties");
		return getProperties(file);
	}

	public static @NotNull Properties getProperties(File file) {
		Properties properties = new Properties();

		if (file.exists()) {
			try {
				properties.load(new FileInputStream(file));
			} catch (Exception ignored) {
			}
		}

		return properties;
	}

	public static String getCurrentMCVersion(@NotNull Project project) {
		return getStonecutter(project).getCurrent().getProject();
	}

	public static @NotNull StonecutterBuild getStonecutter(@NotNull Project project) {
		return (StonecutterBuild) project.getExtensions().getByName("stonecutter");
	}

	public static String getProperty(@NotNull Project project, String id) {
		Map<String, ?> properties = project.getProperties();
		if (!properties.containsKey(id)) {
			throw new IllegalArgumentException("Missing important property with id \"%s\" !".formatted(id));
		}
		return properties.get(id).toString();
	}

	public static String[] getMultiVersions(@NotNull Project project) {
		return getProperty(project, "multi_versions").split(" ");
	}

	public static File getRootFile(@NotNull Project project, String path) {
		return project.getRootProject().file(path);
	}

}
