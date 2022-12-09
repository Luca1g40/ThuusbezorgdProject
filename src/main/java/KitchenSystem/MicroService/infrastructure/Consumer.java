package KitchenSystem.MicroService.infrastructure;

import KitchenSystem.MicroService.application.CommandHandler;
import KitchenSystem.MicroService.command.PlaceNewIngredient;
import KitchenSystem.MicroService.command.PlaceOrder;
import KitchenSystem.MicroService.infrastructure.driver.messaging.event.GenericEvent;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class Consumer {

    private final CommandHandler commandHandler;

    public Consumer(CommandHandler commandHandler) {
        this.commandHandler = commandHandler;

    }

    @RabbitListener(queues = { "order-queue" })
    public void consume(GenericEvent event){
        switch (event.eventKey) {
            case "placeOrder" -> {
                this.commandHandler.handle(new PlaceOrder(event.id, event.tableNumber, event.products));
                break;
            }
        }
    }

    @RabbitListener(queues = { "ingredient-queue" })
    public void consumeIngredient(PlaceNewIngredient placeNewIngredient){
        System.out.println(placeNewIngredient.name);
        this.commandHandler.handle(new PlaceNewIngredient(placeNewIngredient.id, placeNewIngredient.name, placeNewIngredient.amount));
    }
}