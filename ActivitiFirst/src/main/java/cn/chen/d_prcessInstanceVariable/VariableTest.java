package cn.chen.d_prcessInstanceVariable;

import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.Test;

/**
 * 流程变量配置
 * @ClassName VariableTest
 * @Description 
 * @Author Chen Peng
 * @Date 2019年3月18日 下午9:57:40
 */
public class VariableTest {
	ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
	
	/**
	 * 部署流程实例
	 */
	@Test
	public void VariableTestDeploy() {
		Deployment deployment = processEngine.getRepositoryService()
						.createDeployment()
						.addClasspathResource("diagrams/VariableTest.bpmn")
						.addClasspathResource("diagrams/VariableTest.png")
						.deploy();
		System.out.println("流程部署id："+deployment.getId());
		System.out.println("流程部署name："+deployment.getName());
		
		
	}
	
	/**
	 * 启动流程实例
	 */
	@Test
	public void startProcess() {
		String processDefinitionKey = "VariableTest";
		ProcessInstance PI = processEngine.getRuntimeService()
						.startProcessInstanceByKey(processDefinitionKey);
		System.out.println("流程实例id："+PI.getActivityId());
		System.out.println(PI.getId());
	}
	
	/**
	 * 设置流程变量
	 */
	@Test
	public void setVariable() {
		String taskId = "30004";
		TaskService taskService = processEngine.getTaskService();
		taskService.setVariable(taskId, "请假人", "奥托");	//设置流程变量，一次只能设置一个值
		taskService.setVariableLocal(taskId, "请假天数", "6");	//setVariableLocal绑定当前流程taskid，下一步将不能看到此变量
		
		System.out.println("设置流程变量成功");
	}
	
	/**
	 * 查询流程变量的历史记录，在act_hi_varinst表中
	 */
	public void getHisVariable() {
		List<HistoricVariableInstance> list = processEngine.getHistoryService()
						.createHistoricVariableInstanceQuery()
						.variableName("请假天数")
						.list();
		if(list != null && list.size()>0) {
			for (HistoricVariableInstance historicVariableInstance : list) {
				System.out.println(historicVariableInstance.getVariableName());
				System.out.println(historicVariableInstance.getValue());
			}
		}
	}
}
