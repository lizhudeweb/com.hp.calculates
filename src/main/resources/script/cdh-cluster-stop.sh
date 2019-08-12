#!/bin/bash
# cat /etc/profle >> .bashr
# cat  /etc/profile  >> ~/.bashrc   cat /etc/bashrc
# cat  /etc/profile  >> /etc/bashrc 


echo "=================   start stopping Kafka cluster    ======================="
/opt/module/kafka/bin/kafka-server-stop.sh stop 
echo "linux111 kafka is stopped"
for host in datamgr@linux113 datamgr@linux112
do
	ssh $host "/opt/module/kafka/bin/kafka-server-stop.sh stop"
	# bin/kafka-server-stop.sh stop
    if [[ $? -eq 0 ]]; then
		echo "$host kafka is stopped"
    fi
done

# cdh版HA个要依托zookeeper，先关hadoop把
echo "=================   开始关闭yarn     ======================="
ssh datamgr@linux112 '/opt/module/cdh/hadoop-2.5.0-cdh5.3.6/sbin/stop-yarn.sh'
echo "=================   开始关闭HDFS     ======================="
ssh datamgr@linux111 '/opt/module/cdh/hadoop-2.5.0-cdh5.3.6/sbin/stop-dfs.sh'
echo "=================   linux111开始启动jobhistory    ======================="
ssh datamgr@linux111 '/opt/module/cdh/hadoop-2.5.0-cdh5.3.6/sbin/mr-jobhistory-daemon.sh stop historyserver'


echo "=================   start stopping Zookeeper cluster     ======================="
for i in datamgr@linux111 datamgr@linux112 datamgr@linux113
do
	echo "$i zookeeper is stopping"
	ssh $i '/opt/module/cdh/zookeeper-3.4.5-cdh5.3.6/bin/zkServer.sh stop'
	
done
