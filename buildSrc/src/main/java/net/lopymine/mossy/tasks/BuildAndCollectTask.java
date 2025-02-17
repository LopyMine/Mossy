package net.lopymine.mossy.tasks;

import lombok.experimental.ExtensionMethod;
import org.gradle.api.*;
import org.gradle.api.tasks.*;

import net.fabricmc.loom.task.RemapJarTask;

import net.lopymine.mossy.MossyPlugin;

@ExtensionMethod(MossyPlugin.class)
public class BuildAndCollectTask extends Copy {

	@TaskAction
	public void regenerate() {
		Project project = this.getProject();
		RemapJarTask remapJar = (RemapJarTask) project.getTasks().getByName("remapJar");
		from(remapJar.getArchiveFile());
		into(project.file("libs/"));
	}
}
