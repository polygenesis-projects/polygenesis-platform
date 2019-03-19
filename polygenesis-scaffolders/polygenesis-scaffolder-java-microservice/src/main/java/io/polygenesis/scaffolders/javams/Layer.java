package io.polygenesis.scaffolders.javams;

/**
 * Denotes the layers of the Hexagonal Architecture.
 *
 * @author Christos Tsakostas
 */
public enum Layer {
  APP,
  API,
  API_IMPL,
  DOMAIN_MODEL,
  DOMAIN_SERVICES,
  PROJECTION_MODEL,
  PRIMARY_ADAPTER_REST_SPRING,
  PRIMARY_ADAPTER_REST_CLIENT_SPRING,
  PRIMARY_ADAPTER_SUBSCRIBER_ACTIVEMQ,
  SECONDARY_ADAPTER_PERSISTENCE_SPRING_DATA_JPA,
  SECONDARY_ADAPTER_PUBLISHER_ACTIVEMQ,
}
