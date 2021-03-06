package me.kvn.codes.activemq.jms.topic;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class JMSProducer {
    public static void main(String[] args) throws JMSException {
        //消息目的地
        String destinationurl = "tcp://localhost:61616";
        //队列的名称
        String TopicName = "jms-topic-1";
        //创建连接工厂，通过连接工厂创建连接
        ConnectionFactory connectionFactory  = new ActiveMQConnectionFactory(destinationurl);
        Connection connection = connectionFactory.createConnection();
        //启动连接
        connection.start();
        //创建会话：
        /**
         * 1、通过会话创建队列对象
         * 2、通过会话创建生成者对象
         * 3、通过会话创建消息对象
         */
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //创建队列对象，创建时设置队列名称
        Topic topic = session.createTopic(TopicName);//Queue继承自Destionation
        //根据队列对象创建生产者
        MessageProducer producer = session.createProducer(topic);
        //创建消息对象，可以创建多种类型的消息对象，这里是文件消息对象
        for (int i = 0; i < 100; i++) {
            TextMessage textMessage = session.createTextMessage("Hello JMS -- Topic-->" + i);
            //发送消息
            System.out.println("发送消息："+textMessage.getText());
            producer.send(textMessage);
        }
        //关闭连接
        connection.close();
    }
}
