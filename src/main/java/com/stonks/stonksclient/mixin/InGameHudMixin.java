package com.stonks.stonksclient.mixin;

import com.stonks.stonksclient.StonksClient;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public class InGameHudMixin {
    @Inject(at = {@At(value = "TAIL")}, method = {"render(Lnet/minecraft/client/util/math/MatrixStack;F)V"})
    private void onRender(MatrixStack matrices, float delta,
                          CallbackInfo ci) {
        StonksClient.renderGUI(matrices, delta);
    }
}
