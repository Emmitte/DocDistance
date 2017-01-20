package script;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

import word2vec.main.java.com.ansj.vec.Word2VEC;
import word2vec.main.java.com.ansj.vec.domain.WordEntry;

public class StringHandler {
	
	public static byte[] bt;
	public static InputStream is;
	public static Reader read;
	public static Lexeme t;
	public static IKSegmenter iks;
	
	/**
	 * input:str
	 * 将字符串分词转换成数组
	 */
	public List<String> stringToArray(String str) {
		
		List<String> list = new ArrayList<String>();
		bt = str.getBytes();
		is = new ByteArrayInputStream(bt);
		read = new InputStreamReader(is);
		iks = new IKSegmenter(read, true);
		try {
			while ((t = iks.next()) != null) {
				list.add(t.getLexemeText());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
		
	}
	
	/**
	 * input:arr
	 * 使用word2vec将字符数组内容扩充
	 */
	public List<String> extendWord(Word2VEC w, List<String> list) {
	
		List<String> tempList = new ArrayList<String>();
		Set<WordEntry> temp;
		Iterator iter;
		WordEntry entry;
		int i,n;
		n = list.size();
		for(i = 0;i < n;i++){
			temp = w.distance(list.get(i));
			iter = temp.iterator();
			if(!tempList.contains(list.get(i))){
				tempList.add(list.get(i));
			}
			while(iter.hasNext()){
				entry = (WordEntry) iter.next();
				if(!tempList.contains(entry.name)){
					tempList.add(entry.name);
				}
			}
		}
		return tempList;
		
	}
	
	/**
	 * input:list,stopWordsPath
	 * 删除停用词，通过读取stopWordsPath中的停用词表，将list中的停用词删除,并返回去除停用词后的list
	 */
	public List<String> deleteStopWords(List<String> list, String path2) {
		
		FileHandler fh = new FileHandler();
		List<String> stopDic = fh.readDic(path2);
		List<String> temp = new ArrayList<String>();
		int i,n;
		n = list.size();
		try {  
			for(i = 0;i < n;i++){
				if(!stopDic.contains(list.get(i))){
					temp.add(list.get(i));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return temp;
		
	}
}
