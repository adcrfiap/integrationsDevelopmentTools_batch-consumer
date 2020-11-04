# batch-consumer

### Configurar arquivo application.yml

**spring.datasource:**
 - url -> URL destino do banco de dados (necessario para execução do spring batch)  
 - username -> usuario do banco de dados  
 - password -> Senha do banco de daods  

**mail:**
- host -> host do servidor smtp para envio do email  
- username -> usuario smtp  
- password -> Senha do smtp   
- to -> email que ira recerber os dados de coleto do Drone  
- from -> e-mail que sera informado como remetente  
  
**amqp:**
- queue - Nome da fila que sera consumida cloudAMQ
  
### Configuracao.java dentro do pacote config
  
**Dados de conexão do cloudAMQ:**
- Username -> Usuário de conexão
- Password -> senha de conexão
- VirtualHost -> nome do virtual host


### BatchConsumerApplication

Utilizar a annotation @Scheduled para determinar intervalo de tempo que a fila deve ser lida       
