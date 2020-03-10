package com.lvshen.demo.kafka.socket.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author
 */
@Data
@AllArgsConstructor
public class ServerMessage {

    private String content;

    public ServerMessage() {
    }

    @Override
    public String toString() {
        return content;
    }

}
