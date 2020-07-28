package org.Lunaros.framework.api.service.alarm.impl;

import org.Lunaros.basemodel.HttpResponseTemp;
import org.Lunaros.basemodel.ResultStat;
import org.Lunaros.framework.api.biz.alarm.AlarmBiz;
import org.Lunaros.framework.api.biz.auth.AuthBiz;
import org.Lunaros.framework.api.consolemodel.alarm.UserGroupDetail;
import org.Lunaros.framework.api.consolemodel.alarm.UserGroupDraft;
import org.Lunaros.framework.api.controller.exception.ApiException;
import org.Lunaros.framework.api.model.alarm.*;
import org.Lunaros.framework.api.model.auth.User;
import org.Lunaros.framework.api.model.operation.OperationType;
import org.Lunaros.framework.api.model.collection.related.ResourceType;
import org.Lunaros.framework.api.service.alarm.UserGroupService;
import org.Lunaros.framework.engine.AuthUtil;
import org.Lunaros.global.ClientConfigure;
import org.Lunaros.global.CurrentThreadInfo;
import org.Lunaros.global.GlobalConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by KaiRen on 2016/9/27.
 */
@Service
public class UserGroupServiceImpl implements UserGroupService {

    private static Logger logger = LoggerFactory.getLogger(UserGroupServiceImpl.class);

    private final ResourceType resourceType = ResourceType.ALARM;

    @Autowired
    AlarmBiz alarmBiz;

    @Autowired
    AuthBiz authBiz;


    @Override
    public HttpResponseTemp<?> listUserGroupInfo() {

        AuthUtil.collectionVerify(CurrentThreadInfo.getUserId(), GlobalConstant.alarmGroupId, resourceType, OperationType.GET, 0);

        List<UserGroupBasic> userGroupBasics = alarmBiz.listUserGroupInfoBasic();
        if (userGroupBasics == null) {
            return ResultStat.OK.wrap(null);
        }
        List<UserGroupDetailTask> userGroupDetailTasks = new LinkedList<>();

        for (UserGroupBasic userGroupBasic : userGroupBasics) {
            userGroupDetailTasks.add(new UserGroupDetailTask(userGroupBasic));
        }
        List<UserGroupDetail> userGroupDetails = ClientConfigure.executeCompletionService(userGroupDetailTasks);
        Collections.sort(userGroupDetails, new UserGroupDetail.UserGroupDetailComparator());
        return ResultStat.OK.wrap(userGroupDetails);
    }

    @Override
    public HttpResponseTemp<?> createUserGroup(UserGroupDraft userGroupDraft) {

        AuthUtil.collectionVerify(CurrentThreadInfo.getUserId(), GlobalConstant.alarmGroupId, resourceType, OperationType.GET, 0);

        if (userGroupDraft == null) {
            throw ApiException.wrapMessage(ResultStat.USERGROUP_NOT_LEGAL, "user group info is null");
        }
        if (userGroupDraft.checkLegality() != null) {
            throw ApiException.wrapMessage(ResultStat.USERGROUP_NOT_LEGAL, userGroupDraft.checkLegality());
        }
        if (alarmBiz.getUserGroupInfoBasicByName(userGroupDraft.getUserGroupName()) != null) {
            throw ApiException.wrapResultStat(ResultStat.USERGROUP_EXISTED);
        }

        UserGroupBasic userGroupBasic = userGroupDraft.toUserGroupBasic();
        userGroupBasic.setCreatorId(AuthUtil.getUserId());
        userGroupBasic.setCreatorName(AuthUtil.getCurrentUserName());
        userGroupBasic.setCreateTime(System.currentTimeMillis());
        userGroupBasic.setUpdateTime(userGroupBasic.getCreateTime());

        alarmBiz.addUserGroupInfoBasic(userGroupBasic);
        return ResultStat.OK.wrap(null);
    }

