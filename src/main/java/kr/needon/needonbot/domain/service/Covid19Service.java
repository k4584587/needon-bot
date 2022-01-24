package kr.needon.needonbot.domain.service;

import kr.needon.needonbot.domain.model.BotLog;
import kr.needon.needonbot.domain.request.BotConfigRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Log
@Service
@RequiredArgsConstructor
public class Covid19Service extends ListenerAdapter {

    private final LogService logService;

    private final BotConfigService botConfigService;

    /*@Value("${api.key.covid19}")
    private String apiKey;*/


    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        saveConfig(event); //채널 등록함수
    }

    //채널 등록함수
    public void saveConfig(MessageReceivedEvent event) {
        BotConfigRequest request = new BotConfigRequest();
        Guild guild = event.getGuild();
        Message msg = event.getMessage();

        //채널등록 함수
        if (event.isFromType(ChannelType.TEXT)) {
            TextChannel textChannel = event.getTextChannel();
            if (msg.getContentRaw().equals("==covidSet")) {
                MessageChannel channel = event.getChannel();
                User author = event.getAuthor();
                BotLog botLog = new BotLog();

                log.info("코로나 채널 설정 등록");

                request.setBotName("Covid19");
                request.setServerName(guild.getName());
                request.setChannelName(textChannel.getName());

                botLog.setBotName("Covid19");
                botLog.setCalUser(author.getName());
                botLog.setWriteDt(LocalDateTime.now());

                if(botConfigService.searchConfig(request).size() == 0) { //등록시 중복확인
                    botLog.setContent("Covid19 봇 채널 정상 등록");

                    botConfigService.insertBotConfig(request);
                    channel.sendMessage("[!] 코로나 봇 채널 설정이 완료 되었습니다.").queue();

                } else {
                    log.warning("채널 등록 중복이 감지됨");
                    botLog.setContent("Covid19 봇 채널 등록 중복 발생");
                    channel.sendMessage("[!] 이미 이 채널에 코로나 봇 설정이 등록되여 있습니다.").queue();

                }

                logService.insert(botLog);
            }
        }
    }

    @Override
    public void onReady(@NotNull ReadyEvent event) {
        log.info("Covid19 Bot is Ready!");
    }
}
