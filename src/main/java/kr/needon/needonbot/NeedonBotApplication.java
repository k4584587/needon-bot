package kr.needon.needonbot;

import kr.needon.needonbot.domain.service.PingPongService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@RequiredArgsConstructor
@SpringBootApplication
public class NeedonBotApplication {

    private static PingPongService pingPongService;

    public static void main(String[] args) {
        SpringApplication.run(NeedonBotApplication.class, args);
    }

}
