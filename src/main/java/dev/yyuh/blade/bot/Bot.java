package dev.yyuh.blade.bot;

import dev.yyuh.blade.FakePlayer;
import dev.yyuh.blade.event.BotLifecycleEvents;
import dev.yyuh.blade.inventory.BotInventory;
import dev.yyuh.blade.platform.Platform;
import dev.yyuh.blade.util.ClientSimulator;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.helpers.NOPLogger;

import java.util.Random;

@SuppressWarnings("UnusedReturnValue")
public class Bot {
    protected final Player vanillaPlayer;
    protected final Platform platform;
    protected final Random random = new Random();
    public final boolean isClient;
    protected boolean jumped = false;
    protected final ClientSimulator clientSimulator;
    protected final BotInventory inventory;
    protected boolean debug = false;

    public Bot(Player vanillaPlayer, Platform platform) {
        this.isClient = platform.isClient();
        this.vanillaPlayer = vanillaPlayer;
        this.platform = platform;
        this.inventory = new BotInventory(this);
        clientSimulator = new ClientSimulator((ServerPlayer) vanillaPlayer, inventory::hasInventoryOpen);
    }

    public Player getVanillaPlayer() {
        return vanillaPlayer;
    }

    public void doTick() {
        if (isDestroyed()) {
            destroy();
            return;
        }
        BotLifecycleEvents.TICK_START.call(this).onTickStart(this);
        try {
            tick();
        } catch (Throwable t) {
            t.printStackTrace();
        }
        BotLifecycleEvents.TICK_END.call(this).onTickEnd(this);
    }

    protected void tick() {
        if (jumped) {
            vanillaPlayer.setJumping(false);
        }
    }

    public Random getRandom() {
        return random;
    }

    public boolean isDead() {
        return vanillaPlayer.isDeadOrDying();
    }

    public boolean isValid() {
        return !isDestroyed();
    }

    public boolean isDestroyed() {
        return !isClient && vanillaPlayer.isDeadOrDying();
    }

    public void interact() {
        if (inventory.hasInventoryOpen()) return;
        clientSimulator.use();
    }

    public void destroy() {
        platform.destroyBot(this);
        if (isClient) {
            return;
        }
        if (vanillaPlayer instanceof FakePlayer) vanillaPlayer.discard();
    }

    public Platform getPlatform() {
        return platform;
    }

    public Logger getLogger(String name) {
        if (!debug) return NOPLogger.NOP_LOGGER;
        return LoggerFactory.getLogger("BOT-" + name);
    }

    public boolean isDebug() {
        return debug;
    }
}
