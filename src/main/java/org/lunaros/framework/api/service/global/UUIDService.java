package org.Lunaros.framework.api.service.global;


/**
 * Created by junwuguo on 2017/2/22 0022.
 */
public interface UUIDService {

    /**
     * Get the UUID of current LunarOS
     * @return
     */
    String getUUID();

    /**
     * Set the UUID of current LunarOS
     */
    void setUUID();

    /**
     * Update the UUID of current LunarOS
     */
    void updateUUID();
}
