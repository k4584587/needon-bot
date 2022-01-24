package kr.needon.needonbot.core.config;

import kr.needon.needonbot.domain.service.BotConfigService;
import kr.needon.needonbot.domain.service.Covid19Service;
import kr.needon.needonbot.domain.service.LogService;
import kr.needon.needonbot.domain.service.PingPongService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Log
@Component
@RequiredArgsConstructor
public class Discord implements EventListener, CommandLineRunner {

    @Value("${discord.token}")
    private String token;

    @Value("${discord.activity.playing}")
    private String playing;

    private final LogService logService;

    private final BotConfigService botConfigService;

    @Override
    public void run(String... args) throws Exception {

        DefaultShardManagerBuilder builder = DefaultShardManagerBuilder.createDefault(token);
        builder.addEventListeners(new Discord(logService, botConfigService), new PingPongService(logService), new Covid19Service(logService,botConfigService));
        log.info("Finished Building JDA!");
        builder.setActivity(Activity.playing(playing));
        builder.build();
    }

    @Override
    public void onEvent(@NotNull GenericEvent event) {

        if (event instanceof ReadyEvent) {
            log.info("Bot is Ready!");
        }

    }
}
