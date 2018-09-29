package com.nebula.notification.consumer;

import com.nebula.notification.config.RabbitConfig;
import com.nebula.notification.model.EmailReviewDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class EmailConsumer {

	@RabbitListener(queues = RabbitConfig.EMAIL_QUEUE)
	public void consumeEmail(EmailReviewDto dto) {
		log.info(dto.toString());
	}

}
