package net.lopymine.mossy.config;

import lombok.*;
import net.lopymine.mossylib.loader.MossyLoader;
import net.lopymine.mossylib.utils.*;
import org.slf4j.*;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.lopymine.mossy.Mossy;

import java.io.*;
import java.util.concurrent.CompletableFuture;

import static net.lopymine.mossylib.utils.CodecUtils.option;

@Getter
@Setter
@AllArgsConstructor
public class MossyConfig {

	public static final Codec<MossyConfig> CODEC = RecordCodecBuilder.create(instance -> instance.group(
			option("mod_enabled", false, Codec.BOOL, MossyConfig::isModEnabled)
	).apply(instance, MossyConfig::new));

	private static final File CONFIG_FILE = MossyLoader.getConfigDir().resolve(Mossy.MOD_ID + ".json5").toFile();
	private static final Logger LOGGER = LoggerFactory.getLogger(Mossy.MOD_NAME + "/Config");
	private static MossyConfig INSTANCE;
	
	private boolean modEnabled;

	@SuppressWarnings("unused")
	private MossyConfig() {
		throw new IllegalArgumentException();
	}

	public static MossyConfig getInstance() {
		return INSTANCE == null ? reload() : INSTANCE;
	}

	public static MossyConfig reload() {
		return INSTANCE = MossyConfig.read();
	}

	public static MossyConfig getNewInstance() {
		return CodecUtils.parseNewInstanceHacky(CODEC);
	}

	private static MossyConfig read() {
		return ConfigUtils.readConfig(CODEC, CONFIG_FILE, LOGGER);
	}

	public void saveAsync() {
		CompletableFuture.runAsync(this::save);
	}

	public void save() {
		ConfigUtils.saveConfig(this, CODEC, CONFIG_FILE, LOGGER);
	}
}
