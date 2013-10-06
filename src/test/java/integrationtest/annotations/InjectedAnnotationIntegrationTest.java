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
package integrationtest.annotations;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import org.jmock.Mockery;

import org.jmock.annotation.Injected;
import org.jmock.annotation.JMockAnnotations;
import org.jmock.annotation.JMockery;

import org.junit.Test;

/**
 * Integration test for {@link Injected @Injected}. Not null fields should be skipped.
 * <p>
 * @author Balazs Berkes
 */
public class InjectedAnnotationIntegrationTest {

    private Object initializedObject = new Object();

    @JMockery
    private Mockery mockery;
    @Injected
    private Object preInitInjected = initializedObject;
    @Injected
    private Object injected;

    @Test
    public void testInitializeShouldNotCreateNewObjectWhenInjectedFieldAlreadyInstantiated() {
        JMockAnnotations.initialize(this);
        assertEquals(initializedObject, preInitInjected);
    }

    @Test
    public void testInitializeShouldCreateNewObjectWhenInjectedFieldIsNotInstantiated() {
        JMockAnnotations.initialize(this);
        assertNotNull(injected);
        assertNotEquals(initializedObject, injected);
    }
}
