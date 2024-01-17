# JavaFileManager

> [!NOTE]
>
> Versione 1.0.0
> 
> Autore: [**Matteo Bagnoletti Tini**](https://github.com/matbagnoletti)

## Descrizione
Questo progetto ha lo scopo di creare una classe (`JavaFileManager`) che permetta l'input e output in Java da e verso file. L'obiettivo è quello di rendere l'utilizzo degli stream più semplice e veloce, sfruttando l'incapsulamento Java per nasconderne all'utente la complessità.

## Utilizzo

1. Creare un'istanza della classe `JavaFileManager`:
   
   Il costruttore accetta due parametri:
   - `boolean mostraAvvisi`: se `true`, mostra eventuali avvisi ed errori a video.
   - `String URL`: Il percorso dalla root del file da gestire.
   
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

2. Scrivere su un file:
   
   JFM permette di scrivere su un file in tre modi:
   - formato standard (con `PrintWriter`):
   
        ```java
        jfm.scrivi("Ciao, mondo!");
        ```
     
   - formato oggetto serializzato (con `ObjectOutputStream`):
   
        ```java
        jfm.scriviOggettoSerializzato(new Object());
        ```
     > [!CAUTION]
     > 
     > L'oggetto deve essere serializzabile. Il file dovrà poi essere opportunamente letto con `ottieniOggettoSerializzato()`.
     
   - formato dati tipizzati (con `DataOutputStream`):
   
        ```java
        jfm.scriviTipizzato(1);
        ```
        > [!CAUTION]
        >
        > Il file dovrà poi essere opportunamente letto con `ottieni[Testo/Int/Double/Float]Tipizzato()`.

3. Leggere da un file:

    JFM permette di leggere da un file in tre modi:

    - formato standard (con `BufferedReader`):
    
        ```java
        String testo = jfm.leggi();
        ```
      
    - formato oggetto serializzato (con `ObjectInputStream`):
    
        ```java
        Object oggetto = jfm.ottieniOggettoSerializzato();
        ```
      > [!CAUTION]
      >
      > Il file deve essere stato scritto con `scriviOggettoSerializzato()`.
      
    - formato dati tipizzati (con `DataInputStream`):
    
        ```java
        int intero = jfm.ottieniIntTipizzato();
        ```
      > [!CAUTION]
      >
      > Il file deve essere stato scritto con `ottieni[Testo/Int/Double/Float]Tipizzato()`. I dati devono essere letti necessariamente secondo l'ordine in cui sono stati scritti.
   
> [!CAUTION]
> 
> Ricorda che la classe `JavaFileManager` è thread-safe, quindi può essere utilizzata in ambienti multi-thread senza problemi. Tuttavia, l'uso eccessivo della sincronizzazione può portare a una diminuzione delle prestazioni, quindi utilizzala con attenzione.

## Licenza d'uso
Questo progetto (e tutte le sue versioni) sono rilasciate sotto la [**MB General Copyleft License**](LICENSE).