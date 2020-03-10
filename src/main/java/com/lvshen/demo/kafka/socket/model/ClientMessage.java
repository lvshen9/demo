package com.lvshen.demo.kafka.socket.model;


import lombok.AllArgsConstructor;
import lombok.Data;


/**
 * @author
 */
@Data
@AllArgsConstructor
public class ClientMessage {

    private String name;

    public ClientMessage() {
    }

}
