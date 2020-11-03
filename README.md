# batch-consumer

### Configurar arquivo application.yml
spring.datasource:  
 url -> URL destino do banco de dados (necessario para execução do spring batch)  
 username -> usuario do banco de dados  
 password -> Senha do banco de daods  

mail:  
host -> host do servidor smtp para envio do email  
username -> usuario do banco de dados  
password -> Senha do banco de daods  
to -> email que ira recerber os dados de coleto do Drone  
from -> e-mail que sera informado como remetente  
  
amqp:  
queue - Nome da fila que sera consumida cloudAMQ
  
 
 
### Configuracao.java dentro do pacote config
  
Preenchender com os dados de conexão do cloudAMQ.  
-Username  
-Password  
-VirtualHost  


### BatchConsumerApplication

Utilizar a annotation @Scheduled para determinar intervalo de tempo que a fila deve ser lida       
