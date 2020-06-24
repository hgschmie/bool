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

package de.softwareforge.bool;


import de.softwareforge.bool.antlr4.BooleanGrammarBaseVisitor;
import de.softwareforge.bool.antlr4.BooleanGrammarParser.BinaryOpContext;
import de.softwareforge.bool.antlr4.BooleanGrammarParser.IdentifierContext;
import de.softwareforge.bool.antlr4.BooleanGrammarParser.LiteralContext;
import de.softwareforge.bool.antlr4.BooleanGrammarParser.NegationExprContext;
import de.softwareforge.bool.antlr4.BooleanGrammarParser.ParensExprContext;

import java.util.function.Function;

class EvaluationVisitor extends BooleanGrammarBaseVisitor<Boolean> {

    private final Function<String, Boolean> identifierProvider;

    EvaluationVisitor(Function<String, Boolean> identifierProvider) {
        this.identifierProvider = identifierProvider;
    }

    @Override
    public Boolean visitIdentifier(IdentifierContext ctx) {
        final String name = ctx.IDENTIFIER().getText();
        Boolean result = identifierProvider.apply(name);
        if (result == null) {
            throw new IllegalStateException("Identifier '" + name + "' is null");
        }
        return result;
    }

    @Override
    public Boolean visitBinaryOp(BinaryOpContext ctx) {
        if (ctx.AND() != null) {
            return visit(ctx.left).booleanValue() && visit(ctx.right).booleanValue();
        } else if (ctx.OR() != null) {
            return visit(ctx.left).booleanValue() || visit(ctx.right).booleanValue();
        }
        throw new IllegalStateException("Unsupported binary operator: " + ctx.operator.getText());
    }

    @Override
    public Boolean visitNegationExpr(NegationExprContext ctx) {
        return !visit(ctx.expression());
    }

    @Override
    public Boolean visitParensExpr(ParensExprContext ctx) {
        return visit(ctx.expression());
    }

    @Override
    public Boolean visitLiteral(LiteralContext ctx) {
        if (ctx.TRUE() != null) {
            return Boolean.TRUE;
        } else if (ctx.FALSE() != null) {
            return Boolean.FALSE;
        }
        throw new IllegalStateException("Unsupported literal: " + ctx.getText());
    }

    @Override
    protected Boolean defaultResult() {
        return Boolean.TRUE;
    }

    @Override
    protected Boolean aggregateResult(Boolean aggregate, Boolean nextResult) {
        return aggregate.booleanValue() && nextResult.booleanValue();
    }
}
