package org.tpsit;

import java.io.*;

/**
 * JavaFileManager
 * @author Matteo Bagnoletti Tini
 * @version 1.0.0
 * @since 1.0.0
 * @see <a href="https://github.com/matbagnoletti/JavaFileManager">JavaFileManager on GitHub</a>
 * <p> Classe che gestisce i file in Java. Permette di leggere e scrivere dati tipizzati e righe di testo. Se non diversamente impostato, stampa eventuali avvisi sotto forma di errori.
 * Non supporta la scrittura e lettura di dati tipizzati come byte, bytes, long, short, char, chars.
 * JFM è thread-safe. La clausola synchronized garantisce la mutua esclusione tra i thread che accedono a JFM.</p>
 */
public class JavaFileManager {

    /**
     * Variabile che indica se stampare o meno gli avvisi sotto forma di errori (System.err).
     */
    private boolean mostraAvvisi = true;

    /**
     * File da gestire.
     * Sfrutta la classe File di Java per verificarne l'esistenza e per leggerne le proprietà.
     */
    private File fileDaGestire;

    /**
     * Stream di input per la lettura di dati tipizzati.
     */
    private DataInputStream inputStreamTipizzato = null;

    /**
     * Costruttore di JavaFileManager.
     * Versione di default.
     */
    public JavaFileManager() {
        this.fileDaGestire = null;
        System.err.println("Non è stato inserito alcun file da gestire all'interno del JFM creato.\nDa JFM('null').\nGenerato alla creazione.");
    }

    /**
     * Costruttore di JavaFileManager.
     * @param mostraAvvisi Variabile che indica se stampare o meno gli avvisi sotto forma di errori.
     */
    public JavaFileManager(boolean mostraAvvisi) {
        this.mostraAvvisi = mostraAvvisi;
        this.fileDaGestire = null;
        if(this.mostraAvvisi) System.err.println("Non è stato inserito alcun file da gestire all'interno del JFM creato.\nDa JFM('null').\nGenerato alla creazione.");
    }

    /**
     * Costruttore di JavaFileManager.
     * @param nomeFile Nome del file con il percorso dalla root del progetto.
     */
    public JavaFileManager(String nomeFile) {
        this.fileDaGestire = new File(nomeFile);
        if (!this.fileDaGestire.exists() || !this.fileDaGestire.isFile()) {
            if(this.mostraAvvisi) System.err.println("Il file specificato non esiste o non è un file.\nDa JFM('null').\nGenerato alla creazione.");
        } else {
            this.inputStreamTipizzato = apriStreamTipizzato();
        }
    }

    /**
     * Costruttore di JavaFileManager.
     * @param nomeFile Nome del file con il percorso dalla root del progetto.
     * @param creaSeNull Variabile che indica se creare o meno il file se non esiste.
     */
    public JavaFileManager(String nomeFile, boolean creaSeNull) {
        this.fileDaGestire = new File(nomeFile);
        if ((!this.fileDaGestire.exists() || !this.fileDaGestire.isFile())) {
            if(creaSeNull){
                this.fileDaGestire = creaFile(nomeFile);
                if(this.fileDaGestire != null){
                    this.inputStreamTipizzato = apriStreamTipizzato();
                }
            } else {
                if(this.mostraAvvisi) System.err.println("Il file specificato non esiste o non è un file.\nDa JFM('" + this.fileDaGestire + "').\nGenerato alla creazione.");
            }
        } else {
            this.inputStreamTipizzato = apriStreamTipizzato();
        }
    }

