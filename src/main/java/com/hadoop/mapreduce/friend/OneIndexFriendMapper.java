package com.hadoop.mapreduce.friend;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class OneIndexFriendMapper extends Mapper<LongWritable, Text, Text, Text> {

	Text k = new Text();
	Text v = new Text();

	@Override
	protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
		// 1 获取一行
		String line = value.toString();
		String[] first = line.split(":");
		//人
		String map_key = first[0];
		//友
		String[] map_vlaue = first[1].split(",");
		for(String friend : map_vlaue) {
			k.set(friend);
			v.set(map_key);
			//获得  友a：人1
			context.write(k, v);
		}
		
	}
}
