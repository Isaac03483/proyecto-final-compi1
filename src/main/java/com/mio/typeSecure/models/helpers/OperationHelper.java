package com.mio.typeSecure.models.helpers;

import com.mio.typeSecure.models.instructions.Variable;
import com.mio.typeSecure.models.instructions.VariableType;

public class OperationHelper {

    public static void plus(String left, String right, Variable result, VariableType type){

        switch (type){
            case NUMBER -> {

                double leftNumber = Double.parseDouble(left);
                double rightNumber = Double.parseDouble(right);
                double resultValue = leftNumber + rightNumber;
                result.value = String.valueOf(resultValue % 1 == 0 ? (int) resultValue : resultValue);
                result.variableType = VariableType.NUMBER;

            }
            case BIG_INT -> {
                int leftNumber = getIntBigPart(left);
                int rightNumber = getIntBigPart(right);
                int resultValue = leftNumber+rightNumber;
                result.value = resultValue+"n";
                result.variableType = VariableType.BIG_INT;
            }
        }

    }

    public static void concat(String left, String right, Variable result){
        result.variableType = VariableType.STRING;
        result.value = left+right;
    }

    public static void minus(String left, String right, Variable result, VariableType type) {
        switch (type){
            case NUMBER -> {

                double leftNumber = Double.parseDouble(left);
                double rightNumber = Double.parseDouble(right);
                double resultValue = leftNumber - rightNumber;
                result.value = String.valueOf(resultValue % 1 == 0 ? (int) resultValue : resultValue);
                result.variableType = VariableType.NUMBER;

            }
            case BIG_INT -> {
                int leftNumber = getIntBigPart(left);
                int rightNumber = getIntBigPart(right);
                int resultValue = leftNumber-rightNumber;
                result.value = resultValue+"n";
                result.variableType = VariableType.BIG_INT;
            }
        }
    }

    public static void times(String left, String right, Variable result, VariableType type) {
        switch (type){
            case NUMBER -> {

                double leftNumber = Double.parseDouble(left);
                double rightNumber = Double.parseDouble(right);
                double resultValue = leftNumber * rightNumber;
                result.value = String.valueOf(resultValue % 1 == 0 ? (int) resultValue : resultValue);
                result.variableType = VariableType.NUMBER;

            }
            case BIG_INT -> {
                int leftNumber = getIntBigPart(left);
                int rightNumber = getIntBigPart(right);
                int resultValue = leftNumber*rightNumber;
                result.value = resultValue+"n";
                result.variableType = VariableType.BIG_INT;
            }
        }
    }

    public static void divide(String left, String right, Variable result, VariableType type) {
        switch (type){
            case NUMBER -> {

                double leftNumber = Double.parseDouble(left);
                double rightNumber = Double.parseDouble(right);
                double resultValue = leftNumber / rightNumber;
                result.value = String.valueOf(resultValue % 1 == 0 ? (int) resultValue : resultValue);
                result.variableType = VariableType.NUMBER;

            }
            case BIG_INT -> {

                int leftNumber = getIntBigPart(left);
                int rightNumber = getIntBigPart(right);
                int resultValue = leftNumber/rightNumber;
                result.value = resultValue+"n";
                result.variableType = VariableType.BIG_INT;
            }
        }
    }

    public static void mod(String left, String right, Variable result, VariableType type) {
        switch (type){
            case NUMBER -> {

                double leftNumber = Double.parseDouble(left);
                double rightNumber = Double.parseDouble(right);
                double resultValue = leftNumber % rightNumber;
                result.value = String.valueOf(resultValue % 1 == 0 ? (int) resultValue : resultValue);
                result.variableType = VariableType.NUMBER;

            }
            case BIG_INT -> {
                int leftNumber = getIntBigPart(left);
                int rightNumber = getIntBigPart(right);
                int resultValue = leftNumber%rightNumber;
                result.value = resultValue+"n";
                result.variableType = VariableType.BIG_INT;
            }
        }
    }

    public static void increment(Variable result){
        switch(result.variableType){
            case NUMBER -> {
                double value = Double.parseDouble(result.value);
                value++;
                result.value = String.valueOf(value);
            }
            case BIG_INT -> {
                int value = getIntBigPart(result.value);
                value++;
                result.value = String.valueOf(value);
            }
        }

    }

    public static void decrement(Variable result){
        switch(result.variableType){
            case NUMBER -> {
                double value = Double.parseDouble(result.value);
                value--;
                result.value = String.valueOf(value);
            }
            case BIG_INT -> {
                int value = getIntBigPart(result.value);
                value--;
                result.value = String.valueOf(value);
            }
        }
    }

    public static void and(String left, String right, Variable result){
        boolean leftVal = Boolean.parseBoolean(left);
        boolean rightVal = Boolean.parseBoolean(right);
        boolean resultVal = leftVal && rightVal;
        result.value = String.valueOf(resultVal);
        result.variableType = VariableType.BOOLEAN;
    }

