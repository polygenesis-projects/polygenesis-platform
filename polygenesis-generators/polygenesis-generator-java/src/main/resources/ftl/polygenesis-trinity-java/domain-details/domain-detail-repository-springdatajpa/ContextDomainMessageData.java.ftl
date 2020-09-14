<#--
 ==========================LICENSE_START=================================
 PolyGenesis Platform
 ========================================================================
 Copyright (C) 2015 - 2020 Christos Tsakostas, OREGOR LP
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

import com.oregor.trinity4j.domain.AbstractDomainMessageData;
import com.oregor.trinity4j.domain.DomainMessageType;
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
   * @param messageId the message id
   * @param occurredOn the occurred on
   * @param rootId the root id
   * @param rootVersion the root version
   * @param messageType the message type
   * @param messageName the message name
   * @param messageBody the message body
   * @param userId the user id
   * @param ipAddress the ip address
   */
  public ${ simpleObjectName }(
      UUID messageId,
      LocalDateTime occurredOn,
      UUID rootId,
      Integer rootVersion,
      DomainMessageType messageType,
      String messageName,
      String messageBody,
      UUID userId,
      String ipAddress) {
    super(messageId, occurredOn, rootId, rootVersion, messageType, messageName, messageBody, userId,
    ipAddress);
  }
}
