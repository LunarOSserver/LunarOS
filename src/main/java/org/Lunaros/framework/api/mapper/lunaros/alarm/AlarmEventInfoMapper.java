package org.Lunaros.framework.api.mapper.Lunaros.alarm;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.Lunaros.framework.api.consolemodel.alarm.AlarmEventInfoDraft;

import java.util.List;

/**
 * Created by baokangwang on 2016/4/13.
 */
@Mapper
public interface AlarmEventInfoMapper {

    @Select("SELECT * FROM alarm_event_info_draft")
    List<AlarmEventInfoDraft> listAlarmEventInfoDraft();

    @Select("SELECT id FROM alarm_event_info_draft WHERE strategy_id IN (SELECT id FROM portal.strategy WHERE tpl_id=#{templateId})")
    List<String> listAlarmEventInfoIdByTemplateId(@Param("templateId") long templateId);

    @Select("SELECT id FROM alarm_event_info_draft WHERE strategy_id IN (SELECT id FROM portal.strategy WHERE tags IN (${containerIds}))")
    List<String> listAlarmEventInfoIdByContainerIds(@Param("containerIds") String containerIds);

    @Select("SELECT COUNT(*) FROM alarm_event_info_draft")
    int getAlarmEventCount();
}
