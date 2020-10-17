package br.com.fiap.batchConsumer;

import br.com.fiap.batchConsumer.config.Configuracao;
import br.com.fiap.batchConsumer.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.*;
import org.springframework.batch.item.amqp.AmqpItemReader;
import org.springframework.batch.item.amqp.builder.AmqpItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.sql.DataSource;
import java.util.List;

@SpringBootApplication
@EnableBatchProcessing
public class BatchConsumerApplication {

	@Autowired
	private EmailService emailService;

	@Value("${amqp.queue}")
	private String queue;

	Logger logger = LoggerFactory.getLogger(BatchConsumerApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(BatchConsumerApplication.class, args);
	}

	@Bean
	public AmqpItemReader<String> itemReader()  {

				RabbitTemplate template = new RabbitTemplate(Configuracao.getConnection());
				template.setDefaultReceiveQueue(this.queue);

				return new AmqpItemReaderBuilder<String>()
				.amqpTemplate(template)
				.build();
	}

	@Bean
	public ItemProcessor<String, String> itemProcessor( AmqpItemReader amqpItemReader){
		while(true) {

			try {
				byte[] body = (byte[]) amqpItemReader.read();
				String mensagem = new String(body);
				emailService.send(mensagem);
				logger.info(mensagem);

			} catch (NullPointerException ex) {
				logger.info("Fila vazia!");
				break;
			}
		}

		return (mensagem) -> mensagem;
	}

	@Bean
	public ItemWriter sendEmail(DataSource dataSource){
		return new ItemWriter() {
			@Override
			public void write(List items) throws Exception {

			}
		};
	}



	@Bean
	public Step step(StepBuilderFactory stepBuilderFactory,
					 ItemReader<String> itemReader,
					 ItemWriter<String> itemWriter,
					 ItemProcessor<String, String> processor,
					 @Qualifier("taskExecutor") ThreadPoolTaskExecutor taskExecutor) {

		return stepBuilderFactory.get("Step Chunk - processar fila rabbitMQ1")
				.<String, String>chunk(1)
				.reader(itemReader)
				.processor(processor)
				.writer(itemWriter)
				.taskExecutor(taskExecutor)
				.build();
	}

	@Bean
	public Job job(JobBuilderFactory jobBuilderFactory,
				   Step step) {
		return jobBuilderFactory.get("Job - Ler fila rabbitMQ1")
				.start(step)
				.build();
	}


}
