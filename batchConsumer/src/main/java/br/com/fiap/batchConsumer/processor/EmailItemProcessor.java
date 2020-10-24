package br.com.fiap.batchConsumer.processor;

import br.com.fiap.batchConsumer.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.amqp.AmqpItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

public class EmailItemProcessor implements ItemProcessor<String, String> {

    Logger logger = LoggerFactory.getLogger(EmailItemProcessor.class);

//    @Autowired
//    private EmailService emailService;
//
//    @Autowired
//    private AmqpItemReader<String> amqpItemReader;

    @Bean
    @Override
    public String process(String string) throws Exception {

        logger.info("processamento");
//        String mensagem = null;
//        while(true) {
//            try {
//                mensagem = amqpItemReader.read();
//                emailService.send(mensagem);
//                logger.info(mensagem);
//            } catch (NullPointerException ex) {
//                logger.info("Fila vazia!");
//                break;
//            }
//        }
//
//        return mensagem;
        return "testando ....";
    }
}
