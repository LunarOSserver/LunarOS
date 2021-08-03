package org.Lunaros.framework.engine;

import org.Lunaros.exception.DataBaseContentException;
import org.Lunaros.exception.DeploymentEventException;
import org.Lunaros.exception.DeploymentTerminatedException;
import org.Lunaros.framework.api.consolemodel.deployment.EnvDraft;
import org.Lunaros.framework.api.model.auth.User;
import org.Lunaros.framework.api.model.cluster.Cluster;
import org.Lunaros.framework.api.model.deployment.DeployEvent;
import org.Lunaros.framework.api.model.deployment.Deployment;
import org.Lunaros.framework.api.model.deployment.Policy;
import org.Lunaros.framework.api.model.deployment.Version;
import org.Lunaros.framework.engine.exception.DriverException;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

/**
 * Created by sparkchen on 16/4/6.
 */
public interface RuntimeDriver {

    void updateList(Cluster cluster);

    RuntimeDriver init(Cluster cluster);

    boolean isDriverLatest(Cluster cluster);

    // Operation
    void startDeploy(Deployment deployment, Version version, User user, List<EnvDraft> allExtraEnvs)
            throws DriverException, DeploymentEventException, IOException;

    void stopDeploy(Deployment deployment, User user) throws DeploymentEventException, IOException;

    void rollbackDeploy(Deployment deployment, int versionId, List<EnvDraft> allExtraEnvs, User user, Policy policy)
            throws IOException, DeploymentEventException, DeploymentTerminatedException;

    void startUpdate(Deployment deployment, int version, List<EnvDraft> allExtraEnvs, User user, Policy policy)
            throws IOException, DeploymentEventException, DeploymentTerminatedException;

    void abortDeployOperation(Deployment deployment, User user)
            throws DeploymentEventException, IOException, DeploymentTerminatedException;

    void scaleUpDeployment(Deployment deployment, int version, int replicas, List<EnvDraft> allExtraEnvs, User user)
            throws IOException, DeploymentEventException, DeploymentTerminatedException;

    void scaleDownDeployment(Deployment deployment, int version, int replicas, List<EnvDraft> allExtraEnvs, User user)
            throws DeploymentEventException, IOException, DeploymentTerminatedException;

    void checkBasicEvent(Deployment deployment, DeployEvent event)
            throws DeploymentEventException, IOException, DataBaseContentException, ParseException, DeploymentTerminatedException;

    void checkAbortEvent(Deployment deployment, DeployEvent event)
            throws DeploymentEventException, IOException, DeploymentTerminatedException;

    void checkStopEvent(Deployment deployment, DeployEvent event)
            throws DeploymentEventException, IOException, DeploymentTerminatedException;

    void expiredEvent(Deployment deployment, DeployEvent event) throws DeploymentEventException, IOException, DeploymentTerminatedException;

    List<Version> getCurrnetVersionsByDeployment(Deployment deployment) throws DeploymentEventException;

    long getTotalReplicasByDeployment(Deployment deployment) throws DeploymentEventException, DeploymentTerminatedException;
    
    void deletePodByDeployIdAndInsName(Deployment deployment, String insName)
            throws DeploymentEventException, IOException;
}
