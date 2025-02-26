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
		String modName = project.getProperty("data.mod_name");
		String version = project.getVersion().toString();

		String file = "%s-%s.jar".formatted(modName, version);
		String sourcesFile = "%s-%s-sources.jar".formatted(modName, version);

		delete(project.getRootFile("libs/%s".formatted(file)));
		delete(project.getLayout().getBuildDirectory().file(file));
		delete(project.getLayout().getBuildDirectory().file(sourcesFile));
	}
}
