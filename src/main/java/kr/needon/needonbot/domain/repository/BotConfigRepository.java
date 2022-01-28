package kr.needon.needonbot.domain.repository;

import kr.needon.needonbot.domain.model.BotConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface BotConfigRepository extends JpaRepository<BotConfig, Integer>, JpaSpecificationExecutor<BotConfig> {

    List<BotConfig> findAllByBotNameAndChannelName(String botName, String channelName);

    Optional<BotConfig> findByBotNameAndServerName(String botName, String serverName);
}
