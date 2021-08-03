package org.Lunaros.framework.api.service.global.impl;

import org.Lunaros.util.StringUtils;
import org.Lunaros.basemodel.HttpResponseTemp;
import org.Lunaros.basemodel.ResultStat;
import org.Lunaros.framework.api.biz.global.GlobalBiz;
import org.Lunaros.framework.api.controller.exception.ApiException;
import org.Lunaros.framework.api.controller.exception.PermitException;
import org.Lunaros.framework.api.model.global.ClusterMonitor;
import org.Lunaros.framework.api.service.global.ClusterMonitorService;
import org.Lunaros.framework.engine.AuthUtil;
import org.Lunaros.global.CurrentThreadInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by feiliu206363 on 2016/1/5.
 */
@Service
public class ClusterMonitorServiceImpl implements ClusterMonitorService {

    @Autowired
    GlobalBiz globalBiz;

    private void checkAdmin() {
        if (!AuthUtil.isAdmin(CurrentThreadInfo.getUserId())) {
            throw new PermitException("only admin can operate cluster monitor");
        }
    }

    @Override
    public HttpResponseTemp<?> getClusterMonitorInfo() {
        ClusterMonitor clusterMonitor = globalBiz.getMonitor();
        return ResultStat.OK.wrap(clusterMonitor);
    }

    @Override
    public HttpResponseTemp<?> setClusterMonitorInfo(ClusterMonitor clusterMonitor) {
        checkAdmin();

        if (!StringUtils.isBlank(clusterMonitor.checkLegality())) {
            throw ApiException.wrapMessage(ResultStat.PARAM_ERROR, clusterMonitor.checkLegality());
        }

        clusterMonitor.setCreateTime(System.currentTimeMillis());
        globalBiz.deleteMonitor();
        globalBiz.addMonitor(clusterMonitor);
        return ResultStat.OK.wrap(clusterMonitor);
    }

    @Override
    public HttpResponseTemp<?> modifyClusterMonitorInfo(ClusterMonitor clusterMonitor) {
        checkAdmin();

        globalBiz.updateMonitor(clusterMonitor);
        return ResultStat.OK.wrap(clusterMonitor);
    }

    @Override
    public HttpResponseTemp<?> deleteClusterMonitorInfo() {
        checkAdmin();

        globalBiz.deleteMonitor();
        return ResultStat.OK.wrap(null);
    }

    @Override
    public HttpResponseTemp<?> getNormalClusterMonitorInfo() {
        ClusterMonitor clusterMonitor = globalBiz.getMonitor();
        return ResultStat.OK.wrap(clusterMonitor);
    }
}
