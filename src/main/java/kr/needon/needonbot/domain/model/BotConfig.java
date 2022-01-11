package kr.needon.needonbot.domain.model;


import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "ND_100BoatConfig")
@DynamicUpdate
@Data
@NoArgsConstructor
public class BotConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String serverName;

    private String channelName;

    private String botName;

    private LocalDateTime writeDt;


}
