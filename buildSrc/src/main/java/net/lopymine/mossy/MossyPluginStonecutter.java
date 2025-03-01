package net.lopymine.mossy;

import dev.kikugie.stonecutter.*;
import org.gradle.api.*;
import org.gradle.api.tasks.TaskContainer;

import java.util.function.Consumer;
import org.jetbrains.annotations.NotNull;

public class MossyPluginStonecutter implements Plugin<Project> {

	@Override
	public void apply(@NotNull Project project) {
//		StonecutterController controller = project.getExtensions().getByType(StonecutterController.class);
//		TaskContainer tasks = project.getTasks();
//
//		registerChiseled(controller, tasks, (task) -> {
//			task.setGroup("mossy");
//		});
	}

	private static void registerChiseled(StonecutterController controller, TaskContainer tasks, Consumer<Task> consumer) {
		controller.registerChiseled(tasks.register("chiseledBuildAndCollectAll", controller.getChiseled(), consumer::accept));
	}
}
