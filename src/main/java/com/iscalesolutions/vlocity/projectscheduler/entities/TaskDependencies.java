package com.iscalesolutions.vlocity.projectscheduler.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name="task_dependencies")
@IdClass(TaskDependencyId.class)
public class TaskDependencies {
	
	@Id
	@Column(name = "main_task_id")
	Integer mainTaskId;
	
	@Id
	@Column(name = "dependent_task_id")
	Integer dependencyId;
	
	public Integer getMainTaskId() {
		return mainTaskId;
	}
	public void setMainTaskId(Integer mainTaskId) {
		this.mainTaskId = mainTaskId;
	}
	public Integer getDependencyId() {
		return dependencyId;
	}
	public void setDependencyId(Integer dependencyId) {
		this.dependencyId = dependencyId;
	}

}
