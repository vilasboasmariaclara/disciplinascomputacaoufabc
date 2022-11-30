package texteditorisi;
/* The following code was generated by JFlex 1.4.1 on 3/22/15 4:43 PM */

/*
 * 11/13/2004
 *
 * CTokenMaker.java - An object that can take a chunk of text and
 * return a linked list of tokens representing it in the C programming
 * language.
 *
 * This library is distributed under a modified BSD license.  See the included
 * LICENSE file for details.
 */
import javax.swing.text.Segment;

import org.fife.ui.rsyntaxtextarea.AbstractTokenMaker;
import org.fife.ui.rsyntaxtextarea.RSyntaxUtilities;
import org.fife.ui.rsyntaxtextarea.Token;
import org.fife.ui.rsyntaxtextarea.TokenMap;

public class IsiLanguageTokenMaker extends AbstractTokenMaker {

	/**
	 * Returns a list of tokens representing the given text.
	 *
	 * @param text The text to break into tokens.
	 * @param startTokenType The token with which to start tokenizing.
	 * @param startOffset The offset at which the line of tokens begins.
	 * @return A linked list of tokens representing <code>text</code>.
	 */
	@Override
	public Token getTokenList(Segment text, int startTokenType, int startOffset) {

	   resetTokenList();

	   char[] array = text.array;
	   int offset = text.offset;
	   int count = text.count;
	   int end = offset + count;

	   // Token starting offsets are always of the form:
	   // 'startOffset + (currentTokenStart-offset)', but since startOffset and
	   // offset are constant, tokens' starting positions become:
	   // 'newStartOffset+currentTokenStart'.
	   int newStartOffset = startOffset - offset;

	   int currentTokenStart = offset;
	   int currentTokenType = startTokenType;

	   for (int i=offset; i<end; i++) {

	      char c = array[i];

	      switch (currentTokenType) {

	         case Token.NULL:

	            currentTokenStart = i;   // Starting a new token here.

	            switch (c) {

	               case ' ':
	               case '\t':
	                  currentTokenType = Token.WHITESPACE;
	                  break;

	               case '"':
	                  currentTokenType = Token.LITERAL_STRING_DOUBLE_QUOTE;
	                  break;

	               case Token.COMMENT_MARKUP:
	            	   currentTokenType = Token.COMMENT_MARKUP;
	            	   break;

	               case Token.COMMENT_MULTILINE:
	            	   currentTokenType = Token.COMMENT_MARKUP;
	            	   break;

	               default:
	                  if (RSyntaxUtilities.isDigit(c)) {
	                     currentTokenType = Token.LITERAL_NUMBER_DECIMAL_INT;
	                     break;
	                  }
	                  else if (RSyntaxUtilities.isLetter(c) || c=='/' || c=='_') {
	                     currentTokenType = Token.IDENTIFIER;
	                     break;
	                  }

	                  // Anything not currently handled - mark as an identifier
	                  currentTokenType = Token.IDENTIFIER;
	                  break;

	            } // End of switch (c).

	            break;

	         case Token.WHITESPACE:

	            switch (c) {

	               case ' ':
	               case '\t':
	                  break;   // Still whitespace.

	               case '"':
	                  addToken(text, currentTokenStart,i-1, Token.WHITESPACE, newStartOffset+currentTokenStart);
	                  currentTokenStart = i;
	                  currentTokenType = Token.LITERAL_STRING_DOUBLE_QUOTE;
	                  break;

	               case Token.OPERATOR:
		                  addToken(text, currentTokenStart,i-1, Token.WHITESPACE, newStartOffset+currentTokenStart);
		                  currentTokenStart = i;
		                  currentTokenType = Token.OPERATOR;
		                  break;

	               case Token.FUNCTION:
		                  addToken(text, currentTokenStart,i-1, Token.WHITESPACE, newStartOffset+currentTokenStart);
		                  currentTokenStart = i;
		                  currentTokenType = Token.FUNCTION;
		                  break;

	               default:   // Add the whitespace token and start anew.

	                  addToken(text, currentTokenStart,i-1, Token.WHITESPACE, newStartOffset+currentTokenStart);
	                  currentTokenStart = i;

	                  if (RSyntaxUtilities.isDigit(c)) {
	                     currentTokenType = Token.LITERAL_NUMBER_DECIMAL_INT;
	                     break;
	                  }
	                  else if (RSyntaxUtilities.isLetter(c) || c=='/' || c=='_') {
	                     currentTokenType = Token.IDENTIFIER;
	                     break;
	                  }

	                  // Anything not currently handled - mark as identifier
	                  currentTokenType = Token.IDENTIFIER;

	            } // End of switch (c).

	            break;

	         default: // Should never happen

	         case Token.IDENTIFIER:

	            switch (c) {

	               case ' ':
	               case '\t':
	                  addToken(text, currentTokenStart,i-1, Token.IDENTIFIER, newStartOffset+currentTokenStart);
	                  currentTokenStart = i;
	                  currentTokenType = Token.WHITESPACE;
	                  break;

	               case '"':
	                  addToken(text, currentTokenStart,i-1, Token.IDENTIFIER, newStartOffset+currentTokenStart);
	                  currentTokenStart = i;
	                  currentTokenType = Token.LITERAL_STRING_DOUBLE_QUOTE;
	                  break;

	               case '(':
		               addToken(text, currentTokenStart,i-1, Token.FUNCTION, newStartOffset+currentTokenStart);
		               currentTokenStart = i;
		               currentTokenType = Token.OPERATOR;
		               break;

	               case ')':
		               addToken(text, currentTokenStart,i-1, Token.IDENTIFIER, newStartOffset+currentTokenStart);
		               currentTokenStart = i;
		               currentTokenType = Token.OPERATOR;
		               break;

	               case Token.OPERATOR:
		                  addToken(text, currentTokenStart,i-1, Token.IDENTIFIER, newStartOffset+currentTokenStart);
		                  currentTokenStart = i;
		                  currentTokenType = Token.OPERATOR;
		                  break;

	  	           case Token.COMMENT_EOL:
	 	                  i = end - 1;
	 	                  addToken(text, currentTokenStart,i, Token.IDENTIFIER, newStartOffset+currentTokenStart);
   	 	                  // We need to set token type to null so at the bottom we don't add one more token.
	 	                  currentTokenType = Token.NULL;
	 	                  break;

	 	           default:
	 	        	   break;


	            } // End of switch (c).

	            break;

             case Token.OPERATOR:

            	 switch (c) {

	               case ' ':
	               case '\t':
	                  addToken(text, currentTokenStart,i-1, Token.OPERATOR, newStartOffset+currentTokenStart);
	                  currentTokenStart = i;
	                  currentTokenType = Token.WHITESPACE;
	                  break;

	               case Token.IDENTIFIER:
	            	   addToken(text, currentTokenStart,i-1, Token.OPERATOR, newStartOffset+currentTokenStart);
		               currentTokenStart = i;
		               currentTokenType = Token.IDENTIFIER;
		               break;

			       case Token.COMMENT_EOL:
				       i = end - 1;
				       addToken(text, currentTokenStart,i, Token.OPERATOR, newStartOffset+currentTokenStart);
				       // We need to set token type to null so at the bottom we don't add one more token.
				       currentTokenType = Token.NULL;
				       break;

					default:
	 	        	  break;
					}


	         case Token.LITERAL_NUMBER_DECIMAL_INT:

	            switch (c) {

	               case ' ':
	               case '\t':
	                  addToken(text, currentTokenStart,i-1, Token.LITERAL_NUMBER_DECIMAL_INT, newStartOffset+currentTokenStart);
	                  currentTokenStart = i;
	                  currentTokenType = Token.WHITESPACE;
	                  break;

	               case '"':
	                  addToken(text, currentTokenStart,i-1, Token.LITERAL_NUMBER_DECIMAL_INT, newStartOffset+currentTokenStart);
	                  currentTokenStart = i;
	                  currentTokenType = Token.LITERAL_STRING_DOUBLE_QUOTE;
	                  break;

	               case '.':
		                  addToken(text, currentTokenStart,i-1, Token.LITERAL_NUMBER_FLOAT, newStartOffset+currentTokenStart);
		                  currentTokenStart = i;
		                  currentTokenType = Token.LITERAL_NUMBER_DECIMAL_INT;
		                  break;

	               default:

	                  if (RSyntaxUtilities.isDigit(c)) {
	                     break;   // Still a literal number.
	                  }

	                  // Otherwise, remember this was a number and start over.
	                  addToken(text, currentTokenStart,i-1, Token.LITERAL_NUMBER_DECIMAL_INT, newStartOffset+currentTokenStart);
	                  i--;
	                  currentTokenType = Token.NULL;

	            } // End of switch (c).

	            break;

	         case Token.FUNCTION:
            	 switch (c) {

	               case ' ':
	               case '\t':
	                  addToken(text, currentTokenStart,i-1, Token.FUNCTION, newStartOffset+currentTokenStart);
	                  currentTokenStart = i;
	                  currentTokenType = Token.WHITESPACE;
	                  break;

	               case Token.IDENTIFIER:
	            	   addToken(text, currentTokenStart,i-1, Token.FUNCTION, newStartOffset+currentTokenStart);
		               currentTokenStart = i;
		               currentTokenType = Token.IDENTIFIER;
		               break;

			       case Token.COMMENT_EOL:
				       i = end - 1;
				       addToken(text, currentTokenStart,i, Token.OPERATOR, newStartOffset+currentTokenStart);
				       // We need to set token type to null so at the bottom we don't add one more token.
				       currentTokenType = Token.NULL;
				       break;

					default:
	 	        	  if (c==')') {
		                  addToken(text, currentTokenStart,i-1, Token.OPERATOR, newStartOffset+currentTokenStart);
		                  currentTokenStart = i;
		                  currentTokenType = Token.IDENTIFIER;
		                  break;

	 	        	  }
	 	        	  else {
	 	        		  break;
	 	        	  }
					}

	         case Token.COMMENT_EOL:
	            i = end - 1;
	            addToken(text, currentTokenStart,i, currentTokenType, newStartOffset+currentTokenStart);
	            // We need to set token type to null so at the bottom we don't add one more token.
	            currentTokenType = Token.NULL;
	            break;

	         case Token.LITERAL_STRING_DOUBLE_QUOTE:
	            if (c=='"') {
	               addToken(text, currentTokenStart,i, Token.LITERAL_STRING_DOUBLE_QUOTE, newStartOffset+currentTokenStart);
	               currentTokenType = Token.NULL;
	            }
	            break;


	      } // End of switch (currentTokenType).

	   } // End of for (int i=offset; i<end; i++).

	   switch (currentTokenType) {

	      // Remember what token type to begin the next line with.
	      case Token.LITERAL_STRING_DOUBLE_QUOTE:
	         addToken(text, currentTokenStart,end-1, currentTokenType, newStartOffset+currentTokenStart);
	         break;

	      // Do nothing if everything was okay.
	      case Token.NULL:
	         addNullToken();
	         break;

	      // All other token types don't continue to the next line...
	      default:
	         addToken(text, currentTokenStart,end-1, currentTokenType, newStartOffset+currentTokenStart);
	         addNullToken();

	   }

	   // Return the first token in our linked list.
	   return firstToken;

	}

