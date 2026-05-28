package com.pao.laboratory13.exercise1;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    // State constants
    private static final int INIT = 0;
    private static final int AUTH = 1;
    private static final int OPEN = 2;
    private static final int CLOSED = 3;

    private int state = INIT;
    private int historyCount = 0;

    public static void main(String[] args) {
        new Main().run();
    }

    private void run() {
        Scanner scanner = new Scanner(System.in);
        List<String> lines = new ArrayList<>();

        // Read all non‑empty lines
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            if (!line.isEmpty()) {
                lines.add(line);
            }
        }
        scanner.close();

        if (lines.isEmpty()) return;
        int q = Integer.parseInt(lines.get(0));
        for (int i = 1; i < 1 + q && i < lines.size(); i++) {
            String command = lines.get(i);
            System.out.println(process(command));
        }
    }

    private String process(String commandLine) {
        String[] tokens = commandLine.split("\\s+");
        if (tokens.length == 0) return ""; // shouldn't happen

        String cmd = tokens[0];

        // --- Unknown command ---
        switch (cmd) {
            case "AUTH": return handleAuth(tokens);
            case "OPEN": return handleOpen(tokens);
            case "SEND": return handleSend(commandLine, tokens);
            case "BROADCAST": return handleBroadcast(commandLine, tokens);
            case "HISTORY": return handleHistory(tokens);
            case "CLOSE": return handleClose(tokens);
            default: return "ERR E_PARSE UNKNOWN_COMMAND";
        }
    }

    // ---- Command handlers ----

    private String handleAuth(String[] tokens) {
        // AUTH needs exactly 2 tokens
        if (tokens.length != 2) {
            return "ERR E_PARSE AUTH";
        }
        // State check: any state except CLOSED is allowed
        if (state == CLOSED) {
            return "ERR E_STATE CLOSED";
        }
        // Re‑auth: reset history and go to AUTH
        historyCount = 0;
        state = AUTH;
        return "OK AUTH user=" + tokens[1];
    }

    private String handleOpen(String[] tokens) {
        if (tokens.length > 1) {
            return "ERR E_PARSE OPEN";
        }
        if (state == CLOSED) {
            return "ERR E_STATE CLOSED";
        }
        if (state == OPEN) {
            return "ERR E_STATE ALREADY_OPEN";
        }
        if (state != AUTH) { // INIT or anything else
            return "ERR E_STATE NOT_OPEN";
        }
        state = OPEN;
        return "OK OPEN";
    }

    private String handleSend(String commandLine, String[] tokens) {
        // Need at least command + payload
        if (tokens.length < 2) {
            return "ERR E_PARSE SEND";
        }
        if (state == CLOSED) {
            return "ERR E_STATE CLOSED";
        }
        if (state != OPEN) {
            return "ERR E_STATE NOT_OPEN";
        }
        historyCount++;
        return "OK OPEN sent";
    }

    private String handleBroadcast(String commandLine, String[] tokens) {
        if (tokens.length < 2) {
            return "ERR E_PARSE BROADCAST";
        }
        if (state == CLOSED) {
            return "ERR E_STATE CLOSED";
        }
        if (state != OPEN) {
            return "ERR E_STATE NOT_OPEN";
        }
        historyCount++;
        return "OK OPEN broadcast";
    }

    private String handleHistory(String[] tokens) {
        if (tokens.length > 1) {
            return "ERR E_PARSE HISTORY";
        }
        if (state == CLOSED) {
            return "ERR E_STATE CLOSED";
        }
        if (state != OPEN) {
            return "ERR E_STATE NOT_OPEN";
        }
        return "OK OPEN history=" + historyCount;
    }

    private String handleClose(String[] tokens) {
        if (tokens.length > 1) {
            return "ERR E_PARSE CLOSE";
        }
        if (state == CLOSED) {
            return "ERR E_STATE CLOSED";
        }
        if (state != OPEN) {
            return "ERR E_STATE NOT_OPEN";
        }
        state = CLOSED;
        return "OK CLOSED";
    }
}