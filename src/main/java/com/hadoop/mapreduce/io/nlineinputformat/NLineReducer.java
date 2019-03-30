package com.hadoop.mapreduce.io.nlineinputformat;
import java.io.IOException;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class NLineReducer extends Reducer<Text, LongWritable, Text, LongWritable>{
	
	LongWritable v = new LongWritable();
	
	@Override
	protected void reduce(Text key, Iterable<LongWritable> values,
			Context context) throws IOException, InterruptedException {
		
        long count = 0l;
        // 1 汇总
        for (LongWritable value : values) {
            count += value.get();
        }  
        
        v.set(count);
        
        // 2 输出
        context.write(key, v);
	}
}
