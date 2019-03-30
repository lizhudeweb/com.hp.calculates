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
ssh datamgr@hadoop-tx '/opt/module/hadoop-2.7.2/sbin/start-dfs.sh'
echo "=================   开始启动yarn     ======================="
ssh datamgr@hadoop-lc '/opt/module/hadoop-2.7.2/sbin/start-yarn.sh'
# echo "=================   hadoop-tx开始启动jobhistory    ======================="
# ssh datamgr@hadoop-tx '/opt/module/hadoop-2.7.2/sbin/mr-jobhistory-daemon.sh start historyserver'

