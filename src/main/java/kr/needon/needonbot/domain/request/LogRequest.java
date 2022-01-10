package kr.needon.needonbot.domain.request;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LogRequest {

    private Integer id;

    private String calUser;

    private String botName;

    private LocalDateTime writeDt;

}
