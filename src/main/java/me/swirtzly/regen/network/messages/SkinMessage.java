package me.swirtzly.regen.network.messages;

import me.swirtzly.regen.common.regen.RegenCap;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.UUID;
import java.util.function.Supplier;

public class SkinMessage {
    private final byte[] skinByteArray;
    private final UUID playerUUID;

    public SkinMessage(PlayerEntity playerEntity, byte[] skinByteArray) {
        this.skinByteArray = skinByteArray;
        this.playerUUID = playerEntity.getUniqueID();
    }

    public SkinMessage(PacketBuffer buffer) {
        skinByteArray = buffer.readByteArray(Integer.MAX_VALUE);
        playerUUID = buffer.readUniqueId();
    }

    public static void handle(SkinMessage message, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayerEntity serverPlayer = ctx.get().getSender();
            RegenCap.get(serverPlayer).ifPresent(iRegen -> {
                iRegen.setSkin(message.skinByteArray);
                iRegen.syncToClients(null);
            });
        });
        ctx.get().setPacketHandled(true);
    }

    public void toBytes(PacketBuffer buffer) {
        buffer.writeByteArray(this.skinByteArray);
        buffer.writeUniqueId(this.playerUUID);
    }
}
