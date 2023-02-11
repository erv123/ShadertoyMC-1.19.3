package net.erv123.shadertoymc.arucas.impl;


import me.senseiwells.arucas.api.ArucasExecutor;
import net.erv123.shadertoymc.util.ShaderUtils;
import net.minecraft.server.MinecraftServer;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

public enum MinecraftExecutor implements ArucasExecutor {
	INSTANCE;

	public <T> @NotNull Future<T> submit(@NotNull Callable<T> callable) {
		MinecraftServer server = ShaderUtils.SERVER;
		return server.submit(() -> {
			try {
				return callable.call();
			} catch (Throwable throwable) {
				return ShaderUtils.throwUnchecked(throwable);
			}
		});
	}
}