package dev.yyuh.blade.platform;

import dev.yyuh.blade.FakePlayer;
import net.minecraft.network.protocol.game.ClientboundPlayerInfoUpdatePacket;

import java.util.EnumSet;

public interface ServerPlatform extends Platform {
    void declareFakePlayer(FakePlayer player);

    ClientboundPlayerInfoUpdatePacket buildPlayerInfoPacket(EnumSet<ClientboundPlayerInfoUpdatePacket.Action> actions, ClientboundPlayerInfoUpdatePacket.Entry entry);

    @Override
    default boolean isClient() {
        return false;
    }
}
