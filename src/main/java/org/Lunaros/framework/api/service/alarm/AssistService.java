package org.Lunaros.framework.api.service.alarm;

import org.Lunaros.framework.api.model.alarm.assist.ActionWrap;
import org.Lunaros.framework.api.model.alarm.assist.UserWrap;

/**
 * Created by baokangwang on 2016/4/14.
 */
public interface AssistService {

    ActionWrap getActionById(long actionId);

    UserWrap getUsers(String group);

    String storeLink(String content);

    String retrieveLink(long linkId);
}
