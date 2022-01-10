package kr.needon.needonbot.domain.service;

import kr.needon.needonbot.domain.model.BotLog;
import kr.needon.needonbot.domain.repository.BotLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogService {

    private final BotLogRepository botLogRepository;

    public void insert(BotLog botLog) {
        botLogRepository.save(botLog);
    }

}
