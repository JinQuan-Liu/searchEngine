package com.stark.searchengine.connect;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.util.Properties;

/**
 * TransportClient方式创建ES连接，不推荐，弃用
 */
@Component
public class ElasticSearchClient {
	Logger log = LoggerFactory.getLogger(ElasticSearchClient.class);
	public static TransportClient transportClient = null;

	public TransportClient getClientInstance() {
		try {
			if (transportClient == null) {
				InputStream inputStream=this.getClass().getClassLoader()
						.getResourceAsStream("application.properties");

				Properties properties=new Properties();
				properties.load(inputStream);
				// 设置集群名称ABEL,Settings设置es的集群名称,使用的设计模式，链式设计模式、build设计模式。
				Settings settings = Settings.builder().put("cluster.name", properties.getProperty("es.clusterName")).build();
				// 读取es集群中的数据,创建client。
				transportClient = new PreBuiltTransportClient(settings).addTransportAddresses(
						// 用java访问ES用的端口是9300。es的9200是restful的请求端口号
						// 由于我使用的是伪集群,所以就配置了一台机器,如果是集群方式,将竞选主节点的加进来即可。
						// new InetSocketTransportAddress(InetAddress.getByName("192.168.110.133"),
						// 9300),
						// new InetSocketTransportAddress(InetAddress.getByName("192.168.110.133"),
						// 9300),
						new TransportAddress(InetAddress.getByName(properties.getProperty("es.address")), Integer.parseInt(properties.getProperty("es.host"))));
				return transportClient;
			} else {
				return transportClient;
			}

		} catch (IOException e) {
			e.printStackTrace();
			closeClient();
			return null;
		}
	}

	public void closeClient() {
		/* 关闭连接 */
		if (null != transportClient) {
			try {
				transportClient.close();
				log.info("close es client");
			} catch (Exception e) {
			}
		}
	}
}
