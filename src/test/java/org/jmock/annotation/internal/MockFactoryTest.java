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
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

import org.jmock.Mockery;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * Unit test for {@link MockFactory}.
 * <p>
 * @author Balazs Berkes
 */
public class MockFactoryTest {

    @Mock
    private Mockery mockery;

    private MockFactory underTest;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        underTest = new MockFactory(mockery);
    }

    @Test
    public void testCreateMockNamelessMock() {
        DummyClass expected = givenClassToMock();

        DummyClass actual = underTest.createMock(DummyClass.class);

        assertEquals(expected, actual);
    }

    @Test
    public void testCreateMockNamedMock() {
        DummyClass expected = givenNamedClassToMock();

        DummyClass actual = underTest.createMock(DummyClass.class, "dummy");

        assertEquals(expected, actual);
    }

    private DummyClass givenClassToMock() {
        DummyClass expected = new DummyClass();
        when(mockery.mock(DummyClass.class)).thenReturn(expected);
        return expected;
    }

    private DummyClass givenNamedClassToMock() {
        DummyClass expected = new DummyClass();
        when(mockery.mock(eq(DummyClass.class), anyString())).thenReturn(expected);
        return expected;
    }

    public static class DummyClass {
    }

}
