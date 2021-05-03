# Chat App

## Összefoglaló
A Chat App programban a belépett felhasználók cseveghetnek egymással, a számukra szimpatikus szobákban.

## Futtatás
 1. chat_app_core -> src -> main -> resources -> application.properties fájlban az adatbázis url-jét le kell cserélni
 2. chat_app (root) -> Lifecycle -> install
 3. chat_app_desktop -> Plugins -> javafx -> javafx:run (Asztali alkalmazás futtatása)
 4. tomcat server -> run (Webes alkalmazás futtatása) 

## Asztali alkalmazás
Asztali alkalmazásként az adminok tudják használni. Akiknek joguk van szobákat létrehozni, listázni, törölni és frissíteni is, a szobákhoz szabályokat is tudnak csatolni, módosítani és törölni.
Az adminok a beregisztrált felhasználókat is képesek látni, akiket törölni is tudnak.

## Webes alkalmazás
### Regisztráció & Bejelentkezés
Itt lehetőségük van a felhasználóknak regisztrálni, majd bejelentkezni a Chat App-ba. Hibás regisztrációs adatok megadásakor vagy hibás bejelentkezéskor is a felhasználó számára visszajelzést ad a program.  
### Menü & Keresés
Miután bejelentkeztünk egy menü jön be, ahol láthatjuk a saját adatainkat, a Chat App-ba regisztrált felhasználókat, és szobákat is. Utóbbi kettőben keresni is tudunk előbbinél `felhasználónév` és `érdeklődési kör` alapján, míg utóbbinál `szobanév` és `kategória` alapján.
### Chatszoba 
A számunkra tetszőleges szobához tudunk csatlakozni, ahol megjelennek a szoba adatai és szabályai. Emellett megjelenik a lelke is az alkalmazásnak a chat ablak is ahol, chatelni tudunk a szobához csatlakozott felhasználókkal. A saját üzeneteink a jobb oldalon kék alapon jelennek meg, még másé névvel ellátva a bal oldalon fekete alapon. Ha üzenetet szeretnénk küldeni azt azonnal látni fogjuk a chat ablakban, viszont ha látni szeretnénk mit küldött más szükségünk van egy frissítésre. A beszélgetés history-ja mindenki számára elérhető az adott szobában, viszont minden alkalommal mikor csatlakozunk a szobához, a legfrissebb üzeneteket fogjuk látni.