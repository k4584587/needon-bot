package kr.needon.needonbot.core.config;

import kr.needon.needonbot.domain.service.*;
import lombok.RequiredArgsConstructor;
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

    private final PingPongService pingPongService;

    private final Covid19Service covid19Service;

    private final PixivService pixivService;


    @Override
    public void run(String... args) throws Exception {

        DefaultShardManagerBuilder builder = DefaultShardManagerBuilder.createDefault(token);
        builder.addEventListeners(new Discord(logService, botConfigService, pingPongService, covid19Service, pixivService));
        log.info("Finished Building JDA!");
        builder.setActivity(Activity.playing(playing));

        builder.build();
    }

    @Override
    public void onEvent(@NotNull GenericEvent event) {

        pingPongService.onEvent(event);
        covid19Service.onEvent(event);
        pixivService.onEvent(event);

        if (event instanceof ReadyEvent) {
            log.info("Bot is Ready!");
        }

    }
}
