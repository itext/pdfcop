package com.itextpdf.pdfdsl;

import org.junit.Assert;
import org.junit.Test;

public abstract class GroupingBaseTest extends AbstractTest {

    private int expectedChildCount;

    public GroupingBaseTest(String syntaxToCheck, boolean pass, int childCount) {
        super(pass, syntaxToCheck);

        this.expectedChildCount = childCount;
    }

    @Test
    public void test() {
        Assert.assertEquals(this.expectedChildCount, super.context.getChildCount());
    }

}