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

    public DefaultShardManagerBuilder builder;

    private final LogService logService;

    private final BotConfigService botConfigService;

    @Override
    public void run(String... args) throws Exception {

        builder = DefaultShardManagerBuilder.createDefault(token);
        builder.addEventListeners(new Discord(logService, botConfigService));
        builder.build();
        log.info("Finished Building JDA!");

        builder.setActivity(Activity.playing(playing));
        builder.build();

        PingPongService pingPongService = new PingPongService(logService);
        Covid19Service covid19Service = new Covid19Service(logService, botConfigService);

        pingPongService.run(builder); //핑퐁봇
        covid19Service.run(builder); //확진자 봇

    }

    @Override
    public void onEvent(@NotNull GenericEvent event) {

        if (event instanceof ReadyEvent) {
            log.info("Bot is Ready!");
        }


    }
}
