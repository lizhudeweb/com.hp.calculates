package com.hadoop.mapreduce.junior.order;

import java.io.IOException;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Reducer;

public class OrderReducer extends Reducer<OrderBean, NullWritable, OrderBean, NullWritable>{

	@Override
	protected void reduce(OrderBean bean, Iterable<NullWritable> values,
			Context context) throws IOException, InterruptedException {
		
		// 写出
		System.out.println("----------------------reduce：订单："+bean.getOrderId() +"----价格："+bean.getPrice());
		context.write(bean, NullWritable.get());
	}
}
