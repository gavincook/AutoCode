package org.antstudio.autocode.sourceanalyzer;
/**
 * 用于删除代码注释的数据模型
 * @author Gavin
 * @date 2013-12-22 下午9:47:12
 */
public class LineCode{
    private String originCode;
    private StringBuilder codeWithoutComment = new StringBuilder();
    private boolean onlyStart,beforeOnlyStart;
    
    public LineCode(String originCode){
        this.originCode = originCode.replaceAll("\\t", "");
        this.beforeOnlyStart = false;
        removeComment();
    }
    
    public LineCode(String originCode,boolean beforeOnlyStart){
        this.originCode = originCode.replaceAll("\\t", "");
        this.beforeOnlyStart = beforeOnlyStart;
        removeComment();
    }

    public boolean isOnlyStart(){
        return onlyStart;
    }
    
    public String getCodeWithoutComment(){
        return codeWithoutComment.toString();
    }
    
    /**
     * 查找//注释标记的位置
     * @return
     */
    private int calcQuotation(){
        byte lastByte = 0 ,firstByte=0;
        int quotationNum = 0;
        byte[] codes=originCode.getBytes();
        for(int i=0,length=codes.length;i<length;i++){
            byte b = codes[i];
            if(b=='/'&&lastByte=='/'&&firstByte!='*'){
                if(quotationNum%2==0){
                    return i-1;
                }
            }
            
            if(lastByte!='\\'&&b=='\"'){
                quotationNum++;  
            }
            if(lastByte!=0){
            	firstByte = lastByte;
            }
            lastByte = b;
        }
        return -1;
    }
    
    private void removeComment(){
        if(!beforeOnlyStart){//只处理语句不在注释块里的情况
            int lineCommentPostion = calcQuotation();
            if(lineCommentPostion!=-1){
            	//这里用字节数组处理，而不用获取的位置然后使用substring，是因为有可能有中文或者其他占位不确定的字符，一个中文会占2（gb2312）个或者三个(utf-8)字节，
            	//但是string里会将所有的中文或者其他字符作为一个单独的字符处理，因此byte遍历出来的长度和string里面真正的长度不一致
            	byte[] originCodeBytes = new byte[lineCommentPostion];
            	System.arraycopy(originCode.getBytes(), 0, originCodeBytes, 0, lineCommentPostion) ;
                originCode = new String(originCodeBytes);
            }
        }
        int startPosition = originCode.indexOf("/*");
        if(beforeOnlyStart){//首先找注释结束符  */
           startPosition =  originCode.indexOf("*/");
           if(startPosition>-1){
        	   onlyStart = false;
           }
        }
        int endPosition = -1;
        if(startPosition>-1){//如果找到注释标记
            if(!beforeOnlyStart){//这里是为了处理形如  "private .. /*..."的代码
                codeWithoutComment.append(" ").append(originCode.substring(0,startPosition));
            }else{
            	 startPosition = originCode.indexOf("/*",startPosition+2);//此时的startPosition是结束标志的位置
            }
            //继续查找开始下一个开始标记
            //startPosition = originCode.indexOf("/*",startPosition+2);
            while(startPosition>-1){//一直循环到没有开始标记为止
                if(startPosition>endPosition&&endPosition!=-1){//当开始标记大于上一次的结束标记，截取中间的代码
                    codeWithoutComment.append(" ").append(originCode.substring(endPosition+2,startPosition));
                }
                //需找当前开始标记的结束标记
                endPosition = originCode.indexOf("*/",startPosition+2);
                //当结束标记小于开始标记，即没有和当前开始标记匹配的结束标记，跳出循环.形如："/* comment "
                if(endPosition<startPosition){
                    onlyStart = true;
                    break;
                }
                //继续查找下一个开始标记
                startPosition = originCode.indexOf("/*",endPosition+2);
            }
            //如果下一个开始标记小于上一个结束标记，那么将上一个结束标记后的代码取出来，形如:"/* comment */ private .."
            if(startPosition<endPosition){
                codeWithoutComment.append(" ").append(originCode.substring(endPosition+2));
            }
        }else{//如果没有任何代码注释标记
            if(beforeOnlyStart){//之前有未结束的注释标记，那么此行也是注释
               onlyStart = true;
            }else{
                codeWithoutComment.append(" ").append(originCode);
            }
        }
    }
    
}