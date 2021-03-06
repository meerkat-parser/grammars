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

import org.meerkat.Syntax._
import org.meerkat.parsers._
import org.meerkat.parsers.Parsers._
import org.meerkat.parsers.OperatorParsers._

/**
 * 
 * The grammar of Java 7 
 * 
 */
trait Natural extends Lexicals {
  
    implicit val L: Layout
  
    val Type: Nonterminal =  
    syn ( PrimitiveType 
        | ReferenceType 
        )
  
    val PrimitiveType: Nonterminal = 
    syn ( "byte" 
        | "short" 
        | "char" 
        | "int" 
        | "long" 
        | "float"
        | "double" 
        | "boolean" 
        )
    
    val ReferenceType: Nonterminal = 
    syn ( TypeDeclSpecifier ~ TypeArguments.? 
        | ArrayType 
        )

    val ReferenceTypeNonArrayType: Nonterminal = 
    syn ( TypeDeclSpecifier ~ TypeArguments.? )
    
    val TypeList: Nonterminal =  
    syn ( Type.+(",") )

    val TypeName: Nonterminal =  
    syn ( QualifiedIdentifier )

    val TypeVariable: Nonterminal =  
    syn ( Identifier )

    val ArrayType: Nonterminal =  
    syn ( Type ~ "[" ~ "]" )

    val TypeParameters: Nonterminal = 
    syn ( "<" ~ TypeParameter.+(",") ~ ">" )
    
    val TypeParameter: Nonterminal =
    syn ( TypeVariable ~ TypeBound.? )
    
    val TypeBound: Nonterminal = 
    syn ( "extends" ~ ReferenceType.+("&") )
    
    val TypeArguments: Nonterminal =
    syn ( "<" ~ TypeArgument.+(",") ~ ">" )
    
    val TypeArgument: Nonterminal = 
    syn ( Type 
        | "?" ~ (("extends" | "super").! ~ Type).!.?
        )
    
    val QualifiedIdentifier: Nonterminal = 
    syn ( Identifier.+(".") )
    
    val QualifiedIdentifierList: Nonterminal = 
    syn ( QualifiedIdentifier.+(",") )
    
    val CompilationUnit: Nonterminal = 
    syn ( PackageDeclaration.? ~ ImportDeclaration.* ~ TypeDeclaration.* )
    
    val PackageDeclaration: Nonterminal = 
    syn ( Annotation.* ~ "package" ~ QualifiedIdentifier ~ ";" )
    
    val ImportDeclaration: Nonterminal = 
    syn ( "import" ~ "static".? ~ Identifier.+(".") ~ ("." ~ "*").!.? ~ ";" )
    
    val TypeDeclaration: Nonterminal = 
    syn ( ClassDeclaration 
        | InterfaceDeclaration 
        | ";"
        )
    
    val ClassDeclaration: Nonterminal = 
    syn ( NormalClassDeclaration 
        | EnumDeclaration
        )
    
    val NormalClassDeclaration: Nonterminal = 
    syn ( ClassModifier.* ~ " class" ~ Identifier ~ TypeParameters.? ~ ("extends" ~ Type).!.? ~ ("implements" ~ TypeList).!.? ~ ClassBody )
    
    val ClassModifier: Nonterminal = 
    syn ( Annotation 
        | "public" 
        | "protected" 
        | "private" 
        | "abstract" 
        | "static" 
        | "final" 
        | "strictfp"
        )
    
    val ClassBody: Nonterminal = 
    syn ( "{" ~ ClassBodyDeclaration.* ~ "}" )
    
    val ClassBodyDeclaration = 
    syn ( ClassMemberDeclaration 
        | InstanceInitializer 
        | StaticInitializer 
        | ConstructorDeclaration
        )
    
    val InstanceInitializer: Nonterminal = 
    syn ( Block )
    
    val StaticInitializer: Nonterminal = 
    syn ( "static" ~ Block )
    
    val ConstructorDeclaration: Nonterminal = 
    syn ( ConstructorModifier.* ~ ConstructorDeclarator ~ Throws.? ~ ConstructorBody )
    
    val ConstructorModifier: Nonterminal = 
    syn ( Annotation 
        | "public" 
        | "protected" 
        | "private"
        )
    
    val ConstructorDeclarator: Nonterminal = 
    syn ( TypeParameters.? ~ Identifier ~ "(" ~ FormalParameterList.? ~ ")" )
    
    val ConstructorBody: Nonterminal = 
    syn ( "{" ~ ExplicitConstructorInvocation.? ~ BlockStatement.* ~ "}" )
    
