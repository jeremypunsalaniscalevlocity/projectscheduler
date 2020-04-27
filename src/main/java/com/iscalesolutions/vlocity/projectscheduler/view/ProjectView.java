package com.iscalesolutions.vlocity.projectscheduler.view;

import java.time.LocalDate;
import java.util.List;


public class ProjectView {
	
	private Integer projectId;
	private String projectName;
	private String projectDescription;
	private LocalDate startDate;
	private List<TaskView> taskViews;
	
	/**
	 * @return the projectId
	 */
	public Integer getProjectId() {
		return projectId;
	}
	/**
	 * @param projectId the projectId to set
	 */
	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}
	/**
	 * @return the projectName
	 */
	public String getProjectName() {
		return projectName;
	}
	/**
	 * @param projectName the projectName to set
	 */
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	/**
	 * @return the projectDescription
	 */
	public String getProjectDescription() {
		return projectDescription;
	}
	/**
	 * @param projectDescription the projectDescription to set
	 */
	public void setProjectDescription(String projectDescription) {
		this.projectDescription = projectDescription;
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
	
	public List<TaskView> getTaskViews() {
		return taskViews;
	}
	public void setTaskViews(List<TaskView> taskViews) {
		this.taskViews = taskViews;
	}
	@Override
	public String toString() {
		return "ProjectView [projectId=" + projectId + ", projectName=" + projectName + ", projectDescription="
				+ projectDescription + ", startDate=" + startDate + ", taskViews=" + taskViews + "]";
	} 

}
