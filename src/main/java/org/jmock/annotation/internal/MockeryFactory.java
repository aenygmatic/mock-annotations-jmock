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
 *
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

    public static void imposteriseInstance(boolean imposterise) {
        imposteriseInstance = imposterise;
    }

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
        Mockery mockery = new Mockery() {
            {
                setImposteriser(ClassImposteriser.INSTANCE);
            }
        };
        return mockery;
    }

    private MockeryFactory() {
    }
}
