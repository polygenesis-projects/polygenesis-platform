<#--
 ==========================LICENSE_START=================================
 PolyGenesis Platform
 ========================================================================
 Copyright (C) 2015 - 2019 Christos Tsakostas, OREGOR LTD
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
    try {
      JsonNode jsonNode = objectMapper.readTree(message);

      if (jsonNode.hasNonNull("messageType")) {
        return jsonNode.get("messageType").asText();
      } else if (jsonNode.hasNonNull("eventType")) {
        return jsonNode.get("eventType").asText();
      } else if (jsonNode.hasNonNull("messageName")) {
        return jsonNode.get("messageName").asText();
      }
    } catch (IOException e) {
      throw new IllegalArgumentException(
          String.format("Cannot deserialize message. Error=%s", e.getMessage()), e);
    }
    throw new IllegalArgumentException(String.format("Cannot extract message type from message=%s", message));