    public static void or(String left, String right, Variable result){

        boolean leftVal = Boolean.parseBoolean(left);
        boolean rightVal = Boolean.parseBoolean(right);
        boolean resultVal = leftVal || rightVal;
        result.value = String.valueOf(resultVal);
        result.variableType = VariableType.BOOLEAN;

    }

    public static void less(String left, String right, Variable result, VariableType type){
        switch (type){
            case NUMBER -> {

                double leftNumber = Double.parseDouble(left);
                double rightNumber = Double.parseDouble(right);
                boolean resultValue = leftNumber < rightNumber;
                result.value = String.valueOf(resultValue);

            }
            case BIG_INT -> {
                int leftNumber = getIntBigPart(left);
                int rightNumber = getIntBigPart(right);
                boolean resultValue = leftNumber<rightNumber;
                result.value = String.valueOf(resultValue);
            }
        }
        result.variableType = VariableType.BOOLEAN;

    }

    public static void lessEQ(String left, String right, Variable result, VariableType type){
        switch (type){
            case NUMBER -> {

                double leftNumber = Double.parseDouble(left);
                double rightNumber = Double.parseDouble(right);
                boolean resultValue = leftNumber <= rightNumber;
                result.value = String.valueOf(resultValue);

            }
            case BIG_INT -> {
                int leftNumber = getIntBigPart(left);
                int rightNumber = getIntBigPart(right);
                boolean resultValue = leftNumber<=rightNumber;
                result.value = String.valueOf(resultValue);
            }
        }
        result.variableType = VariableType.BOOLEAN;
    }

    public static void greater(String left, String right, Variable result, VariableType type){
        switch (type){
            case NUMBER -> {

                double leftNumber = Double.parseDouble(left);
                double rightNumber = Double.parseDouble(right);
                boolean resultValue = leftNumber > rightNumber;
                result.value = String.valueOf(resultValue);

            }
            case BIG_INT -> {
                int leftNumber = getIntBigPart(left);
                int rightNumber = getIntBigPart(right);
                boolean resultValue = leftNumber>rightNumber;
                result.value = String.valueOf(resultValue);
            }
        }
        result.variableType = VariableType.BOOLEAN;
    }

    public static void greaterEQ(String left, String right, Variable result, VariableType type){
        switch (type){
            case NUMBER -> {

                double leftNumber = Double.parseDouble(left);
                double rightNumber = Double.parseDouble(right);
                boolean resultValue = leftNumber >=rightNumber;
                result.value = String.valueOf(resultValue);

            }
            case BIG_INT -> {
                int leftNumber = getIntBigPart(left);
                int rightNumber = getIntBigPart(right);
                boolean resultValue = leftNumber>=rightNumber;
                result.value = String.valueOf(resultValue);
            }
        }
        result.variableType = VariableType.BOOLEAN;
    }

    public static void equals(String left, String right, Variable result, VariableType type){

        switch (type) {
            case NUMBER -> {

                double leftNumber = Double.parseDouble(left);
                double rightNumber = Double.parseDouble(right);
                boolean resultValue = leftNumber == rightNumber;
                result.value = String.valueOf(resultValue);

            }
            case BIG_INT -> {
                int leftNumber = getIntBigPart(left);
                int rightNumber = getIntBigPart(right);
                boolean resultValue = leftNumber == rightNumber;
                result.value = String.valueOf(resultValue);
            }

            default -> {
                boolean resultValue = left.equals(right);
                result.value = String.valueOf(resultValue);
            }
        }

        result.variableType = VariableType.BOOLEAN;
    }

    public static void notEQ(String left, String right, Variable result, VariableType type){

        switch (type) {
            case NUMBER -> {

                double leftNumber = Double.parseDouble(left);
                double rightNumber = Double.parseDouble(right);
                boolean resultValue = leftNumber != rightNumber;
                result.value = String.valueOf(resultValue);

            }
            case BIG_INT -> {
                int leftNumber = getIntBigPart(left);
                int rightNumber = getIntBigPart(right);
                boolean resultValue = leftNumber != rightNumber;
                result.value = String.valueOf(resultValue);
            }
            default -> {
                boolean resultValue = !left.equals(right);
                result.value = String.valueOf(resultValue);
            }
        }


        result.variableType = VariableType.BOOLEAN;
    }

    public static void not(String right, Variable result){
        boolean resultValue = !Boolean.parseBoolean(right);
        result.value = String.valueOf(resultValue);
        result.variableType = VariableType.BOOLEAN;
    }

    private static int getIntBigPart(String value){
        return value.equals("-1") ? -1 : Integer.parseInt(value.substring(0, value.length()-1));
    }

}
