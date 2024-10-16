package dev.yyuh.blade.platform;

import dev.yyuh.blade.bot.Bot;

import java.util.concurrent.ScheduledExecutorService;

public interface Platform {
    ScheduledExecutorService getExecutor();

    boolean isClient();

    void destroyBot(Bot bot);
}
