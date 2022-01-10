package kr.needon.needonbot.core.config;

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

    @Override
    public void run(String... args) throws Exception {

        builder = DefaultShardManagerBuilder.createDefault(token);
        builder.addEventListeners(new Discord(logService));
        builder.build();
        log.info("Finished Building JDA!");

        builder.setActivity(Activity.playing(playing));
        builder.build();

        PingPongService pingPongService = new PingPongService(logService);
        pingPongService.run(builder);

    }

    @Override
    public void onEvent(@NotNull GenericEvent event) {

        if (event instanceof ReadyEvent) {
            log.info("Bot is Ready!");
        }


    }
}
