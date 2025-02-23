package net.lopymine.mossy;

import lombok.experimental.ExtensionMethod;
import org.gradle.api.*;
import org.gradle.api.artifacts.dsl.DependencyHandler;

import org.jetbrains.annotations.NotNull;

@ExtensionMethod(MossyPlugin.class)
public class MossyDependenciesManager {

	private static void addDependencies(MossyDependenciesExtension extension, Project project) {
		String minecraft = extension.getMinecraft();
		String mappings = extension.getMappings();
		String fabricApi = extension.getFabricApi();
		String fabricLoader = extension.getFabricLoader();
		String modMenu = extension.getModMenu();
		String yacl = extension.getYacl();
		String lombok = extension.getLombok();

		addRepository(project, "Quilt", "https://maven.quiltmc.org/repository/release/");
		addRepository(project, "Sonatype", "https://oss.sonatype.org/content/repositories/snapshots/");
		addRepository(project, "Terraformers", "https://maven.terraformersmc.com/");
		addRepository(project, "YACL", "https://maven.isxander.dev/releases");
		addRepository(project, "Nucleoid", "https://maven.nucleoid.xyz/");

		DependencyHandler dependencies = project.getDependencies();

		dependencies.add("minecraft", "com.mojang:minecraft:%s".formatted(minecraft));
		dependencies.add("mappings", "net.fabricmc:yarn:%s:v2".formatted(mappings));
		dependencies.add("modImplementation", "net.fabricmc.fabric-api:fabric-api:%s".formatted(fabricApi));
		dependencies.add("modImplementation", "net.fabricmc:fabric-loader:%s".formatted(fabricLoader));
		dependencies.add("modImplementation", "com.terraformersmc:modmenu:%s".formatted(modMenu));
		//dependencies.add("modImplementation", "com.mojang:minecraft:${mcVersion}");
	}

	private static void addRepositories(Project project) {
		addRepository(project, "Quilt", "https://maven.quiltmc.org/repository/release/");
		addRepository(project, "Sonatype", "https://oss.sonatype.org/content/repositories/snapshots/");
		addRepository(project, "Terraformers", "https://maven.terraformersmc.com/");
		addRepository(project, "YACL", "https://maven.isxander.dev/releases");
		addRepository(project, "Nucleoid", "https://maven.nucleoid.xyz/");
	}

	private static void addRepository(Project project, String name, String url) {
		project.getRepositories().maven((repository) -> {
			repository.setName(name);
			repository.setUrl(url);
		});
	}

	public static void apply(@NotNull Project project) {
		MossyDependenciesExtension mossyDependencies = project.getExtensions().create("mossyDependencies", MossyDependenciesExtension.class);

		addRepositories(project);
		project.getGradle().addProjectEvaluationListener(new ProjectEvaluationListener() {
			@Override
			public void beforeEvaluate(@NotNull Project project) {
			}

			@Override
			public void afterEvaluate(@NotNull Project project, @NotNull ProjectState state) {
				MossyDependenciesManager.addDependencies(mossyDependencies, project);
			}
		});
	}
}
