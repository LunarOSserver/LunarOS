package org.Lunaros.framework.api.service.alarm;

import org.Lunaros.basemodel.HttpResponseTemp;
import org.Lunaros.framework.api.consolemodel.alarm.UserGroupDraft;
import org.Lunaros.framework.api.model.alarm.UserInfo;

import java.util.List;

/**
 * Created by KaiRen on 2016/9/27.
 */
public interface UserGroupService {

    /**
     *
     * @return
     */
    HttpResponseTemp<?> listUserGroupInfo();

    /**
     *
     * @param userGroupDraft
     * @return
     */
    HttpResponseTemp<?> createUserGroup(UserGroupDraft userGroupDraft);

    /**
     *
     * @param userGroupDraft
     * @return
     */
    HttpResponseTemp<?> modifyUserGroup(UserGroupDraft userGroupDraft);

    /**
     *
     * @param id
     * @return
     */
    HttpResponseTemp<?> deleteUserGroup(long id);

    /**
     *
     * @param id
     * @param userInfoList
     * @return
     */
    HttpResponseTemp<?> bindUserList(long id, List<UserInfo> userInfoList);

    /**
     *
     * @param id
     * @param userId
     * @return
     */
    HttpResponseTemp<?> unbindUser(long id, int userId);
}
