package net.lopymine.mossy;

import lombok.Getter;
import org.gradle.api.tasks.Input;

@Getter
public class MossyDependenciesExtension {

	@Input
	String minecraft;

	@Input
	String mappings;

	@Input
	String fabricApi;

	@Input
	String fabricLoader;

	@Input
	String modMenu;

	@Input
	String yacl;

	@Input
	String lombok;
}
