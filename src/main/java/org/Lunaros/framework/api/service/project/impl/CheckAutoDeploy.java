package org.Lunaros.framework.api.service.project.impl;

import org.Lunaros.util.StringUtils;
import org.Lunaros.exception.DeploymentEventException;
import org.Lunaros.exception.DeploymentTerminatedException;
import org.Lunaros.framework.api.biz.cluster.ClusterBiz;
import org.Lunaros.framework.api.biz.deployment.DeploymentBiz;
import org.Lunaros.framework.api.biz.deployment.VersionBiz;
import org.Lunaros.framework.api.consolemodel.deployment.ContainerDraft;
import org.Lunaros.framework.api.model.ci.related.ImageInformation;
import org.Lunaros.framework.api.model.cluster.Cluster;
import org.Lunaros.framework.api.model.deployment.Deployment;
import org.Lunaros.framework.api.model.deployment.Version;
import org.Lunaros.framework.api.service.deployment.DeploymentService;
import org.Lunaros.framework.engine.ClusterRuntimeDriver;
import org.Lunaros.framework.engine.RuntimeDriver;
import org.Lunaros.framework.engine.event.AutoDeploy.AutoUpdateInfo;
import org.Lunaros.framework.engine.exception.DaoException;
import org.Lunaros.framework.engine.model.CustomObjectMapper;
import org.Lunaros.util.CommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

/**
 * Created by feiliu206363 on 2016/11/4.
 */
@Component("checkAutoDeploy")
public class CheckAutoDeploy {
    private static Logger logger = LoggerFactory.getLogger(CheckAutoDeploy.class);

    @Autowired
    VersionBiz versionBiz;
    @Autowired
    DeploymentBiz deploymentBiz;
    @Autowired
    CustomObjectMapper objectMapper;
    @Autowired
    DeploymentService deploymentService;
    @Autowired
    ClusterBiz clusterBiz;

    public void checkDeploy(ImageInformation imageInformation) {
        List<Deployment> deployments = deploymentBiz.listRunningDeployment();
        if (deployments == null || deployments.isEmpty()) {
            return;
        }
        for (Deployment deployment : deployments) {
            RuntimeDriver driver = ClusterRuntimeDriver.getClusterDriver(deployment.getClusterId());
            if (driver == null) {
                continue;
            }
            try {
                List<Version> versions = driver.getCurrnetVersionsByDeployment(deployment);
                if (versions != null && versions.size() == 1) {
                    Version version = versions.get(0);
                    List<ContainerDraft> containerDrafts = version.getContainerDrafts();
                    if (containerDrafts == null || containerDrafts.isEmpty()) {
                        continue;
                    }
                    boolean update = false;
                    for (ContainerDraft containerDraft : containerDrafts) {
                        if (containerDraft.isAutoDeploy() && check(containerDraft.getRegistry(), imageInformation.getRegistry())
                                && check(containerDraft.getImage(), imageInformation.getImageName())
                                && !check(containerDraft.getTag(), imageInformation.getImageTag())) {
                            update = true;
                            containerDraft.setTag(imageInformation.getImageTag());
                        }
                    }
                    if (update) {
                        Cluster cluster = clusterBiz.getClusterById(deployment.getClusterId());
                        if (cluster == null) {
                            logger.warn("auto deploy triggered, but cluster info is null, deployId=" + deployment.getId()
                                    + ", clusterId=" + deployment.getClusterId());
                            return;
                        }
                        versionBiz.insertVersionWithLogCollect(version, cluster);
                        AutoUpdateInfo autoUpdateInfo = new AutoUpdateInfo();
                        autoUpdateInfo.setDeployId(version.getDeployId());
                        autoUpdateInfo.setVersionId(version.getVersion());
                        autoUpdateInfo.setReplicas((int) driver.getTotalReplicasByDeployment(deployment));
                        deploymentService.startUpdate(autoUpdateInfo, false);
//                        DMEventSender.publishEvent(new AutoDeploymentUpdate(autoUpdateInfo));
                    }
                } else if (versions != null) {
                    logger.warn("more than one version is running, info = " + objectMapper.writeValueAsString(versions));
                }
            } catch (IOException | DeploymentEventException | DaoException | DeploymentTerminatedException e) {
                logger.warn("catch exception when get version information, message is " + e.getMessage());
            }
        }
    }

    private boolean check(String str1, String str2) {
        if (StringUtils.isBlank(str1) || StringUtils.isBlank(str2)) {
            return false;
        }
        if (CommonUtil.domainUrl(str1).equals(CommonUtil.domainUrl(str2))) {
            return true;
        }
        return false;
    }
}
