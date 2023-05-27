package com.trestechnologies.api.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

public abstract class ModelMapper<M> extends ObjectMapper {
  public abstract Class<M> getModel ( );
  
  public M treeToValue ( JsonNode node ) throws JsonProcessingException {
    return treeToValue(node, getModel());
  }

  public List<M> treeToList ( JsonNode node ) throws JsonProcessingException {
    return treeToValue(node, getTypeFactory().constructCollectionType(List.class, getModel()));
  }
}
