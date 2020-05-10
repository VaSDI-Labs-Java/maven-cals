package ru.vadimushka_d;

public enum TypeOperator {
    Addition("+"),
    Subtraction("-"),
    Multiplication("*"),
    Division("/"),
    Remainder("%"),
    POW("^"),
    None("none");

    private final String typeValue;

    TypeOperator(String type) {
        typeValue = type;
    }

    public static TypeOperator getOperator(String pType){
        for (TypeOperator type: TypeOperator.values()){
            if (type.getTypeValue().equals(pType)){
                return type;
            }
        }
        return TypeOperator.None;
    }

    public String getTypeValue() {
        return typeValue;
    }
}
