/**
 * Date de création     : 16.10.2021
 * Groupe               : AMT-D-Flip-Flop
 * Description          : Tester le HomeController
 * Remarque             : -
 */

package com.amt.dflipflop;

import com.amt.dflipflop.Controllers.HomeController;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.*;
@SpringBootTest
class FlipFlopApplicationTests {

    @Autowired
    private HomeController controller;

    /**
     * Test that the context is creating the controller
     */
    @Test
    void contextLoads() {
        MatcherAssert.assertThat(controller, is(notNullValue()));
    }

}
