package com.remainder.service;

import com.remainder.service.controller.RemainderController;
import com.remainder.service.entity.RemainderInput;
import com.remainder.service.utils.ControllerUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;

import javax.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.mockito.Mockito;

@SpringBootTest
class RemainderControllerTest {

    @MockBean
    private ControllerUtils controllerUtils;

    @Mock
    private HttpServletRequest request;

    private RemainderController remainderController;

    @BeforeEach
    void setUp() {
        remainderController = new RemainderController();
    }

    @Test
    @DisplayName("Test case for POST: 7 mod 5 with n=12345")
    void solveRemainderPostTest1() {
        RemainderInput testCase = new RemainderInput(7, 5, 12345);
        executeRemainderPostTest(testCase, 12339);
    }

    @Test
    @DisplayName("Test case for POST: 5 mod 0 with n=4")
    void solveRemainderPostTest2()  {
        RemainderInput testCase = new RemainderInput(5, 0, 4);
        executeRemainderPostTest(testCase, 0);
    }

    @Test
    @DisplayName("Test case for POST: 10 mod 5 with n=15")
    void solveRemainderPostTest3() {
        RemainderInput testCase = new RemainderInput(10, 5, 15);
        executeRemainderPostTest(testCase, 15);
    }

    @Test
    @DisplayName("Test case for POST: 17 mod 8 with n=54321")
    void solveRemainderPostTest4() {
        RemainderInput testCase = new RemainderInput(17, 8, 54321);
        executeRemainderPostTest(testCase, 54306);
    }

    @Test
    @DisplayName("Test case for POST: 499999993 mod 9 with n=1000000000")
    void solveRemainderPostTest5() {
        RemainderInput testCase = new RemainderInput(499999993, 9, 1000000000);
        executeRemainderPostTest(testCase, 999999995);
    }

    @Test
    @DisplayName("Test case for POST: 10 mod 5 with n=187")
    void solveRemainderPostTest6() {
        RemainderInput testCase = new RemainderInput(10, 5, 187);
        executeRemainderPostTest(testCase, 185);
    }

    @Test
    @DisplayName("Test case for POST: 2 mod 0 with n=999999999")
    void solveRemainderPostTest7() {
        RemainderInput testCase = new RemainderInput(2, 0, 999999999);
        executeRemainderPostTest(testCase, 999999998);
    }

    @Test
    @DisplayName("Test case for GET: 7 mod 5 with n=12345")
    void solveRemainderGetTest1() {
        executeRemainderGetTest(7, 5, 12345, 12339);
    }

    @Test
    @DisplayName("Test case for GET: 5 mod 0 with n=4")
    void solveRemainderGetTest2() {
        executeRemainderGetTest(5, 0, 4, 0);
    }

    @Test
    @DisplayName("Test case for GET: 10 mod 5 with n=15")
    void solveRemainderGetTest3() {
        executeRemainderGetTest(10, 5, 15, 15);
    }

    @Test
    @DisplayName("Test case for GET: 17 mod 8 with n=54321")
    void solveRemainderGetTest4() {
        executeRemainderGetTest(17, 8, 54321, 54306);
    }

    @Test
    @DisplayName("Test case for GET: 499999993 mod 9 with n=1000000000")
    void solveRemainderGetTest5() {
        executeRemainderGetTest(499999993, 9, 1000000000, 999999995);
    }

    @Test
    @DisplayName("Test case for GET: 10 mod 5 with n=187")
    void solveRemainderGetTest6() {
        executeRemainderGetTest(10, 5, 187, 185);
    }

    @Test
    @DisplayName("Test case for GET: 2 mod 0 with n=999999999")
    void solveRemainderGetTest7() throws MethodArgumentNotValidException {
        executeRemainderGetTest(2, 0, 999999999, 999999998);
    }


    @Test
    @DisplayName("Test case for POST: Unhandled Exception")
    void solveRemainderPostUnhandledExceptionTest() {
    	
        RemainderInput testCase = new RemainderInput(0, null, 1);
        
        Assertions.assertThrows(Exception.class, () -> {
            remainderController.solveRemainderPost(request, testCase);
        });
    }

    @Test
    @DisplayName("Test case for GET: Unhandled Exception")
    void solveRemainderGetUnhandledExceptionTest() {
    	
        Assertions.assertThrows(Exception.class, () -> {
            remainderController.solveRemainderGet(null, null, null, 1);
        });
    }

    private void executeRemainderPostTest(RemainderInput testCase, int expectedRemainder) {
        // Configuración de los atributos de la solicitud simulada
        Map<String, Object> requestAttributes = new HashMap<>();
        requestAttributes.put("token_update", true);
        requestAttributes.put("token_updated", "mockedToken");

        // Configuración del comportamiento simulado del objeto 'request'
        Mockito.when(request.getAttribute("token_update")).thenReturn(requestAttributes.get("token_update"));
        Mockito.when(request.getAttribute("token_updated")).thenReturn(requestAttributes.get("token_updated"));

        // Llamada al método del controlador para resolver el resto (POST)
        ResponseEntity<Map<String, Object>> responseEntity = remainderController.solveRemainderPost(request, testCase);

        // Verificación de la respuesta esperada
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        // Obtención de la respuesta
        Map<String, Object> response = responseEntity.getBody();

        // Verificación del token
        Assertions.assertEquals("mockedToken", response.get("token"));

        // Verificación del resultado del resto
        Assertions.assertEquals(1, ((ArrayList<Integer>) response.get("Remainder Result")).size());
        Assertions.assertEquals(expectedRemainder, ((ArrayList<Integer>) response.get("Remainder Result")).get(0));
    }

    private void executeRemainderGetTest(int xValue, int yValue, int nValue, int expectedRemainder) {
        // Configuración de los atributos de la solicitud simulada
        Map<String, Object> requestAttributes = new HashMap<>();
        requestAttributes.put("token_update", true);
        requestAttributes.put("token_updated", "mockedToken");

        // Configuración del comportamiento simulado del objeto 'request'
        Mockito.when(request.getAttribute("token_update")).thenReturn(requestAttributes.get("token_update"));
        Mockito.when(request.getAttribute("token_updated")).thenReturn(requestAttributes.get("token_updated"));

        // Llamada al método del controlador para resolver el resto (GET)
        ResponseEntity<Map<String, Object>> responseEntity = remainderController.solveRemainderGet(request, xValue, yValue, nValue);

        // Verificación de la respuesta esperada
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        // Obtención de la respuesta
        Map<String, Object> response = responseEntity.getBody();

        // Verificación del token
        Assertions.assertEquals("mockedToken", response.get("token"));

        // Verificación del resultado del resto
        Assertions.assertEquals(1, ((ArrayList<Integer>) response.get("Remainder Result")).size());
        Assertions.assertEquals(expectedRemainder, ((ArrayList<Integer>) response.get("Remainder Result")).get(0));
    }

}
