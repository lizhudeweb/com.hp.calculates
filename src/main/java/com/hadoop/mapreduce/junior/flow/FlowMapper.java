package com.hadoop.mapreduce.junior.flow;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class FlowMapper extends Mapper<LongWritable, Text, Text, FlowBean>{
	FlowBean bean = new FlowBean();
	Text k = new Text();
	
	//1363157993055 	13560439658	C4-17-FE-BA-DE-D9:CMCC	120.196.100.99			18	15	1116	954	200
	//1363157995033 	15920133257	5C-0E-8B-C7-BA-20:CMCC	120.197.40.4	sug.so.360.cn	信息安全	20	20	3156	2936	200
	@Override
	protected void map(LongWritable key, Text value, Context context)
			throws IOException, InterruptedException {
		// 1 获取一行数据
		String line = value.toString();
		// 2 截取字段
		String[] fields = line.split("\t");
		
		// 3 封装bean对象以及获取电话号
		String phoneNum = fields[1];
		
		long upFlow = Long.parseLong(fields[fields.length - 3]);
		long downFlow = Long.parseLong(fields[fields.length - 2]);
	
		bean.set(upFlow, downFlow);
		k.set(phoneNum);
		
		// 4 写出去
		context.write(k, bean);
		
	}
}
