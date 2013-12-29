package org.antstudio.autocode.sourceanalyzer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 源码分析，用于获取domain中使用@Column标示了的字段，而不使用加载class的形式，因为可能domain会依赖很多其他的三方包，这样减少了类加载
 * @author Gavin
 * @Date Dec 6, 2013 11:01:45 AM
 */
public class SourceAnalyze {
    
    /**
     * 源码路径(java)
     */
    private String sourcePath;
    
    public SourceAnalyze(String sourcePath){
    	this.sourcePath = sourcePath;
    }
    
    /**
     * 获取源码中使用@Column标示了的字段
     * @return
     * @throws Exception
     */
    public List<ColumnDefinition> getColumns() throws Exception{
    	List<ColumnDefinition> columns = new ArrayList<ColumnDefinition>();

        File javaFile = new File(sourcePath);
        InputStreamReader reader = new InputStreamReader(new FileInputStream(javaFile), "UTF-8");
        BufferedReader br = new BufferedReader(reader);
        String line = br.readLine();
        StringBuilder domainCode = new StringBuilder();
        boolean beforeOnlyStart = false;
        while(line!=null){
        	LineCode lc = new  LineCode(line,beforeOnlyStart);
        	beforeOnlyStart =lc.isOnlyStart();
        	domainCode.append(lc.getCodeWithoutComment()).append(" ");
        	line = br.readLine();
        }
        reader.close();
        Pattern columnPattern = Pattern.compile("@\\s*Column\\s*\\(.*\\)");
        Pattern fieldPattern = Pattern.compile("\\w+");
        Pattern stringPattern = Pattern.compile("\"(.+)\"");
        for(String codeSentence:domainCode.toString().split(";")){
            Matcher m = columnPattern.matcher(codeSentence);
            if(m.find()){
                ColumnDefinition columnDefinition = new ColumnDefinition();
                codeSentence = codeSentence.replaceFirst(".*@\\s*Column\\s*\\(.*\\)", "")
                                           .replaceFirst("\\s+public\\s+|\\s+private\\s+|\\s+protected\\s+", "");
                Matcher stringMatcher = stringPattern.matcher( m.group());
                while(stringMatcher.find()){
                    columnDefinition.setAliasName(stringMatcher.group(1));
                }
               
                Matcher matcher = fieldPattern.matcher(codeSentence);
                int position  = codeSentence.indexOf("=");
                boolean first  = true;
                while(matcher.find()){
                    if(position==-1||position>matcher.start()){
                        if(first){
                            columnDefinition.setJavaType(matcher.group());
                            first = !first;
                        }else{
                            columnDefinition.setFieldName(matcher.group());
                        }
                    }
                }
                columns.add(columnDefinition);
            }
        }
        
        return columns;
    }

}
