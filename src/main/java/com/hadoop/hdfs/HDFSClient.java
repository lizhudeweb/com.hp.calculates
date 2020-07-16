package com.hadoop.hdfs;

import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.BlockLocation;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.LocatedFileStatus;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.RemoteIterator;

/**
 * 8020是namenode节点active状态下的端口号;
 * 9000端口：是fileSystem默认的端口号
 */
public class HDFSClient {
	static String url = "hdfs://linux111:9000";

	public static void main(String[] args) throws Exception {
		// 1 获取文件系统
		Configuration configuration = new Configuration();
		// 配置在集群上运行
		configuration.set("fs.defaultFS", "hdfs://linux111:8020");
		
		// FileSystem fileSystem = FileSystem.get(configuration);
		// 直接配置访问集群的路径和访问集群的用户名称
		// 获取文件系统 获取配置信息的优先级：代码>根目录下的配置文件>集群中的配置信息
		FileSystem fileSystem = FileSystem.get(new URI("hdfs://linux111:8020"), configuration, "datamgr");
		
		
		
		//////////////////////////////////////////////////////////////////////
		// 创建文件夹操作
		// fs.mkdirs(new Path("/user/datamgr/hh/"));
//		FsPermission p = new FsPermission((short) 777);
//		FsPermission p = new FsPermission("677");
//		boolean flag = fs.mkdirs(new Path("/user/datamgr/input_fruit/"), p);
		
		
		
		//////////////////////////////////////////////////////////////////////
		//删除文件夹操作
//		boolean flag = fileSystem.delete(new Path("/user/datamgr/input2"), true);
		
		
		
		//////////////////////////////////////////////////////////////////////
		// 本地文件上传到文件系统中
//		fileSystem.copyFromLocalFile(new Path("D:\\input\\one.txt"), new Path("/user/datamgr/input/one.txt"));

		
		
		//////////////////////////////////////////////////////////////////////
		// 下载文件命令
//		fileSystem.copyToLocalFile(new Path("/user/datamgr/input/consumer.xml"), new Path("e:/consume1111r.xml"));
//		fileSystem.copyToLocalFile(false, new Path("/user/datamgr/input/one.txt"), new Path("C:/Users/lizhu/Desktop/one.txt"), true);
		
		
		
		//////////////////////////////////////////////////////////////////////
		//改名
//		boolean flag = fileSystem.rename(new Path("/user/datamgr/input/one.txt"), new Path("/user/datamgr/input/one112.txt"));
		
		
		
		//////////////////////////////////////////////////////////////////////
		// 获取文件夹和文件信息
		// 判断是文件夹还是文件
		FileStatus[] listStatus = fileSystem.listStatus(new Path("/user/datamgr/input/"));
		for (FileStatus status : listStatus) {
			if (status.isFile()) {
				System.out.println("f---" + status.getPath().getName());
			} else {
				System.out.println("d--" + status.getPath().getName());
			}
		}
		
		
		
		//////////////////////////////////////////////////////////////////////
		//查看文件详情
		System.out.println("-------------------");
		RemoteIterator<LocatedFileStatus> listFiles = fileSystem.listFiles(new Path("/"), true);
		while (listFiles.hasNext()) {
			// LocatedFileStatus
			LocatedFileStatus status = listFiles.next();

			// 文件名称
			System.out.println(status.getPath());
			System.out.println(status.getPath().getName());
			// 块的大小
			System.out.println(status.getBlockSize());

			// 文件内容的长度
			System.out.println(status.getLen());

			// 文件权限
			System.out.println(status.getPermission());

			// 文件块的具体信息
			BlockLocation[] blockLocations = status.getBlockLocations();

			for (BlockLocation block : blockLocations) {
				System.out.println(block.getOffset());

				String[] hosts = block.getHosts();
				for (String host : hosts) {
					System.out.println(host);
				}
			}
		}
		System.out.println("-------------------");
		//////////////////////////////////////////////////////////////////////
		
		// 3 关闭资源
		fileSystem.close();
		System.out.println("over");
	}

}
