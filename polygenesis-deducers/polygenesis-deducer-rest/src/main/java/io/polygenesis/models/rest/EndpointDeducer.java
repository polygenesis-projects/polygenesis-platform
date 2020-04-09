/*-
 * ==========================LICENSE_START=================================
 * PolyGenesis Platform
 * ========================================================================
 * Copyright (C) 2015 - 2019 Christos Tsakostas, OREGOR LTD
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

package io.polygenesis.models.rest;

import io.polygenesis.abstraction.thing.Purpose;
import io.polygenesis.models.api.Service;
import io.polygenesis.models.api.ServiceMethod;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * The type Endpoint deducer.
 *
 * @author Christos Tsakostas
 */
public class EndpointDeducer {

  // ===============================================================================================
  // STATIC
  // ===============================================================================================
  private static Map<Purpose, HttpMethod> purposeToHttpMethod = new HashMap<>();

  static {
    purposeToHttpMethod.put(Purpose.create(), HttpMethod.POST);
    purposeToHttpMethod.put(Purpose.modify(), HttpMethod.PUT);
    purposeToHttpMethod.put(Purpose.delete(), HttpMethod.DELETE);
    purposeToHttpMethod.put(Purpose.fetchOne(), HttpMethod.GET);
    purposeToHttpMethod.put(Purpose.fetchCollection(), HttpMethod.GET);
    purposeToHttpMethod.put(Purpose.fetchPagedCollection(), HttpMethod.GET);

    purposeToHttpMethod.put(Purpose.entityCreate(), HttpMethod.POST);
    purposeToHttpMethod.put(Purpose.entityModify(), HttpMethod.PUT);
    purposeToHttpMethod.put(Purpose.entityRemove(), HttpMethod.DELETE);
    purposeToHttpMethod.put(Purpose.entityFetch(), HttpMethod.GET);
    purposeToHttpMethod.put(Purpose.entityFetchAll(), HttpMethod.GET);
  }

  // ===============================================================================================
  // DEPENDENCIES
  // ===============================================================================================

  private final MappingDeducer mappingDeducer;
  private final RequestParameterDeducer requestParameterDeducer;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Endpoint deducer.
   *
   * @param mappingDeducer the mapping deducer
   * @param requestParameterDeducer the request parameter deducer
   */
  public EndpointDeducer(
      MappingDeducer mappingDeducer, RequestParameterDeducer requestParameterDeducer) {
    this.mappingDeducer = mappingDeducer;
    this.requestParameterDeducer = requestParameterDeducer;
  }

  // ===============================================================================================
  // FUNCTIONALITY
  // ===============================================================================================

  /**
   * Deduce from function optional endpoint.
   *
   * @param serviceMethod the service method
   * @param service the service
   * @return the optional endpoint
   */
  public Optional<Endpoint> deduceFromServiceMethod(ServiceMethod serviceMethod, Service service) {
    if (purposeToHttpMethod.containsKey(serviceMethod.getFunction().getPurpose())) {
      HttpMethod httpMethod = purposeToHttpMethod.get(serviceMethod.getFunction().getPurpose());

      return Optional.of(
          new Endpoint(
              service,
              serviceMethod,
              httpMethod,
              mappingDeducer.deduceFrom(serviceMethod.getFunction(), httpMethod),
              requestParameterDeducer.deduceFrom()));
    } else {
      throw new UnsupportedOperationException(serviceMethod.getFunction().getPurpose().getText());
      // return Optional.empty();
    }
  }
}
