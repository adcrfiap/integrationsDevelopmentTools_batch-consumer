package br.com.fiap.batchConsumer.config;


import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;

public class Configuracao {
    private static CachingConnectionFactory connectionFactory;

    public static CachingConnectionFactory getConnection() {

        if (connectionFactory == null) {
            connectionFactory = new CachingConnectionFactory("jackal-01.rmq.cloudamqp.com");//TODO add hostname
            connectionFactory.setUsername("username");//TODO add username
            connectionFactory.setPassword("password");//TODO add password
            connectionFactory.setVirtualHost("virtualHost");//TODO add virtualhost

            //Recommended settings
            connectionFactory.setRequestedHeartBeat(30);
            connectionFactory.setConnectionTimeout(30000);
        }

        return connectionFactory;
    }
}
