package org.Lunaros.framework.api.controller.exception;

import org.Lunaros.basemodel.ResultStat;

/**
 * Created by sparkchen on 16/4/6.
 */
public class DeployIlegalException extends ApiException {
    public DeployIlegalException() {
        this.stat = ResultStat.DEPLOYMENT_NOT_LEGAL;
    }

    public DeployIlegalException(String message) {
        super(ResultStat.DEPLOYMENT_NOT_LEGAL, message);
    }

}
