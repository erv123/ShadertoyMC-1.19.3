package net.erv123.shadertoymc.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.erv123.shadertoymc.util.ScriptUtils;
import net.erv123.shadertoymc.util.ShaderUtils;
import net.erv123.shadertoymc.ShadertoyMC;
import net.minecraft.command.CommandSource;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.Text;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class ShadertoyCommand {
	/*
			/shadertoy
				area
					pos size
					clear
					display
					pos1
					pos2
					size

				shader
					calculate
					paste
	 */
	private ShadertoyCommand() {

	}

	public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
		dispatcher.register(literal("shadertoy")
			.requires(source -> source.hasPermissionLevel(2))
			.then(literal("run")
				.then(argument("shader_file", StringArgumentType.string())
					.suggests((context, builder) -> CommandSource.suggestMatching(ScriptUtils.SCRIPTS, builder))
					.executes(context -> {
						String shaderFile = StringArgumentType.getString(context, "shader_file");
						context.getSource().sendMessage(Text.literal("Running shader: " + shaderFile));
						ScriptUtils.executeScript(shaderFile, context.getSource());
						return 1;
					})
				)
			)
			.then(literal("new")
				.then(argument("shader_file", StringArgumentType.string())
					.executes(context -> {
						String shaderFile = StringArgumentType.getString(context, "shader_file");
						if (!shaderFile.endsWith(".arucas")) {
							shaderFile += ".arucas";
						}

						Path shaderPath = ShaderUtils.SHADERTOY_PATH.resolve(shaderFile);
						try {
							Files.writeString(shaderPath, ScriptUtils.EXAMPLE_SCRIPT);
						} catch (IOException exception) {
							context.getSource().sendFeedback(Text.literal("Failed to write to: " + shaderPath), true);
							return 0;
						}

						context.getSource().sendMessage(Text.literal("New shader file §n" + shaderFile + "§r created"));
						return 1;
					})
				)
			)
		);
	}
}