    val ExplicitConstructorInvocation: Nonterminal = 
    syn ( NonWildTypeArguments.? ~ "this" ~ "(" ~ ArgumentList.? ~ ")" ~ ";" 
        | NonWildTypeArguments.? ~ "super" ~ "(" ~ ArgumentList.? ~ ")" ~ ";" 
        | Primary ~ "." ~ NonWildTypeArguments.? ~ "super" ~ "(" ~ ArgumentList.? ~ ")" ~ ";"
        )
    
    val NonWildTypeArguments: Nonterminal = 
    syn ( "<" ~ ReferenceType.+(",") ~ ">" )
    
    val ClassMemberDeclaration: Nonterminal = 
    syn ( FieldDeclaration 
        | MethodDeclaration 
        | ClassDeclaration 
        | InterfaceDeclaration 
        | ";"
        )
    
    val InterfaceDeclaration: Nonterminal =
    syn ( NormalInterfaceDeclaration 
        | AnnotationTypeDeclaration
        )
    
    val NormalInterfaceDeclaration: Nonterminal = 
    syn ( InterfaceModifier.* ~ "interface" ~ Identifier ~ TypeParameters.? ~ ("extends" ~ TypeList).!.? ~ InterfaceBody )
    
    val InterfaceModifier: Nonterminal = 
    syn ( Annotation 
        | "public" 
        | "protected" 
        | "private" 
        | "abstract" 
        | "static" 
        | "strictfp"
        )
    
    val InterfaceBody: Nonterminal = 
    syn ( "{" ~ InterfaceMemberDeclaration.* ~ "}" )
    
    val InterfaceMemberDeclaration: Nonterminal = 
    syn ( ConstantDeclaration 
        | AbstractMethodDeclaration 
        | ClassDeclaration 
        | InterfaceDeclaration 
        | ";"
        )
    
    val ConstantDeclaration: Nonterminal = 
    syn ( ConstantModifier.* ~ Type ~ VariableDeclarators ~ ";" )
    
    val ConstantModifier: Nonterminal = 
    syn ( Annotation 
        | "public" 
        | "static" 
        | "final"
        )
    
    val AbstractMethodDeclaration: Nonterminal = 
    syn ( AbstractMethodModifier.* ~ TypeParameters.? ~ Result ~ MethodDeclarator ~ Throws.? ~ ";" )
    
    val AbstractMethodModifier: Nonterminal = 
    syn ( Annotation 
        | "public" 
        | "abstract"
        )
    
    val AnnotationTypeDeclaration: Nonterminal = 
    syn ( InterfaceModifier.* ~ "@" ~ "interface" ~ Identifier ~ AnnotationTypeBody )
    
    val AnnotationTypeBody: Nonterminal = 
    syn ( "{" ~ AnnotationTypeElementDeclaration.* ~ "}" )
    
    val AnnotationTypeElementDeclaration: Nonterminal = 
    syn ( AbstractMethodModifier.* ~ Type ~ Identifier ~ "(" ~ ")" ~ ("[" ~ "]").!.* ~ DefaultValue.? ~ ";" 
        | ConstantDeclaration 
        | ClassDeclaration 
        | InterfaceDeclaration 
        | AnnotationTypeDeclaration 
        | ";"
        )
    
    val DefaultValue: Nonterminal =  
    syn ( "default" ~ ElementValue )
    
    val FieldDeclaration: Nonterminal =  
    syn ( FieldModifier.* ~ Type ~ VariableDeclarators ~ ";" )
    
    val FieldModifier: Nonterminal = 
    syn ( Annotation 
        | "public" 
        | "protected" 
        | "private" 
        | "static" 
        | "final" 
        | "transient" 
        | "volatile"
        )
    
    val VariableDeclarators: Nonterminal = 
    syn ( VariableDeclarator.+(",") )
    
    val VariableDeclarator: Nonterminal = 
    syn ( VariableDeclaratorId ~ ("=" ~ VariableInitializer).!.? )
    
    val VariableDeclaratorId: Nonterminal = 
    syn ( Identifier ~ ("[" ~ "]").!.* )
    
    val VariableInitializer: Nonterminal = 
    syn ( ArrayInitializer 
        | Expression($) 
        )
    
    val ArrayInitializer: Nonterminal = 
    syn ( "{" ~ VariableInitializer.*(",") ~ ",".? ~ "}" )
    
    val MethodDeclaration: Nonterminal = 
    syn ( MethodHeader ~ MethodBody )
    
    val MethodHeader: Nonterminal = 
    syn ( MethodModifier.* ~ TypeParameters.? ~ Result ~ MethodDeclarator ~ Throws.? )
    
