package com.hadoop.mapreduce.friend;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class OneIndexFriendDriver {

	/**
	 * 找两个人的共同好友：好友-->2个人
	 * 文档2次的思路：2人共同指认好友， 获得好友的的好友数组，那跟这个好友有关系的就在数组，遍历数组就相当于指认
	 * 源文件：   人1 ：好友a，好友b，好友c 。。。
	 * 		人2 ：好友a，好友c，好友f 。。。
	 * 
	 * map： 友a：人1     友a： 人2     友a：人3 。。
	 * 
	 * reduce：  友a：人1，人2，人3 。。
	 * 			友b：人1，人2，人3。。
	 * --------------------------------------------------------
	 * map：排序 -->对友a的数组2次循环 -->人1-人n2 ：友a
	 * 
	 * reduce： k为字符串人1-人n2  -->累加得   人1-人n2：友a 友b。。。
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		
		args = new String[] { "e:/input/friends.txt", "e:/output" };

		Configuration conf = new Configuration();
		Job job = Job.getInstance(conf);
		
		job.setJarByClass(OneIndexFriendDriver.class);

		job.setMapperClass(OneIndexFriendMapper.class);
		job.setReducerClass(OneIndexFriendReducer.class);

		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(Text.class);
		
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);

		FileInputFormat.setInputPaths(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));

		job.waitForCompletion(true);
		
	}
}
