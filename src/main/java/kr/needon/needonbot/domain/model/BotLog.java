package kr.needon.needonbot.domain.model;


import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "ND_110BotLog")
@DynamicUpdate
@Data
@NoArgsConstructor
public class BotLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String calUser;

    private String botName;

    private LocalDateTime writeDt;

}
