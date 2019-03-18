package cn.chen.a_ActivitiFirst;

import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;

public class helloWorld {
	//自动加载classpath下的activiti.cfg.xml文件
	ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
	/**
	 * 部署流程定义，执行该方法后，主要在数据库中act_ru_*几张表中产生相应的数据，产生相应的流程部署数据
	 * 
	 */
	@Test
	public void deploy() {
		//Deployment对象对应于数据库中act_re_deployment表
		Deployment deploy = processEngine.getRepositoryService()	//获取仓库服务实例
						.createDeployment()		//创建部署对象
						.name("部署名称1")		//添加流程部署名称
						.addClasspathResource("diagrams/Helloworld.bpmn") //加入bpmn文件，部署需要加入两个文件，但是此方法一次只能加入一个文件
						.addClasspathResource("diagrams/Helloworld.png")  //加入png文件
						.deploy();	//执行部署方法
		System.out.println("部署id"+deploy.getId());
		System.out.println("部署名称"+deploy.getName());
	}
	/**
	 * 启动流程实例，流程启动之后，会在act_ru_task中随机产生流程id等数据
	 */
	@Test
	public void startProcessInstance() {
		String processDefinitionKey = "helloworld";
		ProcessInstance processInstance = processEngine.getRuntimeService()		//获取正在执行的流程实例和执行对象
						.startProcessInstanceByKey(processDefinitionKey);	//使用key值在act_re_procdef表种搜索对应数据进行启动，使用key值可以自动搜索最新版本的部署流程
		System.out.println("流程实例id"+processInstance.getId());
		System.out.println("流程实例名称"+processInstance.getName());
	}
	
	/**
	 * 获取当前流程个人任务，此时在act_ru_task中搜索对应任务
	 */
	@Test
	public void queueMytask() {
		String assignee = "张三";
		List<Task> list = processEngine.getTaskService()		//获取当前任务对象
						.createTaskQuery()	//创建任务查询对象
						.taskAssignee(assignee)	//指定签合人员查询
						.list();
		for (Task task : list) {
			System.out.println("taskId："+task.getId());
			System.out.println("taskName："+task.getName());
			System.out.println("###################");
		}
		
	}
	
	/**
	 * 完成对应的任务，每完成一条签合，在act_ru_task表中的数据则会更新，并且随机产生一个新的ID
	 */
	@Test
	public void completeMytask() {
		String taskId="17504";
		processEngine.getTaskService()		//获取当前任务对象
							.complete(taskId);
		System.out.println(taskId+"任务已完成!");
		System.out.println("###############");
	}
}
