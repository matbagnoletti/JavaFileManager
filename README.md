<h1 align="center">JavaFileManager</h1>

<p align="center" style="font-family: monospace">Made by <a href="https://github.com/matbagnoletti">@matbagnoletti</a></p>
<p align="center">
    <img src="https://img.shields.io/github/last-commit/matbagnoletti/JavaFileManager?style=for-the-badge" alt="Ultimo commit">
    <img src="https://img.shields.io/github/languages/code-size/matbagnoletti/JavaFileManager?style=for-the-badge" alt="Dimensione">
</p>

## Descrizione
Questo progetto ha lo scopo di creare una classe (`JavaFileManager`) che permetta l'input e output in Java da e verso file. L'obiettivo è quello di rendere l'utilizzo degli stream più semplice e veloce, sfruttando l'incapsulamento Java per nasconderne all'utente la complessità.

> [!TIP]
>
> La classe `JavaFileManager` è thread-safe, quindi può essere utilizzata in ambienti multi-thread senza problemi. Tuttavia, l'uso eccessivo della sincronizzazione può portare a una diminuzione delle prestazioni, quindi utilizzala con attenzione.

## Utilizzo
Per utilizzare JFM è necessario:
1. Creare un'istanza della classe `JavaFileManager`:

   Il costruttore accetta due parametri: `boolean mostraAvvisi` (se `true`, mostra eventuali avvisi ed errori a video) e `String URL` (il percorso dalla root del file da gestire).

    ```java
    /* Mostra avvisi ed errori a video. Richiederà di inserire il percorso del file successivamente. */
    JavaFileManager jfm = new JavaFileManager();
    
    /* Non mostra avvisi ed errori a video. Richiederà di inserire il percorso del file successivamente. */
    JavaFileManager jfm = new JavaFileManager(false);
    
    /* Mostra di default avvisi ed errori a video. Richiederà di inserire il percorso del file successivamente. */
    JavaFileManager jfm = new JavaFileManager(true);
    
    /* Mostra di default avvisi ed errori a video. Il percorso del file è quello specificato. */
    JavaFileManager jfm = new JavaFileManager("percorso/del/tuo/file.txt");
    
    /* Mostra avvisi ed errori a video. Il percorso del file è quello specificato. */
    JavaFileManager jfm = new JavaFileManager(true, "percorso/del/tuo/file.txt");
    
    /* Non mostra avvisi ed errori a video. Il percorso del file è quello specificato. */
    JavaFileManager jfm = new JavaFileManager(false, "percorso/del/tuo/file.txt");
    ```

2. (eventualmente) Utilizzare i metodi di scrittura appropriati:

   JFM permette di scrivere su un file in tre modi:
   - [formato standard](#scrivere-nel-formato-standard) (con `BufferedWriter`).
   - [formato oggetto serializzato](#scrivere-un-oggetto-serializzato) (con `ObjectOutputStream`).
   - [formato dati tipizzati](#scrivere-dati-tipizzati) (con `DataOutputStream`).

3. (eventualmente) Utilizzare i metodi di lettura appropriati:

   JFM permette di leggere da un file in tre modi:
    - [formato standard](#leggere-nel-formato-standard) (con `BufferedReader`).
    - [formato oggetto serializzato](#leggere-un-oggetto-serializzato) (con `ObjectInputStream`).
    - [formato dati tipizzati](#leggere-dati-tipizzati) (con `DataInputStream`).

## Scrivere nel formato standard

La modalità standard di scrittura su file utilizza la classe `BufferedWriter` e accetta 3 parametri:
- `String testoDaScrivere`: il testo da scrivere sul file.
- `boolean mandaACapo`: se `true`, manda a capo dopo aver scritto il testo.
- `boolean cancellaContenutoPrecedente`: se `true` cancella il contenuto precedente del file, altrimenti scrive in append.

    ```java
    jfm.scrivi("Ciao,");
    jfm.scrivi(" Mondo!", true);
    /* Scrive "Ciao,", concatena " Mondo!" ed, infine, manda a capo */
  
    jfm.scrivi("Ciao, mondo!", true);
    /* Scrive "Ciao, mondo!" e manda a capo */
  
    jfm.scrivi("Ciao, mondo!", true, true);
    /* Scrive "Ciao, mondo!" e manda a capo, cancellando il contenuto precedente del file */
    ```

## Scrivere un oggetto serializzato

La modalità di scrittura su file di un oggetto serializzato utilizza la classe `ObjectOutputStream` e accetta un parametro:
- `Object oggetto`: l'oggetto da scrivere sul file.

   ```java
   jfm.scriviOggettoSerializzato(new Object());
   ```

> [!CAUTION]
>
> L'oggetto deve essere serializzabile. Il file dovrà poi essere opportunamente letto con `ottieniOggettoSerializzato()`.

## Scrivere dati tipizzati

La modalità di scrittura su file di dati tipizzati utilizza la classe `DataOutputStream` e accetta tre parametri:
- `String/int/double/float testoDaScrivere`: il testo da scrivere sul file.
- `boolean mandaACapo`: se `true`, manda a capo dopo aver scritto il testo.
- `boolean cancellaContenutoPrecedente`: se `true` cancella il contenuto precedente del file, altrimenti scrive in append.

   ```java
   jfm.scriviIntTipizzato(1);
   jfm.scriviTestoTipizzato("Ciao, Mondo!");
   ```

> [!CAUTION]
>
> Il file dovrà poi essere opportunamente letto con uno di questi metodi:
> - `ottieniTestoTipizzato()`.
> - `ottieniIntTipizzato()`.
> - `ottieniDoubleTipizzato()`.
> - `ottieniFloatTipizzato()`.
>
> I dati dovranno essere letti necessariamente nello stesso ordine in cui sono stati scritti.

## Leggere nel formato standard

La modalità standard di lettura da file utilizza la classe `BufferedReader`.

   ```java
   String testo = jfm.ottieniTesto();
   ```

## Leggere un oggetto serializzato

La modalità di lettura da file di un oggetto serializzato utilizza la classe `ObjectInputStream`.

   ```java
   Object oggetto = jfm.ottieniOggettoSerializzato();
   ```

> [!CAUTION]
>
> L'oggetto deve essere serializzabile. Il file deve essere stato opportunamente scritto con `scriviOggettoSerializzato()`.

## Leggere dati tipizzati

La modalità di lettura da file di dati tipizzati utilizza la classe `DataInputStream`.

   ```java
   int intero = jfm.ottieniIntTipizzato();
   String testo = jfm.ottieniTestoTipizzato();
   ```

> [!CAUTION]
>
> Il file deve essere stato opportunamente scritto con uno di questi metodi:
> - `scriviTestoTipizzato()`.
> - `scriviIntTipizzato()`.
> - `scriviDoubleTipizzato()`.
> - `scriviFloatTipizzato()`.
>
> I dati devono essere letti necessariamente nello stesso ordine in cui sono stati scritti.

## Licenza d'uso
Questo progetto (e tutte le sue versioni) sono rilasciate sotto la [MB General Copyleft License](LICENSE).