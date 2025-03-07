package net.lopymine.mossy.tasks;

import lombok.experimental.ExtensionMethod;
import org.gradle.api.*;
import org.gradle.api.tasks.TaskAction;

import net.lopymine.mossy.MossyPlugin;

import java.io.*;
import java.nio.file.Files;
import java.util.*;
import java.util.Map.Entry;

@ExtensionMethod(MossyPlugin.class)
public class GeneratePublishWorkflowsForEachVersionTask extends DefaultTask {
	
	@TaskAction
	public void generate() {
		Project project = this.getProject();
		File file = project.getRootFile(".github/workflows/");
		if (!file.exists() && !file.mkdirs()) {
			return;
		}
		Map<String, List<String>> multiLoaders = project.getMultiLoadersAsMap();
		for (Entry<String, List<String>> multiVersion : multiLoaders.entrySet()) {
			List<String> versions = multiVersion.getValue();
			String loader = multiVersion.getKey();
			for (String version : versions) {
				try {
					File workflowFile = file.toPath().resolve("publish_%s_%s.yml".formatted(loader, version)).toFile();
					if (workflowFile.exists()) {
						continue;
					}
					if (!workflowFile.createNewFile()) {
						continue;
					}
					String strip = """
						# Generated workflow by task
						
						name: Publish MULTI_VERSION_ID Version
						on: [workflow_dispatch] # Manual trigger
						
						permissions:
						  contents: write
						
						jobs:
						  build:
						    runs-on: ubuntu-22.04
						    container:
						      image: mcr.microsoft.com/openjdk/jdk:21-ubuntu
						      options: --user root
						    steps:
						      - uses: actions/checkout@v4
						      - name: make gradle wrapper executable
						        run: chmod +x ./gradlew
						      - name: Publish MULTI_VERSION_ID Mod Version
						        run: ./gradlew chiseledBuildAndCollect+MULTI_VERSION_ID chiseledPublish+MULTI_VERSION_ID
						        env:
						          CURSEFORGE_API_KEY: ${{ secrets.CURSEFORGE_API_KEY }}
						          MODRINTH_API_KEY: ${{ secrets.MODRINTH_API_KEY }}
						""".replaceAll("MULTI_VERSION_ID", loader + "+" + version).stripIndent().strip();
					Files.write(workflowFile.toPath(), strip.getBytes());
				} catch (Exception ignored) {
				}
			}
		}
	}

}
