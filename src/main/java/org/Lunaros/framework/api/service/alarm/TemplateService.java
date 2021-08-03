package org.Lunaros.framework.api.service.alarm;

import org.Lunaros.basemodel.HttpResponseTemp;
import org.Lunaros.framework.api.consolemodel.alarm.TemplateInfo;

/**
 * Created by baokangwang on 2016/4/13.
 */
public interface TemplateService {

    /**
     *
     * @return
     */
    HttpResponseTemp<?> listTemplateInfo();

    /**
     *
     * @param templateInfo
     * @return
     */
    HttpResponseTemp<?> createTemplate(TemplateInfo templateInfo);

    /**
     *
     * @param templateInfo
     * @return
     */
    HttpResponseTemp<?> modifyTemplate(TemplateInfo templateInfo);

    /**
     *
     * @param id
     * @return
     */
    HttpResponseTemp<?> getTemplateInfo(long id);

    /**
     *
     * @param id
     * @return
     */
    HttpResponseTemp<?> deleteTemplate(long id);
}
