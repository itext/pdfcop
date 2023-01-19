package com.itextpdf.pdfdsl;

import org.junit.Assert;
import org.junit.Test;

public abstract class NoOperandBaseTest extends AbstractTest {

    private String operator;

    private static final int OPERATOR_INDEX = 0;
    private static final int EXPECTED_VALUE = 1;

    public NoOperandBaseTest(String syntaxToCheck, boolean pass) {
        super(pass, syntaxToCheck);

        this.operator = syntaxToCheck;
    }

    @Test
    public void test() {
        Assert.assertEquals(EXPECTED_VALUE, super.context.getChildCount());
        Assert.assertEquals(this.operator, getValue(OPERATOR_INDEX));
    }

}