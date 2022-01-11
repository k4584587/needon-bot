package kr.needon.needonbot.domain.repository;

import kr.needon.needonbot.domain.model.BotConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface BotConfigRepository extends JpaRepository<BotConfig, Integer>, JpaSpecificationExecutor<BotConfig> {

    List<BotConfig> findAllByBotNameAndChannelName(String botName, String channelName);


}
