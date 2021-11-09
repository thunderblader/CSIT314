package com.example.csit314.useradminview;

/*import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;*/

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class UserAdminHelperTest {

    @Test
    public void getParam() {
        UserAdminHelper helper = new UserAdminHelper("name", "+999", "admin");
        Assert.assertEquals("name", helper.getName());
        Assert.assertEquals("+999", helper.getNumber());
        Assert.assertEquals("admin", helper.getUser_group());
    }
}