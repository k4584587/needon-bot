package kr.needon.needonbot.domain.repository;

import kr.needon.needonbot.domain.model.BotLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BotLogRepository extends JpaRepository<BotLog, Integer>, JpaSpecificationExecutor<BotLog> {



}
