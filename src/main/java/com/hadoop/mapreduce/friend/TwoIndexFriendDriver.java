package com.hadoop.mapreduce.friend;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class TwoIndexFriendDriver {

	public static void main(String[] args) throws Exception {
		
		args = new String[] { "e:/output/part-r-00000", "e:/output11" };
		
		Configuration config = new Configuration();
		Job job = Job.getInstance(config);
		
		job.setJarByClass(TwoIndexFriendDriver.class);

		job.setMapperClass(TwoIndexFriendMapper.class);
		job.setReducerClass(TwoIndexFriendReducer.class);

		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(Text.class);

		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);

		FileInputFormat.setInputPaths(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));

		System.exit(job.waitForCompletion(true) ? 0 : 1);
	}
}
