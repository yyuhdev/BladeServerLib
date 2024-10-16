package club.revived.blade.platform;

import club.revived.blade.bot.Bot;

import java.util.concurrent.ScheduledExecutorService;

public interface Platform {
    ScheduledExecutorService getExecutor();

    boolean isClient();

    void destroyBot(Bot bot);
}
