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

import static org.mockannotations.utils.MockAnnotationValidationUtils.isNull;

import org.jmock.Mockery;
import org.jmock.lib.legacy.ClassImposteriser;

/**
 * Factory for {@link Mockery}.
 * <p>
 * @author Balazs Berkes
 */
public class MockeryFactory {

    private static MockeryFactory singleton;
    private static volatile boolean imposteriseInstance = true;

    public static synchronized MockeryFactory getSingleton() {
        if (isNull(singleton)) {
            singleton = new MockeryFactory();
        }
        return singleton;
    }

    /**
     * Set up whether the {@link Mockery Mockeries} created by this factory should be able to mock classes.
     * <p>
     * @param imposterise {code true} means the create {@link Mockery Mockeries} can mock classes.
     */
    public static void imposteriseInstances(boolean imposterise) {
        imposteriseInstance = imposterise;
    }

    /**
     * Creates a new {@link Mockery} instance.
     * <p>
     * @return new {@literal Mockery}
     */
    public Mockery createMockery() {
        Mockery mockery;
        if (imposteriseInstance) {
            mockery = createInstanceImposteriserMockery();
        } else {
            mockery = createDefaultMockery();
        }
        return mockery;
    }

    private Mockery createDefaultMockery() {
        Mockery mockery;
        mockery = new Mockery();
        return mockery;
    }

    private Mockery createInstanceImposteriserMockery() {
        Mockery mockery = new Mockery();
        mockery.setImposteriser(ClassImposteriser.INSTANCE);
        return mockery;
    }

    private MockeryFactory() {
    }
}
