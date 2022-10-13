javac -d bin *.java
cd bin && java Implementation --off
find . -type f -path "./src" -name "*.class" -exec rm -f {} \;
