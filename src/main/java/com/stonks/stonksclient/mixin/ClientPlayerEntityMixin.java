package com.stonks.stonksclient.mixin;

import com.mojang.authlib.GameProfile;
import com.stonks.stonksclient.StonksClient;
import com.stonks.stonksclient.events.KnockbackEvent;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ClientPlayerEntity.class)
public class ClientPlayerEntityMixin extends AbstractClientPlayerEntity {


    public ClientPlayerEntityMixin(ClientWorld world, GameProfile profile) {
        super(world, profile);
    }

    @Override
    public void setVelocityClient(double x, double y, double z) {
        KnockbackEvent kbe = new KnockbackEvent(x, y, z);
        StonksClient.dispatchEvent(kbe);
        super.setVelocityClient(kbe.x, kbe.y, kbe.z);
    }
}
