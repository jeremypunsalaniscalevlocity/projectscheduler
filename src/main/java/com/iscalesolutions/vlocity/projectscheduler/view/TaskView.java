/**
 * 
 */
package com.iscalesolutions.vlocity.projectscheduler.view;

import java.time.LocalDate;
import java.util.List;



/**
 * @author jempu
 *
 */
public class TaskView {
	
	private Integer taskId;
	private String taskName;
	private String description;
	private Integer duration;
	private LocalDate startDate;
	private LocalDate endDate;
	private List<TaskView> dependencies;
	
	private String taskType;
	
	public TaskView(String taskName, Integer duration) {
		this.setTaskName(taskName);
		this.setDuration(duration);
	}
	
	/**
	 * @return the taskId
	 */
	public Integer getTaskId() {
		return taskId;
	}
	/**
	 * @param taskId the taskId to set
	 */
	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}
	/**
	 * @return the taskName
	 */
	public String getTaskName() {
		return taskName;
	}
	/**
	 * @param taskName the taskName to set
	 */
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return the startDate
	 */
	public LocalDate getStartDate() {
		return startDate;
	}
	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}
	/**
	 * @return the endDate
	 */
	public LocalDate getEndDate() {
		return endDate;
	}
	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}
	/**
	 * @return the duration
	 */
	public Integer getDuration() {
		return duration;
	}
	/**
	 * @param duration the duration to set
	 */
	public void setDuration(Integer duration) {
		this.duration = duration;
	}
	/**
	 * @return the dependencies
	 */
	public List<TaskView> getDependencies() {
		return dependencies;
	}
	/**
	 * @param dependencies the dependencies to set
	 */
	public void setDependencies(List<TaskView> dependencies) {
		this.dependencies = dependencies;
	}

	public String getTaskType() {
		return taskType;
	}

	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}

	@Override
	public String toString() {
		return "TaskView [taskId=" + taskId + ", taskName=" + taskName + ", description=" + description + ", duration="
				+ duration + ", startDate=" + startDate + ", endDate=" + endDate + ", dependencies=" + dependencies
				+ ", taskType=" + taskType + "]";
	}



}