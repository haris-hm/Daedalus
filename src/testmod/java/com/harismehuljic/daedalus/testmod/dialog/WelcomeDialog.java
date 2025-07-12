package com.harismehuljic.daedalus.testmod.dialog;

import com.harismehuljic.daedalus.gui.CustomDialog;
import com.harismehuljic.daedalus.gui.elements.actions.ActionButton;
import com.harismehuljic.daedalus.gui.elements.body.TextBody;
import com.harismehuljic.daedalus.gui.elements.input.TextInput;
import com.harismehuljic.daedalus.gui.elements.text.StylableText;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class WelcomeDialog extends CustomDialog {
    private final ServerPlayerEntity spe;

    public WelcomeDialog(ServerPlayerEntity spe) {
        this.spe = spe;
        this.openDialog(spe);
    }

    @Override
    protected void defineDialog() {
        this.setDialogTitle("Welcome to Daedalus!");
        this.setCloseOnEscape(true);
        this.addBodyElement(new TextBody(new StylableText("Welcome to Daedalus, the ultimate mod for Minecraft! "
                + "This dialog will help you get started with the mod. "
                + "Please enter your name below to continue.")));
        this.addBodyElement(new TextBody(new StylableText("Daedalus is designed to enhance your Minecraft experience "
                + "by providing a wide range of features and tools. "
                + "From custom dialogs to advanced item management, "
                + "Daedalus has everything you need to take your gameplay to the next level.")));
        this.addInputElement(new TextInput("name", 250, new StylableText("Enter your name:")));
        this.addActionButton(new ActionButton(
                Identifier.of("daedalus", "welcome_action"),
                new StylableText("Start"),
                nbtCompound -> {
                    String playerName = nbtCompound.getString("name").orElse("Player");
                    if (this.spe != null) {
                        this.spe.sendMessage(new StylableText("Welcome, " + playerName + "! Enjoy your journey with Daedalus!").setColor(0x00FF00).getText());
                    }
                }
        ));
    }
}
