package org.Lunaros.framework.api.service.global;

import org.Lunaros.framework.api.model.global.ClusterMonitor;
import org.Lunaros.basemodel.HttpResponseTemp;

/**
 * Created by feiliu206363 on 2016/1/5.
 */
public interface ClusterMonitorService {
    /**
     *
     * @return
     */
    HttpResponseTemp<?> getClusterMonitorInfo();

    /**
     *
     * @param clusterMonitor
     * @return
     */
    HttpResponseTemp<?> setClusterMonitorInfo(ClusterMonitor clusterMonitor);

    /**
     *
     * @param clusterMonitor
     * @return
     */
    HttpResponseTemp<?> modifyClusterMonitorInfo(ClusterMonitor clusterMonitor);

    /**
     *
     * @return
     */
    HttpResponseTemp<?> deleteClusterMonitorInfo();

    /**
     *
     * @return
     */
    HttpResponseTemp<?> getNormalClusterMonitorInfo();
}
