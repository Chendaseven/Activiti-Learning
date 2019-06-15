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
 * 流程连线测试，连线中加入判断条件
 * @ClassName FlowTest
 * @Description 
 * @Author Chen Peng
 * @Date 2019年3月19日 下午7:15:30
 */
public class FlowTest {
	ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
	/**
	 * 流程部署及流程启动
	 */
	@Test
	public void sequenceFlowTest() {
		InputStream inputStreamBpmn = this.getClass().getResourceAsStream("line.bpmn");
		InputStream inputStreamPng = this.getClass().getResourceAsStream("line.png");
		//流程部署
		RepositoryService repositoryService = processEngine.getRepositoryService();
		DeploymentBuilder deploymentBuilder = repositoryService.createDeployment();
		deploymentBuilder.addInputStream("line.bpmn", inputStreamBpmn);
		deploymentBuilder.addInputStream("line.png", inputStreamPng);
		deploymentBuilder.name("连线测试");
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
		String taskId = "57508";
		TaskService taskService = processEngine.getTaskService();
		taskService.setVariable(taskId, "message", "不重要");
		taskService.complete(taskId);
		System.out.println("任务已完成");
	}
}
