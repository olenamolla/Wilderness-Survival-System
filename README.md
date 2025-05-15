# Wilderness-Survival-System


This is a simulation game written in Java where multiple players compete to survive and gather resources in a wilderness environment. 
Each player is driven by different strategies and vision types, and the simulation plays out automatically, generating a final game report.

## 💻 How to Run (if you are using Mac)

### 1. Navigate to Project Root
Make sure you're in the root directory of the project:

```bash
cd wilderness-survival-system/
```

### 2. Compile All Java Files (Using zsh)

Enable recursive globbing:

```bash
setopt globstarshort
```

Then compile all Java files:

```bash
javac **/*.java
```

### 3. Run the Simulation


```bash
java -cp . wss.game.Main
```