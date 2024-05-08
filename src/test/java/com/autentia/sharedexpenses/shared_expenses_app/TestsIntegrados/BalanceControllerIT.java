package com.autentia.sharedexpenses.shared_expenses_app.TestsIntegrados;

import com.autentia.sharedexpenses.shared_expenses_app.Services.BalanceService;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class BalanceControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private BalanceService balanceService;

    @BeforeEach
    public void setUp() {

        Map<String, Double> balances = new HashMap<>();
        balances.put("Maria", 60.0);
        balances.put("Belen", 40.0);
        balances.put("Juan", 20.0);
        when(balanceService.calculateUsersBalance()).thenReturn(balances);

        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .defaultRequest(get("/").contentType(MediaType.APPLICATION_JSON))
                .build();

    }

    @Test
    public void shouldGetBalnces() throws Exception {
        mockMvc.perform(get("/balance"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.Maria").value(60.0))
                .andExpect(jsonPath("$.Belen").value(40.0))
                .andExpect(jsonPath("$.Juan").value(20.0));
    }
}
