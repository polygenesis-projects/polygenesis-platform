#!/usr/bin/env bash

###
# ==========================LICENSE_START=================================
# PolyGenesis Platform
# ========================================================================
# Copyright (C) 2015 - 2019 OREGOR LTD
# ========================================================================
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
# 
#      http://www.apache.org/licenses/LICENSE-2.0
# 
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
# ===========================LICENSE_END==================================
###

abort() {
    echo >&2 '
*************************************************************************
*** You are not ready to commit! Fix the errors above and re-execute. ***
*************************************************************************
'
    echo "An error occurred. Exiting..." >&2
    exit 1
}

trap 'abort' 0

set -e

#########################################
# SCRIPTS_START
#########################################

./mvnw license:update-file-header

./mvnw fmt:format

./mvnw clean install -P validate-license,validate-code-format,validate-code-style,validate-code-bugs,validate-code \
 -DskipTests=true -Dmaven.javadoc.skip=true -B -V

./mvnw test -B

#########################################
# SCRIPTS_END
#########################################

trap : 0

echo >&2 '
***********************************************
*** GOOD JOB! You are ready to commit now. ***
***********************************************
'
