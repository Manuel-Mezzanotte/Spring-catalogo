# Catalogo Anime - Progetto Web in Spring Boot

Applicazione web sviluppata in Spring Boot per la gestione di un catalogo tematico dedicato agli Anime. Il progetto rispetta le specifiche richieste (aggiunta, rimozione, alert di conferma), offrendo inoltre un'esperienza visiva moderna e curata (layout a griglia di card) abbinata a funzioni extra come la ricerca in tempo reale e l'ordinamento dinamico.

## Tecnologie Utilizzate
- **Backend:** Java, Spring Boot (Starter Web, Data JPA, Security)
- **Frontend:** HTML5, Thymeleaf, TailwindCSS, Alpine.js
- **Database:** MySQL
- **Utility:** Maven, Lombok, Spring Boot DevTools

## Installazione e Setup

### 1. Prerequisiti
Per far girare il progetto in locale, assicurati di avere:
- **Java JDK**
- **MySQL Server** 
- **Git** 

### 2. Configurazione Database (Importante)
Prima di lanciare l'applicazione, crea semplicemente un database/schema vuoto all'interno della tua istanza MySQL:
```sql
CREATE DATABASE catalogo_anime;
```
Di default, l'applicazione (`application.properties`) proverà a connettersi usando le seguenti credenziali MySQL:
- **Username:** `root`
- **Password:** `root`
> Nota: Se il tuo database root ha una password diversa, ricordati di aggiornarla nel file `src/main/resources/application.properties`.

### 3. Clonazione e Avvio
Apri il terminale ed esegui i seguenti comandi:

1. Clona la repository ed entra nella cartella:
   ```bash
   git clone <URL_REPO_GITHUB>
   cd Spring-catalogo
   ```
2. Avvia l'applicazione con il wrapper di Maven integrato:
   ```bash
   # Su macOS / Linux
   ./mvnw spring-boot:run

   # Su Windows
   .\mvnw.cmd spring-boot:run
   ```

### 4. Accesso al Sistema
Spring Boot, tramite Hibernate JPA, creerà automaticamente le tabelle necessarie nel DB `catalogo_anime`.

Inoltre, **al suo primo avvio**, uno script interno di inizializzazione creerà in automatico un **profilo amministratore di default**. 

Apri il browser alla pagina **[http://localhost:8080](http://localhost:8080)** e accedi inserendo:
- **Username:** `admin`
- **Password:** `admin`

Una volta immesse queste credenziali potrai iniziare a popolare e gestire il catalogo liberamente.
