package com.iscalesolutions.vlocity.projectscheduler.resource;

import java.util.List;
import java.util.ListIterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iscalesolutions.vlocity.projectscheduler.service.TaskService;
import com.iscalesolutions.vlocity.projectscheduler.view.TaskView;


@RestController
@CrossOrigin
@RequestMapping(value = "/rest/task")
public class TaskResource {
	
	@Autowired
	@Qualifier("TaskService")
	private TaskService taskService;

	@PostMapping(value="/save")
	public TaskView saveTask(@RequestBody final TaskView task) throws Exception {
		
		return taskService.saveTask(task);
		
	}
	
	@GetMapping(value="/id/{id}")
	public TaskView getTaskById(@PathVariable("id") final Integer taskId) throws Exception {
		
		return taskService.getTaskById(taskId);
		
	}
	
	@GetMapping(value="/all")
	public List<TaskView> getAllTasks() throws Exception {
		
		return taskService.getAllTaskIdTaskName();
		
	}
	
	@GetMapping(value="/eligible/{id}")
	public List<TaskView> getAllEligibleTasks(@PathVariable("id") final Integer taskId) throws Exception {
		
		List<TaskView> tasks = taskService.getAllTaskIdTaskName();
		
		ListIterator<TaskView> iterator = tasks.listIterator();
		while(iterator.hasNext()) {
			TaskView task = iterator.next();
			List<Integer> allDependentTaskIds = taskService.getAllDependentTaskId(task.getTaskId());
			if(task.getTaskId().equals(taskId) || allDependentTaskIds.contains(taskId)) {
				iterator.remove();
			}
				
		}
		
		return tasks;
		
		
	}
	
}
