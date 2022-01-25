package kr.needon.needonbot.domain.service;

import kr.needon.needonbot.domain.model.BotLog;
import kr.needon.needonbot.domain.request.BotConfigRequest;
import kr.needon.needonbot.utils.APICall;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.apache.http.client.utils.URIBuilder;
import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Log
@Service
@RequiredArgsConstructor
public class Covid19Service extends ListenerAdapter {

    private final LogService logService;

    private final BotConfigService botConfigService;

    @Value("${api.key.covid}")
    private String covidKey;

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        try {
            saveConfig(event); //채널 등록함수
        } catch (IOException | URISyntaxException | ParseException e) {
            e.printStackTrace();
        }
    }

    //채널 등록함수
    public void saveConfig(MessageReceivedEvent event) throws IOException, URISyntaxException, ParseException {
        BotConfigRequest request = new BotConfigRequest();
        MessageChannel channel = event.getChannel();
        Guild guild = event.getGuild();
        Message msg = event.getMessage();

        //채널등록 함수
        if (event.isFromType(ChannelType.TEXT)) {
            TextChannel textChannel = event.getTextChannel();
            if (msg.getContentRaw().equals("!covid")) {
                User author = event.getAuthor();
                BotLog botLog = new BotLog();

                log.info("코로나 채널 설정 등록");

                request.setBotName("Covid19");
                request.setServerName(guild.getName());
                request.setChannelName(textChannel.getName());

                botLog.setBotName("Covid19");
                botLog.setCalUser(author.getName());
                botLog.setWriteDt(LocalDateTime.now());

                if (botConfigService.searchConfig(request).size() == 0) { //등록시 중복확인
                    botLog.setContent("Covid19 봇 채널 정상 등록");

                    botConfigService.insertBotConfig(request);
                    channel.sendMessage("[!] 코로나 봇 채널 설정이 완료 되었습니다.").queue();

                } else {
                    log.warning("채널 등록 중복이 감지됨");
                    botLog.setContent("Covid19 봇 채널 등록 중복 발생");
                    channel.sendMessage("[!] 이미 이 채널에 코로나 봇 설정이 등록되여 있습니다.").queue();

                }
                logService.insert(botLog);

            } else if (msg.getContentRaw().equals("!확진자")) {
                User author = event.getAuthor();
                BotLog botLog = new BotLog();

                LocalDateTime now = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                String formatDateTime = now.format(formatter);

                //로그 insert
                botLog.setBotName("Covid19");
                botLog.setCalUser(author.getName());
                botLog.setWriteDt(LocalDateTime.now());
                botLog.setContent("확진자 봇 호출");

                String totalCaseBefore = getCovidAPI().get("updateTime") + " 총 확진자 : " + getCovidAPI().get("TotalCaseBefore") + "명"; //총확진자 json 파싱
                channel.sendMessage(totalCaseBefore).queue(); //메세지 전송
                log.info(totalCaseBefore);
                logService.insert(botLog);
            }
        }
    }

    public JSONObject getCovidAPI() throws URISyntaxException, IOException, ParseException {
        APICall apiCall = new APICall();

        URIBuilder url = new URIBuilder("http://api.corona-19.kr/korea");
        url.addParameter("serviceKey", covidKey);
        String result = apiCall.call(url, "GET");

        JSONParser parser = new JSONParser();
        return (JSONObject) parser.parse(result);
    }

    @Override
    public void onReady(@NotNull ReadyEvent event) {
        log.info("Covid19 Bot is Ready!");
    }

}
