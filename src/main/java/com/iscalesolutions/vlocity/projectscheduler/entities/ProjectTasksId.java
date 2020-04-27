package com.iscalesolutions.vlocity.projectscheduler.entities;

import java.io.Serializable;
import java.util.Objects;

public class ProjectTasksId implements Serializable {

	private static final long serialVersionUID = 1L;
	
	Integer projectId;
	Integer taskId;
	
	public ProjectTasksId() {}
	
	public ProjectTasksId(Integer projectId, Integer taskId) {
		this.projectId = projectId;
		this.taskId = taskId;
	}

	@Override
	public boolean equals(Object obj) {
		if(this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		ProjectTasksId projectTasksId = (ProjectTasksId) obj;
		return projectId.equals(projectTasksId.projectId) && taskId.equals(projectTasksId.taskId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(projectId, taskId);
	}
	
	
	
}
