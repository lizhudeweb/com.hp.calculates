package com.hadoop.mapreduce.friend;

import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class OneIndexFriendReducer extends Reducer<Text, Text, Text, Text>{
	
	Text value = new Text();
	@Override
	protected void reduce(Text key, Iterable<Text> values,
			Context context) throws IOException, InterruptedException {
		
		// 累加求和
		StringBuilder sb = new StringBuilder();
		for(Text text : values){
			sb.append(text.toString() + ",");
		}
		value.set(sb.toString());
		//获得  友a：人1，人2，人3
		context.write(key, value);
	}

}
