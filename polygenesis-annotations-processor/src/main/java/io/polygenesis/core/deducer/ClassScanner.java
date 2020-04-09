/*-
 * ==========================LICENSE_START=================================
 * PolyGenesis Platform
 * ========================================================================
 * Copyright (C) 2015 - 2019 Christos Tsakostas, OREGOR LP
 * ========================================================================
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
 * ===========================LICENSE_END==================================
 */

package io.polygenesis.core.deducer;

import java.util.LinkedHashSet;
import java.util.Set;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.classreading.MetadataReader;

/**
 * The type Class scanner.
 *
 * @author Christos Tsakostas
 */
public class ClassScanner {

  // ===============================================================================================
  // FUNCTIONALITY
  // ===============================================================================================

  /**
   * Identify set.
   *
   * @param packages the packages
   * @param interfaces the interfaces
   * @param inclusionOrExclusionType the inclusion or exclusion type
   * @return the set
   */
  public Set<Class<?>> identify(
      Set<String> packages,
      Set<String> interfaces,
      InclusionOrExclusionType inclusionOrExclusionType) {
    Set<Class<?>> discoveredInterfaces = new LinkedHashSet<>();

    ClassPathScanningCandidateComponentProvider provider =
        new ClassPathScanningCandidateComponentProvider(false) {
          @Override
          protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
            return true;
          }

          @Override
          protected boolean isCandidateComponent(MetadataReader metadataReader) {
            return metadataReader.getClassMetadata().isInterface();
          }
        };

    packages.forEach(
        basePackage -> {
          Set<BeanDefinition> beans = provider.findCandidateComponents(basePackage);
          examineBeanDefinitions(discoveredInterfaces, beans, interfaces, inclusionOrExclusionType);
        });

    return discoveredInterfaces;
  }

  // ===============================================================================================
  // PRIVATE
  // ===============================================================================================

  private void examineBeanDefinitions(
      Set<Class<?>> discoveredInterfaces,
      Set<BeanDefinition> beans,
      Set<String> interfaces,
      InclusionOrExclusionType inclusionOrExclusionType) {
    try {
      for (BeanDefinition beanDefinition : beans) {
        Class<?> clazz = Class.forName(beanDefinition.getBeanClassName());

        if (inclusionOrExclusionType.equals(InclusionOrExclusionType.INCLUDE)) {
          if (isInterfaceIncluded(interfaces, clazz.getCanonicalName())) {
            discoveredInterfaces.add(clazz);
          }
        } else if (inclusionOrExclusionType.equals(InclusionOrExclusionType.EXCLUDE)) {
          if (!isInterfaceExcluded(interfaces, clazz.getCanonicalName())) {
            discoveredInterfaces.add(clazz);
          }
        } else {
          // By default add all
          discoveredInterfaces.add(clazz);
        }
      }
    } catch (ClassNotFoundException e) {
      throw new IllegalStateException(e.getMessage(), e);
    }
  }

  private boolean isInterfaceIncluded(Set<String> includedInterfaces, String interfaceName) {
    return includedInterfaces != null && includedInterfaces.contains(interfaceName);
  }

  private boolean isInterfaceExcluded(Set<String> excludedInterfaces, String interfaceName) {
    return excludedInterfaces != null && excludedInterfaces.contains(interfaceName);
  }
}
