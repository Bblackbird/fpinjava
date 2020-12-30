package com.funk.fsm;

import com.funk.common.Option;
//import lombok.Value
//import quickfix.Message;
//import quickfixSessionID;

//@Value
public class Input<E> {

    public enum Direction {
        IN, IN_DELAYED, OUT, OUT_DELAYED, INTERNAL, TIMER, FAILURE, RETRY
    }

    public interface Message {
    }

    public interface SessionID {
    }

    public Message message;
    public E event;
    public Direction direction;
    public SessionID sessionID;
    public Option<Exception> failureValue;

    public Input(Message message, E event, Direction direction, SessionID sessionID) {
        this(message, event, direction, sessionID, Option.none());
    }

    public Input(Message message, E event, Direction direction, SessionID sessionID, Exception failureValue) {
        this(message, event, direction, sessionID, Option.some(failureValue));
    }

    public Input(Message message, E event, Direction direction, SessionID sessionID, Option<Exception> failureValue) {
        this.message = message;
        this.event = event;
        this.direction = direction;
        this.sessionID = sessionID;
        this.failureValue = failureValue;
    }

    public static <T> Input<T> in(T event, Message message, SessionID sid) {
        return of(event, message, sid, Direction.IN);
    }

    public static <T> Input<T> out(T event, Message message, SessionID sid) {
        return of(event, message, sid, Direction.OUT);
    }

    public static <T> Input<T> internal(T event, Message message, SessionID sid) {
        return of(event, message, sid, Direction.INTERNAL);
    }

    public static <T> Input<T> failure(T event, Message message, SessionID sid) {
        return of(event, message, sid, Direction.FAILURE);
    }

    public static <T> Input<T> retry(T event, Message message, SessionID sid) {
        return of(event, message, sid, Direction.RETRY);
    }

    private static <T> Input<T> of(T event, Message message, SessionID sid, Direction direction) {
        return new Input<>(message, event, direction, sid);
    }

}
