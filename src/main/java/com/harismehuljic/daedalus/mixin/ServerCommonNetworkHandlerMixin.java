package com.harismehuljic.daedalus.mixin;

import com.harismehuljic.daedalus.data.DialogManager;
import net.minecraft.network.packet.c2s.common.CustomClickActionC2SPacket;
import net.minecraft.server.network.ServerCommonNetworkHandler;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerCommonNetworkHandler.class)
public class ServerCommonNetworkHandlerMixin {
    /**
     * Handles custom click actions from the client, specifically for dialog location selection.
     *
     * @param packet The custom click action packet sent by the client.
     * @param ci     The callback information for the method injection.
     */
    @Inject(method = "onCustomClickAction", at = @At("TAIL"))
    public void handleDialogLocationSelect(CustomClickActionC2SPacket packet, CallbackInfo ci) {
        if (!(((ServerCommonNetworkHandler) (Object) this instanceof ServerPlayNetworkHandler playNetworkHandler))) return;

        DialogManager.executePlayerAction(packet, playNetworkHandler.player);
    }
}
