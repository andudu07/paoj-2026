package com.pao.laboratory11.exercise1;

import java.util.*;
import java.util.stream.*;

public class Main {

    static final Set<String> HIGH_RISK = Set.of("RU", "NG", "IR", "KP", "SY");
    static final Map<String, Integer> CHANNEL_SCORE = Map.of(
            "WEB", 15, "APP", 10, "CRYPTO", 30, "POS", 5, "ATM", 0
    );


    static int score(Transaction t) {
        int s = CHANNEL_SCORE.getOrDefault(t.channel(), 0);
        s += HIGH_RISK.contains(t.country()) ? 25 : 0;
        double a = t.amount();
        if      (a >= 5000) s += 70;
        else if (a >= 1000) s += 40;
        else if (a >= 500)  s += 20;
        else if (a <= 100)  s += 5;
        return s;
    }

    static String verdict(Transaction t) {
        return score(t) >= 60 ? "FLAG" : "ALLOW";
    }

    static final Comparator<Transaction> BY_RISK =
            Comparator.comparingInt(Main::score).reversed().thenComparingInt(Transaction::id);

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = Integer.parseInt(sc.nextLine().trim());

        List<Transaction> list = new ArrayList<>();
        Map<Integer, Transaction> map = new HashMap<>();

        for (int i = 0; i < n; i++) {
            String[] p = sc.nextLine().trim().split("\\s+");
            var t = new Transaction(Integer.parseInt(p[0]), Double.parseDouble(p[1]), p[3], p[4]);
            list.add(t);
            map.put(t.id(), t);
        }

        int q = Integer.parseInt(sc.nextLine().trim());
        while (q-- > 0) {
            String line = sc.nextLine().trim();

            if (line.startsWith("CHECK ")) {
                int id = Integer.parseInt(line.substring(6));
                var t  = map.get(id);
                if (t == null) System.out.println("CHECK " + id + " => NOT_FOUND");
                else System.out.printf("CHECK %d => %s score=%d%n", id, verdict(t), score(t));

            } else if (line.equals("LIST_FLAGGED")) {
                var flagged = list.stream().filter(t -> score(t) >= 60)
                        .sorted(BY_RISK).toList();
                if (flagged.isEmpty()) System.out.println("NONE");
                else flagged.forEach(t -> System.out.printf("[%d] FLAG score=%d%n", t.id(), score(t)));

            } else if (line.startsWith("TOP_RISK ")) {
                int k = Integer.parseInt(line.substring(9));
                list.stream().sorted(BY_RISK).limit(k)
                        .forEach(t -> System.out.printf("[%d] %s score=%d%n", t.id(), verdict(t), score(t)));

            } else {
                System.out.println("ERR UNKNOWN_COMMAND");
            }
        }
    }
}