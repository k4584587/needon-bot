package kr.needon.needonbot.domain.service;

import kr.needon.needonbot.domain.model.BotConfig;
import kr.needon.needonbot.domain.repository.BotConfigRepository;
import kr.needon.needonbot.domain.request.BotConfigRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Log
@Service
@RequiredArgsConstructor
public class BotConfigService {

    private final BotConfigRepository botConfigRepository;

    public List<BotConfig> searchConfig(BotConfigRequest request) {
        return botConfigRepository.findAllByBotNameAndChannelName(request.getBotName(), request.getChannelName());
    }

    public void insertBotConfig(BotConfigRequest request) {

            BotConfig botConfig = new BotConfig();

            botConfig.setBotName(request.getBotName());
            botConfig.setChannelName(request.getChannelName());
            botConfig.setServerName(request.getServerName());
            botConfig.setWriteDt(LocalDateTime.now());
            botConfigRepository.save(botConfig);

    }

}
