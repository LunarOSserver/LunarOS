package org.Lunaros.framework.engine.event;

import org.Lunaros.global.SpringContextManager;

/**
 * Created by xupeng on 16-5-6.
 */
public class DMEventSender {

    public static void publishEvent(DMEvent event) {
        SpringContextManager.publishEvent(event);
    }

}
