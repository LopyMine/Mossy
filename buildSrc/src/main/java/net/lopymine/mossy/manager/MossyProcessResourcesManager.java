package net.lopymine.mossy.manager;

import lombok.experimental.ExtensionMethod;
import org.gradle.api.Project;
import org.gradle.language.jvm.tasks.ProcessResources;

import net.lopymine.mossy.MossyPlugin;

import java.util.*;
import java.util.Map.Entry;

@ExtensionMethod(MossyPlugin.class)
public class MossyProcessResourcesManager {

	public static void apply(Project project) {
		ProcessResources processResources = (ProcessResources) project.getTasks().getByName("processResources");

		Map<String, String> dataProperties = project.getMossyProperties("data");
		for (Entry<String, String> entry : dataProperties.entrySet()) {
			processResources.getInputs().property(entry.getKey(), entry.getValue());
		}
		Map<String, String> properties = new HashMap<>(dataProperties);

		Map<String, String> buildProperties = project.getMossyProperties("build");
		for (Entry<String, String> entry : buildProperties.entrySet()) {
			processResources.getInputs().property(entry.getKey(), entry.getValue());
		}
		properties.putAll(buildProperties);

		processResources.filesMatching("fabric.mod.json", (details) -> {
			System.out.println("EXPANDED" + details.getName());
			details.expand(properties);
		});
	}

}
