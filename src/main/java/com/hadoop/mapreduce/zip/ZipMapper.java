package com.hadoop.mapreduce.zip;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.compress.CompressionCodec;
import org.apache.hadoop.io.compress.CompressionCodecFactory;
import org.apache.hadoop.io.compress.CompressionInputStream;
import org.apache.hadoop.io.compress.CompressionOutputStream;
import org.apache.hadoop.util.ReflectionUtils;

public class ZipMapper {
	
	public static void main(String[] args) throws Exception {
		//压缩文件
		//compressFile("e:/fortest/inputlog/web.txt","org.apache.hadoop.io.compress.GzipCodec");
		decompressFile("e:/fortest/inputlog/web.txt.gz");
		
	}
	
	//压缩文件
	@SuppressWarnings("unchecked")
	public static void compressFile(String filename,String compress_class) throws Exception{
		//获取需要压缩文件的输入流
		FileInputStream in=new FileInputStream(filename);
		
		//创建编解码对象
		Class clazz = Class.forName(compress_class);
		CompressionCodec codec = (CompressionCodec) ReflectionUtils.newInstance(clazz,new Configuration());
		
		//用编解码对象创建压缩输出流
		FileOutputStream out=new FileOutputStream(filename+codec.getDefaultExtension());
		CompressionOutputStream com_out = codec.createOutputStream(out);
		
		//流的拷贝
		IOUtils.copyBytes(in, com_out, 1024*1024*5, true);
		IOUtils.closeStream(out);
		
	}
	
	
	//解压缩文件
	public static void decompressFile(String filename) throws Exception{
		//创建编解码工厂对象
		CompressionCodecFactory factory=new CompressionCodecFactory(new Configuration());
		
		//用工厂对象获取相应压缩文件的编解码对象
		CompressionCodec codec = factory.getCodec(new Path(filename));
		
		//用编解码对象获取压缩文件的压缩输入流
		CompressionInputStream in = codec.createInputStream(new FileInputStream(filename));
		
		//创建解压文件的输出流对象
		FileOutputStream out=new FileOutputStream(filename+".decode");
		
		//流的拷贝
		IOUtils.copyBytes(in, out, 1024*1024*5, true);
		
		
		
	}

}
