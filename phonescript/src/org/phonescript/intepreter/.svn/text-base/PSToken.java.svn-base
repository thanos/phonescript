package org.phonescript.intepreter;

final public class PSToken {
	Object mValue;
	int mType;
	

	PSToken(int type, Object value) {
		mType = type;
		mValue = value;
	}

	public String toString() {
		return "<" + mType +":"  + mValue+">";
	}

	static final byte NAME		= 1;
	static final byte INTEGER 	= 2;
	static final byte REAL 		= 3;
	static final byte LITERALNAME		= 4;
	static final byte IMEDIATENAME		= 5;
	
	
	
//	static final int EQUALS 	= 4;
//	static final int NEWLINE 	= 5;
//	static final int LPARAM 	= 6;
//	static final int RPARAM 	= 7;
//	static final int NL 		= 8;
//	static final int LBRACKET 	= 9;
//	static final int RBRACKET 	= 10;
//	static final int LBRACE 	= 11;
//	static final int RBRACE 	= 12;
//	static final int LANGLE 	= 13;
//	static final int RANGLE 	= 14;
//	static final int COLON 		= 15;
//	static final int DOT 		= 16;
//	static final int OP 		= 17;
	static final byte CHAR 		= 6;
	static final byte STRING 	= 7;
	static final byte PROC 	= 8;
	
	static final char ETX = 3;
	static final byte EOF 		= -1;
	static final int UNKNOWN 	= -99;
	
	
	static final byte KEYWORD_START	= 10;
	static final byte ADD 		= 11;
	static final byte SUB 		= 12;
	static final byte MUL 		= 13;
	static final byte DIV 		= 14;
	static final byte IDIV 		= 15;
	static final byte ABS 		= 16;
	static final byte NEG 		= 17;
	static final byte CEILING 	= 18;
	static final byte FLOOR 	= 19;
	static final byte ROUND 	= 20;
	static final byte TRUNCATE 	= 21;
	static final byte POW 		= 22;
	static final byte SQRT	 	= 23;
	static final byte ATAN	 	= 24;
	static final byte COS	 	= 25;
	static final byte SIN	 	= 26;
	static final byte EXP 		= 27;
	static final byte LN	 	= 28;
	static final byte LOG	 	= 29;
	static final byte MOD 		= 30;
	
	static final byte DUP 			= 31;
	static final byte EXCH 			= 32;
	static final byte POP 			= 33;
	static final byte COPY 			= 34;
	static final byte ROLL 			= 35;
	static final byte INDEX 		= 36;
	static final byte MARK 			= 37;
	static final byte CLEAR 		= 38;
	static final byte COUNT 		= 39;
	static final byte COUNTTOMARK 	= 40;
	static final byte CLEARTOMARK 	= 41;	
	
	static final byte EQ 			= 42;
	static final byte NE 			= 43;
	static final byte GT 			= 44;
	static final byte GE 			= 45;
	static final byte LT 			= 46;
	static final byte LE	 		= 47;
	
	static final byte AND 			= 48;
	static final byte OR 			= 49;
	static final byte XOR 			= 50;
	static final byte FALSE 		= 51;	
	static final byte TRUE 			= 52;	
	static final byte NOT 			= 53;
	
	static final byte ARRAY_START	= 54;
	static final byte ARRAY_END		= 55;
	
	static final byte EXECUTION_ARRAY_START		= 56;
	static final byte EXECUTION_ARRAY_END		= 57;
	static final byte EXEC		= 58;
	static final byte CVX		= 59;
	static final byte FILE		= 60;
	static final byte READ		= 61;
	static final byte IF		 = 62;
	static final byte IFELSE	 = 63;
	static final byte FOR		 = 64;
	static final byte FOREACH	 = 65;
	static final byte DEF	 = 66;
	
	static final String []KEYWORDS = {
		"",
		"add",
		"sub",
		"mul",
		"div",
		"idiv",
		"abs",
		"neg",
		"ceiling",
		"floor",
		"round",
		"truncate",
		"pow",
		"sqrt",
		"atan",
		"cos",
		"sin",
		"exp",
		"ln",
		"log",
		"mod",
		
		"dup",
		"exch",
		"pop",
		"copy",
		"roll",
		"index",
		"mark",
		"clear",
		"count",
		"counttomark",
		"cleartomark",
		
		"eq",
		"ne",
		"gt",
		"ge",
		"lt",
		"le",
		
		"and",
		"or",
		"xor",
		"true",
		"false",
		"not",
		
		"[",
		"]",
		"{",
		"}",
		"exec",
		"cvx",
		"file",
		"read",
		
		"if",
		"ifelse",
		"for",
		"foreach",
		"def",
		
		
	};
	


	
//	static PSToken lookupSYMBOL(CharBuffer buffer, char  ch) {
//		for (int i =0; i < SYMBOLS.length; i +=2)
//				if (ch == SYMBOLS[i]) {
//					buffer.consume();
//					return new PSToken(SYMBOLS[i+1], Character.toString((char)ch));
//				}
//		return null;
//	}	
	
