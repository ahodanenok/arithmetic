package ahodanenok.arithmetic;

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
