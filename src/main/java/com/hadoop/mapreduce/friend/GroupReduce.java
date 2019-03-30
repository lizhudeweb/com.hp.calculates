package com.hadoop.mapreduce.friend;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;

public class GroupReduce extends Reducer<NullWritable, GroupBean, Text, NullWritable> {

    //进行处理得到共同的好友信息
    @Override
    protected void reduce(NullWritable key, Iterable<GroupBean> values, Context context) throws IOException, InterruptedException {

        ArrayList<GroupBean> list = new ArrayList<>();
        for (GroupBean groupBean : values) {
            GroupBean groupBean1 = new GroupBean();
            try {
                BeanUtils.copyProperties(groupBean1, groupBean);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            list.add(groupBean1);
        }
        Collections.reverse(list);
        // 进行嵌套排序

        //封装为方法
        writeSameFriends(list, context);

    }

    private void writeSameFriends(ArrayList<GroupBean> list, Context context) throws IOException, InterruptedException {
        Text text = new Text();
        int index = 0;  //记录内循环地址
        int flag = 0;
//		// 遍历集合中所有bean，查找共同好友
//		for (int i = 0; i < listBean.size() - 1; i++) {
//			for (int j = i + 1; j < listBean.size(); j++) {
        for (GroupBean groupBean : list) {
            System.out.println(groupBean);
            for (flag = index; flag < list.size() - 1; ) {
                GroupBean groupBean2 = list.get(++flag);
                char ower = groupBean.getOwer();  //得到用户的名称
                String team = groupBean.getTeam(); //得到用户的好友成员
                char ower2 = groupBean2.getOwer();  //得到循环比较的用户名称
                String team1 = groupBean2.getTeam();  //得到循环比较的用户的好友
                //比较得到相同的好友
                String sameFriends = comp(team, team1);
                text.set(ower + "&" + ower2 + " -> " + sameFriends);
                if (!sameFriends.isEmpty() && sameFriends.length() != 0) {  //有相同的好友的情况下输出，否则不输出
                    context.write(text, NullWritable.get());
                }
            }
            index++;
        }
    }

    private String comp(String team, String team1) {

        StringBuffer stringBuffer = new StringBuffer();
        char[] chars = team.toCharArray();
        char[] chars1 = team1.toCharArray();
        if (!team.isEmpty() && !team1.isEmpty()) {
            for (char tmp : chars) {

                for (char tmp1 : chars1) {
                    if (tmp == tmp1) {
                        stringBuffer.append(tmp + " ");
                    }
                }
            }
            return stringBuffer.toString();
        } else {
            return null;
        }
    }
}