	static PSToken lookupKEYWORD(String keyword) {
		for (int i =0; i < KEYWORDS.length; i ++)
				if (KEYWORDS[i].equals(keyword)) {
					return new PSToken(i+KEYWORD_START, KEYWORDS[i]);
				}
		return null;
	}	
	
	
	static boolean isOp(int ch) {
		return  ((ch >= '*' && ch <= '-') || ch == '%' || ch == '&' || ch == '~' || ch =='^' || ch =='/' && ch =='>' || ch =='<');
	}
	

	
	static PSToken getToken(PSInterpreter interpreter, CharBuffer buffer) {
		if (buffer.stack.top() != null)
			return (PSToken)buffer.stack.pop();
		
		PSToken token = null;
		while (token == null) {
			int ch = buffer.peek(1);
			while (ch != EOF && Character.isWhitespace((char) ch)) {
				buffer.consume();
				ch = buffer.peek(1);
			}
			if (ch ==  CharBuffer.ETX)
				return new PSToken(EOF,"<eof>");
			
			if (ch == '%') {
				skipCOMMENT(buffer, (char) ch);
				continue;
			}
			if (ch == '(')
				return processSTRING(buffer, (char) ch);
			
			if (ch == '/')
				if (buffer.peek(2) == '/')
					return processName(buffer, (char) ch, IMEDIATENAME, interpreter);
				else
					return processName(buffer, (char) ch, LITERALNAME, interpreter);
			
			if ( (ch =='-' || Character.isDigit((char)ch)) && (token = processNUMBER(buffer, (char) ch)) != null)
				return token;
			
			if ( (token = processName(buffer, (char) ch, NAME, interpreter)) != null)
				return token;
//
//			
//			
//			
//			
//			if (isOp(ch)) 
//				return processOP(buffer, (char) ch);
//			if ((token =  lookupSYMBOL(buffer,  (char) ch)) != null)
//					return token;
//			if (Character.isLetter((char) ch)) 
//				return processIDENTIFIER(buffer, (char) ch);
//				
//			if (ch == '\'')
//				return processCHAR(buffer, (char) ch);
			
		}
		return token;
	}
	
	
	
	
	private static PSToken processName(CharBuffer buffer, char ch, int type, PSInterpreter interpreter) {
		StringBuffer nameStr = new StringBuffer();
		do {
			nameStr.append(ch);
			buffer.consume();
			ch = (char) buffer.peek(1);
		} while (false == Character.isWhitespace(ch) && ch != '%' && ch != CharBuffer.ETX);
		String name = nameStr.toString();
		if (type == IMEDIATENAME && interpreter.names.containsKey(name) )
			return new PSToken(type, interpreter.names.get(name));
		PSToken token = lookupKEYWORD(name);
		if (token != null)
			return token;
		return  new PSToken(type, name);
	}

	private static PSToken processLiteralName(CharBuffer buffer, char c) {
		return null;
	}

	
	private static PSToken processSTRING(CharBuffer buffer, char ch) {
		StringBuffer str = new StringBuffer();
		buffer.consume();
		int parentheses = 0;
		
		while (true) {
			ch = (char) buffer.peek(1);
			if (ch == CharBuffer.ETX)
				break;
			if (ch ==')')
				if (parentheses > 0)
					parentheses--;
				else
					break;
			if (ch =='(')
				parentheses++;
			if (ch == '\\') 
				if (buffer.peek(2) =='\n') {
					buffer.consume();buffer.consume();
					continue;
				} else {
					str.append(ch);
					buffer.consume();
					ch = (char) buffer.peek(1);
				}
				str.append(ch);
				buffer.consume();
			}
			buffer.consume();
		return new PSToken(STRING, str.toString());
	}

//	private static PSToken processCHAR(CharBuffer buffer, char ch) {
//		StringBuffer str = new StringBuffer();
//		buffer.consume();
//		ch = (char) buffer.peek(1);
//		if (ch != '\'') {
//			if (ch == '\\') {
//				str.append(ch);
//				buffer.consume();
//				ch = (char) buffer.peek(1);
//			}
//		str.append(ch);
//		buffer.consume();
//		} 
//		buffer.consume();
//		return new PSToken(CHAR, str.toString());
//	}

//	private static PSToken processOP(CharBuffer buffer, char ch) {
//		StringBuffer opStr = new StringBuffer();
//		boolean real = false;
//		opStr.append(ch);
//		buffer.consume();
//		ch = (char)buffer.peek(1);
//		if (ch =='=' || ch == ch) {
//				opStr.append(ch);
//				buffer.consume();
//		}
//		return new PSToken(OP, opStr.toString());
//	}
	
	private static PSToken processNUMBER(CharBuffer buffer, char ch) {
		StringBuffer numstr = new StringBuffer();
		boolean real = false;
		do {
			numstr.append(ch);
			buffer.consume();
			ch = (char) buffer.peek(1);
			if (ch == '.')
				if (real == false) {
					real = true;
					numstr.append(ch);
					buffer.consume();
					ch = (char) buffer.peek(1);
				}
		} while (Character.isDigit(ch));
		real = true;
		return real ? new PSToken(REAL,  new Double(numstr.toString())) : new PSToken( INTEGER, new Integer(numstr.toString()));
	}

//	private static PSToken processIDENTIFIER(CharBuffer buffer, char ch) {
//		StringBuffer idStr = new StringBuffer();
//
//		do {
//			idStr.append(ch);
//			buffer.consume();
//			ch = (char) buffer.peek(1);
//		} while (Character.isLetterOrDigit(ch));
//		String id = idStr.toString();
//		PSToken token = lookupKEYWORD(id);
//		if (token == null)
//			token =  new PSToken(IDENTIFIER, id);
//		return token;
//	}

	private static void skipCOMMENT(CharBuffer buffer, char ch) {
		do {
			buffer.consume();
			ch = (char) buffer.peek(1);
		} while (ch != '\n' && ch != CharBuffer.ETX);
		buffer.consume();
	}

	public static PSToken process(CharBuffer buffer) {
		return null;
	}
}