    val MethodDeclarator: Nonterminal = 
    syn ( Identifier ~ "(" ~ FormalParameterList.? ~ ")" 
        | MethodDeclarator ~ "[" ~ "]"
        )
    
    val FormalParameterList: Nonterminal = 
    syn ( (FormalParameter ~ ",").!.* ~ LastFormalParameter )
    
    val FormalParameter: Nonterminal =
    syn ( VariableModifier.* ~ Type ~ VariableDeclaratorId )
    
    val VariableModifier: Nonterminal =
    syn ( "final" 
        | Annotation
        )
    
    val LastFormalParameter: Nonterminal = 
    syn ( VariableModifier.* ~ Type ~ "..." ~ VariableDeclaratorId
        | FormalParameter
        )
    
    val MethodModifier: Nonterminal =  
    syn ( Annotation 
        | "public" 
        | "protected" 
        | "private" 
        | "abstract" 
        | "static" 
        | "final" 
        | "synchronized" 
        | "native" 
        | "strictfp"
        )
    
    val Result: Nonterminal = 
    syn ( Type 
        | "void"
        )
    
    val Throws: Nonterminal = 
    syn ( "throws" ~ ExceptionType.+(",") )
    
    val ExceptionType: Nonterminal = 
    syn ( TypeName )
    
    val MethodBody: Nonterminal =
    syn ( Block 
        | ";"
        )
    
    val Annotation: Nonterminal = 
    syn ( "@" ~ TypeName ~ "(" ~ ElementValuePair.*(",") ~ ")"
        | "@" ~ TypeName ~ ("(" ~ ElementValue ~ ")").!.?
        )
    
    val ElementValuePair: Nonterminal = 
    syn ( Identifier ~ "=" ~ ElementValue )
    
    val ElementValue: Nonterminal = 
    syn ( Expression($) 
        | Annotation
        | ElementValueArrayInitializer
        )
    
    val ElementValueArrayInitializer: Nonterminal = 
    syn ( "{" ~ ElementValues.? ~ ",".? ~ "}" )
    
    val ElementValues: Nonterminal = 
    syn ( ElementValue.+(",") )
    
    val EnumDeclaration: Nonterminal = 
    syn ( ClassModifier.* ~ "enum" ~ Identifier ~ ("implements" ~ TypeList).!.? ~ EnumBody )
    
    val EnumBody: Nonterminal =
    syn ( "{" ~ EnumConstant.*(",") ~ ",".? ~ EnumBodyDeclarations.? ~ "}" )
    
    val EnumConstant: Nonterminal = 
    syn ( Annotation.* ~ Identifier ~ Arguments.? ~ ClassBody.? )
    
    val Arguments: Nonterminal = 
    syn ( "(" ~ ArgumentList.? ~ ")" )
    
    val EnumBodyDeclarations: Nonterminal = 
    syn ( ";" ~ ClassBodyDeclaration.* )
    
    val Block: Nonterminal = 
    syn ( "{" ~ BlockStatement.* ~ "}" )
    
    val BlockStatement: Nonterminal = 
    syn ( LocalVariableDeclarationStatement 
        | ClassDeclaration 
        | Statement
        )
    
    val LocalVariableDeclarationStatement: Nonterminal = 
    syn ( VariableModifier.* ~ Type ~ VariableDeclarators ~ ";" )
    
    val Statement: Nonterminal = 
    syn ( StatementWithoutTrailingSubstatement 
        | Identifier ~ ":" ~ Statement 
        | "if" ~ "(" ~ Expression($) ~ ")" ~ Statement 
        | "if" ~ "(" ~ Expression($) ~ ")" ~ StatementNoShortIf ~ "else" ~ Statement 
        | "while" ~ "(" ~ Expression($) ~ ")" ~ Statement 
        | ForStatement
        )
    
    val StatementWithoutTrailingSubstatement: Nonterminal = 
    syn ( Block 
        | ";" 
        | StatementExpression ~ ";" 
        | "assert" ~ Expression($) ~ (":" ~ Expression($)).!.? ~ ";" 
        | "switch" ~ "(" ~ Expression($) ~ ")" ~ "{" ~ SwitchBlockStatementGroup.* ~ SwitchLabel.* ~ "}" 
        | "do" ~ Statement ~ "while" ~ "(" ~ Expression($) ~ ")" ~ ";" 
        | "break" ~ Identifier.? ~ ";" 
        | "continue" ~ Identifier.? ~ ";" 
        | "return" ~ Expression($).? ~ ";" 
        | "synchronized" ~ "(" ~ Expression($) ~ ")" ~ Block 
        | "throw" ~ Expression($) ~ ";" 
        | "try" ~ Block ~ (CatchClause.+ | (CatchClause.* ~ Finally).!).! 
        | "try" ~ ResourceSpecification ~ Block ~ CatchClause.* ~ Finally.?
        )
    
