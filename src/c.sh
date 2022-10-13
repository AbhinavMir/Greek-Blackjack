javac -d bin *.java
cd bin && java Implementation --off 2 1 10 A 100
find . -type f -path "./src" -name "*.class" -exec rm -f {} \;
