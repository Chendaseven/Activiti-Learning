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
	 * Activiti手动创建配置
	 */
	@Test
	public void test1() {
		//创建一个单例的Activiti
		ProcessEngineConfiguration processEngineConfiguration = ProcessEngineConfiguration.createStandaloneProcessEngineConfiguration();
		//配置Activiti的数据库信息
		processEngineConfiguration.setJdbcUrl("jdbc:mysql://localhost:3306/activiti?createDatabaseIfNotExist=true&useUnicode=true&characterEncoding=UTF-8");
		processEngineConfiguration.setJdbcDriver("com.mysql.jdbc.Driver");
		processEngineConfiguration.setJdbcUsername("root");
		processEngineConfiguration.setJdbcPassword("root");
		
/*		  设置数据库建表策略
 * 		  public static final String DB_SCHEMA_UPDATE_FALSE = "false";	  如果不存在就抛出异常
		  public static final String DB_SCHEMA_UPDATE_CREATE_DROP = "create-drop";	每次都先删除表，再创建新的表
		  public static final String DB_SCHEMA_UPDATE_TRUE = "true";	 如果不存在表就创建表，存在就直接使用
		*/
		processEngineConfiguration.setDatabaseSchemaUpdate(processEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
		//使用配置对象创建流程引擎实例，检查数据库链接等环境是否正确
		ProcessEngine processEngine = processEngineConfiguration.buildProcessEngine();
		System.out.println(processEngine);
		
	}
	/**
	 * 使用配置文件创建
	 */
	@Test
	public void test2() {
		ProcessEngineConfiguration processEngineConfiguration = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("activiti.cfg.xml");
		ProcessEngine processEngine = processEngineConfiguration.buildProcessEngine();
		System.out.println(processEngine);
	}
}
