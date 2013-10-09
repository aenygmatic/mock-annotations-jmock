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

import org.junit.Test;

import org.jmock.Mockery;

import org.jmock.annotation.JMockAnnotations;
import org.jmock.annotation.JMockery;

/**
 * Integration test for {@link JMockery @JMockery}.
 * <p>
 * @author Balazs Berkes
 */
public class JMockeryAnnotationPositiveIntegrationTest {

    @JMockery
    private Mockery context1;
    @JMockery
    private Mockery context2;

    @Test
    public void testAnnotatedFieldShouldBeInjecte() {
        JMockAnnotations.initialize(this);

        assertNotNull(context1);
    }

    @Test
    public void testAllAnnotatedFieldShouldBeInjecte() {
        JMockAnnotations.initialize(this);

        assertNotNull(context1);
        assertNotNull(context2);
    }

}
