package cn.chen.c_processInstance;

import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;

/**
 * 流程实例、任务执行
 * @ClassName ProcessInstancetTest
 * @Description 
 * @Author Chen Peng
 * @Date 2019年3月18日 上午11:21:31
 */
public class ProcessInstancetTest {
	ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

	/**
	 * 查询个人任务
	 */
	public void queryPersonTask() {
		String Assignee = "张三";
		TaskQuery taskAssignee = processEngine.getTaskService()
						.createTaskQuery()
						.taskAssignee(Assignee);	//根据Assignee查询任务
		List<Task> list = taskAssignee.list();
	}
}
