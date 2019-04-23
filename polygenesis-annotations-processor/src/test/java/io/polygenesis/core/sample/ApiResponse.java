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

package io.polygenesis.core.sample;

import io.polygenesis.annotations.core.GIgnore;
import java.io.Serializable;
import java.time.LocalDateTime;

// import org.apache.commons.lang3.builder.EqualsBuilder;
// import org.apache.commons.lang3.builder.HashCodeBuilder;
// import org.apache.commons.lang3.builder.ToStringBuilder;

/** @author Christos Tsakostas */
public abstract class ApiResponse implements Serializable {

  protected static final long serialVersionUID = 1L;

  @GIgnore private LocalDateTime occurredOn;

  public ApiResponse() {
    this.occurredOn = LocalDateTime.now();
  }

  public LocalDateTime getOccurredOn() {
    return occurredOn;
  }

  //    @Override
  //    public int hashCode() {
  //        return HashCodeBuilder.reflectionHashCode(this);
  //    }
  //
  //    @Override
  //    public boolean equals(Object obj) {
  //        return EqualsBuilder.reflectionEquals(this, obj);
  //    }
  //
  //
  //    @Override
  //    public String toString() {
  //        return ToStringBuilder.reflectionToString(this);
  //    }
}
