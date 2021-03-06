/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
grammar BooleanGrammar;

//
// super simple grammar to parse boolean expressions
//

parse
 : expression EOF
 ;

expression
    : '(' expression ')'                                    #parensExpr
    | NOT expression                                        #negationExpr
    | left=expression operator=(AND | OR) right=expression  #binaryOp
    | (TRUE | FALSE)                                        #literal
    | IDENTIFIER                                            #identifier
    ;

AND:     'AND';
OR:      'OR';
NOT:     'NOT';
TRUE:    'TRUE';
FALSE:   'FALSE';

IDENTIFIER
    : (LETTER | '_') (LETTER | DIGIT | '_' )*
    ;

fragment DIGIT
    : ('0'..'9')
    ;

fragment LETTER
    : [A-Za-z]
    ;

WS
    : [ \r\n\t]+ -> channel(HIDDEN)
    ;
