package kr.needon.needonbot.domain.service;

import lombok.extern.java.Log;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;

import javax.security.auth.login.LoginException;


@Log
public class PingPongService extends ListenerAdapter {

    public void run(DefaultShardManagerBuilder builder) throws LoginException {
        builder.addEventListeners(new PingPongService());
        builder.build();
        log.info("핑퐁봇 로드 완료!");
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        Message msg = event.getMessage();
        if (msg.getContentRaw().equals("!핑"))
        {
            MessageChannel channel = event.getChannel();
            long time = System.currentTimeMillis();
            channel.sendMessage("퐁!") /* => RestAction<Message> */
                   .queue(response /* => Message */ -> {
                       log.info("핑퐁 봇 요청");
                       response.editMessageFormat("퐁!: %d ms", System.currentTimeMillis() - time).queue();
                   });
        }
    }

}
