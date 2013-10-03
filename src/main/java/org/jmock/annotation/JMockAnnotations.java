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
package org.jmock.annotation;

import static org.mockannotations.utils.MockAnnotationReflectionUtils.getAllDeclaredFields;
import static org.mockannotations.utils.MockAnnotationReflectionUtils.setField;
import static org.mockannotations.utils.MockAnnotationValidationUtils.assertNotNull;

import java.lang.reflect.Field;
import java.util.NavigableMap;
import java.util.TreeMap;

import org.jmock.Mockery;

import org.jmock.annotation.internal.MockFactory;
import org.jmock.annotation.internal.MockeryFactory;

/**
 *
 * @author Balazs Berkes
 */
public class JMockAnnotations {

    public static void initialize(Object testClass) {
        assertNotNull(testClass, "Test class cannot be null!");
        new JMockAnnotationsInitializer().initialize(testClass);
    }

    private JMockAnnotations() {
    }

    private static class JMockAnnotationsInitializer {

        private final NavigableMap<String, MockFactory> mockeries = new TreeMap<String, MockFactory>();
        private final MockeryFactory mockeryFactory = MockeryFactory.getSingleton();

        private Object testClass;

        private void initialize(Object testClass) {
            this.testClass = testClass;
            initializeMockControls();
        }

        private void initializeMockControls() {
            for (Field field : getAllDeclaredFields(testClass.getClass())) {
                if (field.isAnnotationPresent(JMockery.class)) {
                    createAndInjectControl(field);
                }
            }
        }

        private void createAndInjectControl(Field field) {
            assertFieldType(field);
            Mockery mockery = mockeryFactory.createMockery();
            injectToTestclass(field, mockery);
            mockeries.put(field.getName(), new MockFactory(mockery));
        }

        private void assertFieldType(Field field) throws RuntimeException {
            if (field.getType() != Mockery.class) {
                throw new RuntimeException("Field annotated with @Mockery must be type of org.jmock.Mockery!");
            }
        }

        private void injectToTestclass(Field field, Object value) {
            setField(field, testClass, value);
        }
    }
}
