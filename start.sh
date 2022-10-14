cd src
javac -d bin *.java
cd bin && java Implementation --off 2 1 10 A B
find . -type f -path "./src" -name "*.class" -exec rm -f {} \;
