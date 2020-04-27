package com.iscalesolutions.vlocity.projectscheduler.entities;

import java.io.Serializable;
import java.util.Objects;

public class TaskDependencyId implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer mainTaskId;
	private Integer dependencyId;
	
	public TaskDependencyId() {}
	
	public TaskDependencyId(Integer mainTaskId, Integer dependencyId) {
		this.mainTaskId = mainTaskId;
		this.dependencyId = dependencyId;
	}

	@Override
	public boolean equals(Object obj) {
		if(this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		TaskDependencyId taskDependencyId = (TaskDependencyId) obj;
		return mainTaskId.equals(taskDependencyId.mainTaskId) && dependencyId.equals(taskDependencyId.dependencyId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(mainTaskId, dependencyId);
	}
	
	
	
}
