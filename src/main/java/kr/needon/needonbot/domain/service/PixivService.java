package kr.needon.needonbot.domain.service;

import kr.needon.needonbot.domain.request.BotConfigRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import org.springframework.stereotype.Service;

import javax.security.auth.login.LoginException;

@Log
@Service
@RequiredArgsConstructor
public class PixivService extends ListenerAdapter {

    private final LogService logService;

    private final BotConfigService botConfigService;

    public void run(DefaultShardManagerBuilder builder) throws LoginException {
        builder.addEventListeners(new PixivService(logService, botConfigService));
        builder.build();
        log.info("픽시브 봇 로드 완료!");
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
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
            if (msg.getContentRaw().equals("==pixiv")) {
                MessageChannel channel = event.getChannel();


                log.info("픽시브 채널 설정 등록");

                request.setBotName("Pixiv");
                request.setServerName(guild.getName());
                request.setChannelName(textChannel.getName());

                if (botConfigService.searchConfig(request).size() == 0) { //등록시 중복확인

                    botConfigService.insertBotConfig(request);
                    channel.sendMessage("[!] 픽시브 봇 채널 설정이 완료 되었습니다.").queue();

                } else {
                    log.warning("채널 등록 중복이 감지됨");
                    channel.sendMessage("[!] 이미 이 채널에 픽시브 봇 설정이 등록되여 있습니다.").queue();

                }

            }
        }
    }




}
