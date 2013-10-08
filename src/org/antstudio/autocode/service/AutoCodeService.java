package org.antstudio.autocode.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Stack;

import org.antstudio.autocode.container.Container;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * @author Gavin
 * @date 2013-10-3 下午9:50:06
 */
public class AutoCodeService {

	private Properties properties;
	public AutoCodeService(Properties p){
		this.properties = p;
	}
	public void prepareGenerateCode(Map<String,String> data) throws TemplateException, IOException{
		for(Object key:properties.keySet()){
			if(key.toString().startsWith("in.")){
				String outPath = properties.getProperty("out."+key.toString().substring(3));
				if(outPath==null){
					outPath="/autocode/";
					createFolderIfNecessary(outPath);
					generateCode(data,properties.getProperty((String) key),outPath+key.toString().substring(3));
				}else{
					createFolderIfNecessary(outPath);
					generateCode(data,properties.getProperty((String) key),outPath);
				}
				
			}
		}
	}
	/**
	 * 根据path创建必须的folder
	 * @param path
	 */
	private void createFolderIfNecessary(String path){
		String baseDir = Container.get("baseDir", String.class);
		File f;
		if(path.startsWith("/")){//相对路径
			f = new File(baseDir+path.substring(0,path.lastIndexOf("/")));
		}else{//绝对路径
			f = new File(path.substring(0,path.lastIndexOf("/")));
		}
		
		Stack<File> dirs = new Stack<File>();
		if(!f.exists()){
			dirs.push(f);
			while(!dirs.lastElement().getParentFile().exists()){
				dirs.push(dirs.lastElement().getParentFile());
			}
		}
		
		while(!dirs.empty()){
			dirs.pop().mkdir();
		}
	}
	
	private void generateCode(Map<String,String> data,String path,String outPath) throws TemplateException, IOException{
		String baseDir = Container.get("baseDir", String.class);
		Configuration configuration = new Configuration();
		configuration.setEncoding(Locale.getDefault(), "UTF-8");
		//StringTemplateLoader stl = new StringTemplateLoader();
		//stl.putTemplate(path, "这真的就是个测试${dbPath}");
		//configuration.setTemplateLoader(stl);
		configuration.setDirectoryForTemplateLoading(new File(baseDir));
		Template template = configuration.getTemplate(path);
		if(outPath.startsWith("/")){
			outPath=baseDir+outPath;
		}
		File dist = new File(outPath);
		if(!dist.exists()){
			System.out.println(dist);
			dist.createNewFile();
		}
		Writer writer = new OutputStreamWriter(new FileOutputStream(dist));
		template.process(data, writer);
		writer.close();
	}
	
}
