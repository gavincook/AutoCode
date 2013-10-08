package org.antstudio.autocode.template;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * 模板引擎，用于根据模板生成文件
 * @author Gavin
 * @date 2013-9-28 上午12:33:11
 */
public class TemplateEngine {

	public void render() throws IOException, TemplateException{
		Configuration cfg = new Configuration();
		cfg.setDirectoryForTemplateLoading(new File("D://"));
		cfg.setObjectWrapper(new DefaultObjectWrapper());


		Template tmp = cfg.getTemplate("test.ftl");
		Writer writer = new OutputStreamWriter(new FileOutputStream(new File("D://test.txt")));
		Map<String,Object> dataModel = new HashMap<String, Object>();
		dataModel.put("name", "John");
		tmp.process(dataModel, writer);
	}
	
	
	public static void main(String[] args) throws IOException, TemplateException {
		new TemplateEngine().render();
	}
}
