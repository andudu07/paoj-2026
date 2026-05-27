package com.pao.laboratory11.exercise2;

import com.pao.laboratory11.exercise1.Transaction;

import java.util.*;
import java.util.stream.*;

public class Main {

    record Tx(int id, double amount, String date, String country, String channel, String accountId) {
        Transaction toTransaction() {
            return new Transaction(id, amount, country, channel);
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<Tx> list = new ArrayList<>();

        int n = Integer.parseInt(sc.nextLine().trim());
        for (int i = 0; i < n; i++) {
            String line = sc.nextLine().trim();
            if (line.isEmpty()) { i--; continue; }
            String[] p = line.split("\\s+");
            list.add(new Tx(Integer.parseInt(p[0]), Double.parseDouble(p[1]),
                    p[2], p[3], p[4], p[5]));
        }

        int q = Integer.parseInt(sc.nextLine().trim());
        while (q-- > 0) {
            String line = sc.nextLine().trim();
            if (line.isEmpty()) { q++; continue; }
            String[] parts = line.split("\\s+");

            switch (parts[0]) {
                case "REPORT_MONTH"   -> reportMonth(list, parts[1]);
                case "REPORT_ACCOUNT" -> reportAccount(list, parts[1]);
                case "TOP_CHANNELS"   -> topChannels(list, Integer.parseInt(parts[1]));
            }
        }
    }

    static void reportMonth(List<Tx> list, String month) {
        var stats = list.stream()
                .filter(t -> t.date().startsWith(month))
                .mapToDouble(Tx::amount)
                .summaryStatistics();
        System.out.printf(Locale.US, "MONTH %s total=%.2f count=%d%n",
                month, stats.getCount() == 0 ? 0.0 : stats.getSum(), stats.getCount());
    }

    static void reportAccount(List<Tx> list, String accountId) {
        var stats = list.stream()
                .filter(t -> t.accountId().equals(accountId))
                .mapToDouble(Tx::amount)
                .summaryStatistics();
        System.out.printf(Locale.US, "ACCOUNT %s total=%.2f count=%d%n",
                accountId, stats.getCount() == 0 ? 0.0 : stats.getSum(), stats.getCount());
    }

    static void topChannels(List<Tx> list, int k) {
        if (list.isEmpty()) { System.out.println("NONE"); return; }

        list.stream()
                .collect(Collectors.groupingBy(Tx::channel, Collectors.counting()))
                .entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue(Comparator.reverseOrder())
                        .thenComparing(Map.Entry.comparingByKey()))
                .limit(k)
                .forEach(e -> System.out.println(e.getKey() + " " + e.getValue()));
    }
}