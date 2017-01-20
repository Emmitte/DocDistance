package script;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class FileHandler {
	
	public static FileReader fr=null;
	public static BufferedReader br=null;
	public static String line=null;
	
	/**
	 * input:path
	 * 将文件内容添加入list中，一句一条
	 */
	public List<String> putFileToList(String path) {
		
		List<String> docList = new ArrayList<String>();
		try {
			fr = new FileReader(path);
			br = new BufferedReader(fr);
			while ((line = br.readLine()) != null) {
				String[] strArr;
				if(line.contains(".")){
					strArr = line.split(".");
					System.out.println(strArr.length);
				}else if (line.contains("。")) {
					strArr = line.split("。");
				}else if (line.contains("．")) {
					strArr = line.split("．");
				}else if (line.contains(";")) {
					strArr = line.split(";");
				}else {
					strArr = new String[1];
					strArr[0] = line;
				}
				int i,n;
				n = strArr.length;
				for(i = 0;i < n;i++){
					docList.add(strArr[i]);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return docList;
	}
	
	/**
	 * input:path
	 * output:List<String>
	 * 读取词典信息，以list返回词典
	 */
	public List<String> readDic(String path) {
		List<String> list = new ArrayList<String>();
		try {  
            fr=new FileReader(path);
			br=new BufferedReader(fr);
            while ((line=br.readLine()) != null ) {  
                StringTokenizer st = new StringTokenizer(line);
                while(st.hasMoreElements()){
                	list.add(st.nextToken());
                }
			}
            br.close();
            br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
}
