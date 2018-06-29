package org.phonescript.intepreter;

import java.io.IOException;
import java.net.MalformedURLException;

public class PSInterpreter implements Intepreter{	
	Stack stack = new Stack();
	Stack arrays = new Stack();	
	Stack loops = new Stack();	
	boolean stackMode = false;
	final static Double ZERO = new Double(0);
	public void exec(File codeFile) throws MalformedURLException, IOException {
		CharBuffer buffer = new CharBuffer(codeFile ,2);
	
		while(true) {
			PSToken token = PSToken.getToken(buffer);
			if (stackMode)
				if (token.mType != PSToken.EXECUTION_ARRAY_END) 
					stack.push(token);
				else {
					Object proc_marker = arrays.pop();
					stack.push(new PSToken(PSToken.PROC, stack.popTo(proc_marker)));
					stackMode = false;
				}
			else {
			switch(token.mType) {
			case PSToken.EOF:
					return;
					
			// Stack Operators
			case PSToken.DUP:
				stack.dup();
				break;
			case PSToken.EXCH:
				stack.exch();
				break;
			case PSToken.POP:
				stack.pop();
				break;
			case PSToken.COPY:
				stack.copy();
				break;
			case PSToken.ROLL:
				stack.roll();
				break;
			case PSToken.INDEX:
				stack.index();
				break;
			case PSToken.MARK:
				stack.mark();
				break;
			case PSToken.CLEAR:
				stack.clear();
				break;
			case PSToken.COUNT:
				stack.count();
				break;
			case PSToken.COUNTTOMARK:
				stack.countToMark();
				break;
			case PSToken.CLEARTOMARK:
				stack.clearToMark();
				break;
					
			// Mathematical Operators
			
			case PSToken.ADD:
			case PSToken.SUB:
			case PSToken.MUL:
			case PSToken.DIV:
			case PSToken.IDIV:
	
			case PSToken.MOD:
				double y = ((Double)stack.pop()).doubleValue();
				double x = ((Double)stack.pop()).doubleValue();
				double result = 0;
				switch (token.mType) {
			case PSToken.ADD:
				result = x+y;
				break;
			case PSToken.SUB:
				result = x-y;
				break;
			case PSToken.MUL:
				result = x*y;
				break;
			case PSToken.DIV:
				result = x/y;
				break;
			case PSToken.IDIV:
				result = x/y;
				break;
			case PSToken.MOD:
				result = x % y;
				break;
			case PSToken.POW:
				result = Math.pow(x,y);
				break;

				
				}
				stack.push(new Double(result));
				break;
				
			case PSToken.ABS:
			case PSToken.NEG:
			case PSToken.CEILING:
			case PSToken.FLOOR:
			case PSToken.TRUNCATE:
			case PSToken.SIN:
			case PSToken.COS:
			case PSToken.EXP:
			case PSToken.LOG:
			case PSToken.LN:
			case PSToken.ATAN:
				double value = ((Double)stack.pop()).doubleValue();
				switch(token.mType) {
				case PSToken.ABS:
					value = Math.abs(value);	
					break;
				case PSToken.NEG:
					value = -value;	
					break;
				case PSToken.CEILING:
					value = Math.ceil(value);
					break;
				case PSToken.FLOOR:
					value = Math.floor(value);
					break;
				case PSToken.TRUNCATE:
					value = Math.floor(value);
					break;
				case PSToken.SIN:
					value = Math.sin(value);
					break;
				case PSToken.ROUND:
					value = Math.round(value);
					break;
				case PSToken.COS:
					value = Math.cos(value);
					break;
				case PSToken.EXP:
					value = Math.exp(value);
					break;
				case PSToken.LOG:
					value = Math.log(value);
					break;
				case PSToken.LN:
					value = Math.log(value);
					break;
				case PSToken.ATAN:
					value = Math.atan(value);
					break;	
				case PSToken.SQRT:
					value = Math.atan(value);
					break;	
				}	
				stack.push(new Double(value));
				break;
			case PSToken.LE:
			case PSToken.LT:
			case PSToken.GT:
			case PSToken.GE:
				y = ((Double)stack.pop()).doubleValue();
				x  = ((Double)stack.pop()).doubleValue();
				switch(token.mType) { 
				case PSToken.LE:
					stack.push(Boolean.valueOf(x <= y));
					break;
				case PSToken.LT:
					stack.push(Boolean.valueOf(x < y));
					break;
				case PSToken.GT:
					stack.push(Boolean.valueOf(x >= y));
					break;
				case PSToken.GE:
					stack.push(Boolean.valueOf(x > y));
					break;
				}
				break;
		
			case PSToken.ARRAY_START:
				stack.push(token.mValue);
				arrays.push(stack.top());
				break;
			case PSToken.ARRAY_END:
				Object array_marker = arrays.pop();
				stack.push(stack.popTo(array_marker));
				break;
			case PSToken.EXECUTION_ARRAY_START:
				stack.push(token.mValue);
				arrays.push(stack.top());
				stackMode = true;
				break;
			case PSToken.EXECUTION_ARRAY_END:
				Object proc_marker = arrays.pop();
				stack.push(stack.popTo(proc_marker));
				stackMode = false;
				break;
			case PSToken.IF:
				Object proc = stack.pop();
				Boolean bool = (Boolean)stack.pop();
				if (bool.booleanValue() == false)
					break;
				stack.push(proc);
			case PSToken.EXEC:
				exec(buffer);
				break;
			case PSToken.CVX:
				Object str2exec = stack.pop();
				buffer.feed("{ "+str2exec+" }");
				break;
//			case PSToken.FILE:
//				Object access = stack.pop();
//				Object file = stack.pop();
//				stack.push(new File(file, access));
//				break;
//			case PSToken.READ:
//				file = (File)stack.pop();
//				stack.push(((File)file).read());
//				break;
			case PSToken.FOR:
				proc = stack.pop();
				Double limit = (Double)stack.pop();
				Double inc = (Double)stack.pop();
				double ninc = inc.doubleValue();
				double count = ((Double)stack.pop()).doubleValue();
				if (ninc < 0 && count >=  limit.doubleValue() || ninc > 0 && count <= limit.doubleValue()) {
					stack.push(new Double(count));
					buffer.stack.push(token);
					buffer.stack.push(new PSToken(PSToken.EXECUTION_ARRAY_END,"}"));
					stack.push(proc);
					exec(buffer);
					buffer.stack.push(new PSToken(PSToken.EXECUTION_ARRAY_START,"{"));
					buffer.stack.push(new PSToken(PSToken.REAL,limit));
					buffer.stack.push(new PSToken(PSToken.REAL,inc));
					buffer.stack.push(new PSToken(PSToken.REAL,new Double(count+ninc)));
					exec(buffer);
					stack.pop();
					
				}
				break;
			case PSToken.PROC:	
			case PSToken.NAME:
			case PSToken.INTEGER:
			case PSToken.REAL:
			case PSToken.STRING:
			case PSToken.LITERALNAME:
					stack.push(token.mValue);
			}
			
			System.out.println("\n\nGOT: "+token);
			System.out.print(stack);
		}
		}
	}

	public void exec(CharBuffer buffer) {
		if (stack.top() instanceof PSToken && ((PSToken)stack.top()).mType == PSToken.PROC) {
			Object []array = (Object [])((PSToken)stack.top()).mValue;
			for (int i = array.length - 1; i >= 0; i--)
				buffer.stack.push(array[i]);
		}	
	}

	public Stack getStack() {
		return stack;
	}

}
