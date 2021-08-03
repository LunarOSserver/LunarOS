package org.Lunaros.framework.api.service.global.impl;

import org.Lunaros.util.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.Lunaros.basemodel.HttpResponseTemp;
import org.Lunaros.basemodel.ResultStat;
import org.Lunaros.framework.api.biz.global.GlobalBiz;
import org.Lunaros.framework.api.controller.exception.ApiException;
import org.Lunaros.framework.api.controller.exception.PermitException;
import org.Lunaros.framework.api.model.auth.related.LoginType;
import org.Lunaros.framework.api.model.global.LdapInfo;
import org.Lunaros.framework.api.model.global.LdapLoginInfo;
import org.Lunaros.framework.api.service.global.LdapInfoService;
import org.Lunaros.framework.engine.AuthUtil;
import org.Lunaros.framework.shiro.token.MultiAuthenticationToken;
import org.Lunaros.global.CurrentThreadInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by feiliu206363 on 2015/12/30.
 */
@Service
public class LdapInfoServiceImpl implements LdapInfoService {

    @Autowired
    GlobalBiz globalBiz;

    private void checkAdmin() {
        if (!AuthUtil.isAdmin(CurrentThreadInfo.getUserId())) {
            throw new PermitException("only admin can operate ldap info");
        }
    }

    @Override
    public HttpResponseTemp<?> getLdapInfo() {
        checkAdmin();
        LdapInfo ldapInfo = globalBiz.getLdapInfo();
        return ResultStat.OK.wrap(ldapInfo);
    }

    @Override
    public HttpResponseTemp<?> setLdapInfo(LdapInfo ldapInfo) {
        checkAdmin();

        if (!StringUtils.isBlank(ldapInfo.checkLegality())) {
            throw ApiException.wrapMessage(ResultStat.PARAM_ERROR, ldapInfo.checkLegality());
        }

        globalBiz.deleteLdapInfo();
        globalBiz.addLdapInfo(ldapInfo);

//        ResourceHistory resourceHistory = new ResourceHistory(ResourceType.LDAP.getResourceName(), ldapInfo.getId(),
//                OperationType.SET.getOperation(), userId, System.currentTimeMillis(), "OK");
//        resourceHistoryMapper.addResourceHistory(resourceHistory);

        return ResultStat.OK.wrap(ldapInfo);
    }

    @Override
    public HttpResponseTemp<?> modifyLdapInfo(LdapInfo ldapInfo) {
        checkAdmin();

        if (!StringUtils.isBlank(ldapInfo.checkLegality())) {
            throw ApiException.wrapMessage(ResultStat.PARAM_ERROR, ldapInfo.checkLegality());
        }

        globalBiz.updateLdapInfo(ldapInfo);
//        ResourceHistory resourceHistory = new ResourceHistory(ResourceType.LDAP.getResourceName(), ldapInfo.getId(),
//                OperationType.MODIFY.getOperation(), userId, System.currentTimeMillis(), "OK");
//        resourceHistoryMapper.addResourceHistory(resourceHistory);

        return ResultStat.OK.wrap(ldapInfo);
    }

    @Override
    public HttpResponseTemp<?> deleteLdapInfo() {
        checkAdmin();
        globalBiz.deleteLdapInfo();
//        ResourceHistory resourceHistory = new ResourceHistory(ResourceType.LDAP.getResourceName(), id,
//                OperationType.DELETE.getOperation(), userId, System.currentTimeMillis(), "OK");
//        resourceHistoryMapper.addResourceHistory(resourceHistory);
        return ResultStat.OK.wrap(null);
    }

    @Override
    public HttpResponseTemp<?> ldapLoginTest(LdapLoginInfo ldapLoginInfo) {
        if (!StringUtils.isBlank(ldapLoginInfo.checkLegality())) {
            throw ApiException.wrapMessage(ResultStat.PARAM_ERROR, ldapLoginInfo.checkLegality());
        }

        return normalLogin(ldapLoginInfo);
    }

    public HttpResponseTemp<?> normalLogin(LdapLoginInfo ldapLoginInfo) {
        Subject subject = SecurityUtils.getSubject();
        String ldapEmailSuffix = ldapLoginInfo.getEmailSuffix();
        String userName = ldapLoginInfo.getUsername();
        if (ldapEmailSuffix != null && !userName.endsWith(ldapEmailSuffix)) {
            ldapLoginInfo.setUsername(userName + ldapEmailSuffix);
        }
        UsernamePasswordToken token = new MultiAuthenticationToken(ldapLoginInfo.getUsername(),
                ldapLoginInfo.getPassword(), ldapLoginInfo.getServer(), LoginType.LDAP);
        try {
            subject.login(token);
            return ResultStat.OK.wrap(null);
        } catch (UnknownAccountException e) {
            throw ApiException.wrapMessage(ResultStat.USER_NOT_AUTHORIZED, "username wrong");
        } catch (IncorrectCredentialsException e) {
            throw ApiException.wrapMessage(ResultStat.USER_NOT_AUTHORIZED, "password wrong");
        } catch (ExcessiveAttemptsException e) {
            throw ApiException.wrapMessage(ResultStat.USER_NOT_AUTHORIZED, "login wrong too many times");
        } catch (AuthenticationException e) {
            throw ApiException.wrapUnknownException(e);
        }
    }
}
