package net.lopymine.mossy;

import dev.kikugie.stonecutter.*;
import lombok.experimental.ExtensionMethod;
import org.gradle.api.*;
import org.gradle.api.tasks.TaskContainer;

import java.util.List;
import java.util.function.*;
import org.jetbrains.annotations.NotNull;

@ExtensionMethod(MossyPlugin.class)
public class MossyPluginStonecutter implements Plugin<Project> {

	@Override
	public void apply(@NotNull Project project) {
		StonecutterController controller = project.getExtensions().getByType(StonecutterController.class);
		BiConsumer<String, Consumer<ChiseledTask>> registerConsumer = getRegisterConsumer(project, controller);

		registerConsumer.accept("chiseledBuildAndCollectAll", (chiseledTask) -> {
			chiseledTask.setGroup("mossy-build");
			chiseledTask.ofTask("buildAndCollect");
		});

		registerConsumer.accept("chiseledPublishAll", (chiseledTask) -> {
			chiseledTask.setGroup("mossy-publish");
			chiseledTask.ofTask("publishMods");
		});

		registerConsumer.accept("chiseledPublishSpecified", (chiseledTask) -> {
			chiseledTask.setGroup("mossy-publish");
			chiseledTask.ofTask("publishMods");
			List<String> publicationVersions = project.getPublicationVersions();
			List<StonecutterProject> list = chiseledTask.getVersions()
					.get()
					.stream()
					.filter(stonecutterProject -> publicationVersions.contains(stonecutterProject.getProject()))
					.toList();
			chiseledTask.getVersions().set(list);
		});

		for (StonecutterProject version : controller.getVersions()) {
			registerConsumer.accept("chiseledBuildAndCollect+%s".formatted(version.getProject()), (chiseledTask) -> {
				chiseledTask.setGroup("mossy-build");
				chiseledTask.ofTask("publishMods");
				chiseledTask.getVersions().set(List.of(version));
			});
			registerConsumer.accept("chiseledPublish+%s".formatted(version.getProject()), (chiseledTask) -> {
				chiseledTask.setGroup("mossy-publish");
				chiseledTask.ofTask("publishMods");
				chiseledTask.getVersions().set(List.of(version));
			});
		}
	}

	private static BiConsumer<String, Consumer<ChiseledTask>> getRegisterConsumer(@NotNull Project project, StonecutterController controller) {
		return (name, consumer) -> {
			TaskContainer tasks = project.getTasks();
			controller.registerChiseled(tasks.register(name, controller.getChiseled(), (consumer::accept)));
		};
	}
}
