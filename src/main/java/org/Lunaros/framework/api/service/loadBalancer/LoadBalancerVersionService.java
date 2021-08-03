package org.Lunaros.framework.api.service.loadBalancer;

import java.util.List;

import org.Lunaros.framework.api.consolemodel.deployment.VersionInfo;
import org.Lunaros.framework.api.consolemodel.loadBalancer.NginxVersionDraft;

/**
 * Created by jackfan on 17/3/7.
 */
public interface LoadBalancerVersionService {
    
    NginxVersionDraft createVersion(NginxVersionDraft version, int lbId) throws Exception;

    NginxVersionDraft getVersionByLbIdAndVersionId(int lbId, int versionId) throws Exception;
    
    List<VersionInfo> listVersionByLbId(int lbId) throws Exception;
}