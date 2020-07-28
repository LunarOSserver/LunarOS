package org.Lunaros.framework.engine.runtime;

import org.Lunaros.framework.api.model.deployment.related.DeployResourceStatus;

/**
 * Created by sparkchen on 16/4/6.
 */
public interface IResourceStatus {
    DeployResourceStatus getDeployResourceStatusById(long deploymentId);
}
