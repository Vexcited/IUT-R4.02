package iut.java.spring.tests.unit;

import iut.java.spring.dto.MessageDto;
import iut.java.spring.service.interfaces.IMessageService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class MessageServiceTest {
    @Autowired
    private IMessageService service;

    @Test
    void testSayHelloDefault() {
        // ACT
        MessageDto messageDto = service.sayHello(null);

        // ASSERT
        assertThat(messageDto).extracting(MessageDto::getText).isEqualTo("Hello World !");
    }

    @Test
    void testSayHello() {
        // ACT
        MessageDto messageDto = service.sayHello("Mikkel");

        // ASSERT
        assertThat(messageDto).extracting(MessageDto::getText).isEqualTo("Hello Mikkel !");
    }
}