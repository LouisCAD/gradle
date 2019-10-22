/*
 * Copyright 2009 the original author or authors.
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

package org.gradle.api.reporting.internal;

import org.gradle.api.Task;
import org.gradle.api.file.RegularFileProperty;
import org.gradle.api.model.ObjectFactory;
import org.gradle.api.provider.Property;
import org.gradle.api.reporting.SingleFileReport;

import javax.inject.Inject;

public class TaskGeneratedSingleFileReport extends TaskGeneratedReport implements SingleFileReport {
    // TODO - make this managed properties, once instant execution can deserialize abstract beans
    private final RegularFileProperty outputLocation;
    private final Property<Boolean> activated;

    public TaskGeneratedSingleFileReport(String name, Task task) {
        super(name, OutputType.FILE, task);
        this.outputLocation = getObjectFactory().fileProperty();
        this.activated = getObjectFactory().property(Boolean.class).convention(false);
    }

    @Inject
    protected ObjectFactory getObjectFactory() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Property<Boolean> getActivated() {
        return activated;
    }

    @Override
    public RegularFileProperty getOutputLocation() {
        return outputLocation;
    }
}
