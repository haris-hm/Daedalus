package com.harismehuljic.daedalus.commands;

import com.harismehuljic.daedalus.gui.DialogBuilder;
import com.harismehuljic.daedalus.gui.elements.actions.ActionButton;
import com.harismehuljic.daedalus.gui.elements.body.ItemBody;
import com.harismehuljic.daedalus.gui.elements.body.TextBody;
import com.harismehuljic.daedalus.gui.elements.input.NumberInput;
import com.harismehuljic.daedalus.gui.elements.text.StylableText;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

import static net.minecraft.server.command.CommandManager.literal;

public class TestDialogCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess registryAccess,
                                CommandManager.RegistrationEnvironment environment) {
        final LiteralCommandNode<ServerCommandSource> testCommand = dispatcher.register(literal("daedalus")
                .then(literal("test")
                        .executes(TestDialogCommand::executeSendTestDialog)
                )
                .then(literal("test2")
                        .executes(TestDialogCommand::executeSendTest2Dialog)
                )
                .then(literal("test_dialog_class")
                        .executes(TestDialogCommand::executeSendTestClass)
                )
        );
    }

    private static int executeSendTestClass(CommandContext<ServerCommandSource> context) {
        ServerCommandSource source = context.getSource();
        ServerPlayerEntity spe = source.getPlayer();

        new WelcomeDialog(spe);
//        dialog.openDialog(spe);


        return 0;
    }

    private static int executeSendTestDialog(CommandContext<ServerCommandSource> context) {
        ServerCommandSource source = context.getSource();
        ServerPlayerEntity spe = source.getPlayer();

        new DialogBuilder()
                .setTitle("Test Dialog")
                .setCloseOnEscape(false)
                .addBodyElement(new TextBody(new StylableText("This is a test dialog. You can add more text here."), 100))
                .addBodyElement(new TextBody(new StylableText("You can also add items, inputs, and buttons."), 100))
                .addBodyElement(new ItemBody(new ItemStack(Items.ACACIA_BOAT), true, true, 100, 100).setDescription(new TextBody(new StylableText("This is an acacia boat item."), 100)))
                .addInput(new NumberInput("test_number_input", 100, new StylableText("NUMBER"),0,13))
                .addActionButton(new ActionButton(Identifier.of("daedalus", "test_action_two"), new StylableText("Test Action"), (nbtCompound -> {
                    assert spe != null;
                    spe.sendMessage(new StylableText("Test action executed!").setColor(0xA4031C).getText());
                })))
                .addActionButton(new ActionButton(Identifier.of("daedalus", "test_action"), new StylableText("Test Action"), (nbtCompound -> {
                    assert spe != null;
                    spe.sendMessage(new StylableText("Test action executed!").setColor(0xA4031C).getText());
                })))
                .openDialog(spe);

        return 0;
    }

    private static int executeSendTest2Dialog(CommandContext<ServerCommandSource> context) {
        ServerCommandSource source = context.getSource();
        ServerPlayerEntity spe = source.getPlayer();

        new DialogBuilder()
                .setTitle("Test Dialog")
                .setCloseOnEscape(true)
                .addBodyElement(new TextBody(new StylableText("This is a test dialog. You can add more text here."), 100))
                .addBodyElement(new TextBody(new StylableText("You can also add items, inputs, and buttons."), 100))
                .addInput(new NumberInput("test_number_input", 100, new StylableText("NUMBER"),0,13))
                .addActionButton(new ActionButton(Identifier.of("daedalus", "test_action_three"), new StylableText("Test Action"), (nbtCompound -> {
                    assert spe != null;
                    spe.sendMessage(new StylableText("Test action executed!").setColor(0xA4031C).getText());
                })))
                .addActionButton(new ActionButton(Identifier.of("daedalus", "test_action_four"), new StylableText("Test Action"), (nbtCompound -> {
                    assert spe != null;
                    spe.sendMessage(new StylableText("Test action executed!").setColor(0xA4031C).getText());
                })))
                .openDialog(spe);

        return 0;
    }
}
