package org.Lunaros.framework.api.service.global;

import org.Lunaros.basemodel.HttpResponseTemp;
import org.Lunaros.framework.api.model.global.SsoInfo;

/**
 * Created by KaiRen on 2017/4/19.
 */
public interface SsoInfoService {

    /**
     *
     * @return SsoInfo
     */

    HttpResponseTemp<?> getSsoInfo();

    /**
     * @param ssoInfo
     * @return SsoInfo
     */

    HttpResponseTemp<?> setSsoInfo(SsoInfo ssoInfo);

    /**
     *
     * @param ssoInfo
     * @return
     */
    HttpResponseTemp<?> modifySsoInfo(SsoInfo ssoInfo);

    /**
     *
     *
     * @return
     */
    HttpResponseTemp<?> deleteSsoInfo();

    /**
     *
     *
     * @return LoginOption
     */
    HttpResponseTemp<?> getLoginOption();



}
