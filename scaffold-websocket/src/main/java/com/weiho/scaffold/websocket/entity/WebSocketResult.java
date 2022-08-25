package com.weiho.scaffold.websocket.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * WebSocket统一返回体
 *
 * @author Weiho
 * @date 2022/8/22
 */
@Data
@AllArgsConstructor
public class WebSocketResult<T> {
    private int code;
    private T data;
}
