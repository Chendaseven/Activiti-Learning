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
 * ���̱�������
 * @ClassName VariableTest
 * @Description 
 * @Author Chen Peng
 * @Date 2019��3��18�� ����9:57:40
 */
public class VariableTest {
	ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
	
	/**
	 * ��������ʵ��
	 */
	@Test
	public void VariableTestDeploy() {
		Deployment deployment = processEngine.getRepositoryService()
						.createDeployment()
						.addClasspathResource("diagrams/VariableTest.bpmn")
						.addClasspathResource("diagrams/VariableTest.png")
						.deploy();
		System.out.println("���̲���id��"+deployment.getId());
		System.out.println("���̲���name��"+deployment.getName());
		
		
	}
	
	/**
	 * ��������ʵ��
	 */
	@Test
	public void startProcess() {
		String processDefinitionKey = "VariableTest";
		ProcessInstance PI = processEngine.getRuntimeService()
						.startProcessInstanceByKey(processDefinitionKey);
		System.out.println("����ʵ��id��"+PI.getActivityId());
		System.out.println(PI.getId());
	}
	
	/**
	 * �������̱���
	 */
	@Test
	public void setVariable() {
		String taskId = "30004";
		TaskService taskService = processEngine.getTaskService();
		taskService.setVariable(taskId, "�����", "����");	//�������̱�����һ��ֻ������һ��ֵ
		taskService.setVariableLocal(taskId, "�������", "6");	//setVariableLocal�󶨵�ǰ����taskid����һ�������ܿ����˱���
		
		System.out.println("�������̱����ɹ�");
	}
	
	/**
	 * ��ѯ���̱�������ʷ��¼����act_hi_varinst����
	 */
	public void getHisVariable() {
		List<HistoricVariableInstance> list = processEngine.getHistoryService()
						.createHistoricVariableInstanceQuery()
						.variableName("�������")
						.list();
		if(list != null && list.size()>0) {
			for (HistoricVariableInstance historicVariableInstance : list) {
				System.out.println(historicVariableInstance.getVariableName());
				System.out.println(historicVariableInstance.getValue());
			}
		}
	}
}
