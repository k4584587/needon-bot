package kr.needon.needonbot.domain.request;

import lombok.Data;

@Data
public class BotConfigRequest {

    private String serverName;

    private String channelName;

    private String botName;
}
