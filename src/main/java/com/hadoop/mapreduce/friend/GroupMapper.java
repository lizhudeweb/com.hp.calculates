package com.hadoop.mapreduce.friend;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class GroupMapper extends Mapper<LongWritable,Text,NullWritable,GroupBean> {

    /**
     * A:B,C,D,F,E,O
       B:A,C,E,K
     */
    GroupBean groupBean=new GroupBean();
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

        String team = value.toString();
        StringBuffer stringBuffer=new StringBuffer();
        //得到本用户的名称
        groupBean.setOwer(team.charAt(0));
        //处理得到好友的名单
        String tmp=team.substring(2);
        String[] strings = tmp.split(",");
        for (String sTmp:strings){
            stringBuffer.append(sTmp);
        }
        //得到本用户的好友封装到bean当中
        groupBean.setTeam(stringBuffer.toString());
        //写到缓冲区
        context.write(NullWritable.get(),groupBean);
    }
}
