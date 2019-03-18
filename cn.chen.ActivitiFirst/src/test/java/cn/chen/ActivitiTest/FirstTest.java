package cn.chen.ActivitiTest;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.junit.Test;

public class FirstTest {
	/**
	 * Activiti�ֶ���������
	 */
	@Test
	public void test1() {
		//����һ��������Activiti
		ProcessEngineConfiguration processEngineConfiguration = ProcessEngineConfiguration.createStandaloneProcessEngineConfiguration();
		//����Activiti�����ݿ���Ϣ
		processEngineConfiguration.setJdbcUrl("jdbc:mysql://localhost:3306/activiti?createDatabaseIfNotExist=true&useUnicode=true&characterEncoding=UTF-8");
		processEngineConfiguration.setJdbcDriver("com.mysql.jdbc.Driver");
		processEngineConfiguration.setJdbcUsername("root");
		processEngineConfiguration.setJdbcPassword("root");
		
/*		  �������ݿ⽨�����
 * 		  public static final String DB_SCHEMA_UPDATE_FALSE = "false";	  ��������ھ��׳��쳣
		  public static final String DB_SCHEMA_UPDATE_CREATE_DROP = "create-drop";	ÿ�ζ���ɾ�����ٴ����µı�
		  public static final String DB_SCHEMA_UPDATE_TRUE = "true";	 ��������ڱ�ʹ��������ھ�ֱ��ʹ��
		*/
		processEngineConfiguration.setDatabaseSchemaUpdate(processEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
		//ʹ�����ö��󴴽���������ʵ����������ݿ����ӵȻ����Ƿ���ȷ
		ProcessEngine processEngine = processEngineConfiguration.buildProcessEngine();
		System.out.println(processEngine);
		
	}
	/**
	 * ʹ�������ļ�����
	 */
	@Test
	public void test2() {
		ProcessEngineConfiguration processEngineConfiguration = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("activiti.cfg.xml");
		ProcessEngine processEngine = processEngineConfiguration.buildProcessEngine();
		System.out.println(processEngine);
	}
}
