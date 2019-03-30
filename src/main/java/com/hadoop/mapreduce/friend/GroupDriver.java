package com.hadoop.mapreduce.friend;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class GroupDriver  {

	/**
	 * 找到任意两个人的共同好友
	 * map： 切分为obj：人、好友组
	 * 
	 * reduce：2层for循环->（比较2个人的好友组->获得2人的共同好友->写出）
	 * 
	 * @param args
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @throws InterruptedException
	 */
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {

        args = new String[] { "e:/input/friends.txt", "e:/output" };
        
		Configuration conf = new Configuration();
		Job job = Job.getInstance(conf);
		
        job.setJarByClass(GroupDriver.class);
        
        job.setMapperClass(GroupMapper.class);
        job.setReducerClass(GroupReduce.class);

        job.setMapOutputKeyClass(NullWritable.class);
        job.setMapOutputValueClass(GroupBean.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(NullWritable.class);

        FileInputFormat.setInputPaths(job,new Path(args[0]));
        FileOutputFormat.setOutputPath(job,new Path(args[1]));

        job.waitForCompletion(true);
        
    }
}
