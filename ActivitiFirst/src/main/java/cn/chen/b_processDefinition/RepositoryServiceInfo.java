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
		//Deployment对象对应于数据库中act_re_deployment表
		/**
		这一步在数据库中将操作三张表：
		a)	act_re_deployment（部署对象表）
		存放流程定义的显示名和部署时间，每部署一次增加一条记录
		b)	act_re_procdef（流程定义表）
		存放流程定义的属性信息，部署每个新的流程定义都会在这张表中增加一条记录。
		注意：当流程定义的key相同的情况下，使用的是版本升级
		c)	act_ge_bytearray（资源文件表）
		存储流程定义相关的部署信息。即流程定义文档的存放地。
		每部署一次就会增加两条记录，一条是关于bpmn规则文件的，一条是图片的（如果部署时只指定了bpmn一个文件，activiti会在部署时解析bpmn文件内容自动生成流程图）
		两个文件不是很大，都是以二进制形式存储在数据库中。
		 */
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
	 * 可以将bpmn文件和png文件压缩为zip文件一起解析部署
	 */
	public void zipdeploy() {
		InputStream in = this.getClass().getClassLoader().getResourceAsStream("diagrams/XX.zip");
		ZipInputStream zipInputStream = new ZipInputStream(in);
		Deployment deploy = processEngine.getRepositoryService()
						.createDeployment()
						.name("请假流程")
						.addZipInputStream(zipInputStream)
						.deploy();
	}
	
	/**
	 * 查看流程定义：流程定义属于数据表：act_re_procdef
	 */
	@Test
	public void queryProcessDefinition() {
		List<ProcessDefinition> list = processEngine.getRepositoryService()
						.createProcessDefinitionQuery()		//获取流程定义query对象
						//设置查询条件
//						.processDefinitionId(processDefinitionId)
//						.processDefinitionKey(processDefinitionKey)
						.orderByProcessDefinitionVersion().asc()	//按照版本号对流程定义排序
//						.count()	//返回结果集的数量
//						.listPage(firstResult, maxResults)	//分页查询
//						.singleResult()		//搜索唯一结果集
						.list();	//查找流程定义结果集
		for (ProcessDefinition processDefinition : list) {
			System.out.println("流程定义id:"+processDefinition.getId());
			System.out.println("流程定义name:"+processDefinition.getName());
			System.out.println("流程定义key:"+processDefinition.getKey());
			System.out.println("流程定义version:"+processDefinition.getVersion());
		}
	}
	/**
	 * 删除流程定义涉及到art_re_deployment数据表
	 1、当使用普通删除时，如果流程已经发起，那么将看这个流程是否还有更高级别的部署，如果还有其他级别的部署，那么此流程部署可以删除；
	 	如果没有其他部署，那么当前部署将不能删除，因为和task表有关联
	 2、当使用连级删除时，不论是否还有其他级别的部署，都可将此部署删除，包括将会把ru_task表中关联数据一起删除
	 
	 */
	@Test
	public void deleteDeploy() {
		//需要删除的信息
		String deploymentId = "15001";
		//获取仓库服务对象
		org.activiti.engine.RepositoryService repositoryService = processEngine.getRepositoryService();
		//普通删除，如果当前规则有正在执行的流程，那么将抛出异常
//		repositoryService.deleteDeployment(deploymentId);
		//连级删除，会删除和当前规则相关的所有信息，包括正在执行的信息，也包括历史信息，以下两个方法选其一
//		repositoryService.deleteDeploymentCascade(deploymentId);
		repositoryService.deleteDeployment(deploymentId, true);
	}
	
	/**
	 * 获取流程定义文档的资源涉及到art_ge_bytearray数据表
	 1)	deploymentId为流程部署ID
	 2)	resourceName为act_ge_bytearray表中NAME_列的值
	 3)	使用repositoryService的getDeploymentResourceNames方法可以获取指定部署下得所有文件的名称
	 4)	使用repositoryService的getResourceAsStream方法传入部署ID和资源图片名称可以获取部署下指定名称文件的输入流
	 5)	最后的有关IO流的操作，使用FileUtils工具的copyInputStreamToFile方法完成流程流程到文件的拷贝，将资源文件以流的形式输出到指定文件夹下
	 
	 */
	@Test
	public void viewImage() {
		String deploymentId="22501";
		List<String> deploymentResourceNames = processEngine.getRepositoryService()
						.getDeploymentResourceNames(deploymentId);
		String imageName = null;
		for (String string : deploymentResourceNames) {
			System.out.println("png图片名："+string);
			if(string.indexOf(".")>0) {
				System.out.println(string.lastIndexOf("."));
			}
		}
	}
	
	/**
	 * 查询最新版本的流程定义，根据流程版本号查询出所有的流程定义，然后排序取最大或最小
	 */
}
