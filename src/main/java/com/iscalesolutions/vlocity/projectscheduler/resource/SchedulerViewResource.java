package com.iscalesolutions.vlocity.projectscheduler.resource;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iscalesolutions.vlocity.projectscheduler.entities.ProjectTasks;
import com.iscalesolutions.vlocity.projectscheduler.service.ProjectService;
import com.iscalesolutions.vlocity.projectscheduler.service.TaskService;
import com.iscalesolutions.vlocity.projectscheduler.view.ProjectView;
import com.iscalesolutions.vlocity.projectscheduler.view.TaskView;

@RestController
@CrossOrigin
@RequestMapping(value = "/rest/scheduler")
public class SchedulerViewResource {
	
	@Autowired
	@Qualifier("ProjectService")
	private ProjectService projectService;
	
	@Autowired
	@Qualifier("TaskService")
	private TaskService taskService;
	
	@GetMapping(value="/view/{id}")
	public ProjectView viewProjectSchedule(@PathVariable("id") final Integer projectId) throws Exception {
		ProjectView projectView = projectService.getProjectById(projectId);
		
		List<ProjectTasks> projectTasks = projectService.getProjectTasksByProjectId(projectId);
		
		if(Optional.ofNullable(projectTasks).isPresent() && projectTasks.size() > 0) {
			List<TaskView> list = new ArrayList<>();
			for(ProjectTasks t: projectTasks) {
				list.add(taskService.getTaskById(t.getTaskId()));
			}
			list.sort(Comparator.comparingInt(TaskView::getTaskId));
			projectView.setTaskViews(list);
		} else {
			throw new Exception("No tasks set to project " + projectId);
		}
		
		projectView =  taskService.calculateStartEndDates(projectView);
		
		List<TaskView> chronoTasks = taskService.provideTasksInChronologicalOrder(projectView);
		
		projectView.setTaskViews(chronoTasks);
		
		return projectView;
		
	}
	
	@GetMapping(value="/view/all")
	public List<ProjectView> viewAllProjectSchedule() throws Exception {
		List<ProjectView> projects = projectService.getAllProjects();
		
		List<ProjectView> viewAll = new ArrayList<>();
		for(ProjectView project: projects) {
			viewAll.add(this.viewProjectSchedule(project.getProjectId()));
		}
		
		return viewAll;
		
	}

}
