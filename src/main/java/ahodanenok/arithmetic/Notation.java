package ahodanenok.arithmetic;

import ahodanenok.arithmetic.infix.InfixNotationAstBuilder;
import ahodanenok.arithmetic.postfix.PostfixNotationAstBuilder;
import ahodanenok.arithmetic.prefix.PrefixNotationAstBuilder;

public enum Notation {

    PREFIX {
        NotationAstBuilder createAstBuilder() {
            return new PrefixNotationAstBuilder();
        }
    },
    INFIX {
        NotationAstBuilder createAstBuilder() {
            return new InfixNotationAstBuilder();
        }
    },
    POSTFIX {
        NotationAstBuilder createAstBuilder() {
            return new PostfixNotationAstBuilder();
        }
    };

    abstract NotationAstBuilder createAstBuilder();
}
