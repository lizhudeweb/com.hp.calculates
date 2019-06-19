#!/bin/bash
# cat  /etc/profile  >> ~/.bashrc


echo "=================   开始关闭spark history    ======================="
ssh datamgr@linux111 '/opt/module/spark-2.1.1-bin-hadoop2.7/sbin/stop-history-server.sh'

echo "=================   开始关闭spark HA master2 copy  ======================="
ssh datamgr@linux112 '/opt/module/spark-2.1.1-bin-hadoop2.7/sbin/stop-master.sh'

echo "=================   开始关闭spark     ======================="
ssh datamgr@linux111 '/opt/module/spark-2.1.1-bin-hadoop2.7/sbin/stop-all.sh'

echo "=================   开始关闭HDFS     ======================="
ssh datamgr@linux111 '/opt/module/hadoop-2.7.2/sbin/stop-dfs.sh'


echo "=================   stop running Zookeeper cluster    ======================="
for i in datamgr@linux111 datamgr@linux112 datamgr@linux113
do
	echo "$i zookeeper is stopping"
	ssh $i '/opt/module/zookeeper-3.4.10/bin/zkServer.sh stop'
done







# echo "=================   linux111开始关闭jobhistory    ======================="
# ssh datamgr@linux111 '/opt/module/hadoop-2.7.2/sbin/mr-jobhistory-daemon.sh start historyserver'

