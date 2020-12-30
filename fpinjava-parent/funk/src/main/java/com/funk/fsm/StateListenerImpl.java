package com.funk.fsm;

import com.funk.common.Nothing;
import com.funk.common.Tuple;
// import lombok.extern.slf4j;

import java.util.function.Function;

//@Slf4j
public class StateListenerImpl<A, S> implements StateListener<A, S> {

    protected Function<Tuple<S, StateTuple<A, S>>, Nothing> onChange;
    protected Function<Tuple<Exception, StateTuple<A, S>>, Nothing> onError;
    protected Function<Tuple<S, StateTuple<A, S>>, Nothing> onComplete;


    public StateListenerImpl(Function<Tuple<S, StateTuple<A, S>>, Nothing> onChange) {
        this(onChange, (tuple) -> {
            //log.error("Failed"), tuple._1);
            return Nothing.instance;
        });
    }

    public StateListenerImpl(Function<Tuple<S, StateTuple<A, S>>, Nothing> onChange,
                             Function<Tuple<Exception, StateTuple<A, S>>, Nothing> onError) {
        this(onChange, onError, (tuple) -> {
            //log.info("Completed");
            return Nothing.instance;
        });
    }

    public StateListenerImpl(Function<Tuple<S, StateTuple<A, S>>, Nothing> onChange,
                             Function<Tuple<Exception, StateTuple<A, S>>, Nothing> onError,
                             Function<Tuple<S, StateTuple<A, S>>, Nothing> onComplete) {
        this.onChange = onChange;
        this.onError = onError;
        this.onComplete = onComplete;
    }

    public static <A, S> StateListener<A, S> of() {
        return of(t -> {
                    //log.debug(t._1.toString());
                    return Nothing.instance;
                },
                t -> {
                    //log.error(t._1.toString());
                    return Nothing.instance;
                },
                t -> {
                    //log.info(t._1.toString());
                    return Nothing.instance;
                });
    }

    public static <A, S> StateListener<A, S> of(Function<Tuple<S, StateTuple<A, S>>, Nothing> onChange) {
        return new StateListenerImpl<>(onChange);
    }

    public static <A, S> StateListener<A, S> of(Function<Tuple<S, StateTuple<A, S>>, Nothing> onChange,
                                                Function<Tuple<Exception, StateTuple<A, S>>, Nothing> onError) {
        return new StateListenerImpl<>(onChange, onError);
    }

    public static <A, S> StateListener<A, S> of(Function<Tuple<S, StateTuple<A, S>>, Nothing> onChange,
                                                Function<Tuple<Exception, StateTuple<A, S>>, Nothing> onError,
                                                Function<Tuple<S, StateTuple<A, S>>, Nothing> onComplete) {
        return new StateListenerImpl<>(onChange, onError, onComplete);
    }

    public void onChange(S newState, StateTuple<A, S> inputState) {
        try {
            onChange.apply(new Tuple<>(newState, inputState));
        } catch (Exception e) {
            //log.error("Failed onChange", e);
        }
    }

    public void onError(Exception e, StateTuple<A, S> inputState) {
        try {
            onError.apply(new Tuple<>(e, inputState));
        } catch (Exception x) {
            //log.error("Failed onError", x);
        }
    }

    public void onComplete(S newState, StateTuple<A, S> inputState) {
        try {
            onComplete.apply(new Tuple<>(newState, inputState));
        } catch (Exception e) {
            //log.error("Failed onComplete", e);
        }
    }

    public static <A, S> Nothing logDebug(Tuple<S, StateTuple<A, S>> tuple) {
        // if(log.isDebugEnabled() {
        // log.debug(tuple._1.toString());
        // log.debug(tuple._2.toString());
        //}
        return Nothing.instance;
    }

    public static <A, S> Nothing logError(Tuple<S, StateTuple<A, S>> tuple) {
        // log.error(tuple._1.toString());
        // log.error(tuple._2.toString());
        return Nothing.instance;
    }

    public static <A, S> Nothing logComplete(Tuple<S, StateTuple<A, S>> tuple) {
        // if(log.isDebugEnabled() {
        // log.info(tuple._1.toString());
        // log.info(tuple._2.toString());
        //}
        return Nothing.instance;
    }

}
