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
package integrationtest;

import org.junit.Test;
import org.mockito.Mock;

import org.jmock.annotation.JMockAnnotations;

/**
 * Integration test for test class without annotated {@code Mockery}.
 * <p>
 * @author Balazs Berkes
 */
public class MissingMockeryAnnotationIntegrationTest {

    @Mock
    private Object mock;

    @Test(expected = RuntimeException.class)
    public void testInitializeShouldThrowExceptionWhenNoMockeryPresented() {
        JMockAnnotations.initialize(this);
    }
}
