package com.hadoop.mapreduce.io.fileoutputformat;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.RecordWriter;
import org.apache.hadoop.mapreduce.TaskAttemptContext;

public class FilterRecordWriter extends RecordWriter<Text, NullWritable>{
	
	//成员暂存容器
	private FSDataOutputStream datamgrOut = null;
	private FSDataOutputStream otherOut = null;
	
	public FilterRecordWriter(TaskAttemptContext job)  {
		Configuration configuration = job.getConfiguration();
		
		try {
			// 获取文件系统
			FileSystem fs = FileSystem.get(configuration);
			
			////通过文件对象获得不同的输出流对象
			datamgrOut = fs.create(new Path("e:/output/datamgr.log"));
			otherOut = fs.create(new Path("e:/output/other.log"));
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}

	@Override
	public void write(Text key, NullWritable value) throws IOException, InterruptedException {
		// 区分输入的key是否包含datamgr
		
		if (key.toString().contains("datamgr")) {// 包含
			datamgrOut.write(key.toString().getBytes());
		}else {// 不包含
			otherOut.write(key.toString().getBytes());
		}
	}

	@Override
	public void close(TaskAttemptContext context) throws IOException, InterruptedException {
		
		if (datamgrOut != null) {
			datamgrOut.close();
		}
		
		if (otherOut != null) {
			otherOut.close();
		}
	}

}
