javac Round.java
javac Implementation.java
javac Main.java
java Implementation --off 2 10
find . -type f -path "*" -name "*.class" -exec rm -f {} \;
