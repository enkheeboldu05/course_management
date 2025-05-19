JAVAC=javac
JAVA=java
SRC=src
BIN=build
LIB=lib
CP=.:$(LIB)/jbcrypt-0.4.jar:/usr/share/java/mysql-connector-java-9.3.0.jar
SOURCES=$(shell find $(SRC) -name "*.java")

all:
	@echo "🔧 Compiling Java sources..."
	@mkdir -p $(BIN)
	$(JAVAC) -cp $(CP) -d $(BIN) $(SOURCES)
	@echo "✅ Compilation finished."

run:
	@echo "🚀 Running Main..."
	$(JAVA) -cp $(CP):$(BIN) src.Main

clean:
	@echo "🧹 Cleaning build directory..."
	rm -rf $(BIN)/*
	@echo "✅ Done."
