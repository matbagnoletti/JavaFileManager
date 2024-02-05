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
- **Versione 1.0.0 (deprecata)**:
  - Rilascio iniziale.

- **Versione 1.1.0 (deprecata)**:
  - Aggiunta la possibilità di creare direttamente da JFM (se specificato) il file con il percorso nel costruttore.
  - Metodi di scrittura e lettura con firme più esplicite.
  - Nuovi metodi per la gestione del file direttamente da JFM.

- **Versione 1.2.0**:
  - Corretto funzionamento dei metodi di scrittura e lettura per dati tipizzati.
  - Inserimento del metodo `termina()` per liberare le risorse al termine dell'utilizzo di JFM (doveroso).

## Disclaimer
A causa di un errore di progettazione, la versione 1.0.0 e 1.1.0 sono deprecate e se ne sconsiglia altamente l'uso. Si consiglia di utilizzare la versione 1.2.0 o successive.

## Utilizzo
Per utilizzare JFM è necessario:
1. Creare un'istanza della classe `JavaFileManager`:

   Il costruttore accetta tre parametri: `String nomeFile` (il nome del file con il percorso dalla root del progetto), `boolean creaSeNNull` (se `true`, nel caso in cui il file specificato non esista, viene creato) e `boolean mostraAvvisi` (se `true`, mostra eventuali avvisi ed errori a video).

    ```java
    /* Mostra avvisi ed errori a video. Richiederà di inserire il percorso del file successivamente. */
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
    - [copiare il contenuto da/a un altro file](#copiare-il-contenuto-daa-un-altro-file).

5. Chiudere l'istanza di `JavaFileManager`: 

   È doveroso chiudere l'istanza di `JavaFileManager` con il metodo `termina()` per liberare le risorse.

    ```java
    jfm.termina();
    ```

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

La modalità di scrittura su file di dati tipizzati utilizza la classe `DataOutputStream` e accetta due parametri:
- `String/int/double/float testoDaScrivere`: il testo da scrivere sul file.
- `boolean cancellaContenutoPrecedente`: se `true` cancella il contenuto precedente del file, altrimenti scrive in append.

   ```java
   jfm.scriviTipizzato(1);
   jfm.scriviTipizzato("Ciao, Mondo!");
   ```

> [!CAUTION]
>
> La classe DataOutputStream scrive i dati in formato binario uno di seguito all'altro. È quindi è necessario leggerli con i metodi corrispondenti:
> - `leggiString()`.
> - `leggiInt()`.
> - `leggiDouble()`.
> - `leggiFloat()`.
>
> I dati dovranno essere letti necessariamente nello stesso ordine in cui sono stati scritti altrimenti JFM genererà un errore. È doveroso terminare l'istanza di `JavaFileManager` con il metodo `termina()` per liberare le risorse.

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

La modalità di lettura da file di dati tipizzati utilizza la classe `DataInputStream`. I dati letti sono scritti in formato binario uno di seguito all'altro. L'ordine di lettura è dunque importante.

   ```java
   //Nel file: 10"Ciao, mondo!" (in formato binario)
   int intero = jfm.leggiInt(); // 10
   String testo = jfm.leggiString(); // "Ciao, mondo!"
   ```

> [!CAUTION]
>
> Il file deve essere stato opportunamente scritto con il metodo `scriviTipizzato()`.
>
> I dati devono essere letti necessariamente nello stesso ordine in cui sono stati scritti.

## Eliminare il file
Ora è possibile eliminare il file con il metodo `elimina()` sfruttando le proprietà della classe `File` di Java.

   ```java
   jfm.elimina();
   ```

## Cancellare il contenuto del file
Ora è possibile cancellare il contenuto del file con il metodo `cancellaContenuto()`.

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

> [!CAUTION]
>
> Entrambi i metodi utilizzano la [scrittura standard](#scrivere-nel-formato-standard) e la [lettura standard](#leggere-nel-formato-standard), per cui si applicano le stesse considerazioni fatte per tali metodi.

## Licenza d'uso
Questo progetto (e tutte le sue versioni) sono rilasciate sotto la [MB General Copyleft License](LICENSE).
