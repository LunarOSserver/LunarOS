package org.Lunaros.framework.api.service.global.impl;

import org.Lunaros.basemodel.HttpResponseTemp;
import org.Lunaros.basemodel.ResultStat;
import org.Lunaros.framework.api.biz.global.GlobalBiz;
import org.Lunaros.framework.api.controller.exception.ApiException;
import org.Lunaros.framework.api.controller.exception.PermitException;
import org.Lunaros.framework.api.model.global.LoginOption;
import org.Lunaros.framework.api.model.global.SsoInfo;
import org.Lunaros.framework.api.service.global.SsoInfoService;
import org.Lunaros.framework.engine.AuthUtil;
import org.Lunaros.global.CurrentThreadInfo;
import org.Lunaros.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by KaiRen on 2017/4/19.
 */
@Service
public class SsoInfoServiceImpl implements SsoInfoService {
    @Autowired
    GlobalBiz globalBiz;

    private void checkAdmin() {
        if (!AuthUtil.isAdmin(CurrentThreadInfo.getUserId())) {
            throw new PermitException("only admin can operate sso info");
        }
    }

    @Override
    public HttpResponseTemp<?> getSsoInfo() {
        checkAdmin();
        SsoInfo ret = globalBiz.getSsoInfo();
        return ResultStat.OK.wrap(ret);
    }

    @Override
    public HttpResponseTemp<?> setSsoInfo(SsoInfo ssoInfo) {
        checkAdmin();
        if (!StringUtils.isBlank(ssoInfo.checkLegality())) {
            throw ApiException.wrapMessage(ResultStat.PARAM_ERROR, ssoInfo.checkLegality());
        }
        SsoInfo existSsoInfo = globalBiz.getSsoInfo();
        if (existSsoInfo != null) {
            globalBiz.updateSsoInfo(ssoInfo);
        } else {
            globalBiz.setSsoInfo(ssoInfo);
        }
        return null;
    }

    @Override
    public HttpResponseTemp<?> modifySsoInfo(SsoInfo ssoInfo) {
        checkAdmin();
        if (!StringUtils.isBlank(ssoInfo.checkLegality())) {
            throw ApiException.wrapMessage(ResultStat.PARAM_ERROR, ssoInfo.checkLegality());
        }
        globalBiz.updateSsoInfo(ssoInfo);
        return null;
    }

    @Override
    public HttpResponseTemp<?> deleteSsoInfo() {
        checkAdmin();
        globalBiz.deleteSsoInfo();
        return null;
    }

    @Override
    public HttpResponseTemp<?> getLoginOption() {
        LoginOption loginOption = new LoginOption();
        loginOption.setNormalLogin(true);
        if (globalBiz.getSsoInfo() != null) {
            loginOption.setSsoLogin(true);
        } else {
            loginOption.setSsoLogin(false);
        }
        if (globalBiz.getLdapInfo() != null) {
            loginOption.setLdapLogin(true);
        } else {
            loginOption.setLdapLogin(false);
        }
        return ResultStat.OK.wrap(loginOption);
    }
}
