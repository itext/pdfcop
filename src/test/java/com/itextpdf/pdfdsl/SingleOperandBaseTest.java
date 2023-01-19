package com.itextpdf.pdfdsl;

import org.junit.Assert;
import org.junit.Test;

public abstract class SingleOperandBaseTest extends AbstractTest {

    private String value;
    private String operand;

    private static final int OPERAND_INDEX = 0;
    private static final int OPERATOR_INDEX = 1;
    private static final int EXPECTED_CHILD_COUNT = 2;

    public SingleOperandBaseTest(String syntaxToCheck, boolean pass, String value, String operand) {
        super(pass, syntaxToCheck);

        this.value = value;
        this.operand = operand;
    }

    @Test
    public void test() {
        Assert.assertEquals(EXPECTED_CHILD_COUNT, super.context.getChildCount());
        Assert.assertEquals(this.value, getValue(OPERAND_INDEX));
        Assert.assertEquals(this.operand, getValue(OPERATOR_INDEX));
    }
}
