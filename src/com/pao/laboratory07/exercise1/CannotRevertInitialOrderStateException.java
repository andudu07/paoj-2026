package com.pao.laboratory07.exercise1.exceptions;

public class CannotRevertInitialOrderStateException extends Exception {
    public CannotRevertInitialOrderStateException() {
        super("Cannot undo: already at the initial order state.");
    }
}
