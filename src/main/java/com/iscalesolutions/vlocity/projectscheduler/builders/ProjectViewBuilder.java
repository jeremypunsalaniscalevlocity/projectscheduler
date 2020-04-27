package com.iscalesolutions.vlocity.projectscheduler.builders;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.iscalesolutions.vlocity.projectscheduler.view.ProjectView;
import com.iscalesolutions.vlocity.projectscheduler.view.TaskView;

public class ProjectViewBuilder {
	
	private final ProjectView project;
	
	public ProjectViewBuilder(String projectName, LocalDate startDate) {
		this.project = new ProjectView();
		this.project.setProjectName(projectName);
		this.project.setStartDate(startDate);
	}
	
	public ProjectViewBuilder setProjectName(String projectName) {
		this.project.setProjectName(projectName);
		return this;
	}
	
	public ProjectViewBuilder setStartDate(LocalDate startDate) {
		this.project.setStartDate(startDate);
		return this;
	}
	
	public ProjectViewBuilder setProjectDescription(String projectDescription) {
		this.project.setProjectDescription(projectDescription);
		return this;
	}
	
	public ProjectViewBuilder setId(Integer id) {
		this.project.setProjectId(id);
		return this;
	}
	
	public ProjectViewBuilder addTasks(TaskView tasks) {
		List<TaskView> list = Optional.ofNullable(this.project.getTaskViews()).orElse(new ArrayList<TaskView>());
		list.add(tasks);
		this.project.setTaskViews(list);
		return this;
		
	}
	
	public ProjectView build() {
		return this.project;
	}

}
