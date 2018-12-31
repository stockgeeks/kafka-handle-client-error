# Handling and avoiding client errors

This project demonstrate how to avoid and if necessary handle errors processing messages with kafka clients.

# Running the example

To run the example start the infra-structure using the docker-compose file.

`docker-compose up -d`

You can than start the spring boot applications in each module from your IDE or as normal spring-boot jar applications. Each module is explained in details below.

# Avro clients

The project module `avro-clients` demonstrate how much avro adds value when using kafka. It avoids silly mistakes and many of the possible serialization / deserialization errors that can happen when using kakfa clients.

It exposes 2 endpoint that enable us to produce messages to a kafka topic and clients that process those messages.

### Running Instructions

Start the infra-structure and the spring boot application under the avro-clients module, then:

1. Send a valid message that will produce to a kafka topic and watch the logs of the application to validate the producer / consumer flow.
2. Now send a message that will throw a `RuntimeException` when processed. One will notice that the offset is properly commited and there will be NO automatic attempt to reprocess the message that causes the runtime exception, this in general is a good thing as it doesn't hand the consumer trying to reprocess the message 'forever' but in some cases you may want to add a "dead letter queue" or save it to some permanent storage in order to be able to later check the problem with the message.
3. Now try to send a message with an invalid payload, you'll notice that because we're using Avro the producer will give an error avoiding the invalid message to ever be produced to the topic.
4. Finally try now using a different producer to send a completely invalid message to the topic, the difference between this producer and the previous one is that this one is "valid" to the producer but as you will notice avro will still block the message on the producer side and throw an error saying it's payload compared to a previous registered message schema for that topic.

#Conclusion

The best and most straightforward approach to avoid invalid messages is to use apache avro schemas when using kafka, strongly recommended as it really adds a lot of value avoiding that we need to add all kinds of error handling on the client side that makes our code much more complex and hence increasing the chance to add bugs.
