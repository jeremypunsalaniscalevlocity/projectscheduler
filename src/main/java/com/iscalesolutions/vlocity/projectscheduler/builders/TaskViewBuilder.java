/**
 * 
 */
package com.iscalesolutions.vlocity.projectscheduler.builders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.iscalesolutions.vlocity.projectscheduler.view.TaskView;

/**
 * @author jempu
 *
 */
public class TaskViewBuilder {
	
	private final TaskView task;
	
	public TaskViewBuilder(String taskName, Integer taskDuration) {
		this.task = new TaskView(taskName, taskDuration);
	}
	
	public TaskViewBuilder setTaskName(String taskName) {
		this.task.setTaskName(taskName);
		return this;
	}
	
	public TaskViewBuilder setTaskDuration(Integer taskDuration) {
		this.task.setDuration(taskDuration);
		return this;
	}
	
	public TaskViewBuilder setId(Integer taskId) {
		this.task.setTaskId(taskId);
		return this;
	}
	
	public TaskViewBuilder setDescription(String description) {
		this.task.setDescription(description);
		return this;
	}
	
	public TaskViewBuilder addDependencyTask(TaskView dependencyTasks) {
		List<TaskView> dependencies = Optional.ofNullable(this.task.getDependencies()).orElse(new ArrayList<TaskView>());
		dependencies.add(dependencyTasks);
		this.task.setDependencies(dependencies);
		return this;
		
	}
	
	public TaskView build() {
		return this.task;
	}

}
