package com.hadoop.mapreduce.datacompress;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import com.hadoop.mapreduce.junior.wordcount.WordcountMapper;
import com.hadoop.mapreduce.junior.wordcount.WordcountReducer;

// 驱动主程序
public class WordcountDriver {

	/**
	 * 压缩 可在配置文件配置， 此为代码设置
	 * map端
	 * reduce端
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {

		args = new String[] { "e:/input/wc", "e:/output" };
		
		// 1 获取job对象信息
		Configuration configuration = new Configuration();
		
		// 开启map端输出压缩
		// configuration.setBoolean("mapreduce.map.output.compress", true);
		// 设置map端输出压缩方式
		// configuration.setClass("mapreduce.map.output.compress.codec",BZip2Codec.class, CompressionCodec.class);
		
		Job job = Job.getInstance(configuration);

		// 2 设置加载jar位置
		job.setJarByClass(WordcountDriver.class);

		// 3 设置mapper和reducer的class类
		job.setMapperClass(WordcountMapper.class);
		job.setReducerClass(WordcountReducer.class);

		// 4 设置输出mapper的数据类型
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(IntWritable.class);

		// 5 设置最终数据输出的类型
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);

		// 6 设置输入数据和输出数据路径
		FileInputFormat.setInputPaths(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));

		
//		// 设置reduce端输出压缩开启
//		FileOutputFormat.setCompressOutput(job, true);
//
		// 设置压缩的方式
//	    FileOutputFormat.setOutputCompressorClass(job, BZip2Codec.class); 
//	    FileOutputFormat.setOutputCompressorClass(job, GzipCodec.class); 
//	    FileOutputFormat.setOutputCompressorClass(job, DefaultCodec.class); 

		// 7 提交
		boolean result = job.waitForCompletion(true);

		System.exit(result ? 0 : 1);
	}
}
