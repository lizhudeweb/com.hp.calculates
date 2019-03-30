package com.hadoop.mapreduce.friend;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;


public class FriendMapper extends Mapper<LongWritable, Text, Text, Text> {

	/**
	 * setup方法和cleanup方法默认是不做任何操作，且它们只被执行一次。
	 * 但是setup方法一般会在map函数之前执行一些准备工作，如作业的一些配置信息等；
	 * cleanup方法则是在map方法运行完之后最后执行 的，该方法是完成一些结尾清理的工作
	 * 
	 */
	
	Map<String, String[]> in_buf = new HashMap<>();
	//用于存储索引，方便遍历和业务处理
	List<Integer> index = new ArrayList<>();

	@Override
	protected void map(LongWritable key, Text value, Context context) 
			throws IOException, InterruptedException {
		
		String line = value.toString();
		String[] first = line.split(":");
		
		String map_key = first[0];
		String[] map_vlaue = first[1].split(",");
		in_buf.put(map_key, map_vlaue);
		//把key的ASCII值装入list索引集合，方便后面for循环
		index.add((int)map_key.toCharArray()[0]);
	}
	
	Text key = new Text();
	Text value = new Text();
	
	@Override
	protected void cleanup(Context context) throws IOException, InterruptedException {
		for (int i = 0; i < index.size()-1; i++) {
			for (int j = i+1; j < index.size(); j++) {
				//再串一个方法，掏出共同好友
				int aa = index.get(i);
				int bb = index.get(j);
				
				//输出容器map的key
				String out_buf_key=(char)aa+"-"+(char)bb;
				
				//输出容器map的value
				String theSameFriend = getSame(in_buf.get((char)aa+""),in_buf.get((char)bb+""));
				
				//把两人的共同好友输出到缓存容器中
				//没有共同好友就不输出
				if(theSameFriend!=null&&theSameFriend.trim()!=""){
					key.set(out_buf_key);
					value.set(theSameFriend);
					context.write(key, value);
				}
			}
		}
	}
	
	//找出两个数组中相同的值，并把相同的值打包转换成字符串返回
	private String getSame(String[] array1, String[] array2) {
		//暂存容器，用于存储两个数组中所有相同的值
		List<String> same=new ArrayList<>();
		for (int i = 0; i < array1.length; i++) {
			for (int j = 0; j < array2.length; j++) {
				if(array1[i].equals(array2[j])){
					same.add(array2[j]);
				}
			}
		}
		if(same.size()>0){
			//把集合中的内容转换成字符串
			StringBuilder build = new StringBuilder();
			for (String string : same) {
				build.append(string).append(" ");
			}
			return build.toString().trim();
		}
		return null;
	}


}
