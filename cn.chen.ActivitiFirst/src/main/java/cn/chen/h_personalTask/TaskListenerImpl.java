package cn.chen.h_personalTask;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

public class TaskListenerImpl implements TaskListener{

	public void notify(DelegateTask delegateTask) {
		String assignee = "���޼�";
		//ָ����������
		delegateTask.setAssignee(assignee);
		
	}
	
}
