/*
 * Copyright (c) 2015, Anastasia Izmaylova and Ali Afroozeh, Centrum Wiskunde & Informatica (CWI)
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without 
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this 
 *    list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice, this 
 *    list of conditions and the following disclaimer in the documentation and/or 
 *    other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND 
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED 
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. 
 * IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, 
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT 
 * NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, 
 * OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, 
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) 
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY 
 * OF SUCH DAMAGE.
 *
 */

package grammar.java

import org.meerkat.parsers.Parsers
import scala.collection.JavaConversions._
import scala.collection.mutable._
import java.util.Arrays

trait Lexicals {
  
  type T = Parsers.Symbol[org.meerkat.parsers.NoValue]
  
  def JavaLetter: T
  def Digit: T
  def Identifier: T
  def IntegerTypeSuffix: T
  def Digits: T
  def NonZeroDigit: T
  def DecimalNumeral: T
  def DecimalIntegerLiteral: T
  def HexDigits: T
  def HexNumeral: T
  def HexIntegerLiteral: T
  def OctalDigits: T
  def OctalNumeral: T
  def OctalIntegerLiteral: T
  def BinaryDigits: T
  def BinaryNumeral: T
  def BinaryIntegerLiteral: T
  def Sign: T
  def SignedInteger: T
  def ExponentIndicator: T
  def ExponentPart: T
  def FloatTypeSuffix: T
  def DecimalFloatingPointLiteral: T
  def HexSignificand: T
  def BinaryExponentIndicator: T
  def BinaryExponent: T
  def HexadecimalFloatingPointLiteral: T
  def EscapeSequence: T
  def OctalEscape: T
  def SingleCharacter: T
  def CharacterLiteral: T
  def StringCharacter: T
  def StringLiteral: T
  def BooleanLiteral: T
  def NullLiteral: T
  
  def Comment: T
  def WhiteSpace: T
  def Layout: T
  
  def Keyword: Set[String] = 
    new java.util.HashSet[String](Arrays.asList("abstract", "continue", "for", "new", "switch"
    , "assert", "default", "if", "package", "synchronized"
    , "boolean", "do", "goto", "private", "this", "break"
    , "double", "implements", "protected", "throw"
    , "byte", "else", "import", "public", "throws"
    , "case", "enum", "instanceof", "return", "transient"
    , "catch", "extends", "int", "short", "try"
    , "char", "final", "interface", "static"
    , "void", "class", "finally", "long", "strictfp"
    , "volatile", "const", "float", "native", "super"
    , "while", "true", "false", "null"))

}