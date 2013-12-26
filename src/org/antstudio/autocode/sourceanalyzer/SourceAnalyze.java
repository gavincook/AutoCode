package org.antstudio.autocode.sourceanalyzer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @author Gavin
 * @Date Dec 6, 2013 11:01:45 AM
 */
public class SourceAnalyze {
    private static Map<String,String> JAVA_MAPPING_TO_DB = new HashMap<String,String>(){
        private static final long serialVersionUID = 1L;
    {
        put("String", "varchar");
        put("int", "int");
        put("Integer", "int");
        put("Long", "bigint");
        put("long", "bigint");
        put("double", "double");
        put("Double", "double");
        put("Float", "float");
        put("float", "float");
        put("Date", "date");
        put("boolean", "bit");
        put("Boolean", "bit");
    }};
    public static void main(String[] args) throws Exception {
        File javaFile = new File("E:\\workspace\\person\\AutoCode\\src\\Test.java");
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
                            columnDefinition.setDbType(JAVA_MAPPING_TO_DB.get(matcher.group()));
                            first = !first;
                        }else{
                            columnDefinition.setFieldName(matcher.group());
                        }
                    }
                }
                System.out.println(columnDefinition);
            }
        }
        
    }

}
