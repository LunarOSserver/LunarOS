package org.Lunaros.framework.api.consolemodel.project;

import org.Lunaros.util.StringUtils;
import org.Lunaros.framework.api.consolemodel.auth.CreatorInfo;
import org.Lunaros.framework.api.model.auth.related.Role;
import org.Lunaros.framework.api.model.project.Project;

/**
 * Created by feiliu206363 on 2016/9/23.
 */
public class ProjectConsole {
    private Project project;
    private CreatorInfo creatorInfo;
    private Role role;

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public CreatorInfo getCreatorInfo() {
        return creatorInfo;
    }

    public void setCreatorInfo(CreatorInfo creatorInfo) {
        this.creatorInfo = creatorInfo;
    }

    public Role getRole() {
        return role;
    }

    public ProjectConsole setRole(Role role) {
        this.role = role;
        return this;
    }

    public String checkLegality() {
        String error = null;
        if (project == null) {
            error = "project info is null!";
        } else if (!StringUtils.isBlank(project.checkLegality())) {
            error = project.checkLegality();
        }
        return error;
    }
}
