package cn.chen.c_processInstance;

import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;

/**
 * ����ʵ��������ִ��
 * @ClassName ProcessInstancetTest
 * @Description 
 * @Author Chen Peng
 * @Date 2019��3��18�� ����11:21:31
 */
public class ProcessInstancetTest {
	ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

	/**
	 * ��ѯ��������
	 */
	public void queryPersonTask() {
		String Assignee = "����";
		TaskQuery taskAssignee = processEngine.getTaskService()
						.createTaskQuery()
						.taskAssignee(Assignee);	//����Assignee��ѯ����
		List<Task> list = taskAssignee.list();
	}
}
