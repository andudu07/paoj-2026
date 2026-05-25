package com.pao.proiect.catalog.service;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * AuditService — scrie actiunile sistemului intr-un fisier CSV.
 *
 * Format CSV: action_name,timestamp
 * Exemplu:    ADAUGA_PROFESOR,2024-06-03T10:15:30
 *
 * Thread-safe: metoda log() este synchronized.
 * Singleton: o singura instanta pe durata aplicatiei.
 */
public class AuditService {

    private static final String CSV_PATH = "audit.csv";
    private static final DateTimeFormatter FMT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    private static AuditService instance;

    private AuditService() {
        // Scriem header-ul daca fisierul e gol / nou
        try (PrintWriter pw = new PrintWriter(new FileWriter(CSV_PATH, true))) {
            // append mode — nu suprascriem intre rulari
        } catch (IOException e) {
            System.err.println("[AuditService] Nu pot deschide " + CSV_PATH + ": " + e.getMessage());
        }
    }

    public static synchronized AuditService getInstance() {
        if (instance == null) instance = new AuditService();
        return instance;
    }

    /**
     * Inregistreaza o actiune in audit.csv.
     *
     * @param actionName numele actiunii (ex: "ADAUGA_STUDENT")
     */
    public synchronized void log(String actionName) {
        String timestamp = LocalDateTime.now().format(FMT);
        try (PrintWriter pw = new PrintWriter(new FileWriter(CSV_PATH, true))) {
            pw.println(actionName + "," + timestamp);
        } catch (IOException e) {
            System.err.println("[AuditService] Eroare scriere audit: " + e.getMessage());
        }
    }
}
