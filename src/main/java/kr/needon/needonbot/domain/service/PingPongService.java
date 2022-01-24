package kr.needon.needonbot.domain.service;

import kr.needon.needonbot.domain.model.BotLog;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Log
@Service
@RequiredArgsConstructor
public class PingPongService extends ListenerAdapter {

    private final LogService logService;

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {

        User author = event.getAuthor();                //The user that sent the message
        Message msg = event.getMessage();
        BotLog botLog = new BotLog();

        if (msg.getContentRaw().equals("!핑")) {
            MessageChannel channel = event.getChannel();
            long time = System.currentTimeMillis();
            channel.sendMessage("퐁!") /* => RestAction<Message> */
                    .queue(response /* => Message */ -> {
                        log.info(author.getName() + "님이 " + "핑퐁 봇을 요청함");
                        response.editMessageFormat("퐁!: %d ms", System.currentTimeMillis() - time).queue();
                        botLog.setBotName("핑퐁");
                        botLog.setContent("핑퐁 봇 호출");
                        botLog.setCalUser(author.getName());
                        botLog.setWriteDt(LocalDateTime.now());
                        logService.insert(botLog);
                    });
        }
    }

    @Override
    public void onReady(@NotNull ReadyEvent event) {
        log.info("PingPong Bot is Ready!");
    }
}
