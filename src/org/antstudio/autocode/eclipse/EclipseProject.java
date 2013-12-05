package org.antstudio.autocode.eclipse;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Eclipse 项目解析(项目类路径等解析)
 * @author Gavin
 * @date 2013-12-3 下午10:08:43
 */
public class EclipseProject {

	/**
	 * 项目根路径
	 */
	private String projectPath;
	
	public EclipseProject(String projectPath){
		this.projectPath = projectPath;
	}
	
	
	public static void main(String[] args) throws Exception {
		File javaFile = new File("E:\\workspace\\github\\AutoCode\\src\\Test.java");
		InputStreamReader reader = new InputStreamReader(new FileInputStream(javaFile), "UTF-8");
		BufferedReader br = new BufferedReader(reader);
		String line = br.readLine();
		List<String> lines = new ArrayList<String>();
		while(line!=null){
			if(line.trim().startsWith("//")){
				line = br.readLine();
				continue;
			}
			if(line.contains("/*")){
				int position = line.indexOf("*/");
				while(position==-1){
					line = br.readLine();
					position = line.indexOf("*/");
				}
				line = line.substring(position+2);
				System.out.println("??"+line);
				continue;
			}
			
			if(line.matches("^[\\w\\W]*@[\\w\\W]*$")){
				StringBuilder sb = new StringBuilder();
				if(line.matches("^[\\w\\W]*;[\\w\\W]*$")){
					sb.append(line);
				}else{
					sb.append(line);
					line = br.readLine();
					while(line!=null&&!line.matches("^[\\w\\W]*;[\\w\\W]*$")){
						sb.append(line);
						line = br.readLine();
					}
					sb.append(line);
					sb.append("\n=======================\n");
				}
				
				lines.add(sb.toString().replaceAll("\\s+", " ").replaceAll("\\s+\\.\\s+", ".").replaceAll("@\\s+", "@"));
				System.out.println(sb.toString().replaceAll("\\s+", " ").replaceAll("\\s+\\.\\s+", ".").replaceAll("@\\s+", "@"));
			}
			line = br.readLine();
		}
		/*Pattern columnNameRegex = Pattern.compile("^[\\w\\W]*Column");
		for(String l:lines){
			
		}*/
	}//
}
