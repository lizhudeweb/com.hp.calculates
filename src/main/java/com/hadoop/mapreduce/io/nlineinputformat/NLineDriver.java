package com.hadoop.mapreduce.io.nlineinputformat;
import java.io.IOException;
import java.net.URISyntaxException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.NLineInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class NLineDriver {
	
	/**
	 * 根据每个输入文件的行数来规定输出多少个切片
	 * 
	 * 
	 * @param args
	 */
	public static void main(String[] args) throws IOException, URISyntaxException, ClassNotFoundException, InterruptedException {
		
		args = new String[] { "e:/input/web.log", "e:/output1" };
		
		// 获取job对象
		Configuration configuration = new Configuration();
        Job job = Job.getInstance(configuration);
        
        // 设置jar包位置，关联mapper和reducer
        job.setJarByClass(NLineDriver.class);  
        job.setMapperClass(NLineMapper.class);  
        job.setReducerClass(NLineReducer.class);  
        
        // 设置map输出kv类型
        job.setMapOutputKeyClass(Text.class);  
        job.setMapOutputValueClass(LongWritable.class);  
        
        // 设置最终输出kv类型
        job.setOutputKeyClass(Text.class);  
        job.setOutputValueClass(LongWritable.class);  
          
        
        // 设置每个切片InputSplit中划分3000条记录
        NLineInputFormat.setNumLinesPerSplit(job, 3000);
          
        // 使用NLineInputFormat处理记录数  
        job.setInputFormatClass(NLineInputFormat.class);  
        
        
        // 设置输入输出数据路径
        FileInputFormat.setInputPaths(job, new Path(args[0]));  
        FileOutputFormat.setOutputPath(job, new Path(args[1]));  
          
        // 提交job
        job.waitForCompletion(true);  
	}
}
