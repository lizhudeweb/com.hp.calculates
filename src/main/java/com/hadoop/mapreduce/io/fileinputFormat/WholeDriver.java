package com.hadoop.mapreduce.io.fileinputFormat;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.SequenceFileOutputFormat;

public class WholeDriver {

	/**
	 * 自定义InputFormat，将多个小文件合并成一个文件SequenceFile
	 * SequenceFile里面存储着多个文件，存储的形式为文件路径+名称为key，文件内容为value。
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		
		args = new String[] { "e:/input/wc", "e:/output" };
		
		// 1 获取配置信息
		Configuration conf = new Configuration();
		Job job = Job.getInstance(conf);
		
		// 2 设置jar包加载路径
		job.setJarByClass(WholeDriver.class);

		// 3 加载map/reduce类
		job.setMapperClass(WholeMapper.class);
		
		// 关联自定义的inputformat
		job.setInputFormatClass(WholeFileInputformat.class);
		// 设置输出文件的格式为sequencefile
		job.setOutputFormatClass(SequenceFileOutputFormat.class);

		// 5 设置最终输出数据的key和value类型
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(BytesWritable.class);


		// 6 设置输入数据和输出数据路径
		FileInputFormat.setInputPaths(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));

		boolean result = job.waitForCompletion(true);

		System.exit(result ? 0 : 1);
	}
}
