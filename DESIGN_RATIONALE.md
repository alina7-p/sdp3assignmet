# Design Rationale Document

## 1. Chosen Problem Domain
The project implements a Biometric Access Control & Security Gates System. The system verifies user identities and controls physical door and turnstile locks.

## 2. Architecture & Pattern Justification
The domain naturally divides into two independent axes of variation:
- Verification Logic (Abstraction side): Different security routines (e.g., `MultiFactorVIPRoutine`, `EmergencyEvacuationRoutine`).
- Physical Locks (Implementor side): Different hardware mechanisms (e.g., `RfidTurnstileLock`, `SmartMaglock`, and adapted hardware).

### Why Bridge Alone is Not Enough
Bridge allows the verification routines and lock mechanisms to evolve independently without combinatorial subclass explosion. However, Bridge assumes that all concrete implementations natively conform to the `LockMechanism` interface. In a real-world setting, legacy hardware (`LegacyRS485RelayBoard`) provides an incompatible interface and cannot be modified.

### Why Adapter Alone is Not Enough
Adapter solves interface incompatibility for a single hardware class, but it does not address the multi-dimensional variation of access verification business logic (VIP multi-factor vs. emergency overrides).

### Combined Solution
The Bridge pattern decouples `AccessVerificationRoutine` from `LockMechanism`. The Adapter pattern (`LegacyRelayAdapter`) wraps `LegacyRS485RelayBoard` inside the `LockMechanism` role, seamlessly fitting the incompatible hardware into the Bridge structure.

## 3. Incompatibility Analysis of the Adapted Class
The `LegacyRS485RelayBoard` class is genuinely incompatible with the `LockMechanism` interface in four aspects:
1. Method Signature & Names: It uses `sendRelayCommand(...)` instead of `unlockDoor(...)`.
2. Parameter Types & Units: It accepts a channel index (`int`), HEX commands (`String`), and duration in milliseconds (`long`), whereas `LockMechanism` expects a user ID (`String`) and duration in seconds (`int`).
3. Control Translation: Seconds must be multiplied by 1000 to obtain pulse milliseconds, and abstract unlock requests must be translated into hardware HEX commands (e.g., `"0xA1"`).
4. Exception Handling: The legacy board throws checked `SerialPortCommunicationException` or raises hardware error flags. The adapter intercepts all legacy exceptions and translates them into a unified `DoorControlException` without leaking hardware details.

## 4. Required Complexity Module Choice
Dynamic Implementor Selection (Option A):
The `LockFactory` class dynamically selects and instantiates the appropriate `LockMechanism` implementation (including the adapted legacy board) based on runtime string configuration. The client never directly instantiates concrete lock classes.

## 5. Limitation of the Design
The current `LegacyRelayAdapter` assumes a fixed channel configuration (`defaultChannel`) and a static HEX command set per adapter instance. Supporting dynamic channel routing for multi-door relay boards would require additional mapping logic within the adapter or dynamic parameter passing.