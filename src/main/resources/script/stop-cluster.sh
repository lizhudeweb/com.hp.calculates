#!/bin/bash
# cat  /etc/profile  >> ~/.bashrc   cat /etc/bashrc
# cat  /etc/profile  >> /etc/bashrc 

#echo "=================   start stopping Kafka cluster    ======================="
#/opt/module/kafka/bin/kafka-server-stop.sh stop
#for host in datamgr@linux113 datamgr@linux112
#do
#	ssh $host "/opt/module/kafka/bin/kafka-server-stop.sh stop"
#    # ssh $host "source /etc/profile; /opt/module/kafka/bin/kafka-server-stop.sh"
#	# bin/kafka-server-stop.sh stop
#    
#    if [[ $? -eq 0 ]]; then
#		echo "$host kafka is stopped"
#    fi
#done


echo "=================   start stopping Zookeeper cluster     ======================="
for i in datamgr@linux111 datamgr@linux112 datamgr@linux113
do
	echo "$i zookeeper is stopping"
	ssh $i '/opt/module/zookeeper-3.4.10/bin/zkServer.sh stop'
	
done





echo "=================   开始关闭yarn     ======================="
ssh datamgr@linux112 '/opt/module/hadoop-2.7.2/sbin/stop-yarn.sh'
echo "=================   开始关闭HDFS     ======================="
ssh datamgr@linux111 '/opt/module/hadoop-2.7.2/sbin/stop-dfs.sh'

# echo "=================   hadoop-tx开始启动jobhistory    ======================="
# ssh datamgr@hadoop-tx '/opt/module/hadoop-2.7.2/sbin/mr-jobhistory-daemon.sh start historyserver'
