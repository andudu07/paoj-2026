package com.pao.laboratory09.exercise2;

import com.pao.laboratory09.exercise1.TipTranzactie;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.*;

public class Main {
    private static final String OUTPUT_FILE = "output/lab09_ex2.bin";
    private static final int RECORD_SIZE = 32;

    private static final byte PENDING   = 0;
    private static final byte PROCESSED = 1;
    private static final byte REJECTED  = 2;

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        int n = Integer.parseInt(scanner.nextLine().trim());

        int[]           ids   = new int[n];
        double[]        sumas = new double[n];
        String[]        datas = new String[n];
        TipTranzactie[] tips  = new TipTranzactie[n];

        for (int i = 0; i < n; i++) {
            String[] parts = scanner.nextLine().trim().split("\\s+");
            ids[i]   = Integer.parseInt(parts[0]);
            sumas[i] = Double.parseDouble(parts[1]);
            datas[i] = parts[2];
            tips[i]  = TipTranzactie.valueOf(parts[3]);
        }

        new File("output").mkdirs();

        try (DataOutputStream dos = new DataOutputStream(
                new FileOutputStream(OUTPUT_FILE))) {
            for (int i = 0; i < n; i++) {
                byte[] idBytes = ByteBuffer.allocate(4)
                        .order(ByteOrder.LITTLE_ENDIAN)
                        .putInt(ids[i])
                        .array();
                dos.write(idBytes);

                byte[] sumaBytes = ByteBuffer.allocate(8)
                        .order(ByteOrder.LITTLE_ENDIAN)
                        .putDouble(sumas[i])
                        .array();
                dos.write(sumaBytes);

                byte[] dataBytes = new byte[10];
                Arrays.fill(dataBytes, (byte) ' ');
                byte[] raw = datas[i].getBytes("ASCII");
                System.arraycopy(raw, 0, dataBytes, 0, Math.min(raw.length, 10));
                dos.write(dataBytes);

                dos.write(tips[i] == TipTranzactie.CREDIT ? 0 : 1);

                dos.write(PENDING);

                dos.write(new byte[8]);
            }
        }

        try (RandomAccessFile raf = new RandomAccessFile(OUTPUT_FILE, "rw")) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (line.isEmpty()) continue;

                if (line.startsWith("READ ")) {
                    int idx = Integer.parseInt(line.substring(5).trim());
                    System.out.println(readRecord(raf, idx));

                } else if (line.startsWith("UPDATE ")) {
                    String[] parts = line.substring(7).trim().split("\\s+");
                    int idx = Integer.parseInt(parts[0]);
                    String statusStr = parts[1];
                    byte statusByte = statusFromString(statusStr);

                    raf.seek((long) idx * RECORD_SIZE + 23);
                    raf.write(statusByte);

                    System.out.println("Updated [" + idx + "]: " + statusStr);

                } else if (line.equals("PRINT_ALL")) {
                    long fileSize = raf.length();
                    int count = (int) (fileSize / RECORD_SIZE);
                    for (int i = 0; i < count; i++) {
                        System.out.println(readRecord(raf, i));
                    }
                }
            }
        }

        scanner.close();
    }

    private static String readRecord(RandomAccessFile raf, int idx) throws IOException {
        raf.seek((long) idx * RECORD_SIZE);
        byte[] bytes = new byte[RECORD_SIZE];
        raf.readFully(bytes);

        ByteBuffer buf = ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN);

        int    id     = buf.getInt();        // bytes 0-3
        double suma   = buf.getDouble();     // bytes 4-11

        byte[] dataBytes = new byte[10];
        buf.get(dataBytes);
        String data = new String(dataBytes, "ASCII").trim();

        byte tipByte    = buf.get();         // byte 22
        byte statusByte = buf.get();         // byte 23

        String tip    = tipByte == 0 ? "CREDIT" : "DEBIT";
        String status = statusToString(statusByte);

        return String.format(Locale.US, "[%d] id=%d data=%s tip=%s suma=%.2f RON status=%s",
                idx, id, data, tip, suma, status);
    }

    private static String statusToString(byte b) {
        switch (b) {
            case 0:  return "PENDING";
            case 1:  return "PROCESSED";
            case 2:  return "REJECTED";
            default: return "UNKNOWN";
        }
    }

    private static byte statusFromString(String s) {
        switch (s) {
            case "PENDING":   return PENDING;
            case "PROCESSED": return PROCESSED;
            case "REJECTED":  return REJECTED;
            default: throw new IllegalArgumentException("Unknown status: " + s);
        }
    }
}