<#--
 ==========================LICENSE_START=================================
 PolyGenesis Platform
 ========================================================================
 Copyright (C) 2015 - 2019 OREGOR LTD
 ========================================================================
 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at
 
      http://www.apache.org/licenses/LICENSE-2.0
 
 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 ===========================LICENSE_END==================================
-->

package ${ packageName };

import com.oregor.ddd4j.core.AbstractDomainMessageData;
import java.time.LocalDateTime;
import java.util.UUID;
import javax.persistence.Entity;

/**
 * The type Context domain message data.
 *
 * @author PolyGenesis
 */
@Entity
public class ${ simpleObjectName } extends AbstractDomainMessageData {

  private ${ simpleObjectName }() {
    super();
  }

  /**
   * Instantiates a new Some context domain message data.
   *
   * @param id the id
   * @param context the context
   * @param tenantId the tenant id
   * @param objectId the object id
   * @param occurredOn the occurred on
   * @param eventType the event type
   * @param eventVersion the event version
   * @param eventBody the event body
   * @param principal the principal
   * @param ipAddress the ip address
   */
  public ${ simpleObjectName }(
      UUID id,
      String context,
      String tenantId,
      String objectId,
      LocalDateTime occurredOn,
      String eventType,
      Integer eventVersion,
      String eventBody,
      String principal,
      String ipAddress) {
    super(
        id,
        context,
        tenantId,
        objectId,
        occurredOn,
        eventType,
        eventVersion,
        eventBody,
        principal,
        ipAddress);
  }
}
