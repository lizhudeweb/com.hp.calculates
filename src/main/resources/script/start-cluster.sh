#!/bin/bash
# cat  /etc/profile  >> ~/.bashrc

echo "=================   start running Zookeeper cluster    ======================="
for i in datamgr@linux111 datamgr@linux112 datamgr@linux113
do
	ssh $i '/opt/module/zookeeper-3.4.10/bin/zkServer.sh start'
	echo "$i zookeeper is runned"
done

#echo "=================   start running Kafka cluster   ======================="
#KAFKA_HOME_TMP="/opt/module/kafka"  
#for host in datamgr@linux111 datamgr@linux112 datamgr@linux113
#do
#	echo "========  $host start running  ========="
#	ssh $host "nohup ${KAFKA_HOME_TMP}/bin/kafka-server-start.sh ${KAFKA_HOME_TMP}/config/server.properties >/dev/null 2>&1 &" 
#	#/bin/kafka-server-start.sh config/server.properties &
#    #ssh $host "source /etc/profile;nohup ${KAFKA_HOME_TMP}/bin/kafka-server-start.sh ${KAFKA_HOME_TMP}/config/server.properties >/dev/null 2>&1 &" 
#
#	if [[ $? -eq 0 ]]; then
#		echo "$host kafka is runned"
#    fi
#done

echo "=================   开始启动HDFS     ======================="
ssh datamgr@linux111 '/opt/module/hadoop-2.7.2/sbin/start-dfs.sh'
echo "=================   开始启动yarn     ======================="
ssh datamgr@linux112 '/opt/module/hadoop-2.7.2/sbin/start-yarn.sh'

# echo "=================   hadoop-tx开始启动jobhistory    ======================="
# ssh datamgr@hadoop-tx '/opt/module/hadoop-2.7.2/sbin/mr-jobhistory-daemon.sh start historyserver'

