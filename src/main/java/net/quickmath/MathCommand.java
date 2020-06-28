package net.quickmath;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Formatting;

import javax.script.ScriptEngineManager;
import javax.script.ScriptEngine;
import javax.script.ScriptException;

import static com.mojang.brigadier.arguments.StringArgumentType.getString;

final class MathCommand implements Command<ServerCommandSource> {
    static final String equationArgument = "equation";

    @Override
    public int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        //context.getSource().getPlayer().sendMessage(new LiteralText(getString(context, equationArgument)), false);
        ScriptEngineManager mgr = new ScriptEngineManager();
        ScriptEngine engine = mgr.getEngineByName("JavaScript");
        String equation = getString(context, equationArgument);
        try {
            context.getSource().getPlayer().sendMessage(new LiteralText(equation + " = " + engine.eval(equation).toString()).formatted(Formatting.GRAY).formatted(Formatting.ITALIC), false);
        } catch (ScriptException e) {
            context.getSource().getPlayer().sendMessage(new LiteralText("Invalid Equation").formatted(Formatting.GRAY).formatted(Formatting.ITALIC), false);
            return 0;
        }

        return 1;
    }
}