    val StatementNoShortIf: Nonterminal = 
    syn ( StatementWithoutTrailingSubstatement 
        | Identifier ~ ":" ~ StatementNoShortIf 
        | "if" ~ "(" ~ Expression($) ~ ")" ~ StatementNoShortIf ~ "else" ~ StatementNoShortIf 
        | "while" ~ "(" ~ Expression($) ~ ")" ~ StatementNoShortIf 
        | "for" ~ "(" ~ ForInit.? ~ ";" ~ Expression($).? ~ ";" ~ ForUpdate.? ~ ")" ~ StatementNoShortIf
        )
    
    val ForStatement: Nonterminal = 
    syn ( "for" ~ "(" ~ ForInit.? ~ ";" ~ Expression($).? ~ ";" ~ ForUpdate.? ~ ")" ~ Statement 
        | "for" ~ "(" ~ FormalParameter ~ ":" ~ Expression($) ~ ")" ~ Statement
        )
    
    val StatementExpression: Nonterminal =
    syn ( Expression($) )
    
    val CatchClause: Nonterminal = 
    syn ( "catch" ~ "(" ~ VariableModifier.* ~ CatchType ~ Identifier ~ ")" ~ Block )
    
    val CatchType: Nonterminal = 
    syn ( QualifiedIdentifier.+("|") )
    
    val Finally: Nonterminal = 
    syn ( "finally" ~ Block )
    
    val ResourceSpecification: Nonterminal = 
    syn ( "(" ~ Resources ~ ";".? ~ ")" )
    
    val Resources: Nonterminal = 
    syn ( Resource.+(";") )
    
    val Resource: Nonterminal = 
    syn ( VariableModifier.* ~ ReferenceType ~ VariableDeclaratorId ~ "=" ~ Expression($) )
    
    val SwitchBlockStatementGroup: Nonterminal = 
    syn ( SwitchLabel.+ ~ BlockStatement.+ )
    
    val SwitchLabel: Nonterminal = 
    syn ( "case" ~ ConstantExpression ~ ":" 
        | "default" ~ ":"
        )
    
    val LocalVariableDeclaration: Nonterminal =  
    syn ( VariableModifier.* ~ Type ~ VariableDeclarator.+(",") )
    
    val ForInit: Nonterminal = 
    syn ( StatementExpression.+(",") 
        | LocalVariableDeclaration
        )
    
    val ForUpdate: Nonterminal = 
    syn ( StatementExpression.+(",") )
    
    val Literal: Nonterminal = 
    syn ( IntegerLiteral 
        | FloatingPointLiteral 
        | BooleanLiteral 
        | CharacterLiteral 
        | StringLiteral 
        | NullLiteral
        )
    
    val IntegerLiteral: Nonterminal = 
    syn ( DecimalIntegerLiteral.!>>(".") 
        | HexIntegerLiteral.!>>(".") 
        | OctalIntegerLiteral 
        | BinaryIntegerLiteral
        )
    
    val FloatingPointLiteral: Nonterminal = 
    syn ( DecimalFloatingPointLiteral 
        | HexadecimalFloatingPointLiteral
        )
    

    val FieldAccess: Nonterminal = 
    syn ( Primary ~ "." ~ Identifier 
        | "super" ~ "." ~ Identifier 
        | ClassName ~ "." ~ "super" ~ "." ~ Identifier
        )
    
    val MethodInvocation: Nonterminal = 
    syn ( MethodName ~ "(" ~ ArgumentList.? ~ ")" 
        | Primary ~ "." ~ NonWildTypeArguments.? ~ Identifier ~ "(" ~ ArgumentList.? ~ ")" 
        | "super" ~ "." ~ NonWildTypeArguments.? ~ Identifier ~ "(" ~ ArgumentList.? ~ ")" 
        | ClassName ~ "." ~ "super" ~ "." ~ NonWildTypeArguments.? ~ Identifier ~ "(" ~ ArgumentList.? ~ ")" 
        | TypeName ~ "." ~ NonWildTypeArguments ~ Identifier ~ "(" ~ ArgumentList.? ~ ")"
        )                  
    
    val AssignmentOperator: Nonterminal = 
    syn ( "=" 
        | "+=" 
        | "-=" 
        | "*=" 
        | "/=" 
        | "&=" 
        | "|=" 
        | "^=" 
        | "%=" 
        | "<<=" 
        | ">>=" 
        | ">>>="
        )
        
