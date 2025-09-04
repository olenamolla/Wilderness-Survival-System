# Wilderness-Survival-System

This is a simulation game written in Java where multiple players compete to survive and gather resources in a wilderness environment. 
Each player is driven by different strategies and vision types, and the simulation plays out automatically, generating a final game report.

## 📁 Project Files

### Core Game Files
- **Source Code**: Located in package directories (`game/`, `brain/`, `vision/`, `player/`, `map/`, `trader/`, `item/`, `util/`)
- **Documentation**: Generated JavaDoc documentation in `docs/` directory

### Additional Documentation
- **`wss-diagrams.pdf`**: System architecture diagrams and design documentation - check this file for visual representations of the game's structure, class relationships, and system design
- **`wss-output.txt`**: Sample game execution output - check this file to see how the simulation runs, including player movements, resource gathering, trading interactions, and final game reports

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

## 🎮 Game Features

- **Multiple Personalities**: Choose from Greedy, Social, or Survival strategies
- **Different Vision Types**: Cautious, Greedy, or Extended vision ranges
- **Dynamic Map Generation**: Procedurally generated terrain with varying difficulty levels
- **Trading System**: Interact with different trader types (Regular, Greedy, Impatient)
- **Resource Management**: Manage food, water, gold, and strength while navigating challenging terrain

## 📊 Sample Output

After running the simulation, you can review the detailed game output in `wss-output.txt` to see:
- Map generation process
- Player decision-making and movements
- Resource collection and trading interactions
- Final game statistics and winner determination