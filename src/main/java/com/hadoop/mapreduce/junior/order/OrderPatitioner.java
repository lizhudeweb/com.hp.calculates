package com.hadoop.mapreduce.junior.order;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Partitioner;

public class OrderPatitioner extends Partitioner<OrderBean, NullWritable>{

	@Override
	public int getPartition(OrderBean key, NullWritable value, int numPartitions) {
		// 按照key的orderid的hashCode值分区
		System.out.println("--------------------分区:"+numPartitions);
		
		//“a”的值是129，转换成二进制就是10000001，而“b”的值是128，转换成二进制就是10000000。
		//根据与运算符的运算规律，只有两个位都是1，结果才是1，可以知道结果就是10000000，即128。
		
		// int数据而言，它的hashcode值就是其包装类型Integer本身，也有正负之分
		//hashcode值如果为负数的话，可以对其与Integer.MAX_VALUE按位运算，之后其结果就成为了正数
		//a-z或者A-Z的hashcode值就是其对应的整形数值。
		return (key.getOrderId().hashCode() & Integer.MAX_VALUE) % numPartitions;
	}
}
