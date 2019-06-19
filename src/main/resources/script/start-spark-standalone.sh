#!/bin/bash
# cat  /etc/profile  >> ~/.bashrc

echo "=================   start running Zookeeper cluster    ======================="
for i in datamgr@linux111 datamgr@linux112 datamgr@linux113
do
	ssh $i '/opt/module/zookeeper-3.4.10/bin/zkServer.sh start'
	echo "$i zookeeper is runned"
done

echo "=================   开始启动HDFS     ======================="
ssh datamgr@linux111 '/opt/module/hadoop-2.7.2/sbin/start-dfs.sh'

echo "=================   开始启动spark     ======================="
ssh datamgr@linux111 '/opt/module/spark-2.1.1-bin-hadoop2.7/sbin/start-all.sh'

echo "=================   开始启动spark HA master2 copy  ======================="
ssh datamgr@linux112 '/opt/module/spark-2.1.1-bin-hadoop2.7/sbin/start-master.sh'

echo "=================   开始启动spark history    ======================="
ssh datamgr@linux111 '/opt/module/spark-2.1.1-bin-hadoop2.7/sbin/start-history-server.sh'




# echo "=================   linux111开始启动jobhistory    ======================="
# ssh datamgr@linux111 '/opt/module/hadoop-2.7.2/sbin/mr-jobhistory-daemon.sh start historyserver'

