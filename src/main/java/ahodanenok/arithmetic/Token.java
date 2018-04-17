package ahodanenok.arithmetic;

class Token {

    private TokenType type;
    private String value;

    Token(TokenType type) {
        this.type = type;
    }

    Token(TokenType type, String value) {
        this.type = type;
        this.value = value;
    }

    TokenType getType() {
        return type;
    }

    String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Token token = (Token) o;

        if (type != token.type) return false;
        return value != null ? value.equals(token.value) : token.value == null;
    }

    @Override
    public int hashCode() {
        int result = type.hashCode();
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Token: type=" + type + ", value=" + value;
    }
}
