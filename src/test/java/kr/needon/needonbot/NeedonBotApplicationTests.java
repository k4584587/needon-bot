package kr.needon.needonbot;


import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.boot.test.context.SpringBootTest;

import javax.security.auth.login.LoginException;
import java.util.Objects;

import static net.dv8tion.jda.api.JDABuilder.createDefault;

@Log
@SpringBootTest
@RequiredArgsConstructor
class NeedonBotApplicationTests extends ListenerAdapter {

    public static void main(String[] args) throws LoginException {
        JDA jda = createDefault("OTI5OTA3MzI0MjI1NzMyNjE4.YduJrg.B3GQe7ub_hyGnLCk6Pz1XvBLQ8E").build();
        //You can also add event listeners to the already built JDA instance
        // Note that some events may not be received if the listener is added after calling build()
        // This includes events such as the ReadyEvent
        jda.addEventListener(new NeedonBotApplicationTests());
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {

        MessageChannel channel = event.getChannel();
        channel.sendMessage("[!] 이미 이 채널에 코로나 봇 설정이 등록되여 있습니다.").queue();

        if (event.isFromType(ChannelType.PRIVATE))
        {
            System.out.printf("[PM] %s: %s\n", event.getAuthor().getName(),
                                    event.getMessage().getContentDisplay());
        }
        else
        {
            System.out.printf("[%s][%s] %s: %s\n", event.getGuild().getName(),
                        event.getTextChannel().getName(), Objects.requireNonNull(event.getMember()).getEffectiveName(),
                        event.getMessage().getContentDisplay());
        }
    }

}
