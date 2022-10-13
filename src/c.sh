javac -d bin Round.java
javac -d bin Implementing.java
cd bin && java Implementing --off
find . -type f -path "./src" -name "*.class" -exec rm -f {} \;
