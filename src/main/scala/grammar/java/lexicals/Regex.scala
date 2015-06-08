/*
 * Copyright (c) 2014 CWI. All rights reserved.
 * 
 * Authors:
 *     Anastasia Izmaylova  <anastasia.izmaylova@cwi.nl>
 *     Ali Afroozeh         <ali.afroozeh@cwi.nl>
 */
package grammar.java.lexicals

import org.meerkat.util.RegularExpression
import org.meerkat.util.Char._
import grammar.java.Lexicals

trait Regex extends Lexicals {
	import org.meerkat.tmp.Parsers._
  
  val JavaLetter = toTerminal(Regex.JavaLetter)
  val Digit = toTerminal(Regex.Digit)
  val Identifier = toTerminal(Regex.Identifier).!<<("""[a-zA-Z_$]""".r) \ Keyword 
  val IntegerTypeSuffix = toTerminal(Regex.IntegerTypeSuffix)
  val Digits = toTerminal(Regex.Digits)
  val NonZeroDigit = toTerminal(Regex.NonZeroDigit)
  val DecimalNumeral = toTerminal(Regex.DecimalNumeral)
  val DecimalIntegerLiteral = toTerminal(Regex.DecimalIntegerLiteral)
  val HexDigits = toTerminal(Regex.HexDigits)
  val HexNumeral = toTerminal(Regex.HexNumeral)
  val HexIntegerLiteral = toTerminal(Regex.HexIntegerLiteral)
  val OctalDigits = toTerminal(Regex.OctalDigits)
  val OctalNumeral = toTerminal(Regex.OctalNumeral)
  val OctalIntegerLiteral = toTerminal(Regex.OctalIntegerLiteral)
  val BinaryDigits = toTerminal(Regex.BinaryDigits)
  val BinaryNumeral = toTerminal(Regex.BinaryNumeral)
  val BinaryIntegerLiteral = toTerminal(Regex.BinaryIntegerLiteral)
  val Sign = toTerminal(Regex.Sign)
  val SignedInteger = toTerminal(Regex.SignedInteger)
  val ExponentIndicator = toTerminal(Regex.ExponentIndicator)
  val ExponentPart = toTerminal(Regex.ExponentPart)
  val FloatTypeSuffix = toTerminal(Regex.FloatTypeSuffix)
  val DecimalFloatingPointLiteral = toTerminal(Regex.DecimalFloatingPointLiteral)
  val HexSignificand = toTerminal(Regex.HexSignificand)
  val BinaryExponentIndicator = toTerminal(Regex.ExponentIndicator)
  val BinaryExponent = toTerminal(Regex.BinaryExponent)
  val HexadecimalFloatingPointLiteral = toTerminal(Regex.HexadecimalFloatingPointLiteral)
  val EscapeSequence = toTerminal(Regex.EscapeSequence)
  val OctalEscape = toTerminal(Regex.OctalEscape)
  val SingleCharacter = toTerminal(Regex.SingleCharacter)
  val CharacterLiteral = toTerminal(Regex.CharacterLiteral)
  val StringCharacter = toTerminal(Regex.StringLiteral)
  val StringLiteral = toTerminal(Regex.StringLiteral)
  val BooleanLiteral = toTerminal(Regex.BooleanLiteral)
  val NullLiteral = toTerminal(Regex.NullLiteral)
  
  val Comment = toTerminal(Regex.Comment)
  val WhiteSpace = toTerminal(Regex.WhiteSpace)
  val Layout = toTerminal(Regex.Layout)
}

object Regex {
	
  import org.meerkat.util.RegularExpression._

  val JavaLetter: RegularExpression = 'a'--'z' | 'A'--'Z' | '_' | '$'
  
  val Digit: RegularExpression = '0'--'9'

  val Identifier: RegularExpression = JavaLetter ~ (JavaLetter | Digit).*

  val IntegerTypeSuffix: RegularExpression = "l|L"

