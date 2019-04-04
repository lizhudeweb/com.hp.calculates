#!/bin/bash
# cat /etc/profle >> .bashr
# echo "=================   开始关闭所有节点服务     ======================="
# echo "=================   开始关闭Zookeeper     ======================="
# for i in datamgr@hadoop-tx datamgr@hadoop-lc datamgr@hadoop-al
# do
	# echo "================= $i  开始关闭  ======================="
	# ssh $i '/opt/module/zookeeper-3.4.10/bin/zkServer.sh stop'
# done


echo "=================   开始关闭yarn     ======================="
sh /opt/module/hadoop-2.7.2/sbin/mr-jobhistory-daemon.sh stop historyserver
sh /opt/module/hadoop-2.7.2/sbin/yarn-daemon.sh stop nodemanager
sh /opt/module/hadoop-2.7.2/sbin/yarn-daemon.sh stop resourcemanager
sh /opt/module/hadoop-2.7.2/sbin/hadoop-daemon.sh stop datanode
sh /opt/module/hadoop-2.7.2/sbin/hadoop-daemon.sh stop namenode