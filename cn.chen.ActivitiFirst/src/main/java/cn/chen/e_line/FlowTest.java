package cn.chen.e_line;

import java.io.InputStream;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.Test;

/**
 * �������߲��ԣ������м����ж�����
 * @ClassName FlowTest
 * @Description 
 * @Author Chen Peng
 * @Date 2019��3��19�� ����7:15:30
 */
public class FlowTest {
	ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
	/**
	 * ���̲�����������
	 */
	@Test
	public void sequenceFlowTest() {
		InputStream inputStreamBpmn = this.getClass().getResourceAsStream("line.bpmn");
		InputStream inputStreamPng = this.getClass().getResourceAsStream("line.png");
		//���̲���
		RepositoryService repositoryService = processEngine.getRepositoryService();
		DeploymentBuilder deploymentBuilder = repositoryService.createDeployment();
		deploymentBuilder.addInputStream("line.bpmn", inputStreamBpmn);
		deploymentBuilder.addInputStream("line.png", inputStreamPng);
		deploymentBuilder.name("���߲���");
		Deployment deployment = deploymentBuilder.deploy();
		//��ȡ����keyֵ
		ProcessDefinitionQuery ProcessDefinitionQuery = repositoryService.createProcessDefinitionQuery();
		ProcessDefinition processDefinition = ProcessDefinitionQuery.deploymentId(deployment.getId()).singleResult();
		String processDefinitionKey = processDefinition.getKey();
		//��������
		RuntimeService runtimeService = processEngine.getRuntimeService();
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processDefinitionKey);
		System.out.println("pid="+processInstance.getId());
	}
	
	/**
	 * �������̱������������
	 */
	@Test
	public void completeTask() {
		String taskId = "57508";
		TaskService taskService = processEngine.getTaskService();
		taskService.setVariable(taskId, "message", "����Ҫ");
		taskService.complete(taskId);
		System.out.println("���������");
	}
}
