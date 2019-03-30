package com.hadoop.mapreduce.io.kvtinputFormat;
import java.io.IOException;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class KVTextMapper extends Mapper<Text, Text, Text, LongWritable>{
	
    final Text k = new Text();  
    final LongWritable v = new LongWritable();  
    
	@Override
	protected void map(Text key, Text value, Context context)
			throws IOException, InterruptedException {
		
        k.set(key);  
        // 设置key的个数
        v.set(1);  
        // 2 写出
        context.write(k, v);  
	}
}
