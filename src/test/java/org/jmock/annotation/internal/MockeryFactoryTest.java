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

import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import org.jmock.Mockery;

/**
 * Unit test for {@link MockeryFactory}.
 * <p>
 * @author Balazs Berkes
 */
public class MockeryFactoryTest {

    private MockeryFactory underTest;

    @Before
    public void setUp() {
        underTest = MockeryFactory.getSingleton();
    }

    @After
    public void tearDown() {
        resetDefaultClassImposteriser();
    }

    @Test
    public void testCreateMockeryShouldCreateNewMockery() {
        Mockery mockery = underTest.createMockery();

        assertMockeryCreated(mockery);
    }

    @Test
    public void testCreateMockeryShouldCreateClassImposteriserMockeryByDefault() {
        Mockery mockery = underTest.createMockery();

        mockery.mock(Object.class);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateMockeryShouldCreateNonClassImposteriserMockeryWhenSet() {
        MockeryFactory.imposteriseInstance(false);

        Mockery mockery = underTest.createMockery();

        mockery.mock(Object.class);
    }

    @Test
    public void testCreateMockeryShouldCreateMockeryWhenClassImposteriserChanged() {
        MockeryFactory.imposteriseInstance(false);

        Mockery mockery = underTest.createMockery();

        assertMockeryCreated(mockery);
    }

    private void assertMockeryCreated(Mockery mockery) {
        assertNotNull(mockery);
    }

    private void resetDefaultClassImposteriser() {
        MockeryFactory.imposteriseInstance(true);
    }
}
