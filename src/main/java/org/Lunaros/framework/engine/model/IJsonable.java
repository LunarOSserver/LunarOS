package org.Lunaros.framework.engine.model;

import org.Lunaros.framework.engine.exception.DaoException;

/**
 * Created by sparkchen on 16/4/4.
 */
public interface IJsonable  {
    int VERSION_NOW();
    <T extends IJsonable> T fromString(String str) throws DaoException;
    <T extends IJsonable> T fromString(String str, int ver);
}
