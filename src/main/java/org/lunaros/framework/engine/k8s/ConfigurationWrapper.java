package org.Lunaros.framework.engine.k8s;

import io.fabric8.kubernetes.api.model.ConfigMap;
import io.fabric8.kubernetes.api.model.ConfigMapBuilder;
import org.Lunaros.exception.K8sDriverException;
import org.Lunaros.framework.api.model.cluster.Cluster;
import org.Lunaros.framework.api.model.configuration.Configuration;
import org.Lunaros.framework.api.service.project.impl.KubeServiceInfo;
import org.Lunaros.framework.engine.k8s.util.Fabric8KubeUtils;
import org.Lunaros.framework.engine.k8s.util.KubeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by feiliu206363 on 2017/1/20.
 */
public class ConfigurationWrapper {
    private static Logger logger = LoggerFactory.getLogger(ConfigurationWrapper.class);
    private KubeUtils client;

    public ConfigurationWrapper init(int clusterId, String namespace) throws K8sDriverException {
        Cluster cluster = KubeServiceInfo.getClusterBasicById(clusterId);
        if (cluster == null) {
            throw new K8sDriverException("no such cluster info, id=" + clusterId);
        }

        // TODO: when we have different cluster type, should add more op here
        client = Fabric8KubeUtils.buildKubeUtils(cluster, namespace);
        return this;
    }


    public void createConfigmap(Configuration configuration) throws K8sDriverException {
        client.createConfigmap(buildConfigmap(configuration));
    }

    private ConfigMap buildConfigmap(Configuration configuration) {
        return new ConfigMapBuilder()
                .withNewMetadata()
                .withName(configuration.buildK8sName())
                .withLabels(configuration.buildK8sLabels())
                .endMetadata()
                .withData(configuration.getData())
                .build();
    }

    public void deleteConfiguration(Configuration configuration) throws K8sDriverException {
        client.deleteConfigmap(configuration.buildK8sLabels());
    }

    public void updateConfigmap(Configuration configuration) throws K8sDriverException {
        client.patchConfigmap(buildConfigmap(configuration));
    }
}
