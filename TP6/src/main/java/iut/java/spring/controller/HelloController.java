package iut.java.spring.controller;

import iut.java.spring.dto.MessageDto;
import iut.java.spring.service.interfaces.IMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @Autowired
    private IMessageService service;

    @GetMapping(value= { "/hello", "/hello/{who}" })
    public MessageDto hello(@PathVariable(required = false) String who) {
        return service.sayHello(who);
    }
}