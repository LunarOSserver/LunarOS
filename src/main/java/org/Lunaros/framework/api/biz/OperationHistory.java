package org.Lunaros.framework.api.biz;

import org.Lunaros.framework.api.model.operation.OperationRecord;

import java.util.List;

/**
 * Created by feiliu206363 on 2016/4/5.
 */
public interface OperationHistory {

    void insertRecord(OperationRecord record);

    void updateStatus(int id, String status);

    OperationRecord getById(int id);

    List<OperationRecord> listOperationRecordByUserNameTime(Integer userId, long operateTime);
}