    val Expression: OperatorNonterminal = 
    syn (  Expression ~ "." ~ Identifier
        |  Expression ~ "." ~ "this"
        |  Expression ~ "." ~ "new" ~ TypeArguments.? ~ Identifier ~ TypeArgumentsOrDiamond.? ~ "(" ~ ArgumentList.? ~ ")" ~ ClassBody.?
        |  Expression ~ "." ~ NonWildTypeArguments ~ ExplicitGenericInvocationSuffix    
        |  Expression ~ "." ~ "super" ~ ("." ~ Identifier).!.? ~ Arguments
        |  Type ~ "." ~ "class"
        |  "void" ~ "." ~ "class"
        |  Expression ~ "(" ~ ArgumentList.? ~ ")"     
        |  Expression ~ "[" ~ Expression ~ "]"
        |> Expression ~ "++"
        |  Expression ~ "--"
        |> "+".!>>("+") ~ Expression
        |  "-".!>>("-") ~ Expression
        |  "++" ~ Expression
        |  "--" ~ Expression 
        |  "!" ~ Expression
        |  "~" ~ Expression
        |  "new" ~ ClassInstanceCreationExpression
        |  "new" ~ ArrayCreationExpression
        |  "(" ~ PrimitiveType ~ ")" ~ Expression
        |  "(" ~ ReferenceType ~ ")" ~ Expression    
        |> left ( Expression ~ "*" ~ Expression 
        |         Expression ~ "/" ~ Expression 
        |         Expression ~ "%" ~ Expression )
        |> left ( Expression ~ "+".!>>("+") ~ Expression
        |         Expression ~ "-".!>>("-") ~ Expression )
        |> left ( Expression ~ "<<" ~ Expression 
        |         Expression ~ ">>".!>>(">") ~ Expression
        |         Expression ~ ">>>" ~ Expression )
        |> left ( Expression ~ "<".!>>("[=<]".r) ~ Expression
        |         Expression ~ ">".!>>("[=>]".r) ~ Expression 
        |         Expression ~ "<=" ~ Expression
        |         Expression ~ ">=" ~ Expression
        |         Expression ~ "instanceof" ~ Type ) 
        |> left ( Expression ~ "==" ~ Expression
        |         Expression ~ "!=" ~ Expression )
        |> Expression ~ "&".!>>("&") ~ Expression
        |> Expression ~ "^" ~ Expression
        |>  Expression ~ "|".!>>("|") ~ Expression 
        |> Expression ~ "&&" ~ Expression
        |> Expression ~ "||" ~ Expression
        |> Expression ~ "?" ~ Expression ~ ":" ~ Expression 
        |> Expression ~ AssignmentOperator ~ Expression
        | "(" ~ Expression ~ ")"
        | Primary
        )
        
    val Primary: Nonterminal =  
    syn ( Literal 
        | "this"
        | "super"
        | Identifier
        )        

    val ClassInstanceCreationExpression: Nonterminal =
    syn ( TypeArguments.? ~ TypeDeclSpecifier ~ TypeArgumentsOrDiamond.? ~ "(" ~ ArgumentList.? ~ ")" ~ ClassBody.? )
    
    val TypeArgumentsOrDiamond: Nonterminal = 
    syn ( "<" ~ ">" 
        | TypeArguments
        )

    val ArgumentList: Nonterminal =
    syn (Expression($).+(","))

    val ArrayCreationExpression: Nonterminal = 
    syn ( (PrimitiveType | ReferenceType).! ~ DimExpr.+ ~ ("[" ~ "]").!.* 
        | (PrimitiveType | ReferenceTypeNonArrayType).! ~ ("[" ~ "]").!.+ ~ ArrayInitializer
        )
    
    val DimExpr: Nonterminal = 
    syn ("[" ~ Expression($) ~ "]")
        
    val ConstantExpression: Nonterminal = 
    syn ( Expression($) )
    
    val ClassName: Nonterminal = 
    syn ( QualifiedIdentifier )
        
    val MethodName: Nonterminal = 
    syn ( QualifiedIdentifier )
    
    val TypeDeclSpecifier: Nonterminal = 
    syn ( Identifier ~ (TypeArguments.? ~ "." ~ Identifier).!.* )
    
    val SuperSuffix: Nonterminal = 
    syn ( Arguments 
        | "." ~ Identifier ~ Arguments.?
        )
    
    val ExplicitGenericInvocationSuffix: Nonterminal = 
    syn ( "super" ~ SuperSuffix 
        | Identifier ~ Arguments
        )
}
