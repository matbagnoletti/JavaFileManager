package org.tpsit;

import java.io.*;

/**
 * JavaFileManager
 * @author Matteo Bagnoletti Tini
 * @version 1.0.0
 * @since 1.0.0
 *
 * Classe che gestisce i file in Java. Permette di leggere e scrivere dati tipizzati e righe di testo. Se non diversamente impostato, stampa eventuali avvisi sotto forma di errori.
 */
public class JavaFileManager {
    /**
     * Variabile che indica se stampare o meno gli avvisi sotto forma di errori.
     */
    private boolean mostraAvvisi = true;
    /**
     * File da gestire.
     * Sfrutta la classe File di Java per verificarne l'esistenza e per leggerne le proprietà.
     */
    private File fileDaGestire;
    /**
     * Costruttore di JavaFileManager.
     * Versione di default.
     */
    public JavaFileManager() {
        this.fileDaGestire = null;
        System.err.println("Non è stato inserito alcun file da gestire all'interno del JFM creato.");
    }
    /**
     * Costruttore di JavaFileManager.
     * @param mostraAvvisi Variabile che indica se stampare o meno gli avvisi sotto forma di errori.
     */
    public JavaFileManager(boolean mostraAvvisi) {
        this.mostraAvvisi = mostraAvvisi;
        this.fileDaGestire = null;
        if(this.mostraAvvisi) System.err.println("Non è stato inserito alcun file da gestire all'interno del JFM creato.");
    }
    /**
     * Costruttore di JavaFileManager.
     * @param URL Percorso dalla root del progetto del file da gestire.
     */
    public JavaFileManager(String URL) {
        this.fileDaGestire = new File(URL);
        if (!this.fileDaGestire.exists() || !this.fileDaGestire.isFile()) {
            System.err.println("Il file inserito non esiste. Inserire URL assoluto dalla root del progetto.");
            this.fileDaGestire = null;
        }
    }
    /**
     * Costruttore di JavaFileManager.
     * @param mostraAvvisi Variabile che indica se stampare o meno gli avvisi sotto forma di errori.
     * @param URL Percorso dalla root del progetto del file da gestire.
     */
    public JavaFileManager(boolean mostraAvvisi, String URL) {
        this.mostraAvvisi = mostraAvvisi;
        this.fileDaGestire = new File(URL);
        if ((!this.fileDaGestire.exists() || !this.fileDaGestire.isFile()) && this.mostraAvvisi) {
            System.err.println("Il file inserito non esiste. Inserire un URL assoluto dalla root del progetto.");
            this.fileDaGestire = null;
        }
    }
    /**
     * Metodo che permette di leggere l'intero contenuto del file sotto forma di String.
     * @return Stringa contenente l'intero contenuto del file. Null in caso di errore durante la lettura del file.
     */
    public String ottieniTesto() {
        if(this.fileDaGestire != null) {
            try (BufferedReader inputTesto = new BufferedReader(new FileReader(this.fileDaGestire))) {
                StringBuilder contenutoTesto = new StringBuilder();
                String rigaLetta = inputTesto.readLine();
                while (rigaLetta != null) {
                    contenutoTesto.append(rigaLetta).append("\n");
                    rigaLetta = inputTesto.readLine();
                }
                return contenutoTesto.toString();
            } catch (IOException e) {
                if(this.mostraAvvisi) System.err.println("Errore durante la lettura del file tramite JFM.");
                return null;
            }
        } else {
            if(this.mostraAvvisi) System.err.println("Impossibile leggere il contenuto del file tramite JFM. Non è stato inserito alcun file da gestire.");
            return null;
        }
    }
    /**
     * Metodo che permette di leggere l'intero contenuto del file sotto forma di String. Di default, non cancella il contenuto precedente del file e manda a capo a fine riga.
     *
     * @param testoDaScrivere Testo da scrivere nel file.
     */
    public void scriviTesto(String testoDaScrivere) {
        if(this.fileDaGestire != null) {
            try (BufferedWriter outputTesto = new BufferedWriter(new FileWriter(this.fileDaGestire, true))) {
                outputTesto.write(testoDaScrivere);
                outputTesto.newLine();
            } catch (IOException e) {
                if(this.mostraAvvisi) System.err.println("Errore durante la scrittura del file tramite JFM.");
            }
        } else {
            if(this.mostraAvvisi) System.err.println("Impossibile scrivere il contenuto del file tramite JFM. Non è stato inserito alcun file da gestire.");
        }
    }
    /**
     * Metodo che permette di leggere l'intero contenuto del file sotto forma di String. Di default, non cancella il contenuto precedente del file.
     *
     * @param testoDaScrivere Testo da scrivere nel file.
     * @param mandaACapo Variabile che indica se mandare a capo o meno dopo aver scritto il testo.
     */
    public void scriviTesto(String testoDaScrivere, boolean mandaACapo) {
        if(this.fileDaGestire != null) {
            try (BufferedWriter outputTesto = new BufferedWriter(new FileWriter(this.fileDaGestire, true))) {
                outputTesto.write(testoDaScrivere);
                if(mandaACapo) outputTesto.newLine();
            } catch (IOException e) {
                if(this.mostraAvvisi) System.err.println("Errore durante la scrittura del file tramite JFM.");
            }
        } else {
            if(this.mostraAvvisi) System.err.println("Impossibile scrivere il contenuto del file tramite JFM. Non è stato inserito alcun file da gestire.");
        }
    }
    /**
     * Metodo che permette di leggere l'intero contenuto del file sotto forma di String.
     *
     * @param testoDaScrivere Testo da scrivere nel file.
     * @param mandaACapo Variabile che indica se mandare a capo o meno dopo aver scritto il testo.
     * @param cancellaContenutoPrecedente Variabile che indica se cancellare o meno il contenuto precedente del file. Se impostato su false, il file verrà aperto in modalità append.
     */
    public void scriviTesto(String testoDaScrivere, boolean mandaACapo, boolean cancellaContenutoPrecedente) {
        if(this.fileDaGestire != null) {
            try (BufferedWriter outputTesto = new BufferedWriter(new FileWriter(this.fileDaGestire, !cancellaContenutoPrecedente))) {
                outputTesto.write(testoDaScrivere);
                if(mandaACapo) outputTesto.newLine();
            } catch (IOException e) {
                if(this.mostraAvvisi) System.err.println("Errore durante la scrittura del file tramite JFM.");
            }
        } else {
            if(this.mostraAvvisi) System.err.println("Impossibile scrivere il contenuto del file tramite JFM. Non è stato inserito alcun file da gestire.");
        }
    }
}
