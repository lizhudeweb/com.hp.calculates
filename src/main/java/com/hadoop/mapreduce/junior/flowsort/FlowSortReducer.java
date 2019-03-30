package com.hadoop.mapreduce.junior.flowsort;

import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;



public class FlowSortReducer extends Reducer<FlowSortBean, Text, Text, FlowSortBean>{

	@Override
	protected void reduce(FlowSortBean bean, Iterable<Text> values, Context context)
			throws IOException, InterruptedException {
		Text v = values.iterator().next();
		context.write(v, bean);
	}
}
