package com.tech.sus_triage_api.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GeoUtilsTest {
    @Test
    void haversine_sameCoordinates_returnsZero() {
        double result = GeoUtils.haversine(-23.5, -46.6, -23.5, -46.6);
        assertEquals(0.0, result, 0.0001);
    }

    @Test
    void haversine_knownPoints_returnsExpectedDistance() {
        // São Paulo (-23.5505, -46.6333) e Rio de Janeiro (-22.9068, -43.1729)
        double result = GeoUtils.haversine(-23.5505, -46.6333, -22.9068, -43.1729);
        // Aproximadamente 361 km (conforme cálculo da função)
        assertEquals(360.75, result, 1.0);
    }

    @Test
    void haversine_negativeCoordinates_validDistance() {
        double result = GeoUtils.haversine(-10, -10, 10, 10);
        assertTrue(result > 0);
    }
}
