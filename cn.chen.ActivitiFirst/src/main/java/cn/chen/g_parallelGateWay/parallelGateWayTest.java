package cn.chen.g_parallelGateWay;

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

public class parallelGateWayTest {
	ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
	/**
	 * 流程部署及流程启动
	 */
	@Test
	public void sequenceFlowTest() {
		InputStream inputStreamBpmn = this.getClass().getResourceAsStream("parallelGateWay.bpmn");
		InputStream inputStreamPng = this.getClass().getResourceAsStream("parallelGateWay.png");
		//流程部署
		RepositoryService repositoryService = processEngine.getRepositoryService();
		DeploymentBuilder deploymentBuilder = repositoryService.createDeployment();
		deploymentBuilder.addInputStream("parallelGateWay.bpmn", inputStreamBpmn);
		deploymentBuilder.addInputStream("parallelGateWay.png", inputStreamPng);
		deploymentBuilder.name("并行网关测试");
		Deployment deployment = deploymentBuilder.deploy();
		//获取流程key值
		ProcessDefinitionQuery ProcessDefinitionQuery = repositoryService.createProcessDefinitionQuery();
		ProcessDefinition processDefinition = ProcessDefinitionQuery.deploymentId(deployment.getId()).singleResult();
		String processDefinitionKey = processDefinition.getKey();
		//流程启动
		RuntimeService runtimeService = processEngine.getRuntimeService();
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processDefinitionKey);
		System.out.println("pid="+processInstance.getId());
	}
	
	/**
	 * 设置流程变量并完成任务
	 */
	@Test
	public void completeTask() {
		String taskId = "70002";
		TaskService taskService = processEngine.getTaskService();
		taskService.complete(taskId);
		System.out.println("任务已完成");
	}
}
