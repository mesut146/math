package com.mesut.math.parser;

import java.io.*;
import java.util.*;

public class Lexer {

    static String cMapPacked = "\120\120\5\150\150\3\160\160\1\106\106\2\54\54\31\52\52\36\50\50\33\40\40\34\12\12\35\56\56\32\146\146\7\156\156\11\116\116\13\110\110\17\136\136\30\152\155\25\112\115\26\60\71\0\117\117\12\147\147\6"+
            "\105\105\4\75\75\43\57\57\37\51\51\41\41\41\46\15\15\47\11\11\50\53\53\44\55\55\45\157\157\10\145\145\14\151\151\15\107\107\16\111\111\20\137\137\27\133\133\40\135\135\42\101\104\22\141\144\24\161\172\21"+
            "\121\132\23";
    //input -> input id
    static int[] cMap = unpackCMap(cMapPacked);
    //input id -> regex string for error reporting
    static String[] cMapRegex = {"0-9", "p", "F", "h", "E", "P", "g", "f", "o", "n", "O", "N", "e", "i", "G", "H", "I", "q-z", "A-D", "Q-Z", "a-d", "j-m", "J-M", "_", "^", ",", ".", "(", "\\u0020", "\\n", "*", "/", "[", ")", "]", "=", "+", "-", "!", "\\r", "\\t"};
    int[] skip = {2097152};
    int[] accepting = {-67108866,4095,0};
    //id -> token name
    String[] names = {"EOF","NUM","LP","RP","LB","RB","DOT","COMMA","EQ","PLUS","MINUS","STAR","SLASH","POW","BANG","PI","PHI","E","I","INF",
"IDENT","WS"};
    //state->token id
    int[] ids = {0,1,20,20,17,20,0,18,20,13,7,6,2,0,0,11,12,4,3,5,8,
            9,10,14,21,0,0,20,20,0,20,0,20,15,20,20,20,0,16,0,0,
            19};
    static final int INITIAL = 0;
    static final int EOF = 0;
    Reader reader;
    int yypos = 0;//pos in file
    int yyline = 1;
    int yychar;
    public static int bufSize = 100;
    int bufPos = 0;//pos in buffer
    int bufStart = bufPos;
    int bufEnd;
    char[] yybuf = new char[bufSize];
    static String trans_packed = "\51" +
        "\50\0\1\1\2\2\3\3\3\4\4\5\5\6\3\7\3\10\3\11\3\12\3\13\3\14\4\15\7\16\3\17\3\20\10\21\3\22\3\23\3\24\3\25\3\26\3\30\11\31\12\32\13\33\14\34\30\35\30\36\17\37\20\40\21\41\22\42\23\43\24\44\25\45\26\46\27\47\30\50\30" +
        "\2\0\1\32\0" +
        "\30\0\33\1\33\2\33\3\34\4\33\5\33\6\33\7\33\10\33\11\33\12\33\13\33\14\33\15\41\16\33\17\33\20\33\21\33\22\33\23\33\24\33\25\33\26\33\27\33" +
        "\30\0\33\1\33\2\33\3\33\4\33\5\33\6\33\7\33\10\33\11\33\12\33\13\33\14\33\15\33\16\33\17\33\20\33\21\33\22\33\23\33\24\33\25\33\26\33\27\33" +
        "\30\0\33\1\33\2\33\3\33\4\33\5\33\6\33\7\33\10\33\11\33\12\33\13\33\14\33\15\33\16\33\17\33\20\33\21\33\22\33\23\33\24\33\25\33\26\33\27\33" +
        "\30\0\33\1\33\2\33\3\36\4\33\5\33\6\33\7\33\10\33\11\33\12\33\13\33\14\33\15\41\16\33\17\40\20\41\21\33\22\33\23\33\24\33\25\33\26\33\27\33" +
        "\0" +
        "\30\0\33\1\33\2\33\3\33\4\33\5\33\6\33\7\33\10\33\11\42\12\33\13\33\14\33\15\33\16\33\17\33\20\33\21\33\22\33\23\33\24\33\25\33\26\33\27\33" +
        "\30\0\33\1\33\2\33\3\33\4\33\5\33\6\33\7\33\10\33\11\43\12\33\13\44\14\33\15\33\16\33\17\33\20\33\21\33\22\33\23\33\24\33\25\33\26\33\27\33" +
        "\0" +
        "\0" +
        "\0" +
        "\0" +
        "\0" +
        "\0" +
        "\0" +
        "\0" +
        "\0" +
        "\0" +
        "\0" +
        "\0" +
        "\0" +
        "\0" +
        "\0" +
        "\0" +
        "\0" +
        "\0" +
        "\30\0\33\1\33\2\33\3\33\4\33\5\33\6\33\7\33\10\33\11\33\12\33\13\33\14\33\15\33\16\33\17\33\20\33\21\33\22\33\23\33\24\33\25\33\26\33\27\33" +
        "\30\0\33\1\33\2\33\3\33\4\33\5\33\6\33\7\33\10\33\11\33\12\33\13\33\14\33\15\46\16\33\17\33\20\33\21\33\22\33\23\33\24\33\25\33\26\33\27\33" +
        "\0" +
        "\30\0\33\1\33\2\33\3\33\4\33\5\33\6\33\7\33\10\33\11\33\12\33\13\33\14\33\15\46\16\33\17\33\20\33\21\33\22\33\23\33\24\33\25\33\26\33\27\33" +
        "\0" +
        "\30\0\33\1\33\2\33\3\33\4\33\5\33\6\33\7\33\10\33\11\33\12\33\13\33\14\33\15\33\16\33\17\33\20\46\21\33\22\33\23\33\24\33\25\33\26\33\27\33" +
        "\30\0\33\1\33\2\33\3\33\4\33\5\33\6\33\7\33\10\33\11\33\12\33\13\33\14\33\15\33\16\33\17\33\20\33\21\33\22\33\23\33\24\33\25\33\26\33\27\33" +
        "\30\0\33\1\33\2\33\3\33\4\33\5\33\6\33\7\51\10\33\11\33\12\33\13\33\14\33\15\33\16\33\17\33\20\33\21\33\22\33\23\33\24\33\25\33\26\33\27\33" +
        "\30\0\33\1\33\2\33\3\33\4\33\5\33\6\33\7\51\10\33\11\33\12\33\13\33\14\33\15\33\16\33\17\33\20\33\21\33\22\33\23\33\24\33\25\33\26\33\27\33" +
        "\30\0\33\1\33\2\51\3\33\4\33\5\33\6\33\7\33\10\33\11\33\12\33\13\33\14\33\15\33\16\33\17\33\20\33\21\33\22\33\23\33\24\33\25\33\26\33\27\33" +
        "\0" +
        "\30\0\33\1\33\2\33\3\33\4\33\5\33\6\33\7\33\10\33\11\33\12\33\13\33\14\33\15\33\16\33\17\33\20\33\21\33\22\33\23\33\24\33\25\33\26\33\27\33" +
        "\0" +
        "\0" +
        "\30\0\33\1\33\2\33\3\33\4\33\5\33\6\33\7\33\10\33\11\33\12\33\13\33\14\33\15\33\16\33\17\33\20\33\21\33\22\33\23\33\24\33\25\33\26\33\27\33";
    static int[][] trans = unpackTrans(trans_packed);

