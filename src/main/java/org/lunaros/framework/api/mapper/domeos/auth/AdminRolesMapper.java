package org.Lunaros.framework.api.mapper.Lunaros.auth;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.Lunaros.framework.api.model.auth.AdminRole;

/**
 * Created by feiliu206363 on 2016/4/5.
 */
@Mapper
public interface AdminRolesMapper {
    @Select("SELECT * FROM admin_roles WHERE userId=#{userId}")
    AdminRole getAdminById(@Param("userId") long userId);
}
