package it.marcoberri.msMQTesting;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

	@Autowired
	private RabbitTemplate template;

	@Autowired
	private Queue queue;

	@Autowired
	Environment env;

	@GetMapping("/sendMessage")
	public String greeting() {

		String message = "Hello I'm app " + env.getProperty("spring.application.name") + " on "
				+ env.getProperty("server.port");
		MessageModel model = new MessageModel();
		model.setFromApplication(env.getProperty("spring.application.name"));
		model.setPort(env.getProperty("server.port"));

		model.setMessage(message);

		this.template.convertAndSend(queue.getName(), model);
		return "Yup";
	}

	@RabbitListener(queues = "test_1")
	public void receive(@Payload MessageModel in) {
		System.out.println("Received from '" + in.getPort() + "'");
	}

}