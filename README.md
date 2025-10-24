# 🚢 Battleship Game (JavaFX)

> 🎯 A modern version of the classic **Battleship** strategy game, built entirely with **JavaFX**.  
> Designed as a university project to demonstrate **object-oriented programming**, **GUI design**, and **JavaFX event handling**.

---

## ✨ Features

🟦 Interactive grid-based gameplay  
🧠 Smart logic for hits, misses, and ship placement  
🎨 Custom UI styling via `style.css`  
🪟 Fully graphical interface (JavaFX)  
🎯 Dynamic button hover effects & color theming  
💬 Real-time player feedback and game status  
🧩 Modular class design for easy expansion  

---

## 🗂️ Project Structure

Battleship Game/
│
├── BattleshipsGUI.java # Main GUI class (JavaFX Application)
├── BattleshipsGame.java # Core game logic
├── BattleshipCell.java # Represents an individual cell
├── BattleshipTester.java # Test / console runner
├── style.css # JavaFX custom styles
└── README.md # This file

---

## ⚙️ Requirements

| Tool | Version | Notes |
|------|----------|-------|
| ☕ Java | 17 or higher | Tested on JDK 23 |
| 🎨 JavaFX SDK | 25.0.1 | [Download here](https://gluonhq.com/products/javafx/) |
| 🧩 IDE | VS Code / IntelliJ / Eclipse | With JavaFX configured |

---

## 🚀 How to Run

### 🧭 Step 1 — Setup JavaFX SDK

1. Download JavaFX SDK (25.0.1) and extract to:
    C:\javafx-sdk-25.0.1

2. Ensure the folder contains:
    C:\javafx-sdk-25.0.1\lib\javafx.controls.jar
    C:\javafx-sdk-25.0.1\lib\javafx.fxml.jar


---

### 💻 Step 2 — Compile and Run from Terminal

```bash
cd "C:\Users\<yourname>\CODING PROJECTS\University Projects\Battleship Game"

javac --module-path "C:\javafx-sdk-25.0.1\lib" --add-modules javafx.controls,javafx.fxml *.java

java --module-path "C:\javafx-sdk-25.0.1\lib" --add-modules javafx.controls,javafx.fxml BattleshipsGUI
