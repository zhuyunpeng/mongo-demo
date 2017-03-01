package com.zyp.mongo;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

/**
 * MongoClient 工厂类获取MongClient实例，操作数据库
 * @author zyp
 * @since 2017年3月1日-上午9:33:18
 */
public class MongoClientFactory {

	
	/**
	 * 按照默认主机：127.0.0.1，端口：27017-获取MongoClient实例
	 * @author zyp
	 * 2017年3月1日上午9:36:28
	 * @return
	 */
	public static MongoClient getInstanceDefault(){
		return new MongoClient();
	}
	
	/**
	 * 显示指定主机地址获取MongoClient实例
	 * @author zyp
	 * 2017年3月1日上午9:39:16
	 * @param hostname 主机名或者ip地址<br>
	 * 					例如：本机：localhost或者127.0.0.1
	 * @return
	 */
	public static MongoClient getInstanceByHost(String hostname){
		 return new MongoClient(hostname);
	}
	
	/**
	 * 显示指定主机地址及端口，获取MongoClient实例
	 * @author zyp
	 * 2017年3月1日上午9:42:30
	 * @param hostname 主机名
	 * @param port 端口
	 * @return
	 */
	public static MongoClient getInstanceByHostAndPort(String hostname,int port){
		return new MongoClient(hostname, port);
	}
	
	
	/**
	 * 通过MongoClientURI获取MongoClient实例
	 * @author zyp
	 * 2017年3月1日上午9:45:47
	 * @return
	 */
	public static MongoClient getInstanceByMongoClientURI(){
		MongoClientURI connectionString = new MongoClientURI("mongodb://localhost:27017");
		return new MongoClient(connectionString);
	}
	
	
}
