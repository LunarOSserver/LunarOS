package org.Lunaros.framework.api.biz.deployment;

import org.Lunaros.framework.api.model.deployment.related.DeploymentStatus;

/**
 */

public interface DeploymentStatusBiz {

    DeploymentStatus getDeploymentStatus(int deployId);

    void setDeploymentStatus(int deployId,  DeploymentStatus status);

}
