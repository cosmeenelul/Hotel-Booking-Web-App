# 🏨 Hotel Booking Web App (Golden Peak Hotel)
A full-stack hotel booking system built with Spring Boot, MySQL, HTML/CSS/JS. It includes an admin panel for managing rooms, clients, and reservations, and a user interface for booking. The system also provides statistics like top rooms and total revenue.


# RO LANGUAGE <img src="https://flagcdn.com/w40/ro.png" width="20" alt="Romania Flag">

---

## 📸 Demo

*(Adaugă aici un link YouTube sau un GIF cu aplicația în acțiune)*

---

## ⚙️ Tehnologii folosite

- **Backend**: Spring Boot, Spring MVC, Spring Security, Spring Data JPA, Java Mail, Thymeleaf
- **Frontend**: Bootstrap, JavaScript
- **Bază de date**: MySQL
- **Altele**: BCrypt, Token URI Confirmation, Maven
- **Build & Deploy**: Maven + Spring Boot CLI

---

## 🧱 Arhitectură

![Arhitectura aplicației](docs/architecture.png)

*(Schema logică: Client → Controller → Service → Repository → DB)*

---

## 🗄️ Diagrama Bazei de Date

![Diagrama baza de date](src/main/resources/static/img/erd.webp)

---

## 🧩 Funcționalități principale

### 👤 Utilizator

- ✅ **Înregistrare** cont cu token de confirmare email (cu expirare)
- ✅ **Autentificare** + logout
- ✅ Criptare parolă cu **BCrypt**
- ✅ **Creare rezervare** (cu generare factură pe email)
  - **Codul** de **confirmare** este inclus în factură și poate fi folosit:
    - pentru a *căuta rezervarea*
    - sau pentru a o *furniza la recepția hotelului spre modificare sau anulare*
- ✅ **Vizualizare camere** disponibile filtrate după:
  - *Număr persoane*
  - Dată *check-in / check-out*
- ✅ **Editare + ștergere cont**

---

### 🔐 Administrator

- ✅ **GET + Filtrare** in funcție de Data Check-in/Check-out si nr. persoane
- ✅ **CRUD** complet **oaspeți**
- ✅ **CRUD rezervări**
- ✅ Pagină de **statistici**:
  - *Top 3 camere* care au adus *cei mai mulți bani*
  - Cei mai *fideli oaspeți* (după nr. de rezervări)
- ✅ Pagină cu **rezervări active** (în funcție de data curentă)

---

## 🧠 Logica disponibilității camerelor

- ✅ Query personalizat în backend pentru a verifica camerele disponibile în intervalul ales și pentru nr. de persoane
- ✅ Selectarea camerelor dintr-un formular interactiv (bara de selecție), printr-un GET pe un endpoint
- ✅ Rezervările sunt verificate în timp real pentru suprapuneri
- ✅ Corner case-uri in cazul in care user-ul nu iși activează contul este automat șters din DB după 10 minute

---

## 📁 Structura proiectului

```bash
## 🌲 Structura proiectului

```text
BookingSystem/
├── .idea/
├── .mvn/
├── pozeCamere/
├── src/
│   └── main/
│       ├── java/
│       │   └── com/example/AdminDashboard/
│       │       ├── Configuration/
│       │       ├── Controller/
│       │       ├── Converter/
│       │       ├── DTO/
│       │       ├── Entity/
│       │       ├── Event/
│       │       ├── Exception/
│       │       ├── Repository/
│       │       ├── Security/
│       │       ├── Service/
│       │       ├── Utils/
│       │       └── AdminDashboardApplication.java
│       └── resources/
│           ├── static/
│           └── templates/

