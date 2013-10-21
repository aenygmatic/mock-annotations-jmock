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
import static org.mockannotations.utils.MockAnnotationReflectionUtils.getField;
import static org.mockannotations.utils.MockAnnotationReflectionUtils.setField;
import static org.mockannotations.utils.MockAnnotationValidationUtils.assertNotNull;
import static org.mockannotations.utils.MockAnnotationValidationUtils.isNull;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;
import java.util.NavigableMap;
import java.util.TreeMap;

import org.mockannotations.ClassInitializer;
import org.mockannotations.MockHolder;
import org.mockannotations.MockInjector;
import org.mockannotations.utils.AnnotationScanner;

import org.jmock.Mockery;

import org.jmock.annotation.internal.MockFactory;
import org.jmock.annotation.internal.MockeryFactory;

/**
 * Initialize the test class. Scans for {@link Mock @Mock}, {@link JMockery @JMockery} and {@link Injected @Injected}
 * annotations.
 * <p>
 * @author Balazs Berkes
 */
public class JMockAnnotations {

    /**
     * Initialize the test class. Scans for {@link Mock @Mock}, {@link JMockery @JMockery} and
     * {@link Injected @Injected} annotations. Fields annotated with {@code @Mock} will be filled up with mocked object
     * created by the {@code Mockery} annotated by {@code @JMockery} annotation. At least one {@code Mockery} with
     * {@code @JMockery} annotation must be presented.
     * <p>
     * Usage:
     * <pre>
     *     &#064;Before
     *     public void setUp() {
     *         JMockAnnotations.initialize(this);
     *     }
     * </pre>
     *
     * @param testClass the test class
     */
    public static void initialize(Object testClass) {
        assertNotNull(testClass, "Test class cannot be null!");
        new JMockAnnotationsInitializer().initialize(testClass);
    }

    public static class MissingMockeryException extends RuntimeException {

        private MissingMockeryException() {
            super("At least one field must be annotated with @JMockery!");
        }
    }

    public static class InvalidJMockeryFieldException extends RuntimeException {

        private InvalidJMockeryFieldException(Field field) {
            super(String.format("Field annotated with @JMockery must be type of org.jmock.Mockery! Field: %s", field));
        }
    }

    private JMockAnnotations() {
    }

    private static class JMockAnnotationsInitializer {

        private final AnnotationScanner<JMockery> mockeryScanner = AnnotationScanner.getScanner(JMockery.class);
        private final AnnotationScanner<Injected> injectedSanner = AnnotationScanner.getScanner(Injected.class);
        private final NavigableMap<String, Mockery> mockeries = new TreeMap<String, Mockery>();
        private final ClassInitializer classInitializer = new ClassInitializer();
        private final MockeryFactory mockeryFactory = MockeryFactory.getSingleton();
        private final List<MockHolder> mocks = new LinkedList<MockHolder>();
        private final MockInjector mockInjector = new MockInjector(mocks);

        private MockFactory mockFactory;
        private Object testClass;

        private void initialize(Object testClass) {
            this.testClass = testClass;
            initializeMockeries();
            initializeMockFactory();
            initializeMocks();
            initializeTestedClasses();
        }

        private void initializeMockeries() {
            for (Field field : mockeryScanner.scan(testClass)) {
                createAndInjectMockery(field);
            }
            if (mockeries.isEmpty()) {
                throw new MissingMockeryException();
            }
        }

        private void createAndInjectMockery(Field field) {
            assertFieldType(field);
            Mockery mockery = mockeryFactory.createMockery();
            injectToTestclass(field, mockery);
            mockeries.put(field.getName(), mockery);
        }

        private void assertFieldType(Field field) throws RuntimeException {
            if (field.getType() != Mockery.class) {
                throw new InvalidJMockeryFieldException(field);
            }
        }

        private void initializeMocks() {
            for (Field field : getAllDeclaredFields(testClass.getClass())) {
                createAndInjectMock(field);
            }
        }

        private void createAndInjectMock(Field field) {
            if (field.isAnnotationPresent(Mock.class)) {
                processMockAnnotation(field);
            } else {
                processJMockAnnotation(field);
            }
        }

        private void processMockAnnotation(Field field) {
            Mock annotation = field.getAnnotation(Mock.class);
            MockHolder mockHolder = mockFactory.createMock(field, annotation.name(), annotation.mockery());
            mocks.add(mockHolder);
            injectToTestclass(field, mockHolder.getMock());
        }

        private void processJMockAnnotation(Field field) {
            if (field.isAnnotationPresent(org.jmock.auto.Mock.class)) {
                MockHolder mockHolder = mockFactory.createMock(field, field.getName(), "");
                mocks.add(mockHolder);
                injectToTestclass(field, mockHolder.getMock());
            }
        }

        private void initializeTestedClasses() {
            for (Field field : injectedSanner.scan(testClass)) {
                Object testedClass = createInstanceIfNull(field);
                mockInjector.injectTo(testedClass);
            }
        }

        private Object createInstanceIfNull(Field field) {
            Object testedClass = getField(field, testClass);
            if (isNull(testedClass)) {
                testedClass = classInitializer.initialize(field.getType(), mocks);
                injectToTestclass(field, testedClass);
            }
            return testedClass;
        }

        private void injectToTestclass(Field field, Object value) {
            setField(field, testClass, value);
        }

        private void initializeMockFactory() {
            mockFactory = new MockFactory(mockeries);
        }
    }
}
