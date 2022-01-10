package kr.needon.needonbot.core.config;

import lombok.extern.java.Log;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.security.auth.login.LoginException;

@Log
@Component
public class Discord implements EventListener, CommandLineRunner {

    @Value("${discord.token}")
    private String token;

    @Override
    public void run(String... args) throws Exception {
        botConfig();
    }

    public void botConfig() throws LoginException, InterruptedException {
        // Note: It is important to register your ReadyListener before building
        JDA jda = JDABuilder.createDefault(token)
            .addEventListeners(new Discord())
            .build();

        // optionally block until JDA is ready
        jda.awaitReady();

    }

    @Override
    public void onEvent(@NotNull GenericEvent event) {
        if (event instanceof ReadyEvent)
            log.info("Bot is Ready!");
    }
}
