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

-- -------------------------------------------------------------------------------------------------
-- BUSINESS
-- -------------------------------------------------------------------------------------------------

CREATE TABLE `ddd_business` (
  `root_id` BINARY(16) NOT NULL,
  `tenant_id` BINARY(16) NOT NULL,
  `name` VARCHAR(100) NULL,
  `tax_id` VARCHAR(100) NULL,
  `version` int(11) NOT NULL,
  PRIMARY KEY (`root_id`, `tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- -------------------------------------------------------------------------------------------------
-- TODO
-- -------------------------------------------------------------------------------------------------

CREATE TABLE `ddd_todo` (
  `root_id` BINARY(16) NOT NULL,
  `description` VARCHAR(100) NULL,
  `done` BIT(1) NULL,
  `version` int(11) NOT NULL,
  PRIMARY KEY (`root_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- -------------------------------------------------------------------------------------------------
-- DOMAIN MESSAGES
-- -------------------------------------------------------------------------------------------------

CREATE TABLE `${ tablePrefix }domain_message` (
  `id` BINARY(16) NOT NULL,
  `root_id` BINARY(16) NOT NULL,
  `tenant_id` BINARY(16) NULL,
  `stream_version` int(11) NULL,
  `message_name` VARCHAR(512) NOT NULL,
  `message_version` int(11) NOT NULL,
  `message` LONGTEXT NOT NULL,
  `principal` VARCHAR(100) NULL,
  `ip_address` VARCHAR(100) NULL,
  `occurred_on` DATETIME NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
