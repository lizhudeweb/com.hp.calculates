package com.hadoop.mapreduce.friend;

import java.io.IOException;
import java.util.Arrays;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class TwoIndexFriendMapper extends Mapper<LongWritable, Text, Text, Text>{
	Text k = new Text();
	Text v = new Text();
	
	@Override
	protected void map(LongWritable key, Text value, Context context)
			throws IOException, InterruptedException {
		
		//获得  友a \t 人1，人2，人3
		String line = value.toString();
		
		String[] fields = line.split("\t");
		String friend = fields[0];
		String[] renArr = fields[1].split(",");
		//排序很重要，否则map输出的k有重复
		Arrays.sort(renArr);
		v.set(friend);
		for (int i = 0; i < renArr.length - 1; i++) {
			for (int j = i + 1; j < renArr.length; j++) {
				String head = renArr[i] + "-"+ renArr[j];
				k.set(head);
				// 人m-人n	友b
				v.set(fields[0]);
				context.write(k, v);
			}
		}
		
	}
}