	@Override
	public TokenMap getWordsToHighlight() {
	   TokenMap tokenMap = new TokenMap();

	   tokenMap.put(";", Token.COMMENT_EOL);

	   tokenMap.put("??", Token.COMMENT_MARKUP);
	   tokenMap.put("?*", Token.COMMENT_MULTILINE);
	   tokenMap.put("*?", Token.COMMENT_MULTILINE);

	   tokenMap.put("programa",  Token.RESERVED_WORD);
	   tokenMap.put("fimprog;",   Token.RESERVED_WORD);
	   tokenMap.put("se",    Token.RESERVED_WORD);
	   tokenMap.put("senao", Token.RESERVED_WORD);
	   tokenMap.put("enquanto", Token.RESERVED_WORD);
	   tokenMap.put("escolha", Token.RESERVED_WORD);
	   tokenMap.put("falso", Token.RESERVED_WORD);
	   tokenMap.put("verdadeiro", Token.RESERVED_WORD);
	   tokenMap.put("caso", Token.RESERVED_WORD);

	   tokenMap.put("numero",  Token.DATA_TYPE);
	   tokenMap.put("inteiro",   Token.DATA_TYPE);
	   tokenMap.put("texto",    Token.DATA_TYPE);
	   tokenMap.put("caractere", Token.DATA_TYPE);
	   tokenMap.put("boleano", Token.DATA_TYPE);

	   tokenMap.put("#",  Token.OPERATOR);
	   tokenMap.put("^",  Token.OPERATOR);
	   tokenMap.put("**",   Token.OPERATOR);
	   tokenMap.put("$",    Token.OPERATOR);
	   tokenMap.put(">", Token.OPERATOR);
	   tokenMap.put("<", Token.OPERATOR);
	   tokenMap.put(">=", Token.OPERATOR);
	   tokenMap.put("<=", Token.OPERATOR);
	   tokenMap.put("=", Token.OPERATOR);
	   tokenMap.put("==", Token.OPERATOR);
	   tokenMap.put("!=", Token.OPERATOR);
	   tokenMap.put("(", Token.OPERATOR);
	   tokenMap.put(")", Token.OPERATOR);

	   tokenMap.put("escreva", Token.FUNCTION);
	   tokenMap.put("leia",  Token.FUNCTION);

	   return tokenMap;
	}

	@Override
	public void addToken(Segment segment, int start, int end, int tokenType, int startOffset) {
	   // This assumes all keywords, etc. were parsed as "identifiers."
	   if (tokenType==Token.IDENTIFIER) {
	      int value = wordsToHighlight.get(segment, start, end);
	      if (value != -1) {
	         tokenType = value;
	      }
	   }
	   super.addToken(segment, start, end, tokenType, startOffset);
	}

}