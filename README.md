> ⚠️ License Notice  
> This project is source-available for viewing only.  
> Usage, modification, or redistribution is prohibited without permission.




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


LyTrix values *meaningful syntax* and *intentional design* over convenience.

---

## 🧠 Language Architecture


### 🔹 LyTrix-R
- Interpreter-based
- Fast iteration and experimentation
- Focused on learning and runtime behavior


## 🧩 Core Language Concepts (Early Versions)

### Entry Function
LyTrix does not use `main()`.

```txt
void BackLine() {
    <statement_1>;
    <statement_2>;
    .
    .
    .
    <statement_n>
    return EOM;
}