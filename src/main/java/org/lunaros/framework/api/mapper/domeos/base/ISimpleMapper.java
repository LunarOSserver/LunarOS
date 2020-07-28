package org.Lunaros.framework.api.mapper.Lunaros.base;

import org.Lunaros.framework.engine.model.RowMapperDao;

/**
 * Created by sparkchen on 16/4/5.
 */
public interface ISimpleMapper {
    int insertRow( String tableName, RowMapperDao item);

    int updateRow( String tableName, RowMapperDao item);

}
