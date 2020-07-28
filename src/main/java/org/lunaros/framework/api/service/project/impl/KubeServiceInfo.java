package org.Lunaros.framework.api.service.project.impl;

import org.Lunaros.framework.api.biz.cluster.ClusterBiz;
import org.Lunaros.framework.api.biz.global.GlobalBiz;
import org.Lunaros.framework.api.biz.image.ImageBiz;
import org.Lunaros.framework.api.biz.project.ProjectBiz;
import org.Lunaros.framework.api.model.cluster.Cluster;
import org.Lunaros.framework.api.model.global.CiCluster;
import org.Lunaros.framework.engine.model.JobType;
import org.Lunaros.global.GlobalConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by feiliu206363 on 2015/12/6.
 */
@Component("kubeServiceInfo")
public class KubeServiceInfo {

    static GlobalBiz globalBiz;
    static ProjectBiz projectBiz;
    static ClusterBiz clusterBiz;
    static ImageBiz imageBiz;

    @Autowired
    public void setProjectBiz(ProjectBiz projectBiz) {
        KubeServiceInfo.projectBiz = projectBiz;
    }

    @Autowired
    public void setClusterBiz(ClusterBiz clusterBiz) {
        KubeServiceInfo.clusterBiz = clusterBiz;
    }

    @Autowired
    public void setImageBiz(ImageBiz imageBiz) {
        KubeServiceInfo.imageBiz = imageBiz;
    }

    @Autowired
    public void setGlobalService(GlobalBiz globalBiz) {
        KubeServiceInfo.globalBiz = globalBiz;
    }

    public static CiCluster getCiCluster() {
        return globalBiz.getCiCluster();
    }

    public static String getBuildTaskNameByIdAndType(int buildId, JobType type) {
        switch (type) {
            case PROJECT:
                return projectBiz.getBuildTaskNameById(buildId);
        }
        return null;
    }

    public static Cluster getClusterBasicById(int clusterId) {
        return clusterBiz.getById(GlobalConstant.CLUSTER_TABLE_NAME, clusterId, Cluster.class);
    }
}
