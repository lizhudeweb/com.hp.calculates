#!/bin/bash
# cat /etc/profle >> .bashr
# echo "=================   开始启动所有节点服务     ======================="
# echo "=================   开始启动Zookeeper     ======================="
# for i in datamgr@hadoop-tx datamgr@hadoop-lc datamgr@hadoop-al
# do
	# echo "================= $i  开始启动  ======================="
	# ssh $i '/opt/module/zookeeper-3.4.10/bin/zkServer.sh start'
# done


echo "=================   开始启动HDFS     ======================="
sh /opt/module/hadoop-2.7.2/sbin/hadoop-daemon.sh start namenode
sh /opt/module/hadoop-2.7.2/sbin/hadoop-daemon.sh start datanode
sh /opt/module/hadoop-2.7.2/sbin/yarn-daemon.sh start resourcemanager
sh /opt/module/hadoop-2.7.2/sbin/yarn-daemon.sh start nodemanager
# echo "=================   hadoop-tx开始启动jobhistory    ======================="
# ssh datamgr@hadoop-tx '/opt/module/hadoop-2.7.2/sbin/mr-jobhistory-daemon.sh start historyserver'

