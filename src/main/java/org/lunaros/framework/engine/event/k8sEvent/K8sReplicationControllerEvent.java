package org.Lunaros.framework.engine.event.k8sEvent;

import org.Lunaros.framework.engine.event.DMEvent;

/**
 * Created by feiliu206363 on 2016/10/11.
 */
public class K8sReplicationControllerEvent extends DMEvent<K8sEventDetail> {
    public K8sReplicationControllerEvent(K8sEventDetail source) {
        super(source);
    }
}
