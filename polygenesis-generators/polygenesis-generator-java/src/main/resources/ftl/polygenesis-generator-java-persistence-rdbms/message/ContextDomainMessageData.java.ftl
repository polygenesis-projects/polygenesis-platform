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

import com.oregor.ddd4j.domain.AbstractDomainMessageData;
import java.time.LocalDateTime;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * The type Context domain message data.
 *
 * @author ${ authorService.getAuthor() }
 */
@Entity
@Table(name = Constants.DEFAULT_TABLE_PREFIX + "domain_message")
public class ${ simpleObjectName } extends AbstractDomainMessageData {

  private ${ simpleObjectName }() {
    super();
  }

  /**
   * Instantiates a new Context domain message data.
   *
   * @param id the id
   * @param rootId the root id
   * @param tenantId the tenant id
   * @param streamVersion the stream version
   * @param messageName the message name
   * @param messageVersion the message version
   * @param message the message
   * @param principal the principal
   * @param ipAddress the ip address
   * @param occurredOn the occurred on
   */
  public ${ simpleObjectName }(
      UUID id,
      UUID rootId,
      UUID tenantId,
      Integer streamVersion,
      String messageName,
      Integer messageVersion,
      String message,
      String principal,
      String ipAddress,
      LocalDateTime occurredOn) {
    super(id, rootId, tenantId, streamVersion, messageName, messageVersion, message, principal,
      ipAddress, occurredOn);
  }
}
