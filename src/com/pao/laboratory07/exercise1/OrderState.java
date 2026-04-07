package com.pao.laboratory07.exercise1;

import com.pao.laboratory07.exercise1.exceptions.OrderIsAlreadyFinalException;

public enum OrderState {

    PLACED {
        @Override
        public OrderState next() throws OrderIsAlreadyFinalException {
            return PROCESSED;
        }

        @Override
        public boolean isFinal() {
            return false;
        }
    },

    PROCESSED {
        @Override
        public OrderState next() throws OrderIsAlreadyFinalException {
            return SHIPPED;
        }

        @Override
        public boolean isFinal() {
            return false;
        }
    },

    SHIPPED {
        @Override
        public OrderState next() throws OrderIsAlreadyFinalException {
            return DELIVERED;
        }

        @Override
        public boolean isFinal() {
            return false;
        }
    },

    DELIVERED {
        @Override
        public OrderState next() throws OrderIsAlreadyFinalException {
            throw new OrderIsAlreadyFinalException();
        }

        @Override
        public boolean isFinal() {
            return true;
        }
    },

    CANCELLED {
        @Override
        public OrderState next() throws OrderIsAlreadyFinalException {
            throw new OrderIsAlreadyFinalException();
        }

        @Override
        public boolean isFinal() {
            return true;
        }
    };

    /**
     * Returns the next state in the lifecycle.
     * Throws OrderIsAlreadyFinalException if this is a terminal state.
     */
    public abstract OrderState next() throws OrderIsAlreadyFinalException;

    /**
     * Returns true if this state is terminal (no further transitions allowed).
     */
    public abstract boolean isFinal();
}
