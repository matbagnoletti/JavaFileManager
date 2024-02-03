<h1 align="center">JavaFileManager</h1>

<p align="center" style="font-family: monospace">Made by <a href="https://github.com/matbagnoletti">@matbagnoletti</a></p>
<p align="center">
    <img src="https://img.shields.io/github/last-commit/matbagnoletti/JavaFileManager?style=for-the-badge" alt="Ultimo commit">
    <img src="https://img.shields.io/github/languages/top/matbagnoletti/JavaFileManager?style=for-the-badge" alt="Linguaggio">
</p>

## Descrizione
Questo progetto ha lo scopo di creare una classe (`JavaFileManager`) che permetta l'input e output in Java da e verso file. L'obiettivo è quello di rendere l'utilizzo degli stream più semplice e veloce, sfruttando l'incapsulamento Java per nasconderne all'utente la complessità.

> [!TIP]
>
> La classe `JavaFileManager` è thread-safe, quindi può essere utilizzata in ambienti multi-thread senza problemi. Tuttavia, l'uso eccessivo della sincronizzazione può portare a una diminuzione delle prestazioni, quindi utilizzala con attenzione.

## Novità
- **Versione 1.0.0**:
  - Rilascio iniziale.

- **Versione 1.1.0 (attuale)**:
  - Aggiunta la possibilità di creare direttamente da JFM (se specificato) il file con il percorso nel costruttore.
  - Metodi di scrittura e lettura con firme più esplicite.
  - Nuovi metodi per la gestione del file direttamente da JFM.

## Utilizzo
Per utilizzare JFM è necessario:
1. Creare un'istanza della classe `JavaFileManager`:

   Il costruttore accetta tre parametri: `String nomeFile` (il nome del file con il percorso dalla root del progetto), `boolean creaSeNNull` (se `true`, nel caso in cui il file specificato non esista, viene creato) e `boolean mostraAvvisi` (se `true`, mostra eventuali avvisi ed errori a video).

    ```java
    import javax.tools.JavaFileManager;/* Mostra avvisi ed errori a video. Richiederà di inserire il percorso del file successivamente. */
    JavaFileManager jfm = new JavaFileManager();
    
    /* Non mostra avvisi ed errori a video. Richiederà di inserire il percorso del file successivamente. */
    JavaFileManager jfm = new JavaFileManager(false);
    jfm.setFile("percorso/del/tuo/file.txt");
    
    /* Mostra di default avvisi ed errori a video. Richiederà di inserire il percorso del file successivamente. */
    JavaFileManager jfm = new JavaFileManager(true);
    jfm.setFile("percorso/del/tuo/file.txt");
    
    /* Mostra di default avvisi ed errori a video. Il percorso del file è quello specificato. */
    JavaFileManager jfm = new JavaFileManager("percorso/del/tuo/file.txt");
    
    /* Mostra avvisi ed errori a video. Il percorso del file è quello specificato. Se non esiste lo crea. */
    JavaFileManager jfm = new JavaFileManager("percorso/del/tuo/fileInesistente.txt", true);
    ```

