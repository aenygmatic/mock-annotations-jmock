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

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Fields annotated with {@code @Mock} will be filled up with mock object after the test class is initialized by
 * {@link JMockAnnotations#initialize(Object)}.
 * <p>
 * @author Balazs Berkes
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Mock {

    /**
     * Name of the mock.
     * <p>
     * @return the name of the mock object.
     */
    String name() default "";

    /**
     * Name of the associated {@link org.jmock.Mockery Mockery}.
     * <p>
     * @return name of the {@code Mockery}
     */
    String mockery() default "";
}
