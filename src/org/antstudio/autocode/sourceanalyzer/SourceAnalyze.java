package org.antstudio.autocode.sourceanalyzer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;


/**
 * @author Gavin
 * @Date Dec 6, 2013 11:01:45 AM
 */
public class SourceAnalyze {
    
    public static void main(String[] args) throws Exception {
        File javaFile = new File("E:\\workspace\\github\\AutoCode\\src\\Test.java");
        InputStreamReader reader = new InputStreamReader(new FileInputStream(javaFile), "UTF-8");
        BufferedReader br = new BufferedReader(reader);
        String line = br.readLine();
        boolean beforeOnlyStart = false;
        while(line!=null){
        	LineCode lc = new  LineCode(line,beforeOnlyStart);
        	beforeOnlyStart =lc.isOnlyStart();
        	System.out.println(lc.getCodeWithoutComment());
        	line = br.readLine();
        }
    }

}
