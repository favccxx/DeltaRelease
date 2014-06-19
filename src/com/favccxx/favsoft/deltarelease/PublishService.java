package com.favccxx.favsoft.deltarelease;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.configuration.HierarchicalConfiguration;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PublishService {
	
	private static Logger logger = LogManager.getLogger(PublishService.class.getName());
	
	public static void getDeltaReleaseFiles(){
		
		String sourceFolder = SystemConfig.getInstance().getString("apps[@default]");
		String patchFolder = SystemConfig.getInstance().getString("apps[@patchFolder]");
		try {
			//清理输出文件目录
			File file = new File(patchFolder);
			if(file.exists()){
				FileUtils.cleanDirectory(new File(patchFolder));
			}
			FileUtils.forceMkdir(new File(patchFolder));
		} catch (IOException e) {
			logger.error("Error get File, The wrong file path is:" + patchFolder);
			logger.error("Error Message is:" + e.getMessage());
		}
		
		//取出最新的class列表
		List<HierarchicalConfiguration> appList = SystemConfig.getInstance().configurationsAt("apps.app");
		for (Iterator<HierarchicalConfiguration> iterator = appList.iterator(); iterator.hasNext();) {
			HierarchicalConfiguration hierarchicalConfiguration = iterator.next();
			String appName = hierarchicalConfiguration.getString("[@appName]");
			String patchFilePath = hierarchicalConfiguration.getString("[@patchFile]");
			String patch_source_dir = hierarchicalConfiguration.getString("patch_source_dir");
			String patch_dest_dir = hierarchicalConfiguration.getString("patch_dest_dir");
			String destDir = patchFolder + patch_dest_dir;
			//执行拷贝补丁文件
			copyPatchFile(appName,patch_source_dir,patchFilePath,destDir);			
		}
		
		
	}
	
	
	private static void copyPatchFile(String appName,String sourceDir, String patchFilePath, String destDir){
		logger.info("******************************开始拷贝"+ appName +"工程的补丁文件**************************");
		try {
			//创建生成补丁文件的目录
			FileUtils.forceMkdir(new File(destDir));
		} catch (IOException e) {
			logger.error("Error get destDir,The wrong path is:" + destDir);
			logger.error("Error Message is:" + e.getMessage());
		}
			
			File patchFile = new File(Thread.currentThread().getContextClassLoader().getResource(PublishConstants.SYSTEM_CONFIG_DIR+patchFilePath).getFile());	//读取补丁文件--deltaReleaseFile.txt
			try {
				List fileLines = FileUtils.readLines(patchFile);
				int count=0;
				for (Iterator iterator = fileLines.iterator(); iterator.hasNext();) {
					String fileName = (String) iterator.next();
					if(StringUtils.isNotEmpty(fileName)){
						if(fileName.endsWith("java")){
							//java补丁文件,需找到对应的class
							fileName = fileName.replaceAll(".java", ".class");
							String sourceFilePath = sourceDir + "\\WEB-INF\\classes" + fileName;
							File compileFile = new File(sourceFilePath);
							if(compileFile.exists()){
								String destFilePath = destDir + "\\WEB-INF\\classes" + fileName;
								FileUtils.copyFile(compileFile, new File(destFilePath));
								logger.info("-------" + ++count +"成功拷贝补丁文件到：" + destFilePath);
							}else{
								logger.error("未找到该补丁文件，错误的路径：" + sourceFilePath);
							}
							
						}else{
							//JSP、CSS、JS等格式的文件直接copy到工程的目录
							String sourceFilePath = sourceDir + fileName;
							String destFilePath = destDir + fileName;
							FileUtils.copyFile(new File(sourceFilePath), new File(destFilePath));	
							logger.info("-------" + ++count +"成功拷贝补丁文件到：" + destFilePath);
						}
					}
				}
				logger.info("******************************"+ appName +"工程补丁文件获取成功，共拷贝"+ count +"个补丁文件**************************");
			} catch (IOException e) {
				logger.error("Error in execute copyPatchFile. The wrong message is:" + e.getMessage());
			}
			
	}

}
