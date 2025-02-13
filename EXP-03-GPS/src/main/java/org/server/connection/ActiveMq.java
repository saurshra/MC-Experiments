package org.server.connection;

import java.io.IOException;
import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.ConnectionFactory;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.server.Context;

public class ActiveMq {

    private static Connection CONNECTION;

    private ActiveMq() {
    }

    public static synchronized Connection getConnectionInstance() throws JMSException, IOException {
        if (CONNECTION == null) {
            String brokerUrl = Context.getSystemProperties().getAmp().getUrl();
            ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerUrl);
            CONNECTION = connectionFactory.createConnection();
            CONNECTION.start();
        }
        return CONNECTION;
    }

    public static synchronized void sendMessage(String topic, String message) throws JMSException, IOException {
        Session session = getConnectionInstance().createSession(false, Session.AUTO_ACKNOWLEDGE);
        try {
            Topic createTopic = session.createTopic(topic);
            MessageProducer producer = session.createProducer(createTopic);
            producer.setDeliveryMode(DeliveryMode.PERSISTENT);
            TextMessage txtMessage = session.createTextMessage(message);
            producer.send(txtMe
