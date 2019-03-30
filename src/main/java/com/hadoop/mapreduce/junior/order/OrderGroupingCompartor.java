package com.hadoop.mapreduce.junior.order;

import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

public class OrderGroupingCompartor extends WritableComparator {

	// 写一个空参构造
	public OrderGroupingCompartor(){
		super(OrderBean.class, true);
	}
	
	// 重写比较的方法
	@Override
	public int compare(WritableComparable a, WritableComparable b) {
		OrderBean aBean = (OrderBean) a;
		OrderBean bBean = (OrderBean) b;
		
		System.out.println("------------------------OrderGroupingCompartor");
		System.out.println("aBean订单："+aBean.getOrderId() +"----价格："+aBean.getPrice());
		System.out.println("bBean订单："+bBean.getOrderId() +"----价格："+bBean.getPrice());
		System.out.println("reslut："+aBean.getOrderId().compareTo(bBean.getOrderId()));

		// 根据订单id号比较，判断是否是一组
		return aBean.getOrderId().compareTo(bBean.getOrderId());
	}
}
