package com.fsti.flmnt.service.kafka;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Properties;

/** 
 * @{#} EnterMsgProducer.java Create on 2015年6月15日 下午3:34:58
 * <p>
 * 	Reads from Standard Input and creates messages on the local Kafka broker
 *  for flmnt project is:
 *  {"business_type": "1","business_level": 3,"asset_en_name": "good_asset","asset_physical":"AWS_FIN_PAY20150101010201.txt","data_date": "20150615","area": 35,"operate_type": 45,"row_number": 12,"begin_time": "2015-06-15 15:06:33:44","end_time": "2015-06-15 22:34:45:58","version":"V1.0","server_name": "clto113","ftp_account": "admin","file_path": "c:/etc/b/e/f","qualityList": [{"check_type": 2001,"check_object": "678954123","check_result": "30","result_number": 28},{"check_type": 2004,"check_object": "4","check_result": "1","result_number": 28}]}
 *  new:
 *  {"asset_physical":"01_100_1_00_20150624174314_00000.txt.gz","begin_time":"20150624174913","client_address":"10.0.174.68","end_time":"20150624174913","file_path":"/itf/dpi/qixin/yunnan/01_100_1_00_20150624174314_00000.txt.gz","file_size":"9204389","ftp_account":"dpimblqixinyunnan","operate_type":1,"server_name":"10.0.173.138","version":"2.1"}
 * </p>
 * @author <a href="mailto:cwx714@126.com">崔卫翔</a>   
 * @version v 0.1 
*/ 
public class EnterMsgProducer {
	public static void main(String[] args) throws Exception {
		String topic = "snap-shot";//crowd//eltop//rogue//KAFKA_ABILITY_INTERFACE_TOPIC
		String target = "s";

		Properties props = new Properties();
		/*if (args != null && args.length > 0) {
			props.put("metadata.broker.list", args[0] + ":9092");
		} else {
			props.put("metadata.broker.list", "localhost:9092");
		}*/
		props.put("metadata.broker.list", "eloancn703:9092");
		props.put("serializer.class", "kafka.serializer.StringEncoder");
		props.put("request.required.acks", "1");

		ProducerConfig config = new ProducerConfig(props);
		Producer<String, String> producer = new Producer<String, String>(config);
		try {
			System.out.println("Enter messages:");
			BufferedReader br = new BufferedReader(new InputStreamReader(
					System.in));
			String input;
			while ((input = br.readLine()) != null) {
				if (input.equals("quit")) {
					break;
				}
				producer.send(new KeyedMessage<String, String>(topic, target,
						input));
			}
		} finally {
			producer.close();
		}
	}
}
