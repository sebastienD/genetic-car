package fr.genetic.server.web.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

/**
 * {@see http://www.theotherian.com/2014/03/spring-boot-websockets-stomp-chat.html }
 */
@Controller
public class WallController {

    @MessageMapping("/status/champions")
    public void statusOfChampions() {
        // nothing
    }

}
