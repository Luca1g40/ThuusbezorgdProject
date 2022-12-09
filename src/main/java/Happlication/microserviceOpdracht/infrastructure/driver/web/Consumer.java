package Happlication.microserviceOpdracht.infrastructure.driver.web;

import Happlication.microserviceOpdracht.core.application.CommandHandler;
import Happlication.microserviceOpdracht.core.command.ProductCreated;
import Happlication.microserviceOpdracht.core.command.OrderClaimed;
import Happlication.microserviceOpdracht.core.command.OrderDone;
import Happlication.microserviceOpdracht.infrastructure.driver.messaging.event.GenericEvent;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class Consumer {

    private final CommandHandler commandHandler;

    public Consumer(CommandHandler commandHandler) {
        this.commandHandler = commandHandler;
    }

    @RabbitListener(queues = { "order-queue" })
    public void consumeTest(GenericEvent event){
        switch (event.eventKey) {
            case "claimOrder" -> this.commandHandler.handle(new OrderClaimed(event.id));
            case "orderDone" -> this.commandHandler.handle(new OrderDone(event.id));
        }
    }

    @RabbitListener(queues = { "product-queue" })
    public void consumeProduct(ProductCreated productCreated){
        this.commandHandler.handle(
                new ProductCreated(productCreated.id, productCreated.productName,
                        productCreated.productDetails, productCreated.category,
                        true, productCreated.prijs));
    }

}