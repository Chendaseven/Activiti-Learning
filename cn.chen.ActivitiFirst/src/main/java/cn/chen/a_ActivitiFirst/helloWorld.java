package cn.chen.a_ActivitiFirst;

import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;

public class helloWorld {
	//�Զ�����classpath�µ�activiti.cfg.xml�ļ�
	ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
	/**
	 * �������̶��壬ִ�и÷�������Ҫ�����ݿ���act_ru_*���ű��в�����Ӧ�����ݣ�������Ӧ�����̲�������
	 * 
	 */
	@Test
	public void deploy() {
		//Deployment�����Ӧ�����ݿ���act_re_deployment��
		Deployment deploy = processEngine.getRepositoryService()	//��ȡ�ֿ����ʵ��
						.createDeployment()		//�����������
						.name("��������1")		//������̲�������
						.addClasspathResource("diagrams/Helloworld.bpmn") //����bpmn�ļ���������Ҫ���������ļ������Ǵ˷���һ��ֻ�ܼ���һ���ļ�
						.addClasspathResource("diagrams/Helloworld.png")  //����png�ļ�
						.deploy();	//ִ�в��𷽷�
		System.out.println("����id"+deploy.getId());
		System.out.println("��������"+deploy.getName());
	}
	/**
	 * ��������ʵ������������֮�󣬻���act_ru_task�������������id������
	 */
	@Test
	public void startProcessInstance() {
		String processDefinitionKey = "helloworld";
		ProcessInstance processInstance = processEngine.getRuntimeService()		//��ȡ����ִ�е�����ʵ����ִ�ж���
						.startProcessInstanceByKey(processDefinitionKey);	//ʹ��keyֵ��act_re_procdef����������Ӧ���ݽ���������ʹ��keyֵ�����Զ��������°汾�Ĳ�������
		System.out.println("����ʵ��id"+processInstance.getId());
		System.out.println("����ʵ������"+processInstance.getName());
	}
	
	/**
	 * ��ȡ��ǰ���̸������񣬴�ʱ��act_ru_task��������Ӧ����
	 */
	@Test
	public void queueMytask() {
		String assignee = "����";
		List<Task> list = processEngine.getTaskService()		//��ȡ��ǰ�������
						.createTaskQuery()	//���������ѯ����
						.taskAssignee(assignee)	//ָ��ǩ����Ա��ѯ
						.list();
		for (Task task : list) {
			System.out.println("taskId��"+task.getId());
			System.out.println("taskName��"+task.getName());
			System.out.println("###################");
		}
		
	}
	
	/**
	 * ��ɶ�Ӧ������ÿ���һ��ǩ�ϣ���act_ru_task���е����������£������������һ���µ�ID
	 */
	@Test
	public void completeMytask() {
		String taskId="17504";
		processEngine.getTaskService()		//��ȡ��ǰ�������
							.complete(taskId);
		System.out.println(taskId+"���������!");
		System.out.println("###############");
	}
}