  val Digits: RegularExpression = "[0-9]([0-9_]*[0-9])?"
  
  val NonZeroDigit: RegularExpression = '1'--'9'
  
  val DecimalNumeral: RegularExpression =  "0" | NonZeroDigit ~ Digits.? | NonZeroDigit ~ "_".+() ~ Digits
  
  val DecimalIntegerLiteral: RegularExpression = DecimalNumeral ~ IntegerTypeSuffix.?

  val HexDigits: RegularExpression = "[0-9a-fA-F]([0-9a-fA-F_]*[0-9a-fA-F])?"
  
  val HexNumeral: RegularExpression = "0[xX]" ~ HexDigits
  
  val HexIntegerLiteral: RegularExpression = HexNumeral ~ IntegerTypeSuffix.?
  
  val OctalDigits: RegularExpression = "[0-7]([0-7_]*[0-7])?"
  
  val OctalNumeral: RegularExpression = "0[_]*" ~ OctalDigits
  
  val OctalIntegerLiteral: RegularExpression = OctalNumeral ~ IntegerTypeSuffix.?
  
  val BinaryDigits: RegularExpression = "[0-1]([0-1_]*[0-1])?"
  
  val BinaryNumeral: RegularExpression =  "0[bB]" ~  BinaryDigits
  
  val BinaryIntegerLiteral: RegularExpression = BinaryNumeral ~ IntegerTypeSuffix.?
  
  val Sign: RegularExpression = "[+-]"
  
  val SignedInteger: RegularExpression = Sign.? ~ Digits
  
  val ExponentIndicator: RegularExpression = "[eE]";
  
  val ExponentPart: RegularExpression = ExponentIndicator ~ SignedInteger
  
  val FloatTypeSuffix: RegularExpression = "[fFdD]"

  val DecimalFloatingPointLiteral: RegularExpression = 
    ( Digits ~ "\\." ~ Digits.? ~ ExponentPart.? ~ FloatTypeSuffix.?
    | "\\." ~ Digits ~ ExponentPart.? ~ FloatTypeSuffix.?
    | Digits ~ ExponentPart
    | Digits ~ FloatTypeSuffix
    | Digits ~ ExponentPart ~ FloatTypeSuffix
    )
    
  val HexSignificand: RegularExpression = 
    ( HexNumeral
    | HexNumeral ~ "\\."
    | "0[xX]" ~ HexDigits.? ~ "\\." ~ HexDigits
    )
    
  val BinaryExponentIndicator: RegularExpression = "[pP]"
    
  val BinaryExponent: RegularExpression = BinaryExponentIndicator ~ SignedInteger;  
    
  val HexadecimalFloatingPointLiteral: RegularExpression =  HexSignificand ~ BinaryExponent ~ FloatTypeSuffix.?  
    
  val EscapeSequence: RegularExpression = """\\[btnfr"'\\]"""
  
  val UnicodeLiteral: RegularExpression = """\\u[0-9a-fA-F]{4}+"""
  
  val OctalEscape: RegularExpression = """\\([0-7][0-7]?|[0-3][0-7][0-7])"""
  
  val SingleCharacter: RegularExpression = """[^\n\r'\\]""" | UnicodeLiteral | OctalEscape | EscapeSequence
  
  val CharacterLiteral: RegularExpression = "'" ~ SingleCharacter ~ "'"
  
  val StringCharacter: RegularExpression = """[^\n\r"\\]""" | UnicodeLiteral | OctalEscape | EscapeSequence
  
  val StringLiteral: RegularExpression = "\"" ~ StringCharacter.* ~ "\""
  
  val BooleanLiteral: RegularExpression = "true" | "false"
  
  val NullLiteral: RegularExpression = "null"
  
  val Comment: RegularExpression = """(/\*(.|[\r\n])*?\*/|//[^\r\n]*)"""
  
  val WhiteSpace: RegularExpression = """\s"""
  
  val Layout: RegularExpression = (Comment | WhiteSpace)*
 
}