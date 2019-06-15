package cn.chen.b_processDefinition;

import java.io.InputStream;
import java.util.List;
import java.util.zip.ZipInputStream;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.junit.Test;

public class RepositoryServiceInfo {
	ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
	@Test
	public void deploy() {
		//Deployment�����Ӧ�����ݿ���act_re_deployment��
		/**
		��һ�������ݿ��н��������ű�
		a)	act_re_deployment����������
		������̶������ʾ���Ͳ���ʱ�䣬ÿ����һ������һ����¼
		b)	act_re_procdef�����̶����
		������̶����������Ϣ������ÿ���µ����̶��嶼�������ű�������һ����¼��
		ע�⣺�����̶����key��ͬ������£�ʹ�õ��ǰ汾����
		c)	act_ge_bytearray����Դ�ļ���
		�洢���̶�����صĲ�����Ϣ�������̶����ĵ��Ĵ�ŵء�
		ÿ����һ�ξͻ�����������¼��һ���ǹ���bpmn�����ļ��ģ�һ����ͼƬ�ģ��������ʱָֻ����bpmnһ���ļ���activiti���ڲ���ʱ����bpmn�ļ������Զ���������ͼ��
		�����ļ����Ǻܴ󣬶����Զ�������ʽ�洢�����ݿ��С�
		 */
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
	 * ���Խ�bpmn�ļ���png�ļ�ѹ��Ϊzip�ļ�һ���������
	 */
	public void zipdeploy() {
		InputStream in = this.getClass().getClassLoader().getResourceAsStream("diagrams/XX.zip");
		ZipInputStream zipInputStream = new ZipInputStream(in);
		Deployment deploy = processEngine.getRepositoryService()
						.createDeployment()
						.name("�������")
						.addZipInputStream(zipInputStream)
						.deploy();
	}
	
	/**
	 * �鿴���̶��壺���̶����������ݱ�act_re_procdef
	 */
	@Test
	public void queryProcessDefinition() {
		List<ProcessDefinition> list = processEngine.getRepositoryService()
						.createProcessDefinitionQuery()		//��ȡ���̶���query����
						//���ò�ѯ����
//						.processDefinitionId(processDefinitionId)
//						.processDefinitionKey(processDefinitionKey)
						.orderByProcessDefinitionVersion().asc()	//���հ汾�Ŷ����̶�������
//						.count()	//���ؽ����������
//						.listPage(firstResult, maxResults)	//��ҳ��ѯ
//						.singleResult()		//����Ψһ�����
						.list();	//�������̶�������
		for (ProcessDefinition processDefinition : list) {
			System.out.println("���̶���id:"+processDefinition.getId());
			System.out.println("���̶���name:"+processDefinition.getName());
			System.out.println("���̶���key:"+processDefinition.getKey());
			System.out.println("���̶���version:"+processDefinition.getVersion());
		}
	}
	/**
	 * ɾ�����̶����漰��art_re_deployment���ݱ�
	 1����ʹ����ͨɾ��ʱ����������Ѿ�������ô������������Ƿ��и��߼���Ĳ������������������Ĳ�����ô�����̲������ɾ����
	 	���û������������ô��ǰ���𽫲���ɾ������Ϊ��task���й���
	 2����ʹ������ɾ��ʱ�������Ƿ�����������Ĳ��𣬶��ɽ��˲���ɾ�������������ru_task���й�������һ��ɾ��
	 
	 */
	@Test
	public void deleteDeploy() {
		//��Ҫɾ������Ϣ
		String deploymentId = "15001";
		//��ȡ�ֿ�������
		org.activiti.engine.RepositoryService repositoryService = processEngine.getRepositoryService();
		//��ͨɾ���������ǰ����������ִ�е����̣���ô���׳��쳣
//		repositoryService.deleteDeployment(deploymentId);
		//����ɾ������ɾ���͵�ǰ������ص�������Ϣ����������ִ�е���Ϣ��Ҳ������ʷ��Ϣ��������������ѡ��һ
//		repositoryService.deleteDeploymentCascade(deploymentId);
		repositoryService.deleteDeployment(deploymentId, true);
	}
	
	/**
	 * ��ȡ���̶����ĵ�����Դ�漰��art_ge_bytearray���ݱ�
	 1)	deploymentIdΪ���̲���ID
	 2)	resourceNameΪact_ge_bytearray����NAME_�е�ֵ
	 3)	ʹ��repositoryService��getDeploymentResourceNames�������Ի�ȡָ�������µ������ļ�������
	 4)	ʹ��repositoryService��getResourceAsStream�������벿��ID����ԴͼƬ���ƿ��Ի�ȡ������ָ�������ļ���������
	 5)	�����й�IO���Ĳ�����ʹ��FileUtils���ߵ�copyInputStreamToFile��������������̵��ļ��Ŀ���������Դ�ļ���������ʽ�����ָ���ļ�����
	 
	 */
	@Test
	public void viewImage() {
		String deploymentId="22501";
		List<String> deploymentResourceNames = processEngine.getRepositoryService()
						.getDeploymentResourceNames(deploymentId);
		String imageName = null;
		for (String string : deploymentResourceNames) {
			System.out.println("pngͼƬ����"+string);
			if(string.indexOf(".")>0) {
				System.out.println(string.lastIndexOf("."));
			}
		}
	}
	
	/**
	 * ��ѯ���°汾�����̶��壬�������̰汾�Ų�ѯ�����е����̶��壬Ȼ������ȡ������С
	 */
}
