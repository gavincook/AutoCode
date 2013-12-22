package org.antstudio.autocode.sourceanalyzer;
/**
 * ����ɾ������ע�͵�����ģ��
 * @author Gavin
 * @date 2013-12-22 ����9:47:12
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
     * ����//ע�ͱ�ǵ�λ��
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
        if(!beforeOnlyStart){//ֻ������䲻��ע�Ϳ�������
            int lineCommentPostion = calcQuotation();
            if(lineCommentPostion!=-1){
            	//�������ֽ����鴦�������û�ȡ��λ��Ȼ��ʹ��substring������Ϊ�п��������Ļ�������ռλ��ȷ�����ַ���һ�����Ļ�ռ2��gb2312������������(utf-8)�ֽڣ�
            	//����string��Ὣ���е����Ļ��������ַ���Ϊһ���������ַ��������byte���������ĳ��Ⱥ�string���������ĳ��Ȳ�һ��
            	byte[] originCodeBytes = new byte[lineCommentPostion];
            	System.arraycopy(originCode.getBytes(), 0, originCodeBytes, 0, lineCommentPostion) ;
                originCode = new String(originCodeBytes);
            }
        }
        int startPosition = originCode.indexOf("/*");
        if(beforeOnlyStart){//������ע�ͽ�����  */
           startPosition =  originCode.indexOf("*/");
           if(startPosition>-1){
        	   onlyStart = false;
           }
        }
        int endPosition = -1;
        if(startPosition>-1){//����ҵ�ע�ͱ��
            if(!beforeOnlyStart){//������Ϊ�˴�������  "private .. /*..."�Ĵ���
                codeWithoutComment.append(" ").append(originCode.substring(0,startPosition));
            }else{
            	 startPosition = originCode.indexOf("/*",startPosition+2);//��ʱ��startPosition�ǽ�����־��λ��
            }
            //�������ҿ�ʼ��һ����ʼ���
            //startPosition = originCode.indexOf("/*",startPosition+2);
            while(startPosition>-1){//һֱѭ����û�п�ʼ���Ϊֹ
                if(startPosition>endPosition&&endPosition!=-1){//����ʼ��Ǵ�����һ�εĽ�����ǣ���ȡ�м�Ĵ���
                    codeWithoutComment.append(" ").append(originCode.substring(endPosition+2,startPosition));
                }
                //���ҵ�ǰ��ʼ��ǵĽ������
                endPosition = originCode.indexOf("*/",startPosition+2);
                //���������С�ڿ�ʼ��ǣ���û�к͵�ǰ��ʼ���ƥ��Ľ�����ǣ�����ѭ��.���磺"/* comment "
                if(endPosition<startPosition){
                    onlyStart = true;
                    break;
                }
                //����������һ����ʼ���
                startPosition = originCode.indexOf("/*",endPosition+2);
            }
            //�����һ����ʼ���С����һ��������ǣ���ô����һ��������Ǻ�Ĵ���ȡ����������:"/* comment */ private .."
            if(startPosition<endPosition){
                codeWithoutComment.append(" ").append(originCode.substring(endPosition+2));
            }
        }else{//���û���κδ���ע�ͱ��
            if(beforeOnlyStart){//֮ǰ��δ������ע�ͱ�ǣ���ô����Ҳ��ע��
               onlyStart = true;
            }else{
                codeWithoutComment.append(" ").append(originCode);
            }
        }
    }
    
}