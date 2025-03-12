package net.lopymine.mossy;

import dev.kikugie.stonecutter.*;
import dev.kikugie.stonecutter.controller.StonecutterController;
import dev.kikugie.stonecutter.controller.storage.ProjectNode;
import lombok.experimental.ExtensionMethod;
import org.gradle.*;
import org.gradle.api.*;
import org.gradle.api.initialization.Settings;
import org.gradle.api.invocation.Gradle;
import org.gradle.api.tasks.TaskContainer;

import java.util.*;
import java.util.function.*;
import org.jetbrains.annotations.NotNull;

@ExtensionMethod(MossyPlugin.class)
public class MossyPluginStonecutter implements Plugin<Project> {

	@SuppressWarnings("UnstableApiUsage")
	@Override
	public void apply(@NotNull Project project) {
		StonecutterController controller = project.getExtensions().getByType(StonecutterController.class);
		BiConsumer<String, Consumer<ChiseledTask>> registerConsumer = MossyPluginStonecutter.getRegisterConsumer(project, controller);

		registerConsumer.accept("chiseledBuildAndCollectAll", (chiseledTask) -> {
			chiseledTask.setGroup("mossy-build");
			chiseledTask.ofTask("buildAndCollect");
		});

		registerConsumer.accept("chiseledPublishAll", (chiseledTask) -> {
			chiseledTask.setGroup("mossy-publish");
			chiseledTask.ofTask("publishMods");
		});

		for (ProjectNode node : controller.getTree().getNodes()) {
			registerConsumer.accept("chiseledBuildAndCollect+%s".formatted(node.getName()), (chiseledTask) -> {
				chiseledTask.setGroup("mossy-build");
				chiseledTask.getNodes().set(List.of(node));
				chiseledTask.ofTask("buildAndCollect");
			});
			registerConsumer.accept("chiseledPublish+%s".formatted(node.getName()), (chiseledTask) -> {
				chiseledTask.setGroup("mossy-publish");
				chiseledTask.getNodes().set(List.of(node));
				chiseledTask.ofTask("publishMods");
			});
		}

		project.getGradle().addBuildListener(new BuildListener() {
			@Override
			public void settingsEvaluated(@NotNull Settings settings) {

			}

			@Override
			public void projectsLoaded(@NotNull Gradle gradle) {

			}

			@Override
			public void projectsEvaluated(@NotNull Gradle gradle) {
				for (Task task : project.getTasks()) {
					if (!"stonecutter".equals(task.getGroup())) {
						continue;
					}
					task.setGroup("mossy-stonecutter");
				}
			}

			@Override
			public void buildFinished(@NotNull BuildResult result) {

			}
		});
	}

	private static BiConsumer<String, Consumer<ChiseledTask>> getRegisterConsumer(@NotNull Project project, StonecutterController controller) {
		return (name, consumer) -> {
			TaskContainer tasks = project.getTasks();
			controller.registerChiseled(tasks.register(name, controller.getChiseled(), (consumer::accept)));
		};
	}
}
