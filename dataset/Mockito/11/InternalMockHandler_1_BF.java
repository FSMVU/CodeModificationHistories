//fixed some rawtype warnings (#504)

/*
 * Copyright (c) 2007 Mockito contributors
 * This program is made available under the terms of the MIT License.
 */
package org.mockito.internal;

import java.util.List;

import org.mockito.internal.stubbing.InvocationContainer;
import org.mockito.invocation.MockHandler;
import org.mockito.mock.MockCreationSettings;
import org.mockito.stubbing.Answer;

@SuppressWarnings("unchecked")
public interface InternalMockHandler<T> extends MockHandler {

    MockCreationSettings<T> getMockSettings();
    MockCreationSettings getMockSettings();

    void setAnswersForStubbing(List<Answer<?>> answers);

    InvocationContainer getInvocationContainer();

}
}