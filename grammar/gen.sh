export pkg="com.mesut.math.parser"
export out="../src/main/java/com/mesut/math/parser"
java -jar parserx*.jar -desc -in math.g -lang java -package $pkg -out $out
