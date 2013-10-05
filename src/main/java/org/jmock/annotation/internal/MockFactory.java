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

import static org.mockannotations.utils.MockAnnotationValidationUtils.isEmpty;
import static org.mockannotations.utils.MockAnnotationValidationUtils.isNull;

import java.lang.reflect.Field;
import java.util.NavigableMap;

import org.mockannotations.MockHolder;

import org.jmock.Mockery;

/**
 * Creates mock objects via the given {@link Mockery Mockeries}.
 * <p>
 * @author Balazs Berkes
 */
public class MockFactory {

    private NavigableMap<String, Mockery> mockeries;
    private Mockery defaultMockery;

    public MockFactory(NavigableMap<String, Mockery> mockeries) {
        this.mockeries = mockeries;
        this.defaultMockery = mockeries.firstEntry().getValue();
    }

    public MockHolder createMock(Field field, String name, String mockeryName) {
        Mockery associatedMockery = getAssociatedMockery(mockeryName);
        Object mock = createMockObject(field.getType(), name, associatedMockery);

        return MockHolder.create(mock, field, name);
    }

    private Mockery getAssociatedMockery(String mockeryName) {
        Mockery associatedMockery = mockeries.get(mockeryName);
        if (isNull(associatedMockery)) {
            associatedMockery = defaultMockery;
        }
        return associatedMockery;
    }

    private Object createMockObject(Class<?> type, String name, Mockery associatedMockery) {
        if (isEmpty(name)) {
            return associatedMockery.mock(type);
        } else {
            return associatedMockery.mock(type, name);
        }
    }
}
