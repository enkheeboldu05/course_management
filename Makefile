# Makefile for Java User Auth + GUI + DB

# Tools and directories
JAVAC=javac
JAVA=java
SRC=src
BIN=build
LIB=lib
CP=.:$(LIB)/jbcrypt-0.4.jar:/usr/share/java/mysql-connector-java-8.0.33.jar

# Get all .java source files
SOURCES=$(shell find $(SRC) -name "*.java")

# Compile target: compile everything into build/
all:
	@echo "🔧 Compiling Java sources..."
	@mkdir -p $(BIN)
	$(JAVAC) -cp $(CP) -d $(BIN) $(SOURCES)
	@echo "✅ Compilation finished."

# Run the application (Main class)
run:
	@echo "🚀 Running Main..."
	$(JAVA) -cp $(CP):$(BIN) src.Main

# Clean all .class files
clean:
	@echo "🧹 Cleaning build directory..."
	rm -rf $(BIN)/*
	@echo "✅ Done."
