 /*****************************
 * Copyright (c) 2015 by China Youke Communication Co. Ltd.  All rights reserved.
 ****************************/
package com.fsti.flmnt.service.kafka;

import java.util.Properties;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

/** 
 * @{#} AbilityKafkaProducer.java Create on 2015年6月15日 下午3:31:05
 * <p>
 * 	
 * </p>
 * @author <a href="mailto:cwx714@126.com">崔卫翔</a>   
 * @version v 0.1 
 */
public class MonitorKafkaProducer {
	private final Producer<String, String> producer;
    public final static String TOPIC = "KAFKA_ABILITY_INTERFACE_TOPIC";
    public final static String BROKER_LIST = "eloancn703:9092,eloancn704:9092,eloancn705:9092";
 
    private MonitorKafkaProducer(){
        Properties props = new Properties();
        //此处配置的是kafka的端口
        props.put("metadata.broker.list", BROKER_LIST);
        //配置value的序列化类
        props.put("serializer.class", "kafka.serializer.StringEncoder");
        //配置key的序列化类
        props.put("key.serializer.class", "kafka.serializer.StringEncoder");
 
        //request.required.acks
        //0, which means that the producer never waits for an acknowledgement from the broker (the same behavior as 0.7). This option provides the lowest latency but the weakest durability guarantees (some data will be lost when a server fails).
        //1, which means that the producer gets an acknowledgement after the leader replica has received the data. This option provides better durability as the client waits until the server acknowledges the request as successful (only messages that were written to the now-dead leader but not yet replicated will be lost).
        //-1, which means that the producer gets an acknowledgement after all in-sync replicas have received the data. This option provides the best durability, we guarantee that no messages will be lost as long as at least one in sync replica remains.
        props.put("request.required.acks","-1");
 
        producer = new Producer<String, String>(new ProducerConfig(props));
    }
 
    void produce() {
    	String ability1 = "{\"business_type\": \"1\",\"business_level\": 3,\"asset_en_name\": \"good_asset1\","
    			+ "\"asset_physical\":\"AWS_FIN_PAY20150714010107.txt\",\"data_date\": \"20151010\","
    			+ "\"area\": 33,\"operate_type\": 43,\"row_number\": 12,"
    			+ "\"begin_time\": \"2015-06-15 15:06:31:22\",\"end_time\": \"2015-06-15 15:06:34:55\","
    			+ "\"version\":\"V1.0\",\"server_name\": \"clto121\","
    			+ "\"ftp_account\": \"admin\",\"file_path\": \"c:/etc/a/w/s/\",\"qualityList\": "
    			+ "[{\"check_type\": 2001,\"check_object\": \"1\",\"check_result\": \"314256789\",\"result_number\": 23},"
    			+ "{\"check_type\": 2004,\"check_object\": \"1\",\"check_result\": \"2\",\"result_number\": 13}]}";
    	String ability2 = "{\"business_type\": \"1\",\"business_level\": 3,\"asset_en_name\": \"good_asset2\","
    			+ "\"asset_physical\":\"AWS_FIN_PAY20150714010107.txt\",\"data_date\": \"20151010\","
    			+ "\"area\": 33,\"operate_type\": 43,\"row_number\": 12,"
    			+ "\"begin_time\": \"2015-06-16 15:06:31:22\",\"end_time\": \"2015-06-16 15:06:34:55\","
    			+ "\"version\":\"V1.0\",\"server_name\": \"clto717\","
    			+ "\"ftp_account\": \"admin\",\"file_path\": \"c:/etc/q/w/e/\",\"qualityList\": "
    			+ "[{\"check_type\": 2001,\"check_object\": \"1\",\"check_result\": \"123498765\",\"result_number\": 22},"
    			+ "{\"check_type\": 2004,\"check_object\": \"1\",\"check_result\": \"3\",\"result_number\": 12}]}";
    	String ability3 = "{\"business_type\": \"1\",\"business_level\": 3,\"asset_en_name\": \"good_asset3\","
    			+ "\"asset_physical\":\"AWS_FIN_PAY20150714010107.txt\",\"data_date\": \"20151010\","
    			+ "\"area\": 33,\"operate_type\": 43,\"row_number\": 12,"
    			+ "\"begin_time\": \"2015-06-17 15:06:31:22\",\"end_time\": \"2015-06-17 15:06:34:55\","
    			+ "\"version\":\"V1.0\",\"server_name\": \"clto714\","
    			+ "\"ftp_account\": \"admin\",\"file_path\": \"c:/etc/z/x/c/\",\"qualityList\": "
    			+ "[{\"check_type\": 2001,\"check_object\": \"1\",\"check_result\": \"654987321\",\"result_number\": 21},"
    			+ "{\"check_type\": 2004,\"check_object\": \"1\",\"check_result\": \"4\",\"result_number\": 11}]}";
    	for (int i = 1; i < 10; i++) {
    		String key = String.valueOf(i);
    		producer.send(new KeyedMessage<String, String>(TOPIC, key, ability1));
		}
    	for (int i = 10; i < 20; i++) {
    		String key = String.valueOf(i);
    		producer.send(new KeyedMessage<String, String>(TOPIC, key, ability2));
		}
    	for (int i = 20; i <= 30; i++) {
    		String key = String.valueOf(i);
    		producer.send(new KeyedMessage<String, String>(TOPIC, key, ability3));
		}
    }
 
	public static void main(String[] args) {
		new MonitorKafkaProducer().produce();
	}
}
