package fr.genetic.server.web.controller;

import fr.genetic.server.game.Game;
import fr.genetic.server.web.dto.CarDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.stream.Collectors;

/**
 * {@see http://www.theotherian.com/2014/03/spring-boot-websockets-stomp-chat.html }
 */
@Controller
public class WallController {

    @Autowired
    private SimpMessagingTemplate template;

    @MessageMapping("/status/champions")
    @SendTo("/topic/champions")
    public List<CarDto> statusOfChampions() throws Exception {
        return Game.players().entrySet().stream()
                .map(entry -> CarDto.create(entry.getKey(), entry.getValue().leader.car))
                .collect(Collectors.toList());
    }

}
