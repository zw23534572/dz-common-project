package com.dazong.example.web.init;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import reactor.bus.EventBus;
import reactor.bus.selector.Selectors;

/**
 * @author huqichao
 * @create 2017-10-23 16:50
 **/
@Component
public class ReactorRegister implements ApplicationListener<ContextRefreshedEvent> {

    private static final Logger logger = LoggerFactory.getLogger(ReactorRegister.class);

    @Autowired
    private EventBus eventBus;


    /**
     * Handle an application event.
     *
     * @param event the event to respond to
     */
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        logger.debug("application was init");
        //TODO 注册reactor
        //eventBus.on(Selectors.object("RefundNextNodeReactorListener"), refundNextNodeReactorListener);
    }
}
