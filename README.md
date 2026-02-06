# LyTrix Programming Language

LyTrix is a personal, experimental programming language project designed and built from scratch.
It began as **MinLang** (an interpreter-focused learning project) and later evolved into **LyTrix**, a language with a clear identity, philosophy, and long-term goal: **system-level and OS-oriented development**.

LyTrix is not a toy language.
It is a learning-driven, architecture-first language project focused on understanding how real languages, runtimes, and compilers work.

---

## ✨ Philosophy

LyTrix is built with the following principles:

- Clear structure (Java-inspired readability)
- Explicit control flow
- Strong separation between **meta-code** and **runtime code**
- Minimal magic, maximum understanding
- Designed for future OS-level experimentation

LyTrix values *meaningful syntax* and *intentional design* over convenience.

---

## 🧠 Language Architecture

LyTrix has two planned editions:

### 🔹 LyTrix-R
- Interpreter-based
- Fast iteration and experimentation
- Focused on learning and runtime behavior

### 🔹 LyTrix-Q
- Compiler-based (QBE backend)
- Native machine code generation
- Long-term OS and low-level development goals

---

## 🧩 Core Language Concepts (Early Versions)

### Entry Function
LyTrix does not use `main()`.

```txt
void BackLine() {
    return EOM;
}