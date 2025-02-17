package net.lopymine.mossy.tasks;

import lombok.experimental.ExtensionMethod;
import org.gradle.api.Project;
import org.gradle.api.tasks.*;

import net.lopymine.mossy.MossyPlugin;

@ExtensionMethod(MossyPlugin.class)
public class RebuildLibsTask extends Delete {

	@TaskAction
	public void regenerate() {
		Project project = this.getProject();
		String modName = project.getProperty("mod_name");
		String modVersion = project.getProperty("mod_version");
		delete(project.getRootFile("libs/%s-%s.jar".formatted(modName, modVersion)));
		delete(project.getLayout().getBuildDirectory().file("%s-%s.jar".formatted(modName, modVersion)));
		delete(project.getLayout().getBuildDirectory().file("%s-%s-sources.jar".formatted(modName, modVersion)));
	}
}
