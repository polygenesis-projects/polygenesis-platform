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
      Assertion.isNotNull(message, "message is required");

      BatchProcessMessage batchProcessMessage =
        objectMapper.readValue(message, BatchProcessMessage.class);

      Assertion.isNotNull(batchProcessMessage.getDryRun(), "dryRun flag MUST be specified");

      if (batchProcessMessage.isForFetchingPage()) {
        batchProcessService.fetchPage(batchProcessMessage);
      } else if (batchProcessMessage.isForProcessing()) {
        batchProcessService.processForId(batchProcessMessage);
      } else {
        throw new IllegalArgumentException(String.format("Cannot decode BatchProcessMessage: %s", batchProcessMessage));
      }
    } catch (IOException e) {
      throw new IllegalArgumentException(e.getMessage(), e);
    }