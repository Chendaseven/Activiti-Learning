package cn.chen.h_personalTask;

import java.io.InputStream;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.Test;

public class personalTask {
	
	ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
	
	//��������
	@Test
	public void deployID() {
		InputStream bpmnIo = this.getClass().getResourceAsStream("personalTask.bpmn");
		InputStream pngIo = this.getClass().getResourceAsStream("personalTask.png");
		Deployment deployment = processEngine.getRepositoryService()
						.createDeployment()
						.addInputStream("personalTask.bpmn", bpmnIo)
						.addInputStream("personalTask.png", pngIo)
						.name("�����������")
						.deploy();
		System.out.println("����id:"+deployment.getId());
		System.out.println("��������:"+deployment.getName());
	}
	
	//��������
	@Test
	public void startprocess() {
		String processDefinitionKey = "personalTask";
		ProcessInstance processInstance = processEngine.getRuntimeService().startProcessInstanceByKey(processDefinitionKey);
		System.out.println("����id��"+processInstance.getId());
	
	}
	
	//ʹ��TaskID�Ͱ���������ָ��������
	public void newAssignee() {
		String taskId = "";
		String userId = "";
		processEngine.getTaskService().setAssignee(taskId, userId);
	}
	
	
	
	
}
