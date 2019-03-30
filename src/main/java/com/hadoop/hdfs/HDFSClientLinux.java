package com.hadoop.hdfs;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.BlockLocation;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.LocatedFileStatus;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.RemoteIterator;
import org.apache.hadoop.fs.permission.FsPermission;
import org.junit.Test;

/**
 * linux上
 * 
 * HDFSClient操作
 * 8020是namenode节点active状态下的端口号
 * 9000端口：是fileSystem默认的端口号
 */
public class HDFSClientLinux {
	String url = "hdfs://hadoop-tx:9000";
	
	public static void main(String[] args) throws Exception {
		// 1 获取文件系统
		Configuration configuration = new Configuration();
		// 配置在集群上运行
		configuration.set("fs.defaultFS", "hdfs://hadoop-tx:9000");
		FileSystem fileSystem = FileSystem.get(configuration);
		
		// 直接配置访问集群的路径和访问集群的用户名称
//		FileSystem fileSystem = FileSystem.get(new URI("hdfs://hadoop102:9000"),configuration, "admin");
		
		// 2 把本地文件上传到文件系统中
		fileSystem.copyFromLocalFile(new Path("E:\\input\\fruit.tsv"), new Path("/hello1.copy.txt"));
		
		// 3 关闭资源
		fileSystem.close();
		System.out.println("over");
	}

	// 获取文件系统
	@Test
	public void getFileSystem() throws Exception {
		// 0 创建配置信息对象
		Configuration configuration = new Configuration();

		// 1 获取文件系统
		// FileSystem fs = FileSystem.get(new URI("hdfs://hadoop102:8020"), configuration, "pillar");
		FileSystem fs = FileSystem.get(configuration);

		// 2打印文件系统
		System.out.println(fs.toString());

		// 3 关闭资源
		fs.close();
	}

	// 上传文件
	@Test
	public void putFileToHDFS() throws IOException, InterruptedException, URISyntaxException {
		Configuration configuration = new Configuration();

		// 1 获取文件系统 获取配置信息的优先级：代码>根目录下的配置文件>集群中的配置信息
		FileSystem fs = FileSystem.get(new URI("hdfs://hadoop-tx:9000"), configuration, "datamgr");

		// 2 执行上传文件命令
//		fs.copyFromLocalFile(true, new Path("e:\\wc.txt"), new Path("/user/datamgr/input/"));
		fs.copyFromLocalFile(true, new Path("C:\\Users\\Administrator\\Desktop\\fruit.tsv"), new Path("/user/datamgr/input_fruit/fruit.tsv"));
		System.out.println("------");
		// 3 关闭资源
		fs.close();
	}

	// 文件下载
	@Test
	public void getFileFromHDFS() throws Exception {
		// 1 获取文件系统
		Configuration configuration = new Configuration();
		FileSystem fs = FileSystem.get(new URI("hdfs://hadoop-tx:9000"), configuration);

		// 2 执行下载文件命令
		// fs.copyToLocalFile(new Path("/user/datamgr/input/consumer.xml"), new Path("e:/consume1111r.xml"));
		fs.copyToLocalFile(false, new Path("/user/datamgr/input/consumer.xml"), new Path("e:/consume1111r.xml"), true);
		
		// 3 关流
		fs.close();
	}

	// 创建目录
	@Test
	public void mkdirAtHDFS() throws Exception {
		// 1 获取文件系统
		Configuration conf = new Configuration();
		FileSystem fs = FileSystem.get(new URI("hdfs://hadoop-tx:9000"), conf, "datamgr");

		// 2 执行创建文件夹操作
		// fs.mkdirs(new Path("/user/datamgr/hh/"));
//		FsPermission p = new FsPermission((short) 777);
		FsPermission p = new FsPermission("677");

		boolean flag = fs.mkdirs(new Path("/user3/datamgr/input_fruit/"), p);
		System.out.println(flag);
		// 3 关闭资源
		fs.close();
	}

	// 删除文件夹及文件
	@Test
	public void deleteAtHDFS() throws IOException, InterruptedException, URISyntaxException {
		// 1 获取文件系统
		Configuration conf = new Configuration();
		FileSystem fs = FileSystem.get(new URI("hdfs://hadoop-tx:9000"), conf, "datamgr");

		// 2 执行删除操作
//		boolean flag = fs.delete(new Path("/user/datamgr/output/"), true);
		boolean flag = fs.delete(new Path("/user3"), true);
		System.out.println(flag);
		// 3 关闭资源
		fs.close();
	}

	// 更改文件名称
	@Test
	public void renameAtHDFS() throws IOException, InterruptedException, URISyntaxException {
		// 1 获取文件系统
		Configuration conf = new Configuration();
		FileSystem fs = FileSystem.get(new URI("hdfs://hadoop-tx:9000"), conf);

		// 2 执行更改名称操作
		boolean flag = fs.rename(new Path("/user/datamgr/input/test001.txt"), new Path("/user/datamgr/input/test.txt"));
		System.out.println(flag);
		// 3 关闭资源
		fs.close();
	}

	// 查看文件详情
	@Test
	public void readFileAtHDFS() throws IOException, InterruptedException, URISyntaxException {
		// 1 获取文件系统
		Configuration conf = new Configuration();
		FileSystem fs = FileSystem.get(new URI("hdfs://hadoop-tx:9000"), conf);

		// 2 执行查看文件详情操作
		RemoteIterator<LocatedFileStatus> listFiles = fs.listFiles(new Path("/"), true);

		while (listFiles.hasNext()) {
			//LocatedFileStatus
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
			System.out.println("-------------------");
		}
		// 3 关闭资源
		fs.close();
	}

	// 获取文件夹和文件信息
	@Test
	public void readfolderAtHDFS() throws IOException, InterruptedException, URISyntaxException {
		// 1 获取文件系统
		Configuration conf = new Configuration();
		FileSystem fs = FileSystem.get(new URI("hdfs://hadoop-tx:9000"), conf);

		// 2 判断是文件夹还是文件
		FileStatus[] listStatus = fs.listStatus(new Path("/user/datamgr/input/"));

		for (FileStatus status : listStatus) {
			if (status.isFile()) {
				System.out.println("f---" + status.getPath().getName());
			} else {
				System.out.println("d--" + status.getPath().getName());
			}
		}
		// 3 关闭资源
		fs.close();
	}

}
