/*
 * Copyright 2018 the original author or authors.
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
package org.gradle.api.internal.artifacts.dsl.dependencies;

import com.google.common.base.Objects;
import org.gradle.api.Action;
import org.gradle.api.attributes.AttributeContainer;
import org.gradle.api.attributes.Category;
import org.gradle.api.attributes.HasConfigurableAttributes;
import org.gradle.api.internal.artifacts.repositories.metadata.MavenImmutableAttributesFactory;
import org.gradle.api.internal.model.NamedObjectInstantiator;
import org.gradle.internal.component.external.model.ComponentVariant;

public class PlatformSupport {
    private final Category regularPlatform;
    private final Category enforcedPlatform;

    public PlatformSupport(NamedObjectInstantiator instantiator) {
        regularPlatform = instantiator.named(Category.class, Category.REGULAR_PLATFORM);
        enforcedPlatform = instantiator.named(Category.class, Category.ENFORCED_PLATFORM);
    }

    public boolean isTargetingPlatform(HasConfigurableAttributes<?> target) {
        Category category = target.getAttributes().getAttribute(Category.CATEGORY_ATTRIBUTE);
        return regularPlatform.equals(category) || enforcedPlatform.equals(category);
    }

    public <T> void addPlatformAttribute(HasConfigurableAttributes<T> dependency, final Category category) {
        dependency.attributes(new Action<AttributeContainer>() {
            @Override
            public void execute(AttributeContainer attributeContainer) {
                attributeContainer.attribute(Category.CATEGORY_ATTRIBUTE, category);
            }
        });
    }

    /**
     * Checks if the variant is an {@code enforced-platform} one.
     * <p>
     * This method is designed to be called on parsed metadata and thus interacts with the {@code String} version of the attribute.
     *
     * @param variant the variant to test
     * @return {@code true} if this represents an {@code enforced-platform}, {@code false} otherwise
     */
    public static boolean hasForcedDependencies(ComponentVariant variant) {
        return Objects.equal(variant.getAttributes().getAttribute(MavenImmutableAttributesFactory.CATEGORY_ATTRIBUTE), Category.ENFORCED_PLATFORM);
    }
}
