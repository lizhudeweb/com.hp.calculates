package com.hadoop.mapreduce.io.fileinputFormat;

import java.io.IOException;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.JobContext;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;

public class WholeFileInputformat extends FileInputFormat<NullWritable, BytesWritable>{

	// 是否可以切割
	@Override
	protected boolean isSplitable(JobContext context, Path filename) {
		System.out.println("WholeFileInputformat isSplitable: false");
		return false;
	}
	
	@Override
	public RecordReader<NullWritable, BytesWritable> createRecordReader(InputSplit split, TaskAttemptContext context)
			throws IOException, InterruptedException {
		System.out.println("WholeFileInputformat RecordReader start");
		
		WholeRecordReader reader = new WholeRecordReader();
		
		// 调用初始化方法
		reader.initialize(split, context);
		
		System.out.println("WholeFileInputformat RecordReader end");
		return reader;
	}

}
