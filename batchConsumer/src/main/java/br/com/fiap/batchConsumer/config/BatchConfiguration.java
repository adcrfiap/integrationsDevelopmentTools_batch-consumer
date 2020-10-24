package br.com.fiap.batchConsumer.config;

import br.com.fiap.batchConsumer.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

    Logger logger = LoggerFactory.getLogger(BatchConfiguration.class);

    @Autowired
    private JobBuilderFactory jobs;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private EmailService emailService;

    @Value("${amqp.queue}")
    private String queue;


    @Bean
    public Tasklet readQueue(){

        return (contribution, chunkContext) -> {
            RabbitTemplate template = new RabbitTemplate(Configuracao.getConnection());

            while(true) {
                try {
                    byte[] body = template.receive(this.queue).getBody();
                    String mensagem = new String(body);
                    emailService.send(mensagem);
                    logger.info(mensagem);

                } catch (NullPointerException ex) {
                    logger.info("Fila vazia!");
                    return RepeatStatus.FINISHED;
                }
            }

        };
    }

    @Bean
    public Step step(StepBuilderFactory stepBuilderFactory,
                     Tasklet tasklet) {

        return stepBuilderFactory.get("Step tasklet - processar fila rabbitMQ1")
                    .tasklet(tasklet)
                    .allowStartIfComplete(true)
                    .build();
    }

    @Bean
    public Job job(JobBuilderFactory jobBuilderFactory,Step step) {
        String jobName = "Job - Ler fila rabbitMQ1";
        LocalDateTime ld = LocalDateTime.now();

        return jobBuilderFactory.get(jobName+"_"+ld.toString())
                .incrementer( new RunIdIncrementer())
                .start(step)
                .build();
    }
}
