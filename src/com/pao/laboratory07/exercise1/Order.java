package com.pao.laboratory07.exercise1;

import com.pao.laboratory07.exercise1.exceptions.CannotCancelFinalOrderException;
import com.pao.laboratory07.exercise1.exceptions.CannotRevertInitialOrderStateException;
import com.pao.laboratory07.exercise1.exceptions.OrderIsAlreadyFinalException;

import java.util.ArrayDeque;
import java.util.Deque;

public class Order {

    private OrderState currentState;
    private final Deque<OrderState> history = new ArrayDeque<>();

    public Order(OrderState initialState) {
        this.currentState = initialState;
    }

    public void nextState() throws OrderIsAlreadyFinalException {
        OrderState next = currentState.next();
        history.push(currentState);
        currentState = next;
        System.out.println("Order state updated to: " + currentState);
    }

    public void cancel() throws CannotCancelFinalOrderException {
        if (currentState.isFinal()) {
            throw new CannotCancelFinalOrderException();
        }
        history.push(currentState);
        currentState = OrderState.CANCELLED;
        System.out.println("Order has been canceled.");
    }

    public void undoState() throws CannotRevertInitialOrderStateException {
        if (history.isEmpty()) {
            throw new CannotRevertInitialOrderStateException();
        }
        currentState = history.pop();
        System.out.println("Order state reverted to: " + currentState);
    }

    public OrderState getCurrentState() {
        return currentState;
    }
}
