package iut.java.spring.service.impl;

import iut.java.spring.dto.MessageDto;
import iut.java.spring.service.interfaces.IMessageService;
import org.springframework.stereotype.Service;

@Service
public class MessageService implements IMessageService {
    @Override
    public MessageDto sayHello(String who) {
        MessageDto message = new MessageDto();
        message.setText("Hello " + (who != null ? who : "World") + " !");
        return message;
    }
}
