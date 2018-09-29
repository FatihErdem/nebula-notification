package com.nebula.notification.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;
import org.springframework.messaging.handler.annotation.support.MessageHandlerMethodFactory;

@Configuration
public class RabbitConfig implements RabbitListenerConfigurer {

	public static final String EMAIL_QUEUE = "email-queue";

	private static final String QUEUE_DEAD_EMAIL = "dead-email-queue";

	private static final String EXCHANGE_EMAIL = "email-exchange";

	@Bean("emailQueue")
	Queue emailQueue() {

		return QueueBuilder
				.durable(EMAIL_QUEUE)
				.withArgument("x-dead-letter-exchange", "")
				.withArgument("x-dead-letter-routing-key", QUEUE_DEAD_EMAIL)
				.withArgument("x-message-ttl", 60000)
				.build();
	}

	@Bean("deadLetterQueueEmail")
	Queue deadLetterQueueEmail() {
		return QueueBuilder.durable(QUEUE_DEAD_EMAIL).build();
	}

	@Bean("emailExchange")
	Exchange emailExchange() {
		return ExchangeBuilder.topicExchange(EXCHANGE_EMAIL).build();
	}

	@Bean("emailBinding")
	Binding emailBinding(Queue emailQueue, TopicExchange emailExchange) {
		return BindingBuilder.bind(emailQueue).to(emailExchange).with(EXCHANGE_EMAIL);
	}

	@Bean
	MessageHandlerMethodFactory messageHandlerMethodFactory() {
		DefaultMessageHandlerMethodFactory messageHandlerMethodFactory = new DefaultMessageHandlerMethodFactory();
		messageHandlerMethodFactory.setMessageConverter(consumerJackson2MessageConverter());
		return messageHandlerMethodFactory;
	}

	@Override
	public void configureRabbitListeners(RabbitListenerEndpointRegistrar registrar) {
		registrar.setMessageHandlerMethodFactory(messageHandlerMethodFactory());
	}

	@Bean
	public MappingJackson2MessageConverter consumerJackson2MessageConverter() {
		return new MappingJackson2MessageConverter();
	}

}