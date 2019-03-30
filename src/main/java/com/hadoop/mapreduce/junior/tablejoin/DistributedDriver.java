package com.hadoop.mapreduce.junior.tablejoin;

import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class DistributedDriver {

	/**
	 * map端通过加缓存把小表加载到map里去,map里取出需要的值，在封装
	 * 大坑：
	 * 1、将txt文件保存为unicode字符集，因为java默认字符集为unicode
	 * 2、使用EditPlus等工具将txt文件另存为UTF-8无BOM格式
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {

		args = new String[] { "e:/input/table/order.txt", "e:/output" };
		
		// 1 获取job信息
		Configuration configuration = new Configuration();
		Job job = Job.getInstance(configuration);

		// 2 设置加载jar包路径
		job.setJarByClass(DistributedDriver.class);

		// 3 关联map
		job.setMapperClass(DistributedMapper.class);

		// 4 设置最终输出数据类型
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(NullWritable.class);

		// 5 设置输入输出路径
		FileInputFormat.setInputPaths(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));

		// 6 加载缓存数据
		job.addCacheFile(new URI("file:/e:/input/table/pd.txt"));
		
		// 7 map端join的逻辑不需要reduce阶段，设置reducetask数量为0
		job.setNumReduceTasks(0);

		// 8 提交
		boolean result = job.waitForCompletion(true);
		System.exit(result ? 0 : 1);

	}
}
