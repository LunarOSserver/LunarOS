package org.Lunaros.framework.shiro.filter;

import org.apache.http.client.utils.URIBuilder;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.cas.CasFilter;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.WebUtils;
import org.Lunaros.framework.api.model.global.SsoInfo;
import org.Lunaros.framework.api.model.global.SsoToken;
import org.Lunaros.framework.engine.SsoUtil;
import org.Lunaros.global.GlobalConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URISyntaxException;

/**
 * Created by KaiRen on 2017/4/11.
 */
public class SsoFilter extends CasFilter {
    private static Logger logger = LoggerFactory.getLogger(SsoFilter.class);
        public String getLunarosServerUrl() {
        return System.getenv(GlobalConstant.LUNAROS_SERVER_URL);
    }
    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request,
                                     ServletResponse response) throws Exception {
        WebUtils.issueRedirect(request, response, getSuccessUrl());
        return false;
    }


    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException ae, ServletRequest request,
                                     ServletResponse response) {

        String casServerLoginUrl = "";
        SsoInfo ssoInfo = SsoUtil.getSsoInfo();
        if (ssoInfo != null) {
            casServerLoginUrl = ssoInfo.getCasServerUrl() + ssoInfo.getLoginUrl();
        }
        String failureUrl = null;
        try {
            URIBuilder uriBuilder = new URIBuilder(casServerLoginUrl);
            uriBuilder.addParameter("service", getLunarosServerUrl() + GlobalConstant.SSO_API);
            failureUrl = uriBuilder.toString();
        } catch (URISyntaxException e) {
            logger.error("Cannot build failure url : {}", e);
            return false;
        }
        if (logger.isDebugEnabled()) {
            logger.debug( "Authentication exception", ae );
        }
        // is user authenticated or in remember me mode ?
        Subject subject = getSubject(request, response);
        if (subject.isAuthenticated() || subject.isRemembered()) {
            try {
                issueSuccessRedirect(request, response);
            } catch (Exception e) {
                logger.error("Cannot redirect to the default success url", e);
            }
        } else {
            try {
                WebUtils.issueRedirect(request, response, failureUrl);
            } catch (IOException e) {
                logger.error("Cannot redirect to failure url : {}", failureUrl, e);
            }
        }
        return false;
    }
}
