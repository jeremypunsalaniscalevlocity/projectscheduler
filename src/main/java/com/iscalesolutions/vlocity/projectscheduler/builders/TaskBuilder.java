/**
 * 
 */
package com.iscalesolutions.vlocity.projectscheduler.builders;


import com.iscalesolutions.vlocity.projectscheduler.entities.Task;


/**
 * @author jempu
 *
 */
public class TaskBuilder {
	
	private final Task task;
	
	public TaskBuilder(String taskName, Integer taskDuration) {
		this.task = new Task();
		task.setTaskName(taskName);
		task.setDuration(taskDuration);
	}
	
	public TaskBuilder setTaskName(String taskName) {
		this.task.setTaskName(taskName);
		return this;
	}
	
	public TaskBuilder setTaskDuration(Integer taskDuration) {
		this.task.setDuration(taskDuration);
		return this;
	}
	
	public TaskBuilder setId(Integer taskId) {
		this.task.setTaskId(taskId);
		return this;
	}
	
	public TaskBuilder setDescription(String description) {
		this.task.setDescription(description);
		return this;
	}
	
	public Task build() {
		return this.task;
	}

}
