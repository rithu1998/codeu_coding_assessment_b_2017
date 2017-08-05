// Copyright 2017 Google Inc.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//    http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.codeu.mathlang.impl;

import java.io.IOException;
import java.util.StringTokenizer;

import com.google.codeu.mathlang.core.tokens.NameToken;
import com.google.codeu.mathlang.core.tokens.NumberToken;
import com.google.codeu.mathlang.core.tokens.StringToken;
import com.google.codeu.mathlang.core.tokens.SymbolToken;
import com.google.codeu.mathlang.core.tokens.Token;
import com.google.codeu.mathlang.parsing.TokenReader;

// MY TOKEN READER
//
// This is YOUR implementation of the token reader interface. To know how
// it should work, read src/com/google/codeu/mathlang/parsing/TokenReader.java.
// You should not need to change any other files to get your token reader to
// work with the test of the system.
public final class MyTokenReader implements TokenReader {
  String input = "";
  StringTokenizer st = null;
  int x = 3;
  int state = 0;
  public MyTokenReader(String source) {
    // Your token reader will only be given a string for input. The string will
    // contain the whole source (0 or more lines).
	  //System.out.println("Source: " + source);
	  input = source;
	  st = new StringTokenizer(source," \n\r\t+-*\\=;\"", true);
	  
	  /*while (st.hasMoreTokens()) {
	         System.out.println(st.nextToken());
	  }*/
	
  }

  private String returnNonSpaceToken() {
	  String str = " ";
	  while(str.compareTo(" ") == 0) {
		  str = st.nextToken();
	  }
		  
	  return str;
  }
  
  private String getStringToken(){
	  String str = "";
	  while(true){
		  String s = st.nextToken();
		  if(s.compareTo("\"") == 0)
			  break;
		  str += s;
		  //System.out.println("Get string token: " + str);
	  }
	  //System.out.println("Get string token: " + str);
	  return str;
  }
  
  private boolean isNumber(String str){
	  try{
		  double value = Double.parseDouble(str);
		  
		  return true;
	  }catch(Exception ex){
		  return false;
	  }
  }
  
  private double getNumber(String str){
	  try{
		  double value = Double.parseDouble(str);
		  //System.out.println("getNumber :"+ value);
		  return value;
	  }catch(Exception ex){
		  return 0.0;
	  }
  }
  @Override
  public Token next() throws IOException {
    // Most of your work will take place here. For every call to |next| you should
    // return a token until you reach the end. When there are no more tokens, you
    // should return |null| to signal the end of input.

    // If for any reason you detect an error in the input, you may throw an IOException
    // which will stop all execution.
	String str;
    if(input == null)
      return null;
    else {
    	/*switch(x) {
    	case 3:
    		x--;
    		return new NameToken("note");
    	case 2:
    		x--;
    		return new StringToken("my comment");
    	case 1:
    		x--;
        		return new SymbolToken(';');
        default:
        	return null;
    		
    	}*/
    	if(st.hasMoreTokens()) {
				while (true) {
					str = returnNonSpaceToken();
					//System.out.println("returnNonSpaceToken(): " + str);
					
					switch (str) {
					case "note":
						return new NameToken(str);
					case "let":
						state = 1; // Next will be name token
						return new NameToken(str);
					case "print":
						state = 1;
						return new NameToken(str);
					case "\"":
						state = 0;
						return new StringToken(getStringToken());
					case ";":
						return new SymbolToken(';');
					case "=":
						state = 1;
						return new SymbolToken('=');
					case "+":
						state = 1;
						return new SymbolToken('+');
					case "-":
						state = 1;
						return new SymbolToken('-');
					case "*":
						state = 1;
						return new SymbolToken('*');
					case "\\":
						state = 1;
						return new SymbolToken('\\');
					case "\n":
					case "\r":
					case "\t":
						break;
					default:
						//System.out.println("in default " + str);
						if(isNumber(str)){
							Double d = getNumber(str);
							state = 0;
							return new NumberToken(d);
						}else{
							if(state == 1) {
								state = 0;
								return new NameToken(str);
							} else
							return new StringToken(str);
						}
						
					}
				}
    		
    	}
    	else
    		return null;
    }
  }
}
