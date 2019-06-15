package cn.chen.h_personalTask;

import java.io.InputStream;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.Test;

public class personalTask {
	
	ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
	
	//部署流程
	@Test
	public void deployID() {
		InputStream bpmnIo = this.getClass().getResourceAsStream("personalTask.bpmn");
		InputStream pngIo = this.getClass().getResourceAsStream("personalTask.png");
		Deployment deployment = processEngine.getRepositoryService()
						.createDeployment()
						.addInputStream("personalTask.bpmn", bpmnIo)
						.addInputStream("personalTask.png", pngIo)
						.name("个人任务测试")
						.deploy();
		System.out.println("部署id:"+deployment.getId());
		System.out.println("部署名称:"+deployment.getName());
	}
	
	//流程启动
	@Test
	public void startprocess() {
		String processDefinitionKey = "personalTask";
		ProcessInstance processInstance = processEngine.getRuntimeService().startProcessInstanceByKey(processDefinitionKey);
		System.out.println("流程id："+processInstance.getId());
	
	}
	
	//使用TaskID和办理人重新指定办理人
	public void newAssignee() {
		String taskId = "";
		String userId = "";
		processEngine.getTaskService().setAssignee(taskId, userId);
	}
	
	
	
	
}
