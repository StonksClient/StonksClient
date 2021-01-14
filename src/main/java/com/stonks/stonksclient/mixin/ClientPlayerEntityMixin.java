package com.stonks.stonksclient.mixin;

import com.mojang.authlib.GameProfile;
import com.stonks.stonksclient.StonksClient;
import com.stonks.stonksclient.events.KnockbackEvent;
import com.stonks.stonksclient.modules.Module;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.lang.reflect.Field;

@Mixin(ClientPlayerEntity.class)
public abstract class ClientPlayerEntityMixin extends AbstractClientPlayerEntity {


    @Shadow
    public abstract void sendMessage(Text message, boolean actionBar);

    public ClientPlayerEntityMixin(ClientWorld world, GameProfile profile) {
        super(world, profile);
    }

    @Override
    public void setVelocityClient(double x, double y, double z) {
        KnockbackEvent kbe = new KnockbackEvent(x, y, z);
        StonksClient.dispatchEvent(kbe);
        super.setVelocityClient(kbe.x, kbe.y, kbe.z);
    }

    @Inject(at = @At("HEAD"),
            method = "sendChatMessage(Ljava/lang/String;)V",
            cancellable = true)
    private void onSendChatMessage(String msg, CallbackInfo ci) {
        if (msg.startsWith(".")) {
            if (msg.startsWith(".modules")) {
                if (msg.startsWith(".modules toggle ")) {

                    for (Module m : StonksClient.modules) {
                        if (msg.split(" ")[2].equalsIgnoreCase(m.getName())) {
                            m.toggleActivated();
                            sendMessage(new LiteralText("Module toggled !"), false);
                        }
                    }

                } else if (msg.equals(".modules list")) {

                    StringBuilder sb = new StringBuilder();
                    for (Module m : StonksClient.modules) {
                        sb.append(m.getName()).append("\n");
                    }
                    sb.deleteCharAt(sb.length() - 1);
                    sendMessage(new LiteralText(sb.toString()), false);
                } else if (msg.startsWith(".modules settings ")) {
                    String[] args = msg.split(" ");
                    if (args.length < 5) {
                        return;
                    }
                    for (Module m : StonksClient.modules) {
                        if (args[2].equalsIgnoreCase(m.getName())) {
                            for (Field f : StonksClient.options.get(m)) {
                                if (f.getName().equals(args[3])) {
                                    if (f.getType().equals(float.class)) {
                                        try {
                                            f.set(m, Float.parseFloat(args[4]));
                                        } catch (IllegalAccessException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    break;
                                }
                            }
                        }
                    }
                } else {
                    sendMessage(new LiteralText("Bad command !"), false);
                }
            } else {
                sendMessage(new LiteralText("Bad command !"), false);
            }
            ci.cancel(); //do not send the message
        }
    }
}
