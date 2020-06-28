package net.quickmath;

import com.mojang.brigadier.tree.ArgumentCommandNode;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;

import static com.mojang.brigadier.arguments.StringArgumentType.greedyString;

public class QuickMath implements ModInitializer {
    @Override
    public void onInitialize() {
        CommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> {
            LiteralCommandNode<ServerCommandSource> calcNode = CommandManager
                    .literal("calc")
                    .build();

            ArgumentCommandNode<ServerCommandSource, String> equationNode = CommandManager
                    .argument(MathCommand.equationArgument, greedyString())
                    .executes(new MathCommand())
                    .build();

            dispatcher.getRoot().addChild(calcNode);
            calcNode.addChild(equationNode);
        });
    }
}
