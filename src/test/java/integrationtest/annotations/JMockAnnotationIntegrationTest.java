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

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.jmock.Mockery;
import org.jmock.auto.Mock;

import org.jmock.annotation.JMockAnnotations;
import org.jmock.annotation.JMockery;

import org.junit.Test;

/**
 * Integration test for {@link Mock @Mock} annotation.
 * <p>
 * @author Balazs Berkes
 */
public class JMockAnnotationIntegrationTest {

    @JMockery
    private Mockery mockery;
    @Mock
    private Object object;
    @Mock
    private List<String> stringList;

    @Test
    public void testInitializeShouldInitAllAnnotatedField() {
        JMockAnnotations.initialize(this);

        assertNotNull(object);
        assertNotNull(stringList);
    }

    @Test
    public void testInitializeShouldInitAllAnnotatedFieldWithCorrectType() {
        JMockAnnotations.initialize(this);

        assertTrue(object instanceof Object);
        assertTrue(stringList instanceof List);
    }
}