    public Lexer(Reader reader) throws IOException{
        this.reader = reader;
        init();
    }

    public Lexer(File file) throws IOException {
        this.reader = new BufferedReader(new FileReader(file));
        init();
    }

    static boolean getBit(int[] arr, int state) {
        return ((arr[state / 32] >> (state % 32)) & 1) != 0;
    }

    static int[][] unpackTrans(String str) {
        int pos = 0;
        int max = str.charAt(pos++);
        List<int[]> list = new ArrayList<>();
        while (pos < str.length()) {
            int[] arr = new int[max];
            Arrays.fill(arr, -1);
            int trCount = str.charAt(pos++);
            for (int input = 0; input < trCount; input++) {
                //input -> target state
                arr[str.charAt(pos++)] = str.charAt(pos++);
            }
            list.add(arr);
        }
        return list.toArray(new int[0][]);
    }

    static int[] unpackCMap(String str){
        int pos = 0;
        int[] arr = new int[0x010FFFF];//covers all code points
        Arrays.fill(arr, -1);//unused chars leads error
        while(pos < str.length()){
            int left = str.charAt(pos++);
            int right = str.charAt(pos++);
            int id = str.charAt(pos++);
            for(int i = left;i <= right;i++){
                arr[i] = id;
            }
      }
      return arr;
    }

    void init() throws IOException{
      reader.read(yybuf, 0, bufSize);
    }

    void fill() throws IOException{
      if(bufPos == yybuf.length){
        char[] newBuf = new char[yybuf.length * 2];
        System.arraycopy(yybuf, 0, newBuf, 0, yybuf.length);
        reader.read(newBuf, bufPos, yybuf.length);
        yybuf = newBuf;
      }
    }

    String getText(){
      return new String(yybuf, bufStart, bufPos - bufStart);
    }

    String findExpected(int from){
        StringBuilder sb = new StringBuilder();
        for(int i = 0 ; i < trans[from].length;i++){
            sb.append(cMapRegex[i]);
            sb.append(",");
        }
        return sb.toString();
    }

    Token getEOF(){
        Token res =  new Token(EOF, "");
        res.name = "EOF";
        return res;
    }

    public Token next() throws IOException {
        Token tok = next_normal();
        if(getBit(skip, tok.type)){
          return next();
        }
        return tok;
    }

    public Token next_normal() throws IOException {
        fill();
        int curState = INITIAL;
        int lastState = -1;
        int startPos = yypos;
        int startLine = yyline;
        yychar = yybuf[bufPos];
        if (yychar == EOF) return getEOF();
        int backupState = -1;
        while (true) {
            fill();
            yychar = yybuf[bufPos];
            if(yychar == EOF){
                curState = -1;
            }else{
                backupState = curState;
                if(cMap[yychar] == -1){
                    throw new IOException(String.format("unknown input=%c(%d) pos=%s line=%d",yychar, yychar, yypos, yyline));
                }
                curState = trans[curState][cMap[yychar]];
            }
            if (curState == -1) {
                if (lastState != -1) {
                    Token token = new Token(ids[lastState], getText());
                    token.offset = startPos;
                    token.name = names[ids[lastState]];
                    token.line = startLine;
                    bufStart = bufPos;
                    return token;
                }
                else {
                    throw new IOException(String.format("invalid input=%c(%d) pos=%s line=%d buf='%s' expecting=%s",yychar,yychar,yypos,yyline,getText(),findExpected(backupState)));
                }
            }
            else {
                if (getBit(accepting, curState)) lastState = curState;
                if(yychar == '\n'){
                    yyline++;
                    if(bufPos > 0 && yybuf[bufPos - 1] == '\r'){
                        yyline--;
                    }
                }
                else if(yychar == '\r'){
                    yyline++;
                }
                bufPos++;
                yypos++;
            }
        }
    }
}