    /**
     * Costruttore di JavaFileManager.
     * @param nomeFile Nome del file con il percorso dalla root del progetto.
     * @param creaSeNull Variabile che indica se creare o meno il file se non esiste.
     * @param mostraAvvisi Variabile che indica se stampare o meno gli avvisi sotto forma di errori.
     */
    public JavaFileManager(String nomeFile, boolean creaSeNull, boolean mostraAvvisi) {
        this.mostraAvvisi = mostraAvvisi;
        this.fileDaGestire = new File(nomeFile);
        if ((!this.fileDaGestire.exists() || !this.fileDaGestire.isFile())) {
            if(creaSeNull){
                this.fileDaGestire = creaFile(nomeFile);
                if(this.fileDaGestire != null){
                    this.inputStreamTipizzato = apriStreamTipizzato();
                }
            } else {
                if(this.mostraAvvisi) System.err.println("Il file specificato non esiste o non è un file.\nDa JFM('" + this.fileDaGestire + "').\nGenerato alla creazione.");
            }
        } else {
            this.inputStreamTipizzato = apriStreamTipizzato();
        }
    }

    /**
     * Metodo che permette di creare il file specificato.
     * @param nomeFile Nome del file con il percorso dalla root del progetto.
     * @return File creato. Null in caso di errore durante la creazione del file.
     */
    public File creaFile(String nomeFile) {
        File file = new File(nomeFile);
        if (!file.exists()) {
            try {
                if(!file.createNewFile()) {
                    if (this.mostraAvvisi) System.err.println("Errore durante la creazione del file tramite JFM.\nDa JFM('" + file + "').\nGenerato da creaFile().");
                    return null;
                }
            } catch (IOException e) {
                if (this.mostraAvvisi) System.err.println("Errore durante la creazione del file tramite JFM.\nDa JFM('" + file + "').\nGenerato da creaFile().");
                return null;
            }
        }
        return file;
    }

    /**
     * Metodo che permette di aprire lo stream per la lettura di dati tipizzati.
     * @return Stream di input per la lettura di dati tipizzati. Null in caso di errore durante l'apertura del file.
     */
    public DataInputStream apriStreamTipizzato() {
        DataInputStream inputStream;
        try {
            inputStream = new DataInputStream(new FileInputStream(this.fileDaGestire));
        } catch (FileNotFoundException e) {
            if (this.mostraAvvisi) System.err.println("Errore durante l'apertura del file tramite JFM.\nDa JFM('" + this.fileDaGestire + "').\nGenerato da apriStreamTipizzato().");
            return null;
        }
        return inputStream;
    }

    /**
     * Setter dell'attributo File fileDaGestire
     * @param nomeFile Nome del file con il percorso dalla root del progetto.
     * @param creaSeNull Variabile che indica se creare o meno il file se non esiste.
     */
    public synchronized void setFile(String nomeFile, boolean creaSeNull) {
        this.fileDaGestire = new File(nomeFile);
        if ((!this.fileDaGestire.exists() || !this.fileDaGestire.isFile())) {
            if(creaSeNull){
                this.fileDaGestire = creaFile(nomeFile);
                if(this.fileDaGestire != null){
                    this.inputStreamTipizzato = apriStreamTipizzato();
                }
            } else {
                if(this.mostraAvvisi) System.err.println("Il file specificato non esiste o non è un file.\nDa JFM('" + this.fileDaGestire + "').\nGenerato da setFile().");
            }
        } else {
            this.inputStreamTipizzato = apriStreamTipizzato();
        }
    }

    /**
     * Setter dell'attributo boolean mostraAvvisi
     * @param mostraAvvisi Variabile che indica se stampare o meno gli avvisi sotto forma di errori.
     */
    public synchronized void setAvvisi(boolean mostraAvvisi) {
        this.mostraAvvisi = mostraAvvisi;
    }

