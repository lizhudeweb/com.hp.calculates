package com.hadoop.mapreduce.friend;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class FriendDriver {
	
	/**
	 * 找到任意两个人的共同好友
	 * map：map() 切分所有数据->HashMap存储
	 * 	   cleanup()开始：2层for循环->（比较2个人的好友组->获得2人的共同好友->写出）
	 * 
	 * reduce：写出
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		
		args = new String[] { "e:/input/friends.txt", "e:/output" };
		
		//1 创建任务对象job
		Job job = Job.getInstance(new Configuration());
		
		//2 设置jar所在得的位置
		job.setJarByClass(FriendDriver.class);
		
		//3设置mapreduce程序运行主类
		job.setMapperClass(FriendMapper.class);
		job.setReducerClass(FriendReducer.class);
		
		//4 设置各环节的输出类型
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(Text.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);
		
		//5设置数据源和结果数据的路径
		FileInputFormat.setInputPaths(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		
		//6 提交
		System.exit(job.waitForCompletion(true)?0:1);
		
	}

}
