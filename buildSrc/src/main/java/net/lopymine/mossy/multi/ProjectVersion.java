package net.lopymine.mossy.multi;

public record ProjectVersion(String loader, String minecraftVersion) {

	@Override
	public String toString() {
		return "%s+%s".formatted(this.loader(), this.minecraftVersion());
	}

}
