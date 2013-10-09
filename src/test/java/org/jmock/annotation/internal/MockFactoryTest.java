/*
 * Copyright 2013 Balazs Berkes.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jmock.annotation.internal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;
import java.util.NavigableMap;
import java.util.TreeMap;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.mockannotations.MockHolder;

import org.jmock.Mockery;

/**
 * Unit test for {@link MockFactory}.
 * <p>
 * @author Balazs Berkes
 */
public class MockFactoryTest {

    private static final String MOCK_NAME = "myMock";
    private static final DummyClass MOCK = new DummyClass();

    private NavigableMap<String, Mockery> namedMockeries;
    @Mock
    private Mockery defaultMockery;
    @Mock
    private Mockery namedMockery;

    private Field sourceField;
    private String mockName;
    private String mockeryName;

    private MockFactory underTest;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        initializeField();
        initializeMockeyMap();
    }

    @Test
    public void testCreateMockShouldCreateViaDefaultMockeryNamelessMock() {
        givenMockWithoutName();
        givenEmptyMockeryName();

        MockHolder mockHolder = underTest.createMock(sourceField, mockName, mockeryName);

        assertNamelessMockCreated(mockHolder);
    }

    @Test
    public void testCreateMockShouldCreateViaDefaultMockeryNamedMock() {
        givenMockWithName();
        givenEmptyMockeryName();

        MockHolder mockHolder = underTest.createMock(sourceField, mockName, mockeryName);

        assertNamedMockCreated(mockHolder);
    }

    @Test
    public void testCreateMockShouldCreateViaAssociatedMockeryNamelessMock() {
        givenMockWithoutName();
        givenAssociatedMockeryName();

        MockHolder mockHolder = underTest.createMock(sourceField, mockName, mockeryName);

        assertNamelessMockCreated(mockHolder);
    }

    @Test
    public void testCreateMockShouldCreateViaAssociatedMockeryNamedMock() {
        givenMockWithName();
        givenAssociatedMockeryName();

        MockHolder mockHolder = underTest.createMock(sourceField, mockName, mockeryName);

        assertNamedMockCreated(mockHolder);
    }

    private void assertNamelessMockCreated(MockHolder mockHolder) {
        assertEquals(MOCK, mockHolder.getMock());
        assertEquals(sourceField, mockHolder.getSourceField());
        assertTrue(mockHolder.getName().isEmpty());
    }

    private void assertNamedMockCreated(MockHolder mockHolder) {
        assertEquals(MOCK, mockHolder.getMock());
        assertEquals(sourceField, mockHolder.getSourceField());
        assertEquals(mockName, mockHolder.getName());
    }

    private void givenAssociatedMockeryName() {
        this.mockeryName = "namedMockery";
        when(namedMockery.mock(DummyClass.class, mockName)).thenReturn(MOCK);
    }

    private void givenEmptyMockeryName() {
        this.mockeryName = "";
        when(defaultMockery.mock(DummyClass.class, mockName)).thenReturn(MOCK);
    }

    private void givenMockWithName() {
        mockName = MOCK_NAME;
    }

    private void givenMockWithoutName() {
        mockName = "";
        when(defaultMockery.mock(DummyClass.class)).thenReturn(MOCK);
        when(namedMockery.mock(DummyClass.class)).thenReturn(MOCK);
    }

    private void initializeMockeyMap() {
        namedMockeries = new TreeMap<String, Mockery>();
        namedMockeries.put("defaultMockery", defaultMockery);
        namedMockeries.put("namedMockery", namedMockery);
        underTest = new MockFactory(namedMockeries);
    }

    private void initializeField() throws SecurityException {
        for (Field field : this.getClass().getDeclaredFields()) {
            if (field.getName().equals("MOCK")) {
                field.setAccessible(true);
                sourceField = field;
                break;
            }
        }
    }

    public static class DummyClass {
    }
}