    /**
     * Metodo che permette di leggere l'intero contenuto del file sotto forma di String.
     * @return Stringa contenente l'intero contenuto del file. Null in caso di errore durante la lettura del file.
     */
    public synchronized String leggi() {
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
                if(this.mostraAvvisi) System.err.println("Errore durante la lettura del file tramite JFM.\nDa JFM('" + this.fileDaGestire + "').\nGenerato da leggi().");
                return null;
            }
        } else {
            if(this.mostraAvvisi) System.err.println("Impossibile leggere il contenuto del file tramite JFM. Non è stato inserito alcun file da gestire.\nDa JFM('null').\nGenerato da leggi().");
            return null;
        }
    }

    /**
     * Metodo che permette di leggere l'intero contenuto del file sotto forma di String. Di default, non cancella il contenuto precedente del file e manda a capo a fine riga.
     *
     * @param testoDaScrivere Testo da scrivere nel file.
     */
    public synchronized void scrivi(String testoDaScrivere) {
        if(this.fileDaGestire != null) {
            try (BufferedWriter outputTesto = new BufferedWriter(new FileWriter(this.fileDaGestire, true))) {
                outputTesto.write(testoDaScrivere);
                outputTesto.newLine();
            } catch (IOException e) {
                if(this.mostraAvvisi) System.err.println("Errore durante la scrittura del file tramite JFM.\nDa JFM('" + this.fileDaGestire + "').\nGenerato da scrivi().");
            }
        } else {
            if(this.mostraAvvisi) System.err.println("Impossibile scrivere il contenuto del file tramite JFM. Non è stato inserito alcun file da gestire.\nDa JFM('null').\nGenerato da scrivi().");
        }
    }

    /**
     * Metodo che permette di leggere l'intero contenuto del file sotto forma di String. Di default, non cancella il contenuto precedente del file.
     *
     * @param testoDaScrivere Testo da scrivere nel file.
     * @param mandaACapo Variabile che indica se mandare a capo o meno dopo aver scritto il testo.
     */
    public synchronized void scrivi(String testoDaScrivere, boolean mandaACapo) {
        if(this.fileDaGestire != null) {
            try (BufferedWriter outputTesto = new BufferedWriter(new FileWriter(this.fileDaGestire, true))) {
                outputTesto.write(testoDaScrivere);
                if(mandaACapo) outputTesto.newLine();
            } catch (IOException e) {
                if(this.mostraAvvisi) System.err.println("Errore durante la scrittura del file tramite JFM.\nDa JFM('" + this.fileDaGestire + "').\nGenerato da scrivi().");
            }
        } else {
            if(this.mostraAvvisi) System.err.println("Impossibile scrivere il contenuto del file tramite JFM. Non è stato inserito alcun file da gestire.\nDa JFM('null').\nGenerato da scrivi().");
        }
    }

    /**
     * Metodo che permette di leggere l'intero contenuto del file sotto forma di String.
     *
     * @param testoDaScrivere Testo da scrivere nel file.
     * @param mandaACapo Variabile che indica se mandare a capo o meno dopo aver scritto il testo.
     * @param cancellaContenutoPrecedente Variabile che indica se cancellare o meno il contenuto precedente del file. Se impostato su false, il file verrà aperto in modalità append.
     */
    public synchronized void scrivi(String testoDaScrivere, boolean mandaACapo, boolean cancellaContenutoPrecedente) {
        if(this.fileDaGestire != null) {
            try (BufferedWriter outputTesto = new BufferedWriter(new FileWriter(this.fileDaGestire, !cancellaContenutoPrecedente))) {
                outputTesto.write(testoDaScrivere);
                if(mandaACapo) outputTesto.newLine();
            } catch (IOException e) {
                if(this.mostraAvvisi) System.err.println("Errore durante la scrittura del file tramite JFM.\nDa JFM('" + this.fileDaGestire + "').\nGenerato da scrivi().");
            }
        } else {
            if(this.mostraAvvisi) System.err.println("Impossibile scrivere il contenuto del file tramite JFM. Non è stato inserito alcun file da gestire.\nDa JFM('null').\nGenerato da scrivi(String).");
        }
    }

    /**
     * Metodo che permette di serializzare un oggetto in un file. Di default, non cancella il contenuto precedente del file.
     *
     * @param oggettoDaSerializzare Oggetto da serializzare.
     */
    public synchronized void scriviOggetto(Object oggettoDaSerializzare) {
        if(this.fileDaGestire != null) {
            if(oggettoDaSerializzare instanceof Serializable){
                try (ObjectOutputStream outputOggetto = new ObjectOutputStream(new FileOutputStream(this.fileDaGestire, true))) {
                    outputOggetto.writeObject(oggettoDaSerializzare);
                } catch (IOException e) {
                    if(this.mostraAvvisi) System.err.println("Errore durante la serializzazione dell'oggetto tramite JFM.\nDa JFM('" + this.fileDaGestire + "').\nGenerato da scriviOggetto().");
                }
            } else {
                if(this.mostraAvvisi) System.err.println("Impossibile serializzare l'oggetto tramite JFM. L'oggetto inserito non è serializzabile.\nDa JFM('" + this.fileDaGestire + "').\nGenerato da scriviOggetto().");
            }
        } else {
            if(this.mostraAvvisi) System.err.println("Impossibile serializzare l'oggetto tramite JFM. Non è stato inserito alcun file da gestire.\nDa JFM('null').\nGenerato da scriviOggetto().");
        }
    }

    /**
     * Metodo che permette di serializzare un oggetto in un file.
     *
     * @param oggettoDaSerializzare Oggetto da serializzare.
     * @param cancellaContenutoPrecedente Variabile che indica se cancellare o meno il contenuto precedente del file. Se impostato su false, il file verrà aperto in modalità append.
     */
    public synchronized void scriviOggetto(Object oggettoDaSerializzare, boolean cancellaContenutoPrecedente) {
        if(this.fileDaGestire != null) {
            if (oggettoDaSerializzare instanceof Serializable){
                try (ObjectOutputStream outputOggetto = new ObjectOutputStream(new FileOutputStream(this.fileDaGestire, !cancellaContenutoPrecedente))) {
                    outputOggetto.writeObject(oggettoDaSerializzare);
                } catch (IOException e) {
                    if(this.mostraAvvisi) System.err.println("Errore durante la serializzazione dell'oggetto tramite JFM.\nDa JFM('" + this.fileDaGestire + "').\nGenerato da scriviOggetto().");
                }
            } else {
                if(this.mostraAvvisi) System.err.println("Impossibile serializzare l'oggetto tramite JFM. L'oggetto inserito non è serializzabile.\nDa JFM('" + this.fileDaGestire + "').\nGenerato da scriviOggetto().");
            }
        } else {
            if(this.mostraAvvisi) System.err.println("Impossibile serializzare l'oggetto tramite JFM. Non è stato inserito alcun file da gestire.\nDa JFM('null').\nGenerato da scriviOggetto().");
        }
    }

    /**
     * Metodo che permette di de-serializzare un oggetto da un file.
     *
     * @see #scriviOggetto(Object)
     * @see #scriviOggetto(Object, boolean)
     * @return Oggetto de-serializzato. Null in caso di errore durante la de-serializzazione del file.
     */
    public synchronized Object leggiOggetto() {
        if(this.fileDaGestire != null) {
            try (ObjectInputStream inputOggetto = new ObjectInputStream(new FileInputStream(this.fileDaGestire))) {
                return inputOggetto.readObject();
            } catch (IOException | ClassNotFoundException e) {
                if(this.mostraAvvisi) System.err.println("Errore durante la de-serializzazione dell'oggetto tramite JFM.\nDa JFM('" + this.fileDaGestire + "').\nGenerato da leggiOggetto().");
                return null;
            }
        } else {
            if(this.mostraAvvisi) System.err.println("Impossibile de-serializzare l'oggetto tramite JFM. Non è stato inserito alcun file da gestire.\nDa JFM('null').\nGenerato da leggiOggetto().");
            return null;
        }
    }

    /**
     * Metodo che permette di scrivere una stringa UTF in un file. Di default, non cancella il contenuto precedente del file.
     *
     * @param testoDaScrivere Testo da scrivere nel file.
     */
    public synchronized void scriviTipizzato(String testoDaScrivere) {
        if(this.fileDaGestire != null) {
            try (DataOutputStream outputTesto = new DataOutputStream(new FileOutputStream(this.fileDaGestire, true))) {
                outputTesto.writeUTF(testoDaScrivere);
            } catch (IOException e) {
                if(this.mostraAvvisi) System.err.println("Errore durante la scrittura del file tramite JFM.\nDa JFM('" + this.fileDaGestire + "').\nGenerato da scriviTipizzato(String).");
            }
        } else {
            if(this.mostraAvvisi) System.err.println("Impossibile scrivere il contenuto del file tramite JFM. Non è stato inserito alcun file da gestire.\nDa JFM('null').\nGenerato da scriviTipizzato(String).");
        }
    }

    /**
     * Metodo che permette di scrivere una stringa UTF in un file.
     *
     * @param testoDaScrivere Testo da scrivere nel file.
     * @param cancellaContenutoPrecedente  Variabile che indica se cancellare o meno il contenuto precedente del file. Se impostato su false, il file verrà aperto in modalità append.
     */
    public synchronized void scriviTipizzato(String testoDaScrivere, boolean cancellaContenutoPrecedente) {
        if(this.fileDaGestire != null) {
            try (DataOutputStream outputTesto = new DataOutputStream(new FileOutputStream(this.fileDaGestire, !cancellaContenutoPrecedente))) {
                outputTesto.writeUTF(testoDaScrivere);
            } catch (IOException e) {
                if(this.mostraAvvisi) System.err.println("Errore durante la scrittura del file tramite JFM.\nDa JFM('" + this.fileDaGestire + "').\nGenerato da scriviTipizzato(String).");
            }
        } else {
            if(this.mostraAvvisi) System.err.println("Impossibile scrivere il contenuto del file tramite JFM. Non è stato inserito alcun file da gestire.\nDa JFM('null').\nGenerato da scriviTipizzato(String).");
        }
    }

    /**
     * Metodo che permette di scrivere un numero int in un file. Di default, non cancella il contenuto precedente del file.
     *
     * @param testoDaScrivere Testo da scrivere nel file.
     */
    public synchronized void scriviTipizzato(int testoDaScrivere) {
        if(this.fileDaGestire != null) {
            try (DataOutputStream outputTesto = new DataOutputStream(new FileOutputStream(this.fileDaGestire, true))) {
                outputTesto.writeInt(testoDaScrivere);
            } catch (IOException e) {
                if(this.mostraAvvisi) System.err.println("Errore durante la scrittura del file tramite JFM.\nDa JFM('" + this.fileDaGestire + "').\nGenerato da scriviTipizzato(int).");
            }
        } else {
            if(this.mostraAvvisi) System.err.println("Impossibile scrivere il contenuto del file tramite JFM. Non è stato inserito alcun file da gestire.\nDa JFM('null').\nGenerato da scriviTipizzato(int).");
        }
    }

    /**
     * Metodo che permette di scrivere un numero int in un file.
     *
     * @param testoDaScrivere Testo da scrivere nel file.
     * @param cancellaContenutoPrecedente Variabile che indica se cancellare o meno il contenuto precedente del file. Se impostato su false, il file verrà aperto in modalità append.
     */
    public synchronized void scriviTipizzato(int testoDaScrivere, boolean cancellaContenutoPrecedente) {
        if(this.fileDaGestire != null) {
            try (DataOutputStream outputTesto = new DataOutputStream(new FileOutputStream(this.fileDaGestire, !cancellaContenutoPrecedente))) {
                outputTesto.writeInt(testoDaScrivere);
            } catch (IOException e) {
                if(this.mostraAvvisi) System.err.println("Errore durante la scrittura del file tramite JFM.\nDa JFM('" + this.fileDaGestire + "').\nGenerato da scriviTipizzato(int).");
            }
        } else {
            if(this.mostraAvvisi) System.err.println("Impossibile scrivere il contenuto del file tramite JFM. Non è stato inserito alcun file da gestire.\nDa JFM('null').\nGenerato da scriviTipizzato(int).");
        }
    }

    /**
     * Metodo che permette di scrivere un numero double in un file. Di default, non cancella il contenuto precedente del file.
     *
     * @param testoDaScrivere Testo da scrivere nel file.
     */
    public synchronized void scriviTipizzato(double testoDaScrivere) {
        if(this.fileDaGestire != null) {
            try (DataOutputStream outputTesto = new DataOutputStream(new FileOutputStream(this.fileDaGestire, true))) {
                outputTesto.writeDouble(testoDaScrivere);
            } catch (IOException e) {
                if(this.mostraAvvisi) System.err.println("Errore durante la scrittura del file tramite JFM.\nDa JFM('" + this.fileDaGestire + "').\nGenerato da scriviTipizzato(double).");
            }
        } else {
            if(this.mostraAvvisi) System.err.println("Impossibile scrivere il contenuto del file tramite JFM. Non è stato inserito alcun file da gestire.\nDa JFM('null').\nGenerato da scriviTipizzato(double).");
        }
    }

    /**
     * Metodo che permette di scrivere un numero double in un file.
     *
     * @param testoDaScrivere Testo da scrivere nel file.
     * @param cancellaContenutoPrecedente Variabile che indica se cancellare o meno il contenuto precedente del file. Se impostato su false, il file verrà aperto in modalità append.
     */
    public synchronized void scriviTipizzato(double testoDaScrivere, boolean cancellaContenutoPrecedente) {
        if(this.fileDaGestire != null) {
            try (DataOutputStream outputTesto = new DataOutputStream(new FileOutputStream(this.fileDaGestire, !cancellaContenutoPrecedente))) {
                outputTesto.writeDouble(testoDaScrivere);
            } catch (IOException e) {
                if(this.mostraAvvisi) System.err.println("Errore durante la scrittura del file tramite JFM.\nDa JFM('" + this.fileDaGestire + "').\nGenerato da scriviTipizzato(double).");
            }
        } else {
            if(this.mostraAvvisi) System.err.println("Impossibile scrivere il contenuto del file tramite JFM. Non è stato inserito alcun file da gestire.\nDa JFM('null').\nGenerato da scriviTipizzato(double).");
        }
    }

    /**
     * Metodo che permette di scrivere un numero float in un file. Di default, non cancella il contenuto precedente del file.
     *
     * @param testoDaScrivere Testo da scrivere nel file.
     */
    public synchronized void scriviTipizzato(float testoDaScrivere) {
        if(this.fileDaGestire != null) {
            try (DataOutputStream outputTesto = new DataOutputStream(new FileOutputStream(this.fileDaGestire, true))) {
                outputTesto.writeFloat(testoDaScrivere);
            } catch (IOException e) {
                if(this.mostraAvvisi) System.err.println("Errore durante la scrittura del file tramite JFM.\nDa JFM('" + this.fileDaGestire + "').\nGenerato da scriviTipizzato(float).");
            }
        } else {
            if(this.mostraAvvisi) System.err.println("Impossibile scrivere il contenuto del file tramite JFM. Non è stato inserito alcun file da gestire.\nDa JFM('null').\nGenerato da scriviTipizzato(float).");
        }
    }

    /**
     * Metodo che permette di scrivere un numero float in un file.
     *
     * @param testoDaScrivere Testo da scrivere nel file.
     * @param cancellaContenutoPrecedente Variabile che indica se cancellare o meno il contenuto precedente del file. Se impostato su false, il file verrà aperto in modalità append.
     */
    public synchronized void scriviTipizzato(float testoDaScrivere, boolean cancellaContenutoPrecedente) {
        if(this.fileDaGestire != null) {
            try (DataOutputStream outputTesto = new DataOutputStream(new FileOutputStream(this.fileDaGestire, !cancellaContenutoPrecedente))) {
                outputTesto.writeFloat(testoDaScrivere);
            } catch (IOException e) {
                if(this.mostraAvvisi) System.err.println("Errore durante la scrittura del file tramite JFM.\nDa JFM('" + this.fileDaGestire + "').\nGenerato da scriviTipizzato(float).");
            }
        } else {
            if(this.mostraAvvisi) System.err.println("Impossibile scrivere il contenuto del file tramite JFM. Non è stato inserito alcun file da gestire.\nDa JFM('null').\nGenerato da scriviTipizzato(float).");
        }
    }

    /**
     * Metodo che permette di leggere una stringa UTF da un file.
     *
     * @return Stringa UTF letta dal file. Null in caso di errore durante la lettura del file.
     */
    public synchronized String leggiString() {
        if(this.fileDaGestire != null) {
            try {
                return this.inputStreamTipizzato.readUTF();
            } catch (IOException e) {
                if(this.mostraAvvisi) System.err.println("Errore durante la lettura del file tramite JFM.\nDa JFM('" + this.fileDaGestire + "').\nGenerato da leggiString().");
                return null;
            }
        } else {
            if(this.mostraAvvisi) System.err.println("Impossibile leggere il contenuto del file tramite JFM. Non è stato inserito alcun file da gestire.\nDa JFM('null').\nGenerato da leggiString().");
            return null;
        }
    }

    /**
     * Metodo che permette di leggere un numero int da un file.
     *
     * @return Numero int letto dal file. 0 in caso di errore durante la lettura del file.
     */
    public synchronized int leggiInt() {
        if(this.fileDaGestire != null) {
            try {
                return this.inputStreamTipizzato.readInt();
            } catch (IOException e) {
                if(this.mostraAvvisi) System.err.println("Errore durante la lettura del file tramite JFM.\nDa JFM('" + this.fileDaGestire + "').\nGenerato da leggiInt().");
                return 0;
            }
        } else {
            if(this.mostraAvvisi) System.err.println("Impossibile leggere il contenuto del file tramite JFM. Non è stato inserito alcun file da gestire.\nDa JFM('null').\nGenerato da leggiInt().");
            return 0;
        }
    }

    /**
     * Metodo che permette di leggere un numero double da un file.
     *
     * @return Numero double letto dal file. 0 in caso di errore durante la lettura del file.
     */
    public synchronized double leggiDouble() {
        if(this.fileDaGestire != null) {
            try {
                return this.inputStreamTipizzato.readDouble();
            } catch (IOException e) {
                if(this.mostraAvvisi) System.err.println("Errore durante la lettura del file tramite JFM.\nDa JFM('" + this.fileDaGestire + "').\nGenerato da leggiDouble().");
                return 0;
            }
        } else {
            if(this.mostraAvvisi) System.err.println("Impossibile leggere il contenuto del file tramite JFM. Non è stato inserito alcun file da gestire.\nDa JFM('null').\nGenerato da leggiDouble().");
            return 0;
        }
    }

    /**
     * Metodo che permette di leggere un numero float da un file.
     *
     * @return Numero float letto dal file. 0 in caso di errore durante la lettura del file.
     */
    public synchronized float leggiFloat() {
        if(this.fileDaGestire != null) {
            try {
                return this.inputStreamTipizzato.readFloat();
            } catch (IOException e) {
                if(this.mostraAvvisi) System.err.println("Errore durante la lettura del file tramite JFM.\nDa JFM('" + this.fileDaGestire + "').\nGenerato da leggiFloat().");
                return 0;
            }
        } else {
            if(this.mostraAvvisi) System.err.println("Impossibile leggere il contenuto del file tramite JFM. Non è stato inserito alcun file da gestire.\nDa JFM('null').\nGenerato da leggiFloat().");
            return 0;
        }
    }

    /**
     * Metodo che permette di copiare il contenuto del file in un altro file specificato.
     *
     * @param jfm JavaFileManager in cui copiare il contenuto del file.
     */
    public synchronized void copiaIn(JavaFileManager jfm) {
        if(this.leggi() != null) {
            jfm.scrivi(this.leggi(), false);
        } else {
            if(this.mostraAvvisi) System.err.println("Impossibile copiare il contenuto del file tramite JFM. Errore durante la lettura del file.\nDa JFM('" + this.fileDaGestire + "').\nGenerato da copiaIn().");
        }
    }

    /**
     * Metodo che permette di copiare il contenuto del file in un altro file specificato.
     *
     * @param nomeFile Percorso dalla root del progetto del file in cui copiare il contenuto del file.
     */
    public synchronized void copiaIn(String nomeFile) {
        JavaFileManager jfmCopiaIn = new JavaFileManager(nomeFile, true);
        if(this.leggi() != null) {
            jfmCopiaIn.scrivi(this.leggi(), false);
        } else {
            if(this.mostraAvvisi) System.err.println("Impossibile copiare il contenuto del file tramite JFM. Errore durante la lettura del file.\nDa JFM('" + this.fileDaGestire + "').\nGenerato da copiaIn().");
        }
    }

    /**
     * Metodo che permette di copiare il contenuto di un altro file in questo file.
     *
     * @param jfm JavaFileManager da cui copiare il contenuto del file.
     */
    public synchronized void copiaDa(JavaFileManager jfm) {
        if(jfm.leggi() != null) {
            this.scrivi(jfm.leggi(), false);
        } else {
            if(this.mostraAvvisi) System.err.println("Impossibile copiare il contenuto del file tramite JFM. Errore durante la lettura del file.\nDa JFM('" + this.fileDaGestire + "').\nGenerato da copiaDa().");
        }
    }

    /**
     * Metodo che permette di copiare il contenuto di un altro file in questo file.
     *
     * @param nomeFile Percorso dalla root del progetto del file da cui copiare il contenuto del file.
     */
    public synchronized void copiaDa(String nomeFile) {
        JavaFileManager jfmCopiaDa = new JavaFileManager(nomeFile);
        if(jfmCopiaDa.leggi() != null && this.fileDaGestire != null) {
            this.scrivi(jfmCopiaDa.leggi(), false);
        } else {
            if(this.mostraAvvisi) System.err.println("Impossibile copiare il contenuto del file tramite JFM. Errore durante la lettura del file.\nDa JFM('" + this.fileDaGestire + "').\nGenerato da copiaDa().");
        }
    }

    /**
     * Metodo che permette di cancellare il contenuto del file tramite il metodo scrivi().
     * @see #scrivi(String, boolean, boolean)
     */
    public synchronized void cancellaContenuto() {
        this.scrivi("", false, true);
    }

    /**
     * Metodo che permette di eliminare il file.
     * @see File#delete()
     */
    public synchronized void elimina() {
        if(this.fileDaGestire.isFile() && this.fileDaGestire.exists()) {
            if(!this.fileDaGestire.delete()) {
                if(this.mostraAvvisi) System.err.println("Impossibile eliminare il file tramite JFM. Errore durante l'eliminazione del file.\nDa JFM('" + this.fileDaGestire + "').\nGenerato da elimina().");
            }
        } else {
            if(this.mostraAvvisi) System.err.println("Impossibile eliminare il file tramite JFM. Il file non esiste o non è un file.\nDa JFM('" + this.fileDaGestire + "').\nGenerato da elimina().");
        }
    }

    /**
     * Metodo che permette di chiudere lo stream di input per la lettura di dati tipizzati.
     * È obbligatorio invocare questo metodo prima di terminare il programma.
     */
    public synchronized void termina() {
        try {
            if(this.inputStreamTipizzato != null) this.inputStreamTipizzato.close();
        } catch (IOException e) {
            if(this.mostraAvvisi) System.err.println("Errore durante la chiusura del file tramite JFM.\nDa JFM('" + this.fileDaGestire + "').\nGenerato da termina().");
        }
    }

    @Override
    public String toString() {
        return "JFM del file: " + fileDaGestire.toString();
    }
}
