package kr.needon.needonbot.domain.service;

import kr.needon.needonbot.utils.APICall;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
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

@Log
@Service
@RequiredArgsConstructor
public class Covid19AutoService extends ListenerAdapter {

    private final LogService logService;

    private final BotConfigService botConfigService;

    @Value("${api.key.covid}")
    private String covidKey;

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        try {
            autoCovidMessage(event); //채널 등록함수
        } catch (IOException | URISyntaxException | ParseException e) {
            e.printStackTrace();
        }
    }

    public void autoCovidMessage(MessageReceivedEvent event) throws URISyntaxException, IOException, ParseException {
        //함수 개발중
    }

    public JSONObject getCovidAPI() throws URISyntaxException, IOException, ParseException {
        APICall apiCall = new APICall();

        URIBuilder url = new URIBuilder("http://api.corona-19.kr/korea");
        url.addParameter("serviceKey", covidKey);
        String result = apiCall.call(url, "GET");

        JSONParser parser = new JSONParser();
        return (JSONObject) parser.parse(result);
    }

}
