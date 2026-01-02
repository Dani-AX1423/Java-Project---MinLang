#!/bin/bash
# ------------- CHECK ARGS ----------------
if [ $# -ne 2 ]; then
    echo "Usage: ./JVM_run.sh <JavaFileName.java> <BinFolderName>"
    exit 1
fi

JAVA_NAME="$1"
BIN_NAME="$2"

# ------------- FIND THE .JAVA FILE ----------------
JAVA_PATH=$(find . -type f -name "$JAVA_NAME" | head -n 1)

if [ -z "$JAVA_PATH" ]; then
    echo "Error: Could not find '$JAVA_NAME' anywhere in project."
    exit 1
fi

echo "[+] Found Java file at: $JAVA_PATH"

# ------------- FIND THE OUTPUT BIN FOLDER ----------------
OUT_DIR=$(find ./bin -type d -name "$BIN_NAME" | head -n 1)

if [ -z "$OUT_DIR" ]; then
    echo "Error: Could not find bin folder named '$BIN_NAME' under ./bin"
    exit 1
fi

echo "[+] Using output folder: $OUT_DIR"

# ------------- EXTRACT CLASS NAME ----------------
FILENAME=$(basename "$JAVA_PATH")
CLASSNAME="${FILENAME%.*}"

# ------------- COMPILE ----------------
echo "[+] Compiling $FILENAME → $OUT_DIR"
javac -d "$OUT_DIR" $(find src -name "*.java")



#echo "[+] Compiling $FILENAME → $OUT_DIR"
#javac -d "$OUT_DIR" \
#  src/org/dani/lytrix/core/frontend/scanner/streams/*.java \
#  src/org/dani/lytrix/core/frontend/scanner/tokens/*.java \
#  src/org/dani/lytrix/core/frontend/scanner/Lexing_Process/*.java \
#  src/org/dani/lytrix/core/api/cli/*.java


#javac -d "$OUT_DIR" "$JAVA_PATH"

if [ $? -ne 0 ]; then
    echo "[!] Compilation failed!"
    exit 1
fi

echo "[+] Compilation successful!"

# ------------- CHECK PACKAGE ----------------
PKG_LINE=$(grep "^package " "$JAVA_PATH" 2>/dev/null)

if [ -n "$PKG_LINE" ]; then
    PKG=$(echo "$PKG_LINE" | sed 's/package //; s/;//')
    FQCN="$PKG.$CLASSNAME"
    echo "[+] Detected package: $PKG"
    echo "[+] Running class: $FQCN"
    java -cp "$OUT_DIR" "$FQCN"
else
    echo "[+] Running class: $CLASSNAME"
    java -cp "$OUT_DIR" "$CLASSNAME"
fi

