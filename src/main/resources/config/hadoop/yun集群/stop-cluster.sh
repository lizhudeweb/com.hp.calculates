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
ssh datamgr@hadoop-lc '/opt/module/hadoop-2.7.2/sbin/stop-yarn.sh'
echo "=================   开始关闭HDFS     ======================="
ssh datamgr@hadoop-tx '/opt/module/hadoop-2.7.2/sbin/stop-dfs.sh'
# echo "=================   hadoop-tx开始启动jobhistory    ======================="
# ssh datamgr@hadoop-tx '/opt/module/hadoop-2.7.2/sbin/mr-jobhistory-daemon.sh start historyserver'
