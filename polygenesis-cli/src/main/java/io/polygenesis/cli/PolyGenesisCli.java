/*-
 * ==========================LICENSE_START=================================
 * STATICFY
 * ========================================================================
 * Copyright (C) 2020 Christos Tsakostas, OREGOR LTD
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

package io.polygenesis.cli;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The type Polygenesis cli.
 *
 * @author Christos Tsakostas
 */
public class PolyGenesisCli {

  // ===============================================================================================
  // LOGGER
  // ===============================================================================================

  private static final Logger LOG = LoggerFactory.getLogger(PolyGenesisCli.class);

  // ===============================================================================================
  // STATIC
  // ===============================================================================================

  private static final String ARGUMENT_GENERATE_REQUEST_FILE = "gen-req-file";
  private static final String ARGUMENT_USE_REQUEST_FILE = "use-req-file";
  private static final String ARGUMENT_PROJECT_NAME = "project";

  private static final ObjectMapper objectMapper = new ObjectMapper();

  private static PolyGenesisRequestFileGenerator polyGenesisRequestFileGenerator =
      new PolyGenesisRequestFileGenerator(objectMapper);

  // ===============================================================================================
  // MAIN
  // ===============================================================================================

  /**
   * The entry point of application.
   *
   * @param args the input arguments
   */
  public static void main(String[] args) {
    LOG.info("*************************************************");
    LOG.info("PolyGenesis CLI");
    LOG.info("*************************************************");

    if (args.length == 0) {
      throw new IllegalStateException("At least one argument must be provided");
    }

    for (int i = 0; i < args.length; ++i) {
      LOG.info("args[{}]: {}", i, args[i]);
    }

    Set<KeyValue> arguments = getArguments(args);

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // GENERATE REQUEST FILE
    ////////////////////////////////////////////////////////////////////////////////////////////////
    Optional<KeyValue> optionalArgumentGenerateRequestFile =
        getArgumentByKey(arguments, ARGUMENT_GENERATE_REQUEST_FILE);

    Optional<KeyValue> optionalProjectName = getArgumentByKey(arguments, ARGUMENT_PROJECT_NAME);

    if (optionalArgumentGenerateRequestFile.isPresent()) {
      Path generationPath =
          Paths.get(System.getProperty("user.dir").replace("/polygenesis-cli", ""));
      String fileName = optionalArgumentGenerateRequestFile.get().getValue();

      PolyGenesisRequest request =
          new PolyGenesisRequest(
              optionalProjectName.isPresent()
                  ? optionalProjectName.get().getValue()
                  : "someProjectName");

      polyGenesisRequestFileGenerator.create(generationPath, fileName, request);
      return;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // PARSE REQUEST FILE AND EXECUTE POLYGENESIS
    ////////////////////////////////////////////////////////////////////////////////////////////////

    Optional<KeyValue> optionalArgumentUseRequestFile =
        getArgumentByKey(arguments, ARGUMENT_USE_REQUEST_FILE);

    if (optionalArgumentUseRequestFile.isPresent()) {}
  }

  // ===============================================================================================
  // PRIVATE
  // ===============================================================================================

  private static Set<KeyValue> getArguments(String[] args) {
    return Arrays.stream(args)
        .map(
            arg -> {
              String[] array = arg.split("=");
              if (array.length != 2) {
                throw new IllegalArgumentException(String.format("Illegal Argument: %s", arg));
              }

              return new KeyValue(array[0].trim(), array[1].trim());
            })
        .collect(Collectors.toSet());
  }

  private static Optional<KeyValue> getArgumentByKey(Set<KeyValue> arguments, String key) {
    return arguments.stream()
        .filter(argumentInStream -> argumentInStream.getKey().equals(key))
        .findFirst();
  }
}
