package com.iscalesolutions.vlocity.projectscheduler.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name="project_task")
@IdClass(ProjectTasksId.class)
public class ProjectTasks {
	
	@Id
	@Column(name = "project_id")
	Integer projectId;
	
	@Id
	@Column(name = "task_id")
	Integer taskId;
	
	public Integer getProjectId() {
		return projectId;
	}
	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}
	public Integer getTaskId() {
		return taskId;
	}
	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}

}
