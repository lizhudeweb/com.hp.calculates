package com.hadoop.mapreduce.friend;

import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class TwoIndexFriendReducer extends Reducer<Text, Text, Text, Text>{

	Text v = new Text();
	
	@Override
	protected void reduce(Text key, Iterable<Text> values, Context context)
			throws IOException, InterruptedException {
		
		StringBuilder sb = new StringBuilder();
		for(Text text : values){
			sb.append(text.toString() + ",");
		}
		v.set(sb.toString());
		// 人1-人n2：友a 友b。。。
		context.write(key, v);
	}
}
