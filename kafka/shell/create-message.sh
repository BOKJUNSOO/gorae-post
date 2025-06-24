topic=$1
/opt/kafka/bin/kafka-console-producer.sh \
  --topic user-info \
  --bootstrap-server kafka:29092
