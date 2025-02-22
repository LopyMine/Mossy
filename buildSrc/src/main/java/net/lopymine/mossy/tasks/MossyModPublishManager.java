package net.lopymine.mossy.tasks;

import lombok.experimental.ExtensionMethod;
import me.modmuss50.mpp.*;
import me.modmuss50.mpp.platforms.modrinth.ModrinthApi.VersionType;
import org.codehaus.groovy.runtime.ResourceGroovyMethods;
import org.gradle.api.*;
import org.gradle.api.file.RegularFile;
import org.gradle.api.provider.Provider;

import net.fabricmc.loom.task.RemapJarTask;

import net.lopymine.mossy.MossyPlugin;

import java.io.*;
import java.util.Arrays;
import org.jetbrains.annotations.NotNull;

@ExtensionMethod(MossyPlugin.class)
public class MossyModPublishManager {

	public static void apply(@NotNull Project project, ModPublishExtension mpe) {
		//	def loaders = prop("loaders").toString().split(" ")
		//	def modrinthProjectId = prop("modrinth_id").toString()
		//	def embedsDepends = prop("embeds_depends").toString().split(" ")
		//	def curseForgeProjectId = prop("curseforge_id").toString()
		//	def requiresDepends = prop("requires_depends").toString().split(" ")
		//	def optionalDepends = prop("optional_depends").toString().split(" ")
		//	def incompatibleDepends = prop("incompatible_depends").toString().split(" ")
		//	def versionTypeProperty = prop("version_type").toString()
		//	def maxJavaVersion = JavaVersion.toVersion(prop("max_java_version"))
		//	def isClient = Boolean.parseBoolean(prop("is_for_client)").toString())
		//	def isTesting = Boolean.parseBoolean(prop("test_publish)").toString())
		//	def isServer = Boolean.parseBoolean(prop("is_for_server)").toString())
		//	def versionName = "[${currentMultiVersion.toVersionRange()}] ${prop("mod_name")} v${prop("mod_version")}"
		//	def bl = providers.environmentVariable("CURSEFORGE_API_KEY").getOrNull() == null
		//	def bl2 = providers.environmentVariable("MODRINTH_API_KEY").getOrNull() == null
		//	def bl3 = isTesting || bl || bl2

		String name = "[%s] %s v%s".formatted("TODO MULTI VERSION", project.getProperty("mod_name"), project.getProperty("mod_version"));

		String[] loaders = project.getProperty("loaders").split(" ");
		String modrinthId = project.getProperty("modrinth_id");
		String curseForgeId = project.getProperty("curseforge_id");
		String[] dependsEmbeds = project.getProperty("depends_embeds").split(" ");
		String[] dependsRequires = project.getProperty("depends_requires").split(" ");
		String[] dependsOptional = project.getProperty("depends_optional").split(" ");
		String[] dependsIncompatible = project.getProperty("depends_incompatible").split(" ");
		String versionType = project.getProperty("version_type");
		int maxJavaVersion = Integer.parseInt(project.getProperty("max_java_version"));
		Boolean isForClient = Boolean.parseBoolean(project.getProperty("is_for_client"));
		Boolean isForServer = Boolean.parseBoolean(project.getProperty("is_for_server"));
		Boolean testPublish = Boolean.parseBoolean(project.getProperty("test_publish"));

		String curseForgeApiKey = project.getProviders().environmentVariable("CURSEFORGE_API_KEY").getOrNull();
		String modrinthApiKey = project.getProviders().environmentVariable("MODRINTH_API_KEY").getOrNull();

		boolean cannotUpload = testPublish || curseForgeApiKey == null || modrinthApiKey == null;

		mpe.getDisplayName().set(name);
		mpe.getFile().set(getModFile(project));
		mpe.getChangelog().set(getChangeLog(project));
		mpe.getType().set(getType(versionType));
		mpe.getModLoaders().set(Arrays.asList(loaders));
		mpe.getDryRun().set(cannotUpload);

		mpe.curseforge((curseforge) -> {
			curseforge.getProjectId().set(curseForgeId);
			curseforge.getAccessToken().set(curseForgeApiKey);

			for (int i = 17; i < maxJavaVersion + 1; i++) {
				curseforge.getJavaVersions().add(JavaVersion.values()[i]);
			}

			curseforge.getClientRequired().set(isForClient);
			curseforge.getServerRequired().set(isForServer);

			// TODO minecraftVersions

			if (!dependsEmbeds[0].equals("none")) {
				curseforge.embeds(dependsEmbeds);
			}
			if (!dependsRequires[0].equals("none")) {
				curseforge.requires(dependsRequires);
			}
			if (!dependsOptional[0].equals("none")) {
				curseforge.optional(dependsOptional);
			}
			if (!dependsIncompatible[0].equals("none")) {
				curseforge.incompatible(dependsIncompatible);
			}
		});

		mpe.modrinth((modrinth) -> {
			modrinth.getProjectId().set(modrinthId);
			modrinth.getAccessToken().set(modrinthApiKey);

			// TODO minecraftVersions

			if (!dependsEmbeds[0].equals("none")) {
				modrinth.embeds(dependsEmbeds);
			}
			if (!dependsRequires[0].equals("none")) {
				modrinth.requires(dependsRequires);
			}
			if (!dependsOptional[0].equals("none")) {
				modrinth.optional(dependsOptional);
			}
			if (!dependsIncompatible[0].equals("none")) {
				modrinth.incompatible(dependsIncompatible);
			}
		});
	}

	private static Provider<RegularFile> getModFile(@NotNull Project project) {
		return ((RemapJarTask) project.getTasks().getByName("remapJar")).getArchiveFile();
	}

	private static ReleaseType getType(String versionType) {
		return switch (versionType) {
			case "RELEASE" -> ReleaseType.STABLE;
			case "BETA" -> ReleaseType.BETA;
			case "ALPHA" -> ReleaseType.ALPHA;
			default -> throw new IllegalArgumentException("Unknown version type!");
		};
	}

	private static String getChangeLog(@NotNull Project project) {
		try {
			File file = project.getRootFile("CHANGELOG.md");
			if (file.exists()) {
				String text = ResourceGroovyMethods.getText(file);
				if (!text.isBlank()) {
					return text;
				}
			}
		} catch (IOException e) {
			System.out.println("Failed to get changelog text!");
			throw new RuntimeException(e);
		}
		return "No changelog specified.";
	}

}
