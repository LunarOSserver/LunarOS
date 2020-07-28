package org.Lunaros.framework.api.service.global.impl;

import org.Lunaros.framework.api.biz.global.GlobalBiz;
import org.Lunaros.framework.api.model.global.GlobalInfo;
import org.Lunaros.framework.api.model.global.GlobalType;
import org.Lunaros.framework.api.service.global.UUIDService;
import org.Lunaros.util.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * Created by junwuguo on 2017/2/22 0022.
 */
@Service
public class UUIDServiceImpl implements UUIDService {

    @Autowired
    GlobalBiz globalBiz;

    @PostConstruct
    public void initUUID() {
        String uuid = getUUID();
        if (uuid == null) {
            setUUID();
        } else if (!UUIDUtil.checkUUID(uuid)) {
            updateUUID();
        }
    }

    @Override
    public String getUUID() {
        return globalBiz.getUUID();
    }

    @Override
    public void setUUID() {
        GlobalInfo info = new GlobalInfo(GlobalType.UUID, UUIDUtil.generateUUID());
        globalBiz.addGlobalInfo(info);
    }

    @Override
    public void updateUUID() {
        GlobalInfo info = new GlobalInfo(GlobalType.UUID, UUIDUtil.generateUUID());
        globalBiz.updateGlobalInfoByType(info);
    }

}