2. Utilizzare i metodi di scrittura appropriati:

   JFM permette di scrivere su un file in tre modi:
   - [formato standard](#scrivere-nel-formato-standard) (con `BufferedWriter`).
   - [formato oggetto serializzato](#scrivere-un-oggetto-serializzato) (con `ObjectOutputStream`).
   - [formato dati tipizzati](#scrivere-dati-tipizzati) (con `DataOutputStream`).

3. Utilizzare i metodi di lettura appropriati:

   JFM permette di leggere da un file in tre modi:
    - [formato standard](#leggere-nel-formato-standard) (con `BufferedReader`).
    - [formato oggetto serializzato](#leggere-un-oggetto-serializzato) (con `ObjectInputStream`).
    - [formato dati tipizzati](#leggere-dati-tipizzati) (con `DataInputStream`).

4. Utilizzare i metodi di gestione del file:

   JFM permette di gestire il file in tre modi:
    - [eliminare il file](#eliminare-il-file).
    - [cancellare il contenuto del file](#cancellare-il-contenuto-del-file).
    - [copiare il contenuto da/a un altro file](#copiare-il-contenuto-da/a-un-altro-file).

## Scrivere nel formato standard

La modalità standard di scrittura su file utilizza la classe `BufferedWriter` e accetta 3 parametri:
- `String testoDaScrivere`: il testo da scrivere sul file.
- `boolean mandaACapo`: se `true`, manda a capo dopo aver scritto il testo.
- `boolean cancellaContenutoPrecedente`: se `true` cancella il contenuto precedente del file, altrimenti scrive in append.

    ```java
    jfm.scrivi("Ciao,");
    jfm.scrivi(" Mondo!", true);
    /* Scrive "Ciao,", concatena " Mondo!" ed, infine, manda a capo (attenzione agli spazi) */
  
    jfm.scrivi("Ciao, mondo!", true);
    /* Scrive "Ciao, mondo!" e manda a capo */
  
    jfm.scrivi("Ciao, mondo!", true, true);
    /* Scrive "Ciao, mondo!" e manda a capo, cancellando il contenuto precedente del file */
    ```

## Scrivere un oggetto serializzato

La modalità di scrittura su file di un oggetto serializzato utilizza la classe `ObjectOutputStream` e accetta un parametro:
- `Object oggetto`: l'oggetto da scrivere sul file.

   ```java
   jfm.scriviOggetto(new Object());
   ```

> [!CAUTION]
>
> L'oggetto deve essere serializzabile. Il file dovrà poi essere opportunamente letto con `leggiOggetto()`.

## Scrivere dati tipizzati

La modalità di scrittura su file di dati tipizzati utilizza la classe `DataOutputStream` e accetta tre parametri:
- `String/int/double/float testoDaScrivere`: il testo da scrivere sul file.
- `boolean mandaACapo`: se `true`, manda a capo dopo aver scritto il testo.
- `boolean cancellaContenutoPrecedente`: se `true` cancella il contenuto precedente del file, altrimenti scrive in append.

   ```java
   jfm.scriviTipizzato(1);
   jfm.scriviTipizzato("Ciao, Mondo!");
   ```

> [!CAUTION]
>
> Il file dovrà poi essere opportunamente letto con uno di questi metodi:
> - `leggiTipoString()`.
> - `leggiTipoInt()`.
> - `leggiTipoDouble()`.
> - `leggiTipoFloat()`.
>
> I dati dovranno essere letti necessariamente nello stesso ordine in cui sono stati scritti.

## Leggere nel formato standard

La modalità standard di lettura da file utilizza la classe `BufferedReader`.

   ```java
   String testo = jfm.leggi();
   ```

## Leggere un oggetto serializzato

La modalità di lettura da file di un oggetto serializzato utilizza la classe `ObjectInputStream`.

   ```java
   Object oggetto = jfm.leggiOggetto();
   ```

> [!CAUTION]
>
> L'oggetto deve essere serializzabile. Il file deve essere stato opportunamente scritto con `scriviOggetto()`.

## Leggere dati tipizzati

La modalità di lettura da file di dati tipizzati utilizza la classe `DataInputStream`.

   ```java
   int intero = jfm.leggiTipoInt();
   String testo = jfm.leggiTipoString();
   ```

> [!CAUTION]
>
> Il file deve essere stato opportunamente scritto con uno di questi metodi:
> - `scriviTipizzato()`.
> - `scriviTipizzato()`.
> - `scriviTipizzato()`.
> - `scriviTipizzato()`.
>
> I dati devono essere letti necessariamente nello stesso ordine in cui sono stati scritti.

## Eliminare il file
Ora è possibile eliminare il file con il metodo `elimina()` sfruttando le proprietà della classe `File` di Java.

   ```java
   jfm.elimina();
   ```

## Cancellare il contenuto del file
Ora è possibile cancellare il contenuto del file con il metodo `cancellaContenuto()` che sfrutta la scrittura in append.

   ```java
   jfm.cancellaContenuto();
   ```

## Copiare il contenuto da/a un altro file
Ora è possibile copiare il contenuto da/a un altro file con il metodo `copiaDa()` e `copiaIn()`.
Entrambi i metodi accettano un parametro che può essere di due tipi:
- `String nomeFile`: il percorso del file da cui copiare o in cui copiare il contenuto.
- `JavaFileManager jfm`: l'istanza di `JavaFileManager` da cui copiare o in cui copiare il contenuto.

   ```java
   JavaFileManager jfm = new JavaFileManager("percorso/del/tuo/file.txt");
   jfm.copiaDa("percorso/del/tuo/file/da/cui/copiare.txt");
   
   JavaFileManager jfm2 = new JavaFileManager("percorso/del/tuo/file.txt");
   jfm.copiaIn(jfm2);
   ```

## Licenza d'uso
Questo progetto (e tutte le sue versioni) sono rilasciate sotto la [MB General Copyleft License](LICENSE).