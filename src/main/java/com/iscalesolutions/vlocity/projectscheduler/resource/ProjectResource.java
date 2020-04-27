package com.iscalesolutions.vlocity.projectscheduler.resource;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iscalesolutions.vlocity.projectscheduler.entities.ProjectTasks;
import com.iscalesolutions.vlocity.projectscheduler.service.ProjectService;
import com.iscalesolutions.vlocity.projectscheduler.service.TaskService;
import com.iscalesolutions.vlocity.projectscheduler.view.ProjectView;
import com.iscalesolutions.vlocity.projectscheduler.view.TaskView;


@RestController
@CrossOrigin
@RequestMapping(value = "/rest/project")
public class ProjectResource {
	
	@Autowired
	@Qualifier("ProjectService")
	private ProjectService projectService;
	
	@Autowired
	@Qualifier("TaskService")
	private TaskService taskService;

	@PostMapping(value="/save")
	public ProjectView saveTask(@RequestBody final ProjectView projectView) throws Exception {
		
		return projectService.save(projectView);
		
	}
	
	@GetMapping(value="/id/{id}")
	public ProjectView getProjectById(@PathVariable("id") final Integer projectId) throws Exception {
		
		ProjectView view = projectService.getProjectById(projectId);
		
		List<ProjectTasks> projectTasks = projectService.getProjectTasksByProjectId(projectId);
		
		if(Optional.ofNullable(projectTasks).isPresent() && projectTasks.size() > 0) {
			List<TaskView> list = new ArrayList<>();
			for(ProjectTasks t: projectTasks) {
				list.add(taskService.getTaskById(t.getTaskId()));
			}
			view.setTaskViews(list);
		}
		
		return view;
		
	}
	
	@GetMapping(value="/all")
	public List<ProjectView> getAllProjects() throws Exception {
		
		return projectService.getAllProjects();
		
	}
	
	@GetMapping(value="/all/withtasks")
	public List<ProjectView> getAllProjectsWithTasks() throws Exception {
		
		return projectService.getAllProjectsWithTasks();
		
	}
	
}