    @Override
    public HttpResponseTemp<?> modifyUserGroup(UserGroupDraft userGroupDraft) {

        AuthUtil.collectionVerify(CurrentThreadInfo.getUserId(), GlobalConstant.alarmGroupId, resourceType, OperationType.MODIFY, 0);

        if (userGroupDraft == null) {
            throw ApiException.wrapMessage(ResultStat.USERGROUP_NOT_LEGAL, "user group info is null");
        }
        if (userGroupDraft.checkLegality() != null) {
            throw ApiException.wrapMessage(ResultStat.USERGROUP_NOT_LEGAL, userGroupDraft.checkLegality());
        }
        UserGroupBasic updatedUserGroupBasic = alarmBiz.getUserGroupInfoBasicById(userGroupDraft.getId());
        if (updatedUserGroupBasic == null) {
            throw ApiException.wrapResultStat(ResultStat.USERGROUP_NOT_EXISTED);
        }

        updatedUserGroupBasic.setUserGroupName(userGroupDraft.getUserGroupName());
        updatedUserGroupBasic.setUpdateTime(System.currentTimeMillis());

        alarmBiz.updateUserGroupInfoBasicById(updatedUserGroupBasic);
        return ResultStat.OK.wrap(null);
    }

    @Override
    public HttpResponseTemp<?> deleteUserGroup(long id) {

        AuthUtil.collectionVerify(CurrentThreadInfo.getUserId(), GlobalConstant.alarmGroupId, resourceType, OperationType.MODIFY, 0);

        UserGroupBasic userGroupBasic = alarmBiz.getUserGroupInfoBasicById(id);
        if (userGroupBasic == null) {
            throw ApiException.wrapResultStat(ResultStat.USERGROUP_NOT_EXISTED);
        }

        alarmBiz.deleteUserGroupUserBindByUserGroupId(id);
        alarmBiz.deleteUserGroupInfoBasicById(id);
        return ResultStat.OK.wrap(null);
    }

    @Override
    public HttpResponseTemp<?> bindUserList(long id, List<UserInfo> userInfoList) {

        AuthUtil.collectionVerify(CurrentThreadInfo.getUserId(), GlobalConstant.alarmGroupId, resourceType, OperationType.MODIFY, 0);

        if (userInfoList == null) {
            throw ApiException.wrapMessage(ResultStat.USER_NOT_LEGAL, "user info list is null");
        }
        for (UserInfo userInfo : userInfoList) {
            User user = authBiz.getUserById(userInfo.getId());
            if (user == null) {
                throw ApiException.wrapResultStat(ResultStat.USER_NOT_LEGAL);
            }
        }
        UserGroupBasic userGroupBasic = alarmBiz.getUserGroupInfoBasicById(id);
        if (userGroupBasic == null) {
            throw ApiException.wrapResultStat(ResultStat.USERGROUP_NOT_EXISTED);
        }

        for (UserInfo userInfo : userInfoList) {
            int userId = userInfo.getId();
            if (alarmBiz.getUserGroupUserBindTime(id, userId) != null) {
                alarmBiz.updateUserGroupUserBind(id, userId, System.currentTimeMillis());
            } else {
                alarmBiz.addUserGroupUserBind(id, userId, System.currentTimeMillis());
            }
        }

        return ResultStat.OK.wrap(null);
    }

    @Override
    public HttpResponseTemp<?> unbindUser(long id, int userId) {

        AuthUtil.collectionVerify(CurrentThreadInfo.getUserId(), GlobalConstant.alarmGroupId, resourceType, OperationType.MODIFY, 0);

        if (alarmBiz.getUserGroupInfoBasicById(id) == null) {
            throw ApiException.wrapResultStat(ResultStat.USERGROUP_NOT_EXISTED);
        }
        if (authBiz.getUserById(userId) == null) {
            throw ApiException.wrapResultStat(ResultStat.USER_NOT_EXIST);
        }

        alarmBiz.deleteUserGroupUserBind(id, userId);
        return ResultStat.OK.wrap(null);
    }

    private class UserGroupDetailTask implements Callable<UserGroupDetail> {
        UserGroupBasic userGroupBasic;

        public UserGroupDetailTask(UserGroupBasic userGroupBasic) {
            this.userGroupBasic = userGroupBasic;
        }

        @Override
        public UserGroupDetail call() throws Exception {
            UserGroupDetail userGroupDetail = new UserGroupDetail(userGroupBasic);

            long userGroupId = userGroupBasic.getId();
            List<UserInfo> userInfoList = alarmBiz.getUserInfoByUserGroupId(userGroupId);
            userGroupDetail.setUserList(userInfoList);
            List<TemplateInfoBasic> templateInfoBasicList = alarmBiz.getTemplateInfoBasicByUserGroupId(userGroupId);
            userGroupDetail.setTemplateList(templateInfoBasicList);

            return userGroupDetail;
        }
    }
}
