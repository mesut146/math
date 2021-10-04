token{
  NUM: [0-9] ("." [0-9])?;
  IDENT: [a-zA-z] [a-zA-Z0-9_]*;
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
}

skip{
  WS: " " | "\n" | "\r" | "\t";
}

eq: lhs "=" expr | expr;
lhs: var | funcCall;

expr: term rest=(("+" | "-") expr)*;
term: pow rest=(("*" | "/") term)*;
pow: unary rest=("^" pow)*;
unary: "-" elem | elem "!";
elem: cons | var | funcCall | "(" expr ")";
funcCall: IDENT "(" args? ")";
args: expr rest=("," expr)*;
cons: E | PI | PHI | I | INF | NUM;
var: IDENT;
