package com.iscalesolutions.vlocity.projectscheduler.builders;

import java.time.LocalDate;

import com.iscalesolutions.vlocity.projectscheduler.entities.Project;

public class ProjectBuilder {
	
	private final Project project;
	
	public ProjectBuilder(String projectName, LocalDate startDate) {
		this.project = new Project();
		this.project.setProjectName(projectName);
		this.project.setStartDate(startDate);
	}
	
	public ProjectBuilder setProjectName(String projectName) {
		this.project.setProjectName(projectName);
		return this;
	}
	
	public ProjectBuilder setStartDate(LocalDate startDate) {
		this.project.setStartDate(startDate);
		return this;
	}
	
	public ProjectBuilder setProjectDescription(String projectDescription) {
		this.project.setProjectDescription(projectDescription);
		return this;
	}
	
	public ProjectBuilder setId(Integer id) {
		this.project.setProjectId(id);
		return this;
	}
	
	public Project build() {
		return this.project;
	}

}
