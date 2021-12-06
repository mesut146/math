token{
  NUM: [0-9]+ ("." [0-9]+)?;
  LP: "(";
  RP: ")";
  LB: "[";
  RB: "]";
  DOT: ".";
  COMMA: ",";
  EQ: "=";
  PLUS: "+";
  MINUS: "-";
  STAR: "*";
  SLASH: "/";
  POW: "^";
  BANG: "!";
  PI: "pi" | "Pi" | "PI";
  PHI: "phi" | "Phi" | "PHI";
  E: "e" | "E";
  I: "i";
  INF: "inf" | "INF" | "Inf";
  IDENT: [a-zA-Z] [a-zA-Z0-9_]*;
}

skip{
  WS: " " | "\n" | "\r" | "\t";
}

%start: line;

line: expr ("=" expr)?;


expr: mul rest=(("+" | "-") mul)*;
mul: unary rest=(("*" | "/") unary)*;
unary: "-" unary | pow;
pow: bang rest=("^" unaryOrBang)?;
unaryOrBang: "-" unary | bang;
bang: elem "!"?;
elem: cons | var | funcCall | "(" expr ")";

funcCall: name "(" args? ")";
name: IDENT | PI;
args: line rest=("," line)*;

fx: name "(" fxargs? ")";
fxargs: fxarg ("," fxarg)*;
//fxarg: expr | name "=" expr;
fxarg: var;

cons: E | PI | PHI | I | INF | NUM;
var: IDENT